package com.desafio.services;

import com.desafio.entities.*;
import com.desafio.exception.ResourceNotFoundException;
import com.desafio.repositories.ProductRepository;
import com.desafio.repositories.UserRepository;
import com.desafio.repositories.NotificationRepository;
import com.desafio.resources.dto.NotificationScheduleDTO;
import com.desafio.entities.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableAsync
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

//    @Autowired
//    private EmailService emailService;

    @Autowired
    private ProductRepository productRepository;

    public String enviarNotificacao(Order order) {
        User recipient = order.getClient();
        String message = createMessage(order);

        if (recipient.isOptOut()) {
            System.out.println("Usuário optou por não receber notificações.");
            return "Notificação não enviada, pois o usuário optou por não receber.";
        }

//        emailService.enviarEmailTexto(recipient.getName(),"deu certo","deu certo");
        sendNotification(recipient, message);
        order.setNotificationMessage(message);
        return message;
    }

    public void criarNotificacao(Long userId, Long orderId, LocalDateTime scheduledTime, boolean optOut) {
        String status = "Scheduled";
        NotificationScheduleDTO scheduleDTO = new NotificationScheduleDTO(userId, orderId, scheduledTime, optOut, status);
        scheduleNotification(scheduleDTO);
    }

    public void scheduleNotification(NotificationScheduleDTO scheduleDTO) {
        User user = userRepository.findById(scheduleDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setOrderId(scheduleDTO.getOrderId());
        notification.setScheduledTime(scheduleDTO.getScheduledTime());
        notification.setOptOut(scheduleDTO.isOptOut());
        notification.setStatus(scheduleDTO.getStatus());

        notificationRepository.save(notification);
    }

    public void cancelNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notificação não encontrada"));
        notification.setStatus("Canceled");
        notificationRepository.save(notification);
    }

    @Scheduled(fixedRate = 60000)
    public void checkScheduledNotifications() {
        LocalDateTime now = LocalDateTime.now();
        List<Notification> notifications = notificationRepository.findAll();

        for (Notification notification : notifications) {
            if (notification.getScheduledTime().isBefore(now) && !notification.isOptOut() && "Scheduled".equals(notification.getStatus())) {
                sendScheduledNotification(notification);
                notification.setStatus("Sent");
                notificationRepository.save(notification);
            }
        }
    }

    private void sendScheduledNotification(Notification notification) {
        Order order = orderService.findById(notification.getOrderId());
        if (order != null) {
            enviarNotificacao(order);
        } else {
            System.out.println("Pedido não encontrado para ID: " + notification.getOrderId());
        }
    }

    private String createMessage(Order order) {

        String adress = "";

        if (order.getOrderStatus() == Status.PAID) {
            return "Seu pagamento foi processado! Seu pedido chegará em 5 dias!";
        } else if (order.getOrderStatus() == Status.WAITING_PAYMENT) {
            for (OrderItem item : order.getItems()) {
                Product product = productRepository.findById(item.getProduct().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
                adress = product.getImgUrl();
                break;
            }
            return "Seu pedido está aguardando pagamento. Veja o produto aqui: " + adress;
        } else {
            return "Status do pedido: " + order.getOrderStatus();
        }
    }

    public List<Notification> getAllScheduledNotifications() {
        return notificationRepository.findAll(); 
    }

    private void sendNotification(User recipient, String message) {
//        emailService.enviarEmailTexto(recipient.getEmail(),"COMPRA APROVADA", message);
        System.out.println("Enviando notificação para " + recipient.getEmail() + ": " + message);
    }

    public List<NotificationScheduleDTO> getScheduledNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private NotificationScheduleDTO convertToDTO(Notification notification) {
        return new NotificationScheduleDTO(
                notification.getUser().getId(),
                notification.getOrderId(),
                notification.getScheduledTime(),
                notification.isOptOut(),
                notification.getStatus()
        );
    }

}
