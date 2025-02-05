package com.doston.bot.factory;

import com.doston.model.Comment;
import com.doston.model.User;

import java.util.Date;
import java.util.UUID;

public class CommentFactory {
    public static Comment buildComment(User user, UUID postId, UUID parentId, String text, int stepHierarchyOfParent) {
        return Comment.builder()
                .id(UUID.randomUUID())
                .userId(user.getId())
                .username(user.getUsername())
                .postId(postId)
                .parentId(parentId)
                .text(text)
                .stepHierarchy(stepHierarchyOfParent + 1)
                .createdDate(new Date())
                .build();
    }
}
