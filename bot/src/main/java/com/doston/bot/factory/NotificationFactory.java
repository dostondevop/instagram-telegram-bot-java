package com.doston.bot.factory;

import com.doston.enumeration.NotificationType;
import com.doston.model.Notification;

import java.util.Date;
import java.util.UUID;

public class NotificationFactory {
    public static Notification buildNotification(UUID clpId, UUID userId, NotificationType notificationType, String username) {
        return Notification.builder()
                .id(UUID.randomUUID())
                .clpId(clpId)
                .notificationType(notificationType)
                .userId(userId)
                .username(username)
                .createdDate(new Date())
                .build();
    }
}
