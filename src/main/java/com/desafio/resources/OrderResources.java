package com.desafio.resources;

import com.desafio.entities.Order;
import com.desafio.entities.OrderItem;
import com.desafio.entities.Product;
import com.desafio.entities.User;
import com.desafio.entities.enums.Status;
import com.desafio.exception.ResourceNotFoundException;
import com.desafio.repositories.OrderItemRepository;
import com.desafio.repositories.ProductRepository;
import com.desafio.repositories.UserRepository;
import com.desafio.services.NotificationService;
import com.desafio.services.OrderService;
import com.desafio.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderResources {

    @Autowired
    private OrderService service;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Order>> findAll(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByLogin(userDetails.getUsername());
        List<Order> list = service.findByClientId(user.getId());
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails) {
        Order order = service.findById(id);
        if (order != null) {
            User user = order.getClient();
            User client = order.getClient();

            boolean isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

            if (!isAdmin && !client.getUsername().equals(userDetails.getUsername())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            if (!user.isOptOut()) {
                String notificationMessage = notificationService.enviarNotificacao(order);
                order.setNotificationMessage(notificationMessage);
            } else {
                order.setNotificationMessage(null);
            }
            return ResponseEntity.ok().body(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Order> register(@RequestBody @Valid Order order, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByLogin(userDetails.getUsername());
        if (user.isOptOut()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        order.setClient(user);
        order.setStatus(Status.PENDING);
        order.setMoment(LocalDateTime.now());

        double total = 0.0;

        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

            item.setProduct(product);
            item.setPrice(product.getPrice());
            total += item.getSubTotal();
        }

        order.setTotal(total);
        order = service.save(order);

        if (!user.isOptOut()) {
            String notificationMessage = notificationService.enviarNotificacao(order);
            order.setNotificationMessage(notificationMessage);
        }

        for (OrderItem item : order.getItems()) {
            item.setOrder(order);
            orderItemRepository.save(item);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
