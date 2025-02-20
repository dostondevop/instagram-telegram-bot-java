package com.doston.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.doston.model.Notification;
import com.doston.service.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

public class NotificationService implements BaseService<Notification, UUID> {
    private static final String PATH = "instagram/src/main/resources/file_resources/notifications.json";

    @Override
    public Notification add(Notification notification) {
        List<Notification> notifications = read();
        notifications.add(notification);
        write(notifications);
        return notification;
    }

    public List<Notification> notificationList(UUID userId) {
        return read().stream()
                .filter(n -> n.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Notification getNotificationByCLPIdAndUserId(UUID clpId, UUID userId) {
        return read().stream()
                .filter(n -> n.getClpId().equals(clpId) && n.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public int countOfNotifications(UUID userId) {
        return notificationList(userId).size();
    }

    @Override
    public List<Notification> read() {
        return JsonUtil.read(PATH, new TypeReference<>() {});
    }

    @Override
    public void write(List<Notification> list) {
        JsonUtil.write(PATH, list);
    }
}