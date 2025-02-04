package com.zuhriddin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zuhriddin.model.Chat;
import com.zuhriddin.service.util.JsonUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChatService implements BaseService<Chat, UUID> {

    private static final String PATH = "instagram/src/main/resources/file_resources/chats.json";

    @Override
    public boolean has(List<Chat> list, Chat chat) {
        return list.stream()
                .anyMatch(chat1 -> (chat1.getUser1Id().equals(chat.getUser1Id()) &&
                        chat1.getUser2Id().equals(chat.getUser2Id())) || (chat1.getUser1Id().equals(chat.getUser2Id()) &&
                        chat1.getUser2Id().equals(chat.getUser1Id())));
    }

    public boolean hasChat(UUID user1Id, UUID user2Id) {
        return read().stream()
                .anyMatch(chat -> (chat.getUser1Id().equals(user1Id) && chat.getUser2Id().equals(user2Id)) ||
                        (chat.getUser2Id().equals(user1Id) && chat.getUser1Id().equals(user2Id)));
    }

    public Chat getChatByUser1IdAndUser2Id(UUID user1Id, UUID user2Id) {
        return read().stream()
                .filter(chat -> (chat.getUser1Id().equals(user1Id) && chat.getUser2Id().equals(user2Id)) ||
                        (chat.getUser2Id().equals(user1Id) && chat.getUser1Id().equals(user2Id)))
                .findFirst()
                .orElse(null);
    }

    public List<UUID> getToUsersIdsByUserId(UUID userId) {
        return read().stream()
                .filter(chat -> chat.getUser1Id().equals(userId) || chat.getUser2Id().equals(userId))
                .map(chat -> {
                    if (chat.getUser1Id().equals(userId)) {
                        return chat.getUser2Id();
                    } else {
                        return chat.getUser1Id();
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public List<Chat> read() {
        return JsonUtil.read(PATH, new TypeReference<>() {});
    }

    @Override
    public void write(List<Chat> list) {
        JsonUtil.write(PATH, list);
    }
}
