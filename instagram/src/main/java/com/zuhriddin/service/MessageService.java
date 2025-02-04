package com.zuhriddin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zuhriddin.model.Message;
import com.zuhriddin.service.util.JsonUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MessageService implements BaseService<Message, UUID> {

    private static final String PATH = "instagram/src/main/resources/file_resources/messages.json";

    @Override
    public Message add(Message message) {
        List<Message> messages = read();
        messages.add(message);
        write(messages);
        return message;
    }

    public List<Message> getMessageListByChatId(UUID chatId) {
        return read().stream()
                .filter(message -> message.getToChatId().equals(chatId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> read() {
        return JsonUtil.read(PATH, new TypeReference<>() {});
    }

    @Override
    public void write(List<Message> list) {
        JsonUtil.write(PATH, list);
    }
}
