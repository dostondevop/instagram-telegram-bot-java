package com.doston.bot.factory;

import com.doston.enumeration.PostType;
import com.doston.model.Message;

import java.util.Date;
import java.util.UUID;

public class MessageFactory {
    public static Message buildMessage(String text, UUID userId, UUID chatId, String path, PostType postType) {
        return Message.builder()
                .id(UUID.randomUUID())
                .text(text)
                .fromId(userId)
                .toChatId(chatId)
                .path(path)
                .postType(postType)
                .createdDate(new Date())
                .build();
    }
}
