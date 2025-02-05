package com.doston.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.doston.model.Like;
import com.doston.service.util.JsonUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LikeService implements BaseService<Like, UUID> {
    private static final PostService postService = new PostService();
    private static final String PATH = "instagram/src/main/resources/file_resources/likes.json";

    @Override
    public boolean has(List<Like> list, Like like) {
        return list.stream()
                .anyMatch(like1 -> like1.getUserId().equals(like.getUserId()) &&
                        like1.getPostId().equals(like.getPostId()));
    }

    public boolean has(UUID userId, UUID postId) {
        return read().stream()
                .anyMatch(like -> like.getUserId().equals(userId) && like.getPostId().equals(postId));
    }

    public List<Like> listOfPostLikes(UUID postId) {
        return read().stream()
                .filter(like -> like.getPostId().equals(postId))
                .collect(Collectors.toList());
    }

    public int numberOfPostLikes(UUID postId) {
        return listOfPostLikes(postId).size();
    }

    public Like getLikeByUserIdAndPostId(UUID userId, UUID postId) {
        return read().stream()
                .filter(n -> n.getPostId().equals(postId) && n.getUserId().equals(userId))
                .findFirst().orElseThrow();
    }

    @Override
    public List<Like> read() {
        return JsonUtil.read(PATH, new TypeReference<>() {});
    }

    @Override
    public void write(List<Like> list) {
        JsonUtil.write(PATH, list);
    }
}
