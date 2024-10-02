package com.desafio.resources.dto;

import java.time.LocalDateTime;

public class NotificationScheduleDTO {
    private Long id;
    private Long userId;  
    private Long orderId;
    private LocalDateTime scheduledTime;
    private boolean optOut;
    private String status;

    public NotificationScheduleDTO(Long userId, Long orderId, LocalDateTime scheduledTime, boolean optOut, String status) {
        if (userId == null || orderId == null) {
            throw new IllegalArgumentException("userId e orderId não podem ser nulos");
        }
        this.userId = userId;
        this.orderId = orderId;
        this.scheduledTime = scheduledTime;
        this.optOut = optOut;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public boolean isOptOut() {
        return optOut;
    }

    public void setOptOut(boolean optOut) {
        this.optOut = optOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
