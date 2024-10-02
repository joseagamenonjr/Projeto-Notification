package com.desafio.resources;

import com.desafio.resources.dto.NotificationScheduleDTO;
import com.desafio.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificacoes")
public class NotificationResources {

    @Autowired
    private NotificationService notificationService;

    // Endpoint para criar um novo agendamento
    @PostMapping("/schedule")
    public ResponseEntity<String> scheduleNotification(@RequestBody NotificationScheduleDTO notificationSchedule) {
        notificationService.criarNotificacao(
                notificationSchedule.getUserId(),
                notificationSchedule.getOrderId(),
                notificationSchedule.getScheduledTime(),
                notificationSchedule.isOptOut()
        );
        return ResponseEntity.ok("Notificação agendada com sucesso");
    }

    // Endpoint para cancelar uma notificação
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancelNotification(@PathVariable Long id) {
        notificationService.cancelNotification(id);
        return ResponseEntity.ok("Notificação cancelada com sucesso");
    }

    // Endpoint para listar notificações agendadas
    @GetMapping("/scheduled")
    public ResponseEntity<List<NotificationScheduleDTO>> getScheduledNotifications() {
        return ResponseEntity.ok(notificationService.getScheduledNotifications());
    }
}
