package com.doston.bot.botservice;

import java.util.*;
import com.doston.model.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.doston.enumeration.Gender;
import com.doston.bot.botutil.BotUtil;
import com.doston.service.PostService;
import com.doston.service.UserService;
import com.doston.enumeration.Language;
import com.doston.service.FollowService;
import com.doston.bot.botutil.BotConstant;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class AccountBotService {
    private static final UserService userService = new UserService();
    private static final FollowService followService = new FollowService();
    private static final PostService postService = new PostService();

    private static final String DEFAULT_IMAGE_PATH = "instagram/src/main/resources/image_resources/default_image_for_profile/img.png";

    public SendPhoto buildAccount(Long chatId, UUID userId) {
        User user = userService.get(userId);

        String text = buildAccountData(chatId, user);
        InlineKeyboardMarkup inlineKeyboardMarkup = buildAccountButtons(chatId, user);
        String path = getImagePath(user);

        return BotUtil.buildSendPhoto(chatId, text, path, inlineKeyboardMarkup);
    }

    private String buildAccountData(Long chatId, User user) {
        return BotConstant.getUsername(chatId) + ": \t" + user.getUsername() + "\n" +
                BotConstant.getName(chatId) + ": \t" + user.getName() + "\n" +
                (user.getLink() != null ? BotConstant.getLink(chatId) + ": " + user.getLink() + "\n" : "") +
                (user.getGender() != null ? user.getGender().equals(Gender.MALE) ? BotConstant.getMale(chatId)
                        : BotConstant.getFemale(chatId) + "\n" : "") +
                (user.getBirthDate() != null ? user.getBirthDate() + "\n" : "") +
                (user.getBio() != null ? user.getBio() : "");
    }

    private InlineKeyboardMarkup buildAccountButtons(Long chatId, User user) {
        User currentUser = userService.getUserByChatId(chatId);

        int countOfPosts = postService.countOfMyPosts(user.getId());
        int countOfFollowers = followService.countOfFollowers(user.getId());
        int countOfFollowings = followService.countOfFollowings(user.getId());

        List<String> text;
        if (chatId.equals(user.getChatId())) {
            text = List.of(countOfPosts + "\n" + BotConstant.getPosts(chatId),
                    countOfFollowers + "\n" + BotConstant.getFollowers(chatId),
                    countOfFollowings + "\n" + BotConstant.getFollowings(chatId),
                    BotConstant.getEditProfile(chatId), BotConstant.getSettings(chatId));
        } else if (followService.has(currentUser.getId(), user.getId())) {
            text = List.of(countOfPosts + "\n" + BotConstant.getPosts(chatId),
                    countOfFollowers + "\n" + BotConstant.getFollowers(chatId),
                    countOfFollowings + "\n" + BotConstant.getFollowings(chatId),
                    BotConstant.getUnfollow(chatId), BotConstant.getMessage(chatId));
        } else {
            text = List.of(countOfPosts + "\n" + BotConstant.getPosts(chatId),
                    countOfFollowers + "\n" + BotConstant.getFollowers(chatId),
                    countOfFollowings + "\n" + BotConstant.getFollowings(chatId),
                    BotConstant.getFollow(chatId), BotConstant.getMessage(chatId));
        }

        return BotUtil.inlineKeyboardMarkup(List.of(user.getId()), text, 3);
    }

    private String getImagePath(User user) {
        if (user.getImagePath() != null) {
            return user.getImagePath();
        } else {
            return DEFAULT_IMAGE_PATH;
        }
    }

    public UUID getUserId(Long chatId) {
        User user = userService.getUserByChatId(chatId);
        return user.getId();
    }

    public SendMessage printEditProfilePage(Long chatId) {
        List<String> textButtons = List.of(BotConstant.getPicture(chatId), BotConstant.getName(chatId), BotConstant.getUsername(chatId),
                BotConstant.getBio(chatId), BotConstant.getBirthdate(chatId), BotConstant.getLink(chatId), BotConstant.getGender(chatId), BotConstant.getBackPage(chatId));
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.buildReplyKeyboardMarkup(textButtons, 4);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEditProfile(chatId), replyKeyboardMarkup);
    }

    public SendMessage printEditPicturePage(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterYourPicture(chatId), replyKeyboardMarkup);
    }

    public void editPicture(Long chatId, String path) {
        User user = userService.getUserByChatId(chatId);
        user.setImagePath(path);
        userService.update(user);
    }

    public SendMessage printEditNamePage(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterYourName(chatId), replyKeyboardMarkup);
    }

    public void editName(Long chatId, String name) {
        User user = userService.getUserByChatId(chatId);
        user.setName(name);
        userService.update(user);
    }

    public SendMessage printEditUsernamePage(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterYourUsername(chatId), replyKeyboardMarkup);
    }

    public void editUsername(Long chatId, String username) {
        User user = userService.getUserByChatId(chatId);
        user.setUsername(username);
        userService.update(user);
    }

    public SendMessage printEditBioPage(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterYourBio(chatId), replyKeyboardMarkup);
    }

    public void editBio(Long chatId, String bio) {
        User user = userService.getUserByChatId(chatId);
        user.setBio(bio);
        userService.update(user);
    }

    public SendMessage printEditBirthdatePage(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterYourBirthdate(chatId) + " [dd.mm.yyyy]", replyKeyboardMarkup);
    }

    public void editBirthdate(Long chatId, String birthdate) throws ParseException {
        User user = userService.getUserByChatId(chatId);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = simpleDateFormat.parse(birthdate);
        user.setBirthDate(date);
        userService.update(user);
    }

    public SendMessage printEditLinkPage(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterYourLink(chatId), replyKeyboardMarkup);
    }

    public void editLink(Long chatId, String link) {
        User user = userService.getUserByChatId(chatId);
        user.setLink(link);
        userService.update(user);
    }

    public SendMessage printEditGenderPage(Long chatId) {
        List<String> list = List.of(BotConstant.getMale(chatId), BotConstant.getFemale(chatId));
        InlineKeyboardMarkup inlineKeyboardMarkup = BotUtil.inlineKeyboardMarkup(list, list, 2);
        return BotUtil.buildSendMessage(chatId, BotConstant.getChooseYourGender(chatId), inlineKeyboardMarkup);
    }

    public void editGender(Long chatId, String callBackData) {
        User user = userService.getUserByChatId(chatId);
        if(BotConstant.getMale(chatId).equalsIgnoreCase(callBackData)) {
            user.setGender(Gender.MALE);
        } else {
            user.setGender(Gender.FEMALE);
        }
        userService.update(user);
    }

    public SendMessage printSettingsPage(Long chatId) {
        List<String> textButtons = List.of(BotConstant.getEmail(chatId), BotConstant.getPassword(chatId), BotConstant.getSavedPosts(chatId), BotConstant.getLanguage(chatId), BotConstant.getBackPage(chatId));
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.buildReplyKeyboardMarkup(textButtons, 3);
        return BotUtil.buildSendMessage(chatId, BotConstant.getSettings(chatId), replyKeyboardMarkup);
    }

    public SendMessage printEditEmailPage(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterYourEmail(chatId), replyKeyboardMarkup);
    }

    public void editEmail(Long chatId, String email) {
        User user = userService.getUserByChatId(chatId);
        user.setEmail(email);
        userService.update(user);
    }

    public SendMessage printEditPasswordPage(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterYourPassword(chatId), replyKeyboardMarkup);
    }

    public void editPassword(Long chatId, String password) {
        User user = userService.getUserByChatId(chatId);
        user.setPassword(password);
        userService.update(user);
    }

    public SendMessage printChaneLanguagePage(Long chatId) {
        List<String> list = List.of(BotConstant.getENG(chatId), BotConstant.getRU(chatId), BotConstant.getUZ(chatId));
        InlineKeyboardMarkup inlineKeyboardMarkup = BotUtil.inlineKeyboardMarkup(list, list, 3);
        return BotUtil.buildSendMessage(chatId, BotConstant.getChooseLanguage(chatId), inlineKeyboardMarkup);
    }

    public void changeLanguage(Long chatId, String callBackData) {
        User user = userService.getUserByChatId(chatId);
        if(BotConstant.getENG(chatId).equalsIgnoreCase(callBackData)) {
            user.setLanguage(Language.ENG);
        } else if (BotConstant.getRU(chatId).equalsIgnoreCase(callBackData)) {
            user.setLanguage(Language.RU);
        } else {
            user.setLanguage(Language.UZ);
        }
        userService.update(user);
    }
}