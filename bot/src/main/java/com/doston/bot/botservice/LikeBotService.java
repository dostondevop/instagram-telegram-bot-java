package com.doston.bot.botservice;

import com.doston.bot.factory.LikeFactory;
import com.doston.bot.factory.NotificationFactory;
import com.doston.enumeration.NotificationType;
import com.doston.model.Like;
import com.doston.model.Notification;
import com.doston.model.Post;
import com.doston.model.User;
import com.doston.service.LikeService;
import com.doston.service.NotificationService;
import com.doston.service.PostService;
import com.doston.service.UserService;

import java.util.UUID;

public class LikeBotService {
    private static final LikeService likeService = new LikeService();
    private static final UserService userService = new UserService();
    private static final PostService postService = new PostService();
    private static final NotificationService notificationService = new NotificationService();

    public void passLike(Long chatId, UUID postId) {
        Post post = postService.get(postId);
        User toUser = userService.get(post.getUserId());
        User user = userService.getUserByChatId(chatId);

        Like like = LikeFactory.buildLike(user.getId(), user.getUsername(), postId);
        likeService.add(like);
        Notification notification = NotificationFactory.buildNotification(like.getId(), toUser.getId(), NotificationType.LIKE, user.getUsername());
        notificationService.add(notification);
    }

    public void deleteLike(Long chatId, UUID postId) {
        User user = userService.getUserByChatId(chatId);
        Like like = likeService.getLikeByUserIdAndPostId(user.getId(), postId);
        likeService.delete(like.getId());
        Notification notification = notificationService.getNotificationByCLPIdAndUserId(postId, user.getId());
        if (notification != null) {
            notificationService.delete(notification.getId());
        }
    }
}