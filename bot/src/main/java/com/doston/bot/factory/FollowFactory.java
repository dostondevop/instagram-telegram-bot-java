package com.doston.bot.factory;

import java.util.Date;
import java.util.UUID;
import com.doston.model.Follow;

public class FollowFactory {
    public static Follow buildFollow(UUID followerId, UUID followingId) {
        return Follow.builder()
                .id(UUID.randomUUID())
                .followerId(followerId)
                .followingId(followingId)
                .createdTime(new Date())
                .build();
    }
}