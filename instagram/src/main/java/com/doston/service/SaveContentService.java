package com.doston.service;

import java.util.List;
import java.util.UUID;
import java.util.Objects;
import com.doston.model.Post;
import java.util.stream.Collectors;
import com.doston.model.SaveContent;
import com.doston.service.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;

public class SaveContentService implements BaseService<SaveContent, UUID> {
    private static final String PATH = "instagram/src/main/resources/file_resources/save_contents.json";
    private static final PostService postService = new PostService();

    @Override
    public boolean has(List<SaveContent> list, SaveContent saveContent) {
        return list.stream()
                .anyMatch(saveContent1 -> saveContent1.getUserId().equals(saveContent.getUserId()) &&
                saveContent1.getPostId().equals(saveContent.getPostId()));
    }

    public List<Post> listSaveContends(UUID userId) {
        return read().stream()
                .filter(saveContent -> saveContent.getUserId().equals(userId))
                .map(saveContent -> postService.get(saveContent.getPostId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public boolean has(UUID userId, UUID postId) {
        return read().stream()
                .anyMatch(saveContent -> saveContent.getUserId().equals(userId) &&
                        saveContent.getPostId().equals(postId));
    }

    @Override
    public List<SaveContent> read() {
        return JsonUtil.read(PATH, new TypeReference<>() {});
    }

    @Override
    public void write(List<SaveContent> list) {
        JsonUtil.write(PATH, list);
    }
}