package com.zuhriddin.bot.botservice;

import com.zuhriddin.bot.botutil.BotConstant;
import com.zuhriddin.bot.botutil.languages.BotConstantENG;
import com.zuhriddin.bot.botutil.BotUtil;
import com.zuhriddin.bot.factory.NotificationFactory;
import com.zuhriddin.enumeration.NotificationType;
import com.zuhriddin.model.Notification;
import com.zuhriddin.model.User;
import com.zuhriddin.service.FollowService;
import com.zuhriddin.service.NotificationService;
import com.zuhriddin.service.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class NotificationBotService {
   private static final UserService userService = new UserService();
   private static final FollowService followService = new FollowService();
   private static final NotificationService notificationService = new NotificationService();

   public void createNotificationForEveryFollower(Long chatId, UUID postId) {
       User user = userService.getUserByChatId(chatId);
       List<UUID> followers = followService.getFollowers(user.getId());
       List<User> userFollowers = userService.getUserFollowers(followers);
       userFollowers.forEach(user1 ->
               notificationService.add(NotificationFactory.buildNotification(postId, user1.getId(), NotificationType.POST, user.getUsername())));
   }

    public List<SendMessage> sendNotifications(Long chatId) {
        User user = userService.getUserByChatId(chatId);
        List<Notification> notifications = notificationService.notificationList(user.getId());

        return notifications.stream()
                .map(n -> {
                    String text = constructText(chatId, n);
                    List<String> list = buildButtonTextByCheckingNotificationType(chatId, n);
                    InlineKeyboardMarkup inlineKeyboardMarkup =
                            BotUtil.inlineKeyboardMarkup(List.of(n.getClpId()),
                                    list, 1);
                    return BotUtil.buildSendMessage(chatId, text, inlineKeyboardMarkup);
                }).collect(Collectors.toList());
    }

    private List<String> buildButtonTextByCheckingNotificationType(Long chatId, Notification notification) {
        if (notification.getNotificationType().equals(NotificationType.FOLLOW)) {
            return List.of(BotConstant.getAccount(chatId));
        } else if (notification.getNotificationType().equals(NotificationType.POST)) {
            return List.of(BotConstant.getPosts(chatId));
        } else if (notification.getNotificationType().equals(NotificationType.COMMENT)) {
            return List.of(BotConstant.getComment(chatId));
        } else {
            return List.of("Find");
        }
    }

    private String constructText(Long chatId, Notification notification) {
        String text;
        if (notification.getNotificationType().equals(NotificationType.POST)) {
            text = notification.getUsername() + BotConstant.getPostedNewPost(chatId) + "\t\t" + BotConstant.getTime(chatId) + notification.getCreatedDate();
        } else if (notification.getNotificationType().equals(NotificationType.FOLLOW)) {
            text = notification.getUsername() + BotConstant.getFollowedYou(chatId) + "\t\t" + BotConstant.getTime(chatId) + notification.getCreatedDate();
        } else if (notification.getNotificationType().equals(NotificationType.LIKE)) {
            text = notification.getUsername() + BotConstant.getPassedLikeToYourPost(chatId) + "\t\t" + BotConstant.getTime(chatId) + notification.getCreatedDate();
        } else if (notification.getNotificationType().equals(NotificationType.COMMENT)) {
            text = notification.getUsername() + BotConstant.getWroteCommentToYourPost(chatId) + "\t\t" + BotConstant.getTime(chatId) + notification.getCreatedDate();
        } else {
            text = notification.getUsername() + BotConstant.getWroteMessageToYou(chatId) + "\t\t" + BotConstant.getTime(chatId) + notification.getCreatedDate();
        }
        return text;
    }

    public void checkNotificationAndDeleteIt(Long chatId, UUID clpId) {
        User user = userService.getUserByChatId(chatId);
        Notification notification = notificationService.getNotificationByCLPIdAndUserId(clpId, user.getId());
        if (notification != null) {
            notificationService.delete(notification.getId());
        }
    }
}