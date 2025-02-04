package com.zuhriddin.bot.factory;

import com.zuhriddin.model.Like;

import java.util.Date;
import java.util.UUID;

public class LikeFactory {
    public static Like buildLike(UUID userId, String username, UUID postId) {
        return Like.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .username(username)
                .postId(postId)
                .createdDate(new Date())
                .build();
    }
}
