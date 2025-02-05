package com.doston.bot.botservice;

import com.doston.model.SaveContent;
import com.doston.model.User;
import com.doston.service.PostService;
import com.doston.service.SaveContentService;
import com.doston.service.UserService;

import java.util.UUID;

public class SaveContentBotService {
    private static final PostService postService = new PostService();
    private static final UserService userService = new UserService();
    private static final SaveContentService saveContentService = new SaveContentService();

    public void passSavaContent(Long chatId, UUID postId) {
        User user = userService.getUserByChatId(chatId);
        SaveContent saveContent = SaveContent.builder()
                .id(UUID.randomUUID())
                .userId(user.getId())
                .postId(postId)
                .build();
        saveContentService.add(saveContent);
    }

}
