package com.doston.bot.botutil;

import com.doston.enumeration.PostType;
import com.doston.model.Post;
import com.doston.model.User;
import com.doston.service.CommentService;
import com.doston.service.LikeService;
import com.doston.service.SaveContentService;
import com.doston.service.UserService;
import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;

@UtilityClass
public class PostUtil {
    private static final UserService userService = new UserService();
    private static final LikeService likeService = new LikeService();
    private static final CommentService commentService = new CommentService();
    private static final SaveContentService saveContentService = new SaveContentService();

    public SendPhoto constructPhotoPost(Long chatId, Post post, int indexOfCurrentPost, int lengthOfPosts) {
        String text = constructPostText(chatId, post);
        InlineKeyboardMarkup inlineKeyboardMarkup = constructPostButton(chatId, post, indexOfCurrentPost, lengthOfPosts);
        SendPhoto sendPhoto = BotUtil.buildSendPhoto(chatId, text, post.getPath(), inlineKeyboardMarkup);
        sendPhoto.setParseMode(ParseMode.MARKDOWN);
        return sendPhoto;
    }

    public SendVideo constructVideoPost(Long chatId, Post post, int indexOfCurrentPost, int lengthOfPosts) {
        String text = constructPostText(chatId, post);
        InlineKeyboardMarkup inlineKeyboardMarkup = constructPostButton(chatId, post, indexOfCurrentPost, lengthOfPosts);
        SendVideo sendVideo = BotUtil.buildSendVideo(chatId, text, post.getPath(), inlineKeyboardMarkup);
        sendVideo.setParseMode(ParseMode.MARKDOWN);
        return sendVideo;
    }

    private String constructPostText(Long chatId, Post post) {
        return "*" + post.getUsername() + "* \n" +
                BotConstant.getLocation(chatId) + post.getLocation() + "\n" +
                BotConstant.getTitle(chatId) + post.getTitle() + "\n" +
                post.getCreatedDate();
    }

    private InlineKeyboardMarkup constructPostButton(Long chatId, Post post, int indexOfCurrentPost, int lengthOfPosts) {
        List<String> postButtons;

        int numberOfPostLikes = likeService.numberOfPostLikes(post.getId());
        int numberOfPostComments = commentService.numberOfPostComments(post.getId());

        if (lengthOfPosts == 1) {
            postButtons = List.of(getLikeTypeByExistingLike(chatId, post) + "\t" + numberOfPostLikes,
                    BotConstant.getComment(chatId) + "\t" + numberOfPostComments, BotConstant.getSend(chatId), getSaveTypeByExistingSave(chatId, post),
                    indexOfCurrentPost + "/" + lengthOfPosts);
        } else if (indexOfCurrentPost - 1 == 0) {
            postButtons = List.of(getLikeTypeByExistingLike(chatId, post) + "\t" + numberOfPostLikes,
                    BotConstant.getComment(chatId) + "\t" + numberOfPostComments, BotConstant.getSend(chatId), getSaveTypeByExistingSave(chatId, post),
                    indexOfCurrentPost + "/" + lengthOfPosts, BotConstant.getNext(chatId));
        } else if (indexOfCurrentPost == lengthOfPosts) {
            postButtons = List.of(getLikeTypeByExistingLike(chatId, post) + "\t" + numberOfPostLikes,
                    BotConstant.getComment(chatId) + "\t" + numberOfPostComments, BotConstant.getSend(chatId), getSaveTypeByExistingSave(chatId, post),
                    BotConstant.getBack(chatId), indexOfCurrentPost + "/" + lengthOfPosts);
        } else {
            postButtons = List.of(getLikeTypeByExistingLike(chatId, post) + "\t" + numberOfPostLikes,
                    BotConstant.getComment(chatId) + "\t" + numberOfPostComments, BotConstant.getSend(chatId), getSaveTypeByExistingSave(chatId, post),
                    BotConstant.getBack(chatId), indexOfCurrentPost + "/" + lengthOfPosts, BotConstant.getNext(chatId));
        }
        return BotUtil.inlineKeyboardMarkup(List.of(post.getId()), postButtons, 4);
    }

    private String getLikeTypeByExistingLike(Long chatId, Post post) {
        User user = userService.getUserByChatId(chatId);

        boolean existingLike = likeService.has(user.getId(), post.getId());
        if (existingLike) {
            return BotConstant.getPassedLike(chatId);
        } else if (user.getId().equals(post.getUserId())) {
            return BotConstant.getCanNotPassingLike(chatId);
        } else {
            return BotConstant.getNotPassedLike(chatId);
        }
    }

    private String getSaveTypeByExistingSave(Long chatId, Post post) {
        User user = userService.getUserByChatId(chatId);
        boolean existingLike = saveContentService.has(user.getId(), post.getId());
        if (existingLike) {
            return BotConstant.getSaved(chatId);
        }
        return BotConstant.getSave(chatId);
    }

    public EditMessageMedia buildEditMessageMedia(Long chatId, Integer messageId, Post post, int indexOfCurrentPost, int lengthOfPosts) {
        EditMessageMedia editMessageMedia = new EditMessageMedia();
        editMessageMedia.setChatId(chatId);
        editMessageMedia.setMessageId(messageId);
        if (post.getPostType().equals(PostType.PHOTO)) {
            InputMediaPhoto mediaPhoto = new InputMediaPhoto();
            mediaPhoto.setMedia(getFileId(post.getPath()));
            mediaPhoto.setCaption(constructPostText(chatId, post));
            mediaPhoto.setParseMode(ParseMode.MARKDOWN);
            editMessageMedia.setMedia(mediaPhoto);
        } else {
            InputMediaVideo mediaVideo = new InputMediaVideo();
            mediaVideo.setMedia(getFileId(post.getPath()));
            mediaVideo.setCaption(constructPostText(chatId, post));
            mediaVideo.setParseMode(ParseMode.MARKDOWN);
            editMessageMedia.setMedia(mediaVideo);
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = constructPostButton(chatId, post, indexOfCurrentPost + 1, lengthOfPosts);
        editMessageMedia.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageMedia;
    }

    private String getFileId(String path) {
        String[] strings = path.split("/");
        String str = strings[strings.length - 1];
        return str.substring(0, str.length() - 4);
    }
}