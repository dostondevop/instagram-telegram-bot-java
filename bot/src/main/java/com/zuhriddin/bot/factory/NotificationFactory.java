package com.zuhriddin.bot.factory;

import com.zuhriddin.enumeration.NotificationType;
import com.zuhriddin.model.Notification;
import com.zuhriddin.model.User;

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
