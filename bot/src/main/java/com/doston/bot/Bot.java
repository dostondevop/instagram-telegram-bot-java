package com.doston.bot;

import com.doston.bot.botservice.*;
import com.doston.bot.botutil.BotConstant;
import com.doston.bot.botutil.languages.BotConstantENG;
import com.doston.bot.botutil.BotUtil;
import com.doston.enumeration.*;
import com.doston.model.Comment;
import com.doston.model.Post;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bot extends TelegramLongPollingBot {
    private static final AccountBotService accountBotService = new AccountBotService();
    private static final HomeBotService homeBotService = new HomeBotService();
    private static final PostBotService postBotService = new PostBotService();
    private static final RegisterBotService registerBotService = new RegisterBotService();
    private static final LoginBotService loginBotService = new LoginBotService();
    private static final UserStateBotService userStateBotService = new UserStateBotService();
    private static final FollowBotService followBotService = new FollowBotService();
    private static final NotificationBotService notificationBotService = new NotificationBotService();
    private static final LikeBotService likeBotService = new LikeBotService();
    private static final CommentBotService commentBotService = new CommentBotService();
    private static final ChatBotService chatBotService = new ChatBotService();
    private static final QuitBotService quitBotService = new QuitBotService();
    private static final SaveContentBotService saveContentBotService = new SaveContentBotService();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            String text;
            if (message.hasText()) {
                text = message.getText();
            } else {
                text = "";
            }

            quitBotService.setMessageIdToMap(chatId,message.getMessageId());

            if (BotConstantENG.START.equals(text)) {
                if (registerBotService.hasUser(chatId)) {
                    userStateBotService.setUserState(chatId, UserState.LOGIN);
                } else {
                    userStateBotService.setUserState(chatId, UserState.REGISTER);
                }
            } else if (BotConstant.getHome(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.HOME);
                homeBotService.setIndexMap(chatId, 0);
            } else if (BotConstant.getSearch(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.SEARCH);
            } else if (BotConstant.getPost(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.ADD_POST);
            } else if (BotConstant.getAccount(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.ACCOUNT);
            } else if (BotConstant.getPicture(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.PICTURE);
                executeSendMessage(accountBotService.printEditPicturePage(chatId));
            } else if (BotConstant.getName(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.NAME);
                executeSendMessage(accountBotService.printEditNamePage(chatId));
            } else if (BotConstant.getUsername(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.USERNAME);
                executeSendMessage(accountBotService.printEditUsernamePage(chatId));
            } else if (BotConstant.getBio(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.BIO);
                executeSendMessage(accountBotService.printEditBioPage(chatId));
            } else if (BotConstant.getBirthdate(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.BIRTHDATE);
                executeSendMessage(accountBotService.printEditBirthdatePage(chatId));
            } else if (BotConstant.getLink(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.LINK);
                executeSendMessage(accountBotService.printEditLinkPage(chatId));
            } else if (BotConstant.getGender(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.GENDER);
                executeSendMessage(accountBotService.printEditGenderPage(chatId));
            } else if (BotConstant.getEmail(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.EMAIL);
                executeSendMessage(accountBotService.printEditEmailPage(chatId));
            } else if (BotConstant.getPassword(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.PASSWORD);
                executeSendMessage(accountBotService.printEditPasswordPage(chatId));
            } else if (BotConstant.getSavedPosts(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.SAVED_POSTS);
                homeBotService.setIndexMap(chatId, 0);
                UUID userId = accountBotService.getUserId(chatId);
                UserState userState = userStateBotService.getUserState(chatId);
                ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
                executeSendMessage(BotUtil.buildSendMessage(chatId, BotConstant.getSavedPosts(chatId), replyKeyboardMarkup));
                home(chatId, userId, userState);
            } else if (BotConstant.getLanguage(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.LANGUAGE);
                executeSendMessage(accountBotService.printChaneLanguagePage(chatId));
            } else if (BotConstant.getQuit(chatId).equals(text)) {
                deleteAllMessages(chatId);
            } else if (BotConstant.getBackPage(chatId).equals(text)) {
                backPage(chatId, text);
            } else if (BotConstant.getChats(chatId).equals(text)) {
                chats(chatId, text);
            } else if (text.endsWith(BotConstant.getNotifications(chatId))) {
                userStateBotService.setUserState(chatId, UserState.NOTIFICATION);
            }

            UserState userState = userStateBotService.getUserState(chatId);
            switch (userState) {
                case REGISTER -> registerUser(message, chatId);
                case LOGIN -> login(chatId, text);
                case HOME -> {
                    executeSendMessage(homeBotService.handleHome(chatId, userState, text));
                    home(chatId, null, userState);
                }
                case SEARCH -> {
                    executeSendMessage(homeBotService.handleHome(chatId, userState, text));
                    executeSendMessage(followBotService.searchPage(chatId));
                }
                case SEARCH_USERNAME -> executeSendMessage(followBotService.showUser(chatId, text));
                case ADD_POST -> post(chatId, message, userState);
                case ACCOUNT -> {
                    executeSendMessage(homeBotService.handleHome(chatId, userState, text));
                    UUID userId = accountBotService.getUserId(chatId);
                    executeSendPhoto(accountBotService.buildAccount(chatId, userId));
                }
                case NOTIFICATION -> {
                    executeSendMessage(homeBotService.handleHome(chatId, userState, text));
                    printNotifications(chatId);
                }
                case PICTURE -> addPicture(message, chatId);
                case NAME -> {
                    if (!BotConstant.getName(chatId).equals(text)) {
                        accountBotService.editName(chatId, text);
                        userStateBotService.setUserState(chatId, UserState.EDIT_PROFILE);
                        executeSendMessage(accountBotService.printEditProfilePage(chatId));
                    }
                }
                case USERNAME -> {
                    if (!BotConstant.getUsername(chatId).equals(text)) {
                        accountBotService.editUsername(chatId, text);
                        userStateBotService.setUserState(chatId, UserState.EDIT_PROFILE);
                        executeSendMessage(accountBotService.printEditProfilePage(chatId));
                    }
                }
                case BIO -> {
                    if (!BotConstant.getBio(chatId).equals(text)) {
                        accountBotService.editBio(chatId, text);
                        userStateBotService.setUserState(chatId, UserState.EDIT_PROFILE);
                        executeSendMessage(accountBotService.printEditProfilePage(chatId));
                    }
                }
                case BIRTHDATE -> {
                    if (!BotConstant.getBirthdate(chatId).equals(text)) {
                        try {
                            accountBotService.editBirthdate(chatId, text);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        userStateBotService.setUserState(chatId, UserState.EDIT_PROFILE);
                        executeSendMessage(accountBotService.printEditProfilePage(chatId));
                    }
                }
                case LINK -> {
                    if (!BotConstant.getLink(chatId).equals(text)) {
                        accountBotService.editLink(chatId, text);
                        userStateBotService.setUserState(chatId, UserState.EDIT_PROFILE);
                        executeSendMessage(accountBotService.printEditProfilePage(chatId));
                    }
                }
                case EMAIL -> {
                    if (!BotConstant.getEmail(chatId).equals(text)) {
                        accountBotService.editEmail(chatId, text);
                        userStateBotService.setUserState(chatId, UserState.SETTINGS);
                        executeSendMessage(accountBotService.printSettingsPage(chatId));
                    }
                }
                case PASSWORD -> {
                    if (!BotConstant.getPassword(chatId).equals(text)) {
                        accountBotService.editPassword(chatId, text);
                        userStateBotService.setUserState(chatId, UserState.SETTINGS);
                        executeSendMessage(accountBotService.printSettingsPage(chatId));
                    }
                }
                case COMMENTS -> comments(message, chatId, text);
                case CHAT -> saveMessageInChat(message, chatId);
            }
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            String[] split = data.split("#");
            String text = split[0];
            String callBackData = split[1];

            if (BotConstant.getAccount(chatId).equals(text)) {
                constructHostAccount(chatId, callBackData);
            } else if (BotConstant.getPosts(chatId).equals(text)) {
                findPostByNotification(chatId, callBackData);
            } else if (BotConstant.getComment(chatId).equals(text)) {
                findCommentByNotification(chatId, callBackData);
            } else if (BotConstant.getFollow(chatId).equals(text)) {
                UUID userId = UUID.fromString(callBackData);
                executeSendMessage(followBotService.followUser(chatId, userId));
            } else if (BotConstant.getUnfollow(chatId).equals(text)) {
                UUID userId = UUID.fromString(callBackData);
                executeSendMessage(followBotService.unfollowUser(chatId, userId));
            } else if (BotConstant.getSave(chatId).equals(text)) {
                UUID postId = UUID.fromString(callBackData);
                saveContentBotService.passSavaContent(chatId, postId);
                editMessageMedia(chatId, postId);
            } else if (BotConstant.getMessage(chatId).equals(text)) {
                message(chatId, callBackData);
            } else if (BotConstant.getEditProfile(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.EDIT_PROFILE);
                executeSendMessage(accountBotService.printEditProfilePage(chatId));
            } else if (BotConstant.getSettings(chatId).equals(text)) {
                userStateBotService.setUserState(chatId, UserState.SETTINGS);
                executeSendMessage(accountBotService.printSettingsPage(chatId));
            } else if (BotConstant.getNext(chatId).equals(text)) {
                next(chatId, callBackData);
            } else if (BotConstant.getBack(chatId).equals(text)) {
                back(chatId, callBackData);
            } else if (text.endsWith(BotConstant.getPosts(chatId))) {
                posts(chatId, callBackData);
            } else if (text.endsWith(BotConstant.getFollowers(chatId))) {
                UserState userPrevState = userStateBotService.getUserState(chatId);
                if (UserState.ACCOUNT.equals(userPrevState)) {
                    userStateBotService.setUserState(chatId, UserState.FOLLOWERS);
                } else {
                    userStateBotService.setUserState(chatId, UserState.HOST_FOLLOWERS);
                }
                followBotService.setIndexMap(chatId, 0);
                UUID userId = UUID.fromString(callBackData);
                Integer integer = executeSendMessage(followBotService.followersList(chatId, userId));
                followBotService.setMessageId(chatId, integer);
                executeSendMessage(followBotService.defaultMessageWithBackButton(chatId));
            } else if (text.endsWith(BotConstant.getFollowings(chatId))) {
                UserState userPrevState = userStateBotService.getUserState(chatId);
                if (UserState.ACCOUNT.equals(userPrevState)) {
                    userStateBotService.setUserState(chatId, UserState.FOLLOWINGS);
                } else {
                    userStateBotService.setUserState(chatId, UserState.HOST_FOLLOWINGS);
                }
                System.out.println(userPrevState);
                followBotService.setIndexMap(chatId, 0);
                UUID userId = UUID.fromString(callBackData);
                Integer integer = executeSendMessage(followBotService.followersList(chatId, userId));
                followBotService.setMessageId(chatId, integer);
                executeSendMessage(followBotService.defaultMessageWithBackButton(chatId));
            } else if (text.startsWith(BotConstant.getNotPassedLike(chatId))) {
                UUID postId = UUID.fromString(callBackData);
                likeBotService.passLike(chatId, postId);
                editMessageMedia(chatId, postId);
            } else if (text.startsWith(BotConstant.getPassedLike(chatId))) {
                UUID postId = UUID.fromString(callBackData);
                likeBotService.deleteLike(chatId, postId);
                editMessageMedia(chatId, postId);
            } else if (text.startsWith(BotConstant.getComment(chatId))) {
                UUID postId = UUID.fromString(callBackData);
                userStateBotService.setCurrentPostId(chatId, postId);
                UserState userPrevState = userStateBotService.getUserState(chatId);
                if (UserState.HOME.equals(userPrevState)) {
                    userStateBotService.setUserState(chatId, UserState.COMMENTS);
                } else if (UserState.SAVED_POSTS.equals(userPrevState)) {
                    userStateBotService.setUserState(chatId, UserState.SAVED_POST_COMMENTS);
                } else if (UserState.HOST_POSTS.equals(userPrevState)) {
                    userStateBotService.setUserState(chatId, UserState.HOST_COMMENTS);
                } else {
                    userStateBotService.setUserState(chatId, UserState.POST_COMMENTS);
                }
                printComments(chatId, postId);
            } else if (text.equals(BotConstant.getMale(chatId)) || text.equals(BotConstant.getFemale(chatId))) {
                accountBotService.editGender(chatId, callBackData);
                userStateBotService.setUserState(chatId, UserState.EDIT_PROFILE);
                executeSendMessage(accountBotService.printEditProfilePage(chatId));
            } else if (BotConstant.getENG(chatId).equals(text) || BotConstant.getRU(chatId).equals(text) || BotConstant.getUZ(chatId).equals(text)) {
                accountBotService.changeLanguage(chatId, callBackData);
                userStateBotService.setUserState(chatId, UserState.SETTINGS);
                executeSendMessage(accountBotService.printSettingsPage(chatId));
            } else {
                UserState userState = userStateBotService.getUserState(chatId);
                if (UserState.CHATS.equals(userState)) {
                    message(chatId, callBackData);
                } else {
                    constructHostAccount(chatId, callBackData);
                }
            }
        }
    }

    private void registerUser(Message message, Long chatId) {
        String text = message.getText();
        RegisterStep registerStep;
        if (registerBotService.hasUserInMap(chatId)) {
            registerStep = registerBotService.getRegisterStep(chatId);
        } else {
            registerStep = RegisterStep.FIRST;
        }

        if (StringUtils.equalsIgnoreCase(text, BotConstant.getManuallyRegister(chatId))) {
            executeSendMessage(registerBotService.firstMethod(chatId));
        } else if (StringUtils.equalsIgnoreCase(text, BotConstant.getAutomaticallyRegister(chatId))) {
            if (message.hasContact()) {
                Contact contact = message.getContact();
                executeSendMessage(registerBotService.automaticRegister(contact, chatId));
            }
        } else {

            switch (registerStep) {
                case FIRST -> executeSendMessage(registerBotService.manuallyOrAutomaticRequest(chatId));
                case NAME -> executeSendMessage(registerBotService.nameMethod(chatId, text));
                case USERNAME -> executeSendMessage(registerBotService.userNameMethod(chatId, text));
                case PHONE_NUMBER -> executeSendMessage(registerBotService.phoneNumberMethod(chatId, text));
                case PASSWORD -> {
                    executeSendMessage(registerBotService.passwordMethod(chatId, text));
                    executeSendMessage(loginBotService.loginPage(chatId));
                }
            }
        }
    }

    private void login(Long chatId, String text) {
        String loginStep = loginBotService.getLoginStep(chatId);

        if (loginStep == null) {
            executeSendMessage(loginBotService.loginPage(chatId));
        } else if (StringUtils.equalsIgnoreCase(text, BotConstant.getLogin(chatId))) {
            executeSendMessage(loginBotService.enterLogin(chatId, text));
        } else if (StringUtils.equalsIgnoreCase(loginStep, BotConstant.getLogin(chatId))) {
            executeSendMessage(loginBotService.checkPhoneNumber(chatId, text));
        } else if (!StringUtils.equalsIgnoreCase(loginStep, BotConstant.getLogin(chatId))) {
            boolean checkLogin = loginBotService.checkLogin(chatId, text);
            executeSendMessage(loginBotService.login(chatId, text));
            if (checkLogin) {
                homeBotService.setIndexMap(chatId, 0);
                executeSendMessage(homeBotService.handleHome(chatId, UserState.HOME, text));
                UserState userState = userStateBotService.getUserState(chatId);
                home(chatId, null, userState);
            }
        }
    }

    private void home(Long chatId, UUID userId, UserState userState) {
        Integer messageId;
        if (homeBotService.isPhoto(chatId, userId, userState)) {
            messageId = executeSendPhoto(homeBotService.postPhotoForHomePage(chatId, userId, userState));
        } else {
            messageId = executeSendVideo(homeBotService.postVideoForHomePage(chatId, userId, userState));
        }
        homeBotService.setMessageId(chatId, messageId);
    }

    private void post(Long chatId, Message message, UserState userState) {
        PostState postState = postBotService.getPostState(chatId);
        String text = message.getText();

        switch (postState) {
            case BEGIN -> {
                executeSendMessage(homeBotService.handleHome(chatId, userState, text));
                executeSendMessage(postBotService.createNewPost(chatId));
            }
            case TITLE -> executeSendMessage(postBotService.setTitle(chatId, text));
            case LOCATION -> executeSendMessage(postBotService.setLocation(chatId, text));
            case IMAGE -> setImage(message, chatId);
        }
    }

    private void setImage(Message message, Long chatId) {
        if (message.hasPhoto()) {
            String fileId = message.getPhoto().get(message.getPhoto().size() - 1).getFileId();
            String path = constructPostPath(fileId, ".jpg");
            savePhoto(message, path);
            executeSendMessage(postBotService.savePhotoOrVideo(chatId, path, PostType.PHOTO));
        } else if (message.hasVideo()) {
            String fileId = message.getVideo().getFileId();
            String path = constructPostPath(fileId, ".mp4");
            saveVideo(message, path);
            executeSendMessage(postBotService.savePhotoOrVideo(chatId, path, PostType.VIDEO));
        }
    }

    private void printNotifications(Long chatId) {
        List<SendMessage> notifications = notificationBotService.sendNotifications(chatId);

        notifications.forEach(this::executeSendMessage);
    }

    private void constructHostAccount(Long chatId, String callBackData) {
        userStateBotService.setUserState(chatId, UserState.HOST_ACCOUNT);
        UUID userId = UUID.fromString(callBackData);
        userStateBotService.setCurrentStepByCheckingCurrentHostId(chatId, userId);
        ReplyKeyboard replyKeyboard = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
        executeSendMessage(BotUtil.buildSendMessage(chatId, BotConstant.getAccount(chatId), replyKeyboard));
        executeSendPhoto(accountBotService.buildAccount(chatId, userId));
        notificationBotService.checkNotificationAndDeleteIt(chatId, userId);
    }

    private void posts(Long chatId, String callBackData) {
        UserState userPrevState = userStateBotService.getUserState(chatId);
        if (UserState.ACCOUNT.equals(userPrevState)) {
            userStateBotService.setUserState(chatId, UserState.POSTS);
        } else {
            userStateBotService.setUserState(chatId, UserState.HOST_POSTS);
        }
        ReplyKeyboard replyKeyboard = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
        executeSendMessage(BotUtil.buildSendMessage(chatId, BotConstant.getPosts(chatId), replyKeyboard));
        homeBotService.setIndexMap(chatId, 0);
        UUID userId = UUID.fromString(callBackData);
        UserState userState = userStateBotService.getUserState(chatId);
        home(chatId, userId, userState);
    }

    private void next(Long chatId, String callBackData) {
        UserState userState = userStateBotService.getUserState(chatId);
        if (UserState.FOLLOWERS.equals(userState) || UserState.FOLLOWINGS.equals(userState) || UserState.CHATS.equals(userState)) {
            UUID userId = UUID.fromString(callBackData);
            int index = followBotService.getIndexMap(chatId);
            int length = followBotService.getLength(chatId, userId);
            if (index != length - 1) {
                followBotService.setIndexMap(chatId, index + 1);
            }
            executeEditMessageText(followBotService.editFollowersList(chatId, userId));
        } else if (UserState.CHAT.equals(userState)) {
            deleteMessages(chatId);
            UUID toUserId = UUID.fromString(callBackData);
            int index = chatBotService.getIndexMap(chatId);
            int length = chatBotService.getLengthOfLists(chatId, toUserId);
            if (index != length - 1) {
                chatBotService.setIndexMap(chatId, index + 1);
            }
            printMessages(chatId, toUserId, length);
        } else {
            int index = homeBotService.getIndexMap(chatId);
            int length = homeBotService.getLength(chatId);
            if (index != length - 1) {
                homeBotService.setIndexMap(chatId, index + 1);
            }
            UUID postId = UUID.fromString(callBackData);
            editMessageMedia(chatId, postId);
        }
    }

    private void back(Long chatId, String callBackData) {
        UserState userState = userStateBotService.getUserState(chatId);
        if (userState.equals(UserState.FOLLOWERS) || userState.equals(UserState.FOLLOWINGS) || UserState.CHATS.equals(userState)) {
            int index = followBotService.getIndexMap(chatId);
            if (index != 0) {
                followBotService.setIndexMap(chatId, index - 1);
            }
            UUID userId = UUID.fromString(callBackData);
            executeEditMessageText(followBotService.editFollowersList(chatId, userId));
        } else if (UserState.CHAT.equals(userState)) {
            deleteMessages(chatId);
            UUID toUserId = UUID.fromString(callBackData);
            int index = chatBotService.getIndexMap(chatId);
            int length = chatBotService.getLengthOfLists(chatId, toUserId);
            if (index != 0) {
                chatBotService.setIndexMap(chatId, index - 1);
            }
            printMessages(chatId, toUserId, length);
        } else {
            int index = homeBotService.getIndexMap(chatId);
            if (index != 0) {
                homeBotService.setIndexMap(chatId, index - 1);
            }
            UUID postId = UUID.fromString(callBackData);
            editMessageMedia(chatId, postId);
        }
    }

    private void editMessageMedia(Long chatId, UUID postId) {
        UUID userId = postBotService.getPostById(postId).getUserId();
        UserState userState = userStateBotService.getUserState(chatId);
        executeEditMessageMedia(homeBotService.editPostMedia(chatId, userId, userState));
    }

    private void addPicture(Message message, Long chatId) {
        if (message.hasPhoto()) {
            String fileId = message.getPhoto().get(message.getPhoto().size() - 1).getFileId();
            String path = constructUserPath(fileId);
            savePhoto(message, path);
            accountBotService.editPicture(chatId, path);
            userStateBotService.setUserState(chatId, UserState.EDIT_PROFILE);
            executeSendMessage(accountBotService.printEditProfilePage(chatId));
        }
    }

    private void comments(Message message, Long chatId, String text) {
        Message replyMessage = message.getReplyToMessage();
        Integer replyMessageId = null;
        if (replyMessage != null) {
            replyMessageId = replyMessage.getMessageId();
        }
        commentBotService.saveComment(chatId, text, replyMessageId);
        deleteComments(chatId);
        UUID postId = commentBotService.getPostId(chatId);
        printComments(chatId, postId);
    }

    private void findCommentByNotification(Long chatId, String callBackData) {
        UUID commentId = UUID.fromString(callBackData);
        Comment comment = commentBotService.getCommentById(commentId);
        userStateBotService.setUserState(chatId, UserState.COMMENTS);
        UserState userState = userStateBotService.getUserState(chatId);
        Post post = postBotService.getPostById(comment.getPostId());
        int indexOfPost = homeBotService.getIndexOfPost(post.getId(), userState, chatId, post.getUserId());
        homeBotService.setIndexMap(chatId, indexOfPost);
        home(chatId, post.getUserId(), userState);
        printComments(chatId, comment.getPostId());
    }

    private void findPostByNotification(Long chatId, String callBackData) {
        userStateBotService.setUserState(chatId, UserState.POSTS);
        UserState userState = userStateBotService.getUserState(chatId);
        UUID postId = UUID.fromString(callBackData);
        Post post = postBotService.getPostById(postId);
        int indexOfPost = homeBotService.getIndexOfPost(postId, userState, chatId, post.getUserId());
        homeBotService.setIndexMap(chatId, indexOfPost);
        ReplyKeyboard replyKeyboard = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
        executeSendMessage(BotUtil.buildSendMessage(chatId, BotConstant.getPosts(chatId), replyKeyboard));
        home(chatId, post.getUserId(), userState);
    }

    private void printComments(Long chatId, UUID postId) {
        commentBotService.setMap(chatId, postId);
        List<Comment> mainComments = commentBotService.mainComments(postId);

        if (!mainComments.isEmpty()) {
            ReplyKeyboard replyKeyboard = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
            SendMessage sendMessage = BotUtil.buildSendMessage(chatId, "*=============== " + BotConstant.getComments(chatId) + " ===============*", replyKeyboard);
            sendMessage.setParseMode(ParseMode.MARKDOWN);
            executeSendMessage(sendMessage);
            mainComments.forEach(comment -> {
                Integer messageId = executeSendMessage(commentBotService.constructComment(chatId, comment));
                commentBotService.setMessageId(chatId, messageId, comment);
                notificationBotService.checkNotificationAndDeleteIt(chatId, comment.getId());
                recursion(chatId, comment.getId());
            });
        } else {
            executeSendMessage(BotUtil.buildSendMessage(chatId, BotConstant.getCommentsAreNotExist(chatId)));
        }
    }

    private void recursion(Long chatId, UUID commentId) {
        List<Comment> list = commentBotService.getSubCommentsList(chatId, commentId);
        if (list != null && !list.isEmpty()) {
            Comment comment = list.get(0);
            Integer messageId = executeSendMessage(commentBotService.constructComment(chatId, comment));
            commentBotService.setMessageId(chatId, messageId, comment);
            notificationBotService.checkNotificationAndDeleteIt(chatId, comment.getId());
            recursion(chatId, comment.getId());
        } else {
            Comment comment = commentBotService.getCommentById(commentId);
            if (comment.getParentId() != null) {
                List<Comment> commentList = commentBotService.getSubCommentsList(chatId, comment.getParentId());
                commentList.remove(comment);
                commentBotService.setSubCommentsList(chatId, comment.getParentId(), commentList);
                recursion(chatId, comment.getParentId());
            }
        }
    }

    private void deleteComments(Long chatId) {
        List<Integer> messageIdsList = commentBotService.listMessageIds(chatId);

        messageIdsList.forEach(m -> executeDeleteMessage(BotUtil.buildDeleteMessage(chatId, m)));
        commentBotService.deleteSubCommentsList(chatId);
        commentBotService.deleteMessageIds(chatId);
    }

    private void message(Long chatId, String callBackData) {
        UserState userPrevState = userStateBotService.getUserState(chatId);
        if (UserState.HOST_ACCOUNT.equals(userPrevState)) {
            userStateBotService.setUserState(chatId, UserState.HOST_CHAT);
        } else {
            userStateBotService.setUserState(chatId, UserState.CHAT);
        }
        UUID toUserId = UUID.fromString(callBackData);
        chatBotService.addChat(chatId, toUserId);
        int length = chatBotService.getLengthOfLists(chatId, toUserId);
        executeSendMessage(chatBotService.printChatName(chatId, toUserId));
        if (length == 0) {
            executeSendMessage(BotUtil.buildSendMessage(chatId, BotConstant.getNoMessages(chatId)));
        } else {
            chatBotService.setIndexMap(chatId, 0);
            printMessages(chatId, toUserId, length);
        }
    }

    private void printMessages(Long chatId, UUID toUserId, int length) {
        List<com.doston.model.Message> messageList = chatBotService.getMessageList(chatId, toUserId);
        for (int i = messageList.size() - 1; i >= 0; i--) {
            com.doston.model.Message message = messageList.get(i);
            String txt = chatBotService.buildMessage(message);
            if (PostType.PHOTO.equals(message.getPostType())) {
                SendPhoto sendPhoto = BotUtil.buildSendPhoto(chatId, txt, message.getPath(), null);
                sendPhoto.setParseMode(ParseMode.MARKDOWN);
                Integer messageId = executeSendPhoto(sendPhoto);
                chatBotService.setMessageIdMap(chatId, messageId);
            } else if (PostType.VIDEO.equals(message.getPostType())) {
                SendVideo sendVideo = BotUtil.buildSendVideo(chatId, txt, message.getPath(), null);
                sendVideo.setParseMode(ParseMode.MARKDOWN);
                Integer messageId = executeSendVideo(sendVideo);
                chatBotService.setMessageIdMap(chatId, messageId);
            } else {
                SendMessage sendMessage = BotUtil.buildSendMessage(chatId, txt);
                sendMessage.setParseMode(ParseMode.MARKDOWN);
                Integer messageId = executeSendMessage(sendMessage);
                chatBotService.setMessageIdMap(chatId, messageId);
            }
        }
        int currentIndexOfList = chatBotService.getIndexMap(chatId);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        inlineKeyboardMarkup.setKeyboard(rows);
        followBotService.setExtraButtons(chatId, toUserId, inlineKeyboardMarkup, currentIndexOfList, length);
        Integer messageId = executeSendMessage(BotUtil.buildSendMessage(chatId, "*****************", inlineKeyboardMarkup));
        chatBotService.setMessageIdMap(chatId, messageId);
    }

    private void deleteMessages(Long chatId) {
        List<Integer> messageIdList = chatBotService.getMessageIdList(chatId);
        if (messageIdList != null) {
            messageIdList.forEach(m -> executeDeleteMessage(BotUtil.buildDeleteMessage(chatId, m)));
            chatBotService.deleteMessageIdList(chatId);
        }
    }

    private void saveMessageInChat(Message message, Long chatId) {
        Integer messageId = message.getMessageId();
        chatBotService.setMessageIdMap(chatId, messageId);
        if (message.hasPhoto()) {
            String fileId = message.getPhoto().get(message.getPhoto().size() - 1).getFileId();
            String path = constructMessagePath(fileId, ".jpg");
            savePhoto(message, path);
            chatBotService.saveMessage(chatId, null, path, PostType.PHOTO);
        } else if (message.hasVideo()) {
            String fileId = message.getVideo().getFileId();
            String path = constructMessagePath(fileId, ".mp4");
            saveVideo(message, path);
            chatBotService.saveMessage(chatId, null, path, PostType.VIDEO);
        } else if (message.hasText()) {
            chatBotService.saveMessage(chatId, message.getText(), null, null);
        }
        deleteMessages(chatId);
        UUID toUserId = chatBotService.getToUserId(chatId);
        int length = chatBotService.getLengthOfLists(chatId, toUserId);
        System.out.println(toUserId);
        System.out.println(length);
        printMessages(chatId, toUserId, length);
    }

    private void deleteAllMessages(Long chatId) {
        List<Integer> messageIdList = quitBotService.getMessageIdList(chatId);
        messageIdList.forEach(message -> executeDeleteMessage(BotUtil.buildDeleteMessage(chatId, message)));

        quitBotService.deleteAllMessages(chatId);
    }

    private void chats(Long chatId, String text) {
        userStateBotService.setUserState(chatId, UserState.CHATS);
        UserState currentUserState = userStateBotService.getUserState(chatId);
        executeSendMessage(homeBotService.handleHome(chatId, currentUserState, text));
        followBotService.setIndexMap(chatId, 0);
        UUID userId = accountBotService.getUserId(chatId);
        Integer integer = executeSendMessage(followBotService.followersList(chatId, userId));
        followBotService.setMessageId(chatId, integer);
    }

    private void backPage(Long chatId, String text) {
        UserState userState = userStateBotService.getUserState(chatId);

        switch (userState) {
            case EDIT_PROFILE, SETTINGS, FOLLOWERS, FOLLOWINGS -> userStateBotService.setUserState(chatId, UserState.ACCOUNT);
            case PICTURE, NAME, USERNAME, BIO, BIRTHDATE, LINK, GENDER -> {
                userStateBotService.setUserState(chatId, UserState.EDIT_PROFILE);
                executeSendMessage(accountBotService.printEditProfilePage(chatId));
            }
            case EMAIL, PASSWORD, SAVED_POSTS, LANGUAGE -> {
                userStateBotService.setUserState(chatId, UserState.SETTINGS);
                executeSendMessage(accountBotService.printSettingsPage(chatId));
            }
            case CHAT -> chats(chatId, text);
            case POSTS -> userStateBotService.setUserState(chatId, UserState.ACCOUNT);
            case HOST_POSTS, HOST_CHAT, HOST_FOLLOWERS, HOST_FOLLOWINGS -> {
                userStateBotService.setUserState(chatId, UserState.HOST_ACCOUNT);
                UUID userId = userStateBotService.getCurrentHostId(chatId);
                ReplyKeyboard replyKeyboard = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
                executeSendMessage(BotUtil.buildSendMessage(chatId, BotConstant.getPosts(chatId), replyKeyboard));
                executeSendPhoto(accountBotService.buildAccount(chatId, userId));
            }
            case COMMENTS -> {
                userStateBotService.setUserState(chatId, UserState.HOME);
                UserState currentUserState = userStateBotService.getUserState(chatId);
                UUID postId = userStateBotService.getCurrentPostId(chatId);
                Post post = postBotService.getPostById(postId);
                int indexOfPost = homeBotService.getIndexOfPost(postId, currentUserState, chatId, post.getUserId());
                homeBotService.setIndexMap(chatId, indexOfPost);
            }
            case POST_COMMENTS -> {
                userStateBotService.setUserState(chatId, UserState.POSTS);
                backComments(chatId);
            }
            case HOST_COMMENTS -> {
                userStateBotService.setUserState(chatId, UserState.HOST_POSTS);
                backComments(chatId);
            }
            case SAVED_POST_COMMENTS -> {
                userStateBotService.setUserState(chatId, UserState.SAVED_POSTS);
                backComments(chatId);
            }
        }
    }

    private void backComments(Long chatId) {
        UserState currentUserState = userStateBotService.getUserState(chatId);
        UUID postId = userStateBotService.getCurrentPostId(chatId);
        Post post = postBotService.getPostById(postId);
        int indexOfPost = homeBotService.getIndexOfPost(postId, currentUserState, chatId, post.getUserId());
        homeBotService.setIndexMap(chatId, indexOfPost);
        ReplyKeyboard replyKeyboard = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
        executeSendMessage(BotUtil.buildSendMessage(chatId, BotConstant.getPosts(chatId), replyKeyboard));
        home(chatId, post.getUserId(), currentUserState);
    }

    @Override
    public String getBotUsername() {
        return "https://t.me/my_example_telegram_bot";
    }

    @Override
    public String getBotToken() {
        return "6951873817:AAGdT8rDVAC24zE5WJUBhis6j_-VxFKvq90";
    }

    private Integer executeSendMessage(SendMessage sendMessage) {
        try {
            Message sentMessage = execute(sendMessage);
            quitBotService.setMessageIdToMap(sentMessage.getChatId(), sentMessage.getMessageId());
            return sentMessage.getMessageId();
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer executeSendPhoto(SendPhoto sendPhoto) {
        try {
            Message sentMessage = execute(sendPhoto);
            quitBotService.setMessageIdToMap(sentMessage.getChatId(), sentMessage.getMessageId());
            return sentMessage.getMessageId();
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private Integer executeSendVideo(SendVideo sendVideo) {
        try {
            Message sentMessage = execute(sendVideo);
            quitBotService.setMessageIdToMap(sentMessage.getChatId(), sentMessage.getMessageId());
            return sentMessage.getMessageId();
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeEditMessageMedia(EditMessageMedia editMessageMedia) {
        try {
            execute(editMessageMedia);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeEditMessageText(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeDeleteMessage(DeleteMessage deleteMessage) {
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private String constructUserPath(String username) {
        return "instagram/src/main/resources/user_image_resources/" + username + ".jpg";
    }

    private String constructMessagePath(String fileId, String type) {
        return "instagram/src/main/resources/message_resources/" + fileId + type;
    }

    private String constructPostPath(String fileId, String type) {
        return "instagram/src/main/resources/post_resources/" + fileId + type;
    }

    public void savePhoto(Message message, String path) {
        String fileId = message.getPhoto().get(message.getPhoto().size() - 1).getFileId();

        GetFile getFile = new GetFile();
        getFile.setFileId(fileId);
        try {
            org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
            java.io.File downloadedFile = downloadFile(file.getFilePath());

            saveFileToCustomLocation(downloadedFile, path);
            System.out.println("File is downloaded: " + downloadedFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveVideo(Message message, String path) {
        String fileId = message.getVideo().getFileId();

        GetFile getFile = new GetFile();
        getFile.setFileId(fileId);

        try {
            org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
            java.io.File downloadedFile = downloadFile(file.getFilePath());

            saveFileToCustomLocation(downloadedFile, path);
            System.out.println("File is downloaded: " + downloadedFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveFileToCustomLocation(java.io.File sourceFile, String destinationPath) {
        try {
            Path destination = Path.of(destinationPath);
            Files.copy(sourceFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File is saved: " + destination.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}