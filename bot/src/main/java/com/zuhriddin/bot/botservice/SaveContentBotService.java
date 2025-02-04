package com.zuhriddin.bot.botservice;

import com.zuhriddin.model.SaveContent;
import com.zuhriddin.model.User;
import com.zuhriddin.service.PostService;
import com.zuhriddin.service.SaveContentService;
import com.zuhriddin.service.UserService;

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
