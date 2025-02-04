package com.zuhriddin.bot.botservice;

import com.zuhriddin.bot.botutil.BotConstant;
import com.zuhriddin.bot.botutil.languages.BotConstantENG;
import com.zuhriddin.bot.botutil.BotUtil;
import com.zuhriddin.bot.factory.FollowFactory;
import com.zuhriddin.bot.factory.NotificationFactory;
import com.zuhriddin.enumeration.NotificationType;
import com.zuhriddin.enumeration.UserState;
import com.zuhriddin.model.Follow;
import com.zuhriddin.model.Notification;
import com.zuhriddin.model.User;
import com.zuhriddin.service.ChatService;
import com.zuhriddin.service.FollowService;
import com.zuhriddin.service.NotificationService;
import com.zuhriddin.service.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;
import java.util.stream.Collectors;


public class FollowBotService {
    private static final UserService userService = new UserService();
    private static final FollowService followService = new FollowService();
    private static final NotificationService notificationService = new NotificationService();
    private static final ChatService chatService = new ChatService();

    private static final UserStateBotService userStateBotService = new UserStateBotService();

    private static final Map<Long, Integer> indexMap = new HashMap<>();
    private static final Map<Long, Integer> messageIdMap = new HashMap<>();

    public SendMessage searchPage(Long chatId) {
        userStateBotService.setUserState(chatId, UserState.SEARCH_USERNAME);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterUsernameOfUserWhoYouWantToFind(chatId));
    }

    public SendMessage showUser(Long chatId, String text) {
        User foundUser = userService.getUserByUserName(text);
        if (foundUser != null) {
            String txt = BotConstant.getUserFound(chatId) + foundUser.getUsername();
            InlineKeyboardMarkup inlineKeyboardMarkup = BotUtil.inlineKeyboardMarkup(
                    List.of(foundUser.getId()),
                    List.of(BotConstant.getAccount(chatId)),
                    1
            );
            return BotUtil.buildSendMessage(chatId, txt, inlineKeyboardMarkup);
        } else {
            return BotUtil.buildSendMessage(chatId,BotConstant.getUserNotFound(chatId));
        }
    }

    public SendMessage followUser(Long chatId, UUID followingId) {
        User user = userService.getUserByChatId(chatId);

        Follow follow = FollowFactory.buildFollow(user.getId(), followingId);
        followService.add(follow);
        Notification notification = NotificationFactory.buildNotification(user.getId(), followingId, NotificationType.FOLLOW, user.getUsername());
        notificationService.add(notification);

        return BotUtil.buildSendMessage(chatId, BotConstant.getYouHaveSuccessfullyFollowed(chatId));
    }

    public SendMessage unfollowUser(Long chatId, UUID followingId) {
        User user = userService.getUserByChatId(chatId);

        Follow follow = followService.getFollow(user.getId(), followingId);
        followService.delete(follow.getId());
        Notification notification = notificationService.getNotificationByCLPIdAndUserId(user.getId(), followingId);
        if (notification != null) {
            notificationService.delete(notification.getId());
        }

        return BotUtil.buildSendMessage(chatId, BotConstant.getYouHaveSuccessfullyUnfollowed(chatId));
    }

    public SendMessage followersList(Long chatId, UUID userId) {
        UserState userState = userStateBotService.getUserState(chatId);
        List<List<UUID>> lists = getFollowersList(userId, userState);

        if (!lists.isEmpty()) {
            List<UUID> followersId = lists.get(0);
            List<String> followersUsername = getUsernamesByUserId(followersId);
            InlineKeyboardMarkup inlineKeyboardMarkup = BotUtil.inlineKeyboardMarkup(followersId, followersUsername, 1);
            setExtraButtons(chatId, userId, inlineKeyboardMarkup, 0, lists.size());
            return BotUtil.buildSendMessage(chatId,
                    ((userState.equals(UserState.FOLLOWERS) || UserState.HOST_FOLLOWERS.equals(userState)) ? BotConstant.getFollowers(chatId) : userState.equals(UserState.CHATS) ? BotConstant.getChats(chatId) : BotConstant.getFollowings(chatId)),
                    inlineKeyboardMarkup);
        }
        return new SendMessage();
    }

    public EditMessageText editFollowersList(Long chatId, UUID userId) {
        UserState userState = userStateBotService.getUserState(chatId);
        List<List<UUID>> lists = getFollowersList(userId, userState);

        if (!lists.isEmpty()) {
            int currentIndexOfLists = indexMap.get(chatId);
            int length = lists.size();
            if (currentIndexOfLists < length) {
                Integer messageId = messageIdMap.get(chatId);
                List<UUID> followersId = lists.get(currentIndexOfLists);
                List<String> followersUsername = getUsernamesByUserId(followersId);
                InlineKeyboardMarkup inlineKeyboardMarkup = BotUtil.inlineKeyboardMarkup(followersId, followersUsername, 1);
                setExtraButtons(chatId, userId, inlineKeyboardMarkup, currentIndexOfLists, length);
                return BotUtil.buildEditMessageText(chatId,
                        (userState.equals(UserState.FOLLOWERS) ? BotConstant.getFollowers(chatId) : userState.equals(UserState.CHATS) ? BotConstant.getChats(chatId) : BotConstant.getFollowings(chatId)),
                        messageId, inlineKeyboardMarkup);
            }
        }
        return new EditMessageText();
    }

    private List<List<UUID>> getFollowersList(UUID userId, UserState userState) {
        List<UUID> followersList;
        if (userState.equals(UserState.FOLLOWERS) || UserState.HOST_FOLLOWERS.equals(userState)) {
           followersList = followService.getFollowers(userId);
        } else if (UserState.CHATS.equals(userState)) {
            followersList = chatService.getToUsersIdsByUserId(userId);
        } else {
           followersList = followService.getFollowingsList(userId);
        }
        return BotUtil.getListGroupedByTen(followersList);
    }

    private List<String> getUsernamesByUserId(List<UUID> userIds) {
        return userIds.stream()
                .map(uuid -> {
                    User user = userService.get(uuid);
                    return user.getUsername();
                })
                .collect(Collectors.toList());
    }

    public void setExtraButtons(Long chatId, UUID userId, InlineKeyboardMarkup inlineKeyboardMarkup, int indexOfCurrentList, int lengthOfLists) {
        List<List<InlineKeyboardButton>> keyboard = inlineKeyboardMarkup.getKeyboard();
        List<String> list;
        if (lengthOfLists == 1) {
            list = List.of(indexOfCurrentList + 1 + "/" + lengthOfLists);
        } else if (indexOfCurrentList == 0) {
            list = List.of(indexOfCurrentList + 1 + "/" + lengthOfLists, BotConstant.getNext(chatId));
        } else if (indexOfCurrentList == lengthOfLists - 1) {
            list = List.of(BotConstant.getBack(chatId), indexOfCurrentList + 1 + "/" + lengthOfLists);
        } else {
            list = List.of(BotConstant.getBack(chatId), indexOfCurrentList + 1 + "/" + lengthOfLists + BotConstant.getNext(chatId));
        }
        List<InlineKeyboardButton> row = list.stream()
                .map(s -> BotUtil.createInlineKeyboardButton(userId, s))
                .toList();
        keyboard.add(row);
        inlineKeyboardMarkup.setKeyboard(keyboard);
    }

    public int getLength(Long chatId, UUID userId) {
        UserState userState = userStateBotService.getUserState(chatId);
        return getFollowersList(userId, userState).size();
    }

    public int getIndexMap(Long chatId) {
        return indexMap.get(chatId);
    }

    public void setIndexMap(Long chatId, Integer index) {
        indexMap.put(chatId, index);
    }

    public void setMessageId(Long chatId, Integer messageId) {
        messageIdMap.put(chatId, messageId);
    }

    public void deleteFollowFromMap(Long chatId) {
        indexMap.remove(chatId);
        messageIdMap.remove(chatId);
    }

    public SendMessage defaultMessageWithBackButton(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
        return BotUtil.buildSendMessage(chatId,"*********************", replyKeyboardMarkup);
    }
}