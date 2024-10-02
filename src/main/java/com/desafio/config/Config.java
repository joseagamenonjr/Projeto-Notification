package com.desafio.config;

import com.desafio.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import com.desafio.services.NotificationService;

@Configuration
public class Config implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private NotificationService notificacaoService;

    @Override
    public void run(String... args) throws Exception {
//        User u1 = new User(null, "Jose Agamenon", "jose@gmail.com", "9999999", "$2a$10$V4Y.UIliCoHzmldd5DW8H.LE4qoVceOGOiGDNJ3r9ZsQcYuNUjwOG", "ze", UserRole.ADMIN);
//        User u2 = new User(null, "Margot", "jose@gmail.com", "999999", "$2a$10$V4Y.UIliCoHzmldd5DW8H.LE4qoVceOGOiGDNJ3r9ZsQcYuNUjwOG", "margot", UserRole.ADMIN);
//        User u3 = new User(null, "Maria", "jose@gmail.com", "999999", "$2a$10$V4Y.UIliCoHzmldd5DW8H.LE4qoVceOGOiGDNJ3r9ZsQcYuNUjwOG", "maria", UserRole.USER);
//
//        Order o1 = new Order(null, LocalDateTime.of(2019, 6, 20, 19, 53, 07), Status.PAID, u1);
//        Order o2 = new Order(null, LocalDateTime.of(2019, 7, 21, 3, 42, 10), Status.WAITING_PAYMENT, u2);
//        Order o3 = new Order(null, LocalDateTime.of(2019, 6, 20, 19, 53, 07), Status.PAID, u1);
//
//
//        Category cat1 = new Category(null, "Electronicos");
//        Category cat2 = new Category(null, "Celulares");
//
//        Product p1 = new Product(null, "IPhone 15 pro Max", "IPhone 15 pro Max 256Gb", 9000.0, "www.jose.com.br/iphone15");
//        Product p2 = new Product(null, "Smart TV Samsung 50'", "Smart TV Samsung 50' OLED", 4390.0, "www.jose.com.br/tvsamgung");
//
//        userRepository.saveAll(Arrays.asList(u1, u2, u3));
//        orderRepository.saveAll(Arrays.asList(o1, o2, o3));
//        categoryRepository.saveAll(Arrays.asList(cat1, cat2));
//        productRepository.saveAll(Arrays.asList(p1, p2));
//
//        p1.getCategories().add(cat2);
//        p2.getCategories().add(cat1);
//        productRepository.saveAll(Arrays.asList(p1, p2));
//
//        // Adicionando itens de pedido para o1
//        OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
//        OrderItem oi2 = new OrderItem(o1, p2, 1, p2.getPrice());
//        OrderItem oi4 = new OrderItem(o2, p2, 1, p2.getPrice());
//        orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi4));
//
//         OrderItem oi3 = new OrderItem(o2, p1, 1, p1.getPrice());
//        orderItemRepository.save(oi3); // Salva o item do segundo pedido
//
//        // Criando pagamentos
//        Payment pay1 = new Payment(null, Instant.parse("2019-06-20T21:53:07Z"), o1);
//        Payment pay3 = new Payment(null, Instant.parse("2019-07-21T03:42:10Z"), o3);
//        o1.setPayment(pay1);
//        o3.setPayment(pay3);
//
//        orderRepository.save(o1);
//        orderRepository.save(o3);
    }
}
