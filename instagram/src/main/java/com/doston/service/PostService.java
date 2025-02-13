package com.doston.service;

import java.util.Map;
import java.util.UUID;
import java.util.List;
import com.doston.model.Post;
import com.doston.model.Follow;
import java.util.stream.Collectors;
import com.doston.service.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

public class PostService implements BaseService<Post, UUID> {
    private static final String PATH = "instagram/src/main/resources/file_resources/posts.json";

    @Override
    public boolean has(List<Post> list, Post post) {
        return list.stream()
                .anyMatch(post1 -> post1.getUserId().equals(post.getUserId()) &&
                        post1.getTitle().equalsIgnoreCase(post.getTitle()));
    }

    public List<Post> listMyPosts(UUID userId) {
        List<Post> posts = read();
        return posts.stream()
                .filter(post -> post.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public int countOfMyPosts(UUID userId) {
        return listMyPosts(userId).size();
    }

    public List<Post> listOthersPosts(UUID userId) {
        List<Post> posts = read();
        return posts.stream()
                .filter(post -> !post.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Post> listMyFollowingsPosts(Map<UUID, Follow> map) {
        List<Post> posts = read();

        return posts.stream()
                .filter(post -> map.get(post.getUserId()) != null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> read() {
        return JsonUtil.read(PATH, new TypeReference<>() {});
    }

    @Override
    public void write(List<Post> list) {
        JsonUtil.write(PATH, list);
    }
}