package com.desafio.entities.assistant;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotificationScheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void scheduleNotification(NotificationPK notificationPK, long delay, TimeUnit timeUnit) {
        scheduler.schedule(notificationPK::send, delay, timeUnit);
    }
}
