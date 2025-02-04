package com.zuhriddin.bot.botservice;

import com.zuhriddin.bot.factory.LikeFactory;
import com.zuhriddin.bot.factory.NotificationFactory;
import com.zuhriddin.enumeration.NotificationType;
import com.zuhriddin.model.Like;
import com.zuhriddin.model.Notification;
import com.zuhriddin.model.Post;
import com.zuhriddin.model.User;
import com.zuhriddin.service.LikeService;
import com.zuhriddin.service.NotificationService;
import com.zuhriddin.service.PostService;
import com.zuhriddin.service.UserService;

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