package com.zuhriddin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zuhriddin.model.Comment;
import com.zuhriddin.service.util.JsonUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommentService implements BaseService<Comment, UUID> {

    private static final String PATH = "instagram/src/main/resources/file_resources/comments.json";

    @Override
    public Comment add(Comment comment) {
        List<Comment> list = read();
        list.add(comment);
        write(list);
        return comment;
    }

    public Comment update(Comment comment) {
        List<Comment> comments = read();
        comments.stream()
                .filter(comment1 -> comment1.getId().equals(comment.getId()))
                .findFirst()
                .ifPresent(comment1 -> {
                    comment1.setText(comment.getText());
                });
        write(comments);
        return comment;
    }

    public List<Comment> listMainComment(UUID postId) {
        List<Comment> comments = read();
        return comments.stream()
                .filter(comment1 -> comment1.getPostId().equals(postId) && comment1.getParentId() == null)
                .collect(Collectors.toList());
    }

    public List<Comment> listSubCommentsByCommentId(UUID commentId, UUID postId) {
        List<Comment> comments = read();
        return comments.stream()
                .filter(comment -> commentId.equals(comment.getParentId()) && comment.getPostId().equals(postId))
                .collect(Collectors.toList());
    }

    public List<Comment> listPostComments(UUID postId) {
        return read().stream()
                .filter(comment -> comment.getPostId().equals(postId))
                .collect(Collectors.toList());
    }

    public int numberOfPostComments(UUID postId) {
        return listPostComments(postId).size();
    }

    @Override
    public List<Comment> read() {
        return JsonUtil.read(PATH, new TypeReference<>() {});
    }

    @Override
    public void write(List<Comment> list) {
        JsonUtil.write(PATH, list);
    }
}
