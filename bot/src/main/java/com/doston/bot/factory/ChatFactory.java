package com.doston.bot.factory;

import com.doston.model.Chat;

import java.util.UUID;

public class ChatFactory {
    public static Chat buildChat(UUID user1Id, UUID user2Id) {
        return Chat.builder()
                .id(UUID.randomUUID())
                .user1Id(user1Id)
                .user2Id(user2Id)
                .build();
    }
}
