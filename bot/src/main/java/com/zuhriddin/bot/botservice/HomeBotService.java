package com.zuhriddin.bot.botservice;

import com.zuhriddin.bot.botutil.BotConstant;
import com.zuhriddin.bot.botutil.languages.BotConstantENG;
import com.zuhriddin.bot.botutil.BotUtil;
import com.zuhriddin.bot.botutil.PostUtil;
import com.zuhriddin.enumeration.PostType;
import com.zuhriddin.enumeration.UserState;
import com.zuhriddin.model.Follow;
import com.zuhriddin.model.Post;
import com.zuhriddin.model.User;
import com.zuhriddin.service.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.*;

public class HomeBotService {
    private static final FollowService followService = new FollowService();
    private static final PostService postService = new PostService();
    private static final UserService userService = new UserService();
    private static final NotificationService notificationService = new NotificationService();
    private static final SaveContentService saveContentService = new SaveContentService();

    private static final NotificationBotService notificationBotService = new NotificationBotService();

    private static final Map<Long, Integer> indexMap = new HashMap<>();
    private static final Map<Long, Integer> messageIdMap = new HashMap<>();

    public SendMessage handleHome(Long chatId, UserState userState, String text) {
        User user = userService.getUserByChatId(chatId);
        int countOfNotification = notificationService.countOfNotifications(user.getId());
        List<String> homeList = List.of(BotConstant.getHome(chatId), BotConstant.getSearch(chatId), BotConstant.getPost(chatId),
                BotConstant.getAccount(chatId), countOfNotification + "\n" + BotConstant.getNotifications(chatId),
                BotConstant.getChats(chatId), BotConstant.getQuit(chatId));
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.buildReplyKeyboardMarkup(homeList, 6);

        return BotUtil.buildSendMessage(chatId, userState.toString(), replyKeyboardMarkup);
    }

    public SendPhoto postPhotoForHomePage(Long chatId, UUID userId, UserState userState) {
        List<Post> listPosts = getPostListByCheckingUserState(userState, chatId, userId);

        if (!listPosts.isEmpty()) {
            int length = listPosts.size();
            int indexOfCurrentPost = indexMap.get(chatId);
            Post post = listPosts.get(length - 1 - indexOfCurrentPost);
            notificationBotService.checkNotificationAndDeleteIt(chatId, post.getId());
            return PostUtil.constructPhotoPost(chatId, post, indexOfCurrentPost + 1, length);
        }
        return new SendPhoto();
    }

    public SendVideo postVideoForHomePage(Long chatId, UUID userId, UserState userState) {
        List<Post> listPosts = getPostListByCheckingUserState(userState, chatId, userId);

        if (!listPosts.isEmpty()) {
            int length = listPosts.size();
            int indexOfCurrentPost = indexMap.get(chatId);
            Post post = listPosts.get(length - 1 - indexOfCurrentPost);
            notificationBotService.checkNotificationAndDeleteIt(chatId, post.getId());
            return PostUtil.constructVideoPost(chatId, post, indexOfCurrentPost + 1, length);
        }
        return new SendVideo();
    }

    public EditMessageMedia editPostMedia(Long chatId, UUID userId, UserState userState) {
        List<Post> listPosts = getPostListByCheckingUserState(userState, chatId, userId);

        if (!listPosts.isEmpty()) {
            int length = listPosts.size();
            int indexOfCurrentPost = indexMap.get(chatId);
            if (indexOfCurrentPost < length) {
                Integer messageId = messageIdMap.get(chatId);
                Post post = listPosts.get(length - 1 - indexOfCurrentPost);
                notificationBotService.checkNotificationAndDeleteIt(chatId, post.getId());
                return PostUtil.buildEditMessageMedia(chatId, messageId, post, indexOfCurrentPost, length);
            }
        }
        return new EditMessageMedia();
    }

    private List<Post> getPostListByCheckingUserState(UserState userState, Long chatId, UUID userId) {
        if (UserState.HOME.equals(userState)) {
            return getFollowingsPostList(chatId);
        } else if (UserState.SAVED_POSTS.equals(userState)) {
            UUID id = userService.getUserByChatId(chatId).getId();
            return saveContentService.listSaveContends(id);
        } else {
            return postService.listMyPosts(userId);
        }
    }

    private List<Post> getFollowingsPostList(Long chatId) {
        User user = userService.getUserByChatId(chatId);
        Map<UUID, Follow> followingsMap = followService.getFollowings(user.getId());
        return postService.listMyFollowingsPosts(followingsMap);
    }

    public int getIndexOfPost(UUID postId, UserState userState, Long chatId, UUID userId) {
        List<Post> posts = getPostListByCheckingUserState(userState, chatId, userId);
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getId().equals(postId)) {
                return posts.size() - i - 1;
            }
        }
        return posts.size() - 1;
    }

    public boolean isPhoto(Long chatId, UUID userId, UserState userState) {
        List<Post> postList = getPostListByCheckingUserState(userState, chatId, userId);

        if (!postList.isEmpty()) {
            int currentIndex = indexMap.get(chatId);
            if (currentIndex < postList.size()) {
                Post post = postList.get(currentIndex);
                return post.getPostType().equals(PostType.PHOTO);
            }
        }
        return false;
    }

    public int getLength(Long chatId) {
        return getFollowingsPostList(chatId).size();
    }

    public int getIndexMap(Long chatId) {
        return indexMap.get(chatId);
    }

    public void setIndexMap(Long chatId, int index) {
        indexMap.put(chatId, index);
    }

    public void setMessageId(Long chatId, Integer messageId) {
        messageIdMap.put(chatId, messageId);
    }

    public void deleteHomeFromMap(Long chatId) {
        indexMap.remove(chatId);
        messageIdMap.remove(chatId);
    }
}