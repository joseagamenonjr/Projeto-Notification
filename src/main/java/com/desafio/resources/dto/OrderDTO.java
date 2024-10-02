package com.desafio.resources.dto;

import com.desafio.entities.Order;

public class OrderDTO {
    private Order order;
    private String notificationMessage;

    public OrderDTO(Order order, String notificationMessage) {
        this.order = order;
        this.notificationMessage = notificationMessage;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }
}
