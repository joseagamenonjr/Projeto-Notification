package com.desafio.entities.assistant;

import com.desafio.entities.User;

public abstract class NotificationPK {
    protected String message;
    protected User recipient;

    public NotificationPK(String message, User recipient) {
        this.message = message;
        this.recipient = recipient;
    }

    public abstract void send();
}
