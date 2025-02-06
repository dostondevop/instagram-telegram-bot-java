package com.doston.bot.botservice;

import com.doston.bot.botutil.BotUtil;
import com.doston.bot.factory.CommentFactory;
import com.doston.bot.factory.NotificationFactory;
import com.doston.enumeration.NotificationType;
import com.doston.model.Comment;
import com.doston.model.Notification;
import com.doston.model.Post;
import com.doston.model.User;
import com.doston.service.CommentService;
import com.doston.service.NotificationService;
import com.doston.service.PostService;
import com.doston.service.UserService;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.*;
import java.util.UUID;

public class CommentBotService {
    private static final CommentService commentService = new CommentService();
    private static final UserService userService = new UserService();
    private static final PostService postService = new PostService();
    private static final NotificationService notificationService = new NotificationService();

    private static final Map<Long, Map<Integer, Comment>> messageIdMap = new HashMap<>();
    private static final Map<Long, Map<UUID, List<Comment>>> commentMap = new HashMap<>();
    private static final Map<Long, UUID> postIdMap = new HashMap<>();

    public UUID getPostId(Long chatId) {
        return postIdMap.get(chatId);
    }

    public List<Comment> getSubCommentsList(Long chatId, UUID commentId) {
        return commentMap.get(chatId).get(commentId);
    }

    public void setSubCommentsList(Long chatId, UUID commentId, List<Comment> comments) {
        Map<UUID, List<Comment>> map = commentMap.get(chatId);
        map.put(commentId, comments);
        commentMap.put(chatId, map);
    }
    public void deleteSubCommentsList(Long chatId) {
        commentMap.remove(chatId);
    }

    public void setMessageId(Long chatId, Integer messageId, Comment comment) {
        Map<Integer, Comment> map = messageIdMap.getOrDefault(chatId, new HashMap<>());
        map.put(messageId, comment);
        messageIdMap.put(chatId, map);
    }

    public void deleteMessageIds(Long chatId) {
        messageIdMap.remove(chatId);
    }

    public void setMap(Long chatId, UUID postId) {
        postIdMap.put(chatId, postId);
        List<Comment> list = commentService.list();
        Map<UUID, List<Comment>> map = commentMap.getOrDefault(chatId, new HashMap<>());

        for (Comment comment : list) {
            List<Comment> comments = commentService.listSubCommentsByCommentId(comment.getId(), postId);
            if (!comments.isEmpty()) {
                map.put(comment.getId(), comments);
            }
        }
        commentMap.put(chatId, map);
    }

    public SendMessage constructComment(Long chatId, Comment comment) {
        String text = comment.getStepHierarchy() + ". " + (comment.getStepHierarchy() == 1 ? "" : "\t\t\t\t\t\t".repeat(comment.getStepHierarchy() - 1)) +
                "*" + comment.getUsername() + ": * \t" + comment.getText() + "\t" + comment.getCreatedDate();
        SendMessage sendMessage = BotUtil.buildSendMessage(chatId, text);
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        return sendMessage;
    }

    public Comment getCommentById(UUID commentId) {
        return commentService.get(commentId);
    }

    public List<Comment> mainComments(UUID postId) {
        return commentService.listMainComment(postId);
    }

    public void saveComment(Long chatId, String text, Integer replyMessageId) {
        User user = userService.getUserByChatId(chatId);
        UUID postId = postIdMap.get(chatId);
        Post post = postService.get(postId);
        User toUser = userService.get(post.getUserId());
        Comment parentComment = null;
        if (replyMessageId != null) {
            parentComment = messageIdMap.get(chatId).get(replyMessageId);
        }

        Comment comment = CommentFactory.buildComment(user, postId,
                (parentComment == null ? null : parentComment.getId()), text,
                (parentComment == null ? 0 : parentComment.getStepHierarchy()));
        commentService.add(comment);
        Notification notification = NotificationFactory.buildNotification(comment.getId(), toUser.getId(), NotificationType.COMMENT, user.getUsername());
        notificationService.add(notification);
    }

    public List<Integer> listMessageIds(Long chatId) {
        Map<Integer, Comment> map = messageIdMap.get(chatId);
        List<Integer> messageIdList = new ArrayList<>();
        for (Map.Entry<Integer, Comment> messageId: map.entrySet()) {
            messageIdList.add(messageId.getKey());
        }
        return messageIdList;
    }

    public void deleteCommentFromMap (Long chatId) {
        messageIdMap.remove(chatId);
        commentMap.remove(chatId);
        postIdMap.remove(chatId);
    }
}