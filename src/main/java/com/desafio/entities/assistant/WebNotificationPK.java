package com.desafio.entities.assistant;

import com.desafio.entities.User;

public class WebNotificationPK extends NotificationPK {
    public WebNotificationPK(String message, User recipient) {
        super(message, recipient);
    }

    @Override
    public void send() {
        System.out.println("Enviando notificação para " + recipient.getEmail() + ": " + message);
    }
}
