package com.doston.bot.botservice;

import com.doston.bot.botutil.BotConstant;
import com.doston.bot.botutil.BotUtil;
import com.doston.enumeration.PostState;
import com.doston.enumeration.PostType;
import com.doston.model.Post;
import com.doston.model.User;
import com.doston.service.PostService;
import com.doston.service.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.*;

public class PostBotService {
    private static final NotificationBotService notificationBotService = new NotificationBotService();
    private static final PostService postService = new PostService();
    private static final UserService userService = new UserService();
    private static final Map<Long, Post> postMap = new HashMap<>();

    public PostState getPostState(Long chatId) {
        Post post = postMap.get(chatId);

        if (post != null) {
            return post.getPostState();
        }

        return PostState.BEGIN;
    }

    public SendMessage createNewPost(Long chatId) {
        User user = userService.getUserByChatId(chatId);

        Post post = new Post();
        post.setId(UUID.randomUUID());
        post.setUserId(user.getId());
        post.setUsername(user.getUsername());
        post.setPostState(PostState.TITLE);
        postMap.put(chatId, post);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterTitleOfPost(chatId));
    }

    public SendMessage setTitle(Long chatId, String text) {
        Post post = postMap.get(chatId);
        post.setTitle(text);
        post.setPostState(PostState.LOCATION);
        postMap.put(chatId, post);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterLocationOfPost(chatId));
    }

    public SendMessage setLocation(Long chatId, String text) {
        Post post = postMap.get(chatId);
        post.setLocation(text);
        post.setPostState(PostState.IMAGE);
        postMap.put(chatId, post);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterPhotoOrVideoForPost(chatId));
    }

    public SendMessage savePhotoOrVideo(Long chatId, String path, PostType postType) {
        Post post = postMap.get(chatId);
        post.setPath(path);
        post.setPostType(postType);
        post.setCreatedDate(new Date());
        post.setPostState(PostState.FINAL);
        postService.add(post);
        notificationBotService.createNotificationForEveryFollower(chatId, post.getId());
        postMap.remove(chatId);
        return BotUtil.buildSendMessage(chatId, BotConstant.getPostIsSuccessfullyAdded(chatId));
    }

    public Post getPostById(UUID postId) {
        return postService.get(postId);
    }

    public void deletePostFromMap(Long chatId) {
        postMap.remove(chatId);
    }
}