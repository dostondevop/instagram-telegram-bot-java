package com.doston.bot.botutil;

import com.doston.bot.botutil.languages.BotConstantENG;
import com.doston.bot.botutil.languages.BotConstantRU;
import com.doston.bot.botutil.languages.BotConstantUZ;
import com.doston.enumeration.Gender;
import com.doston.enumeration.Language;
import com.doston.service.UserService;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BotConstant {
    private static final UserService userService = new UserService();

    private Language getUserLanguage(Long chatId) {
        return userService.getUserByChatId(chatId).getLanguage();
    }

    public String getAutomaticallyRegister(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.AUTOMATICALLY_REGISTER;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.AUTOMATICALLY_REGISTER;
        } else {
            return BotConstantENG.AUTOMATICALLY_REGISTER;
        }
    }

    public String getManuallyRegister(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.MANUALLY_REGISTER;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.MANUALLY_REGISTER;
        } else {
            return BotConstantENG.MANUALLY_REGISTER;
        }
    }

    public String getLogin(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.LOGIN;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.LOGIN;
        } else {
            return BotConstantENG.LOGIN;
        }
    }

    public String getHome(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.HOME;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.HOME;
        } else {
            return BotConstantENG.HOME;
        }
    }

    public String getSearch(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.SEARCH;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.SEARCH;
        } else {
            return BotConstantENG.SEARCH;
        }
    }

    public String getPost(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.POST;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.POST;
        } else {
            return BotConstantENG.POST;
        }
    }

    public String getEditProfile(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.EDIT_PROFILE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.EDIT_PROFILE;
        } else {
            return BotConstantENG.EDIT_PROFILE;
        }
    }

    public String getAccount(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.ACCOUNT;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.ACCOUNT;
        } else {
            return BotConstantENG.ACCOUNT;
        }
    }

    public String getFollowers(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.FOLLOWERS;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.FOLLOWERS;
        } else {
            return BotConstantENG.FOLLOWERS;
        }
    }

    public String getFollowings(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.FOLLOWINGS;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.FOLLOWINGS;
        } else {
            return BotConstantENG.FOLLOWINGS;
        }
    }

    public String getFollow(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.FOLLOW;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.FOLLOW;
        } else {
            return BotConstantENG.FOLLOW;
        }
    }

    public String getUnfollow(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.UNFOLLOW;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.UNFOLLOW;
        } else {
            return BotConstantENG.UNFOLLOW;
        }
    }

    public String getMessage(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.MESSAGE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.MESSAGE;
        } else {
            return BotConstantENG.MESSAGE;
        }
    }

    public String getNotifications(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.NOTIFICATIONS;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.NOTIFICATIONS;
        } else {
            return BotConstantENG.NOTIFICATIONS;
        }
    }

    public String getChats(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.CHATS;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.CHATS;
        } else {
            return BotConstantENG.CHATS;
        }
    }

    public String getPosts(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.POSTS;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.POSTS;
        } else {
            return BotConstantENG.POSTS;
        }
    }

    public String getNotPassedLike(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.NOT_PASSED_LIKE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.NOT_PASSED_LIKE;
        } else {
            return BotConstantENG.NOT_PASSED_LIKE;
        }
    }

    public String getCanNotPassingLike(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.CAN_NOT_PASSING_LIKE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.CAN_NOT_PASSING_LIKE;
        } else {
            return BotConstantENG.CAN_NOT_PASSING_LIKE;
        }
    }

    public String getPassedLike(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.PASSED_LIKE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.PASSED_LIKE;
        } else {
            return BotConstantENG.PASSED_LIKE;
        }
    }

    public String getComment(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.COMMENT;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.COMMENT;
        } else {
            return BotConstantENG.COMMENT;
        }
    }

    public String getSave(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.SAVE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.SAVE;
        } else {
            return BotConstantENG.SAVE;
        }
    }

    public String getSaved(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.SAVED;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.SAVED;
        } else {
            return BotConstantENG.SAVED;
        }
    }

    public String getSend(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.SEND;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.SEND;
        } else {
            return BotConstantENG.SEND;
        }
    }

    public String getNext(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.NEXT;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.NEXT;
        } else {
            return BotConstantENG.NEXT;
        }
    }

    public String getBack(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.BACK;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.BACK;
        } else {
            return BotConstantENG.BACK;
        }
    }

    public String getBackPage(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.BACK_PAGE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.BACK_PAGE;
        } else {
            return BotConstantENG.BACK_PAGE;
        }
    }

    public String getQuit(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.QUIT;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.QUIT;
        } else {
            return BotConstantENG.QUIT;
        }
    }

    public String getPicture(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.PICTURE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.PICTURE;
        } else {
            return BotConstantENG.PICTURE;
        }
    }

    public String getName(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.NAME;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.NAME;
        } else {
            return BotConstantENG.NAME;
        }
    }

    public String getUsername(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.USERNAME;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.USERNAME;
        } else {
            return BotConstantENG.USERNAME;
        }
    }

    public String getLink(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.LINK;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.LINK;
        } else {
            return BotConstantENG.LINK;
        }
    }

    public String getBio(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.BIO;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.BIO;
        } else {
            return BotConstantENG.BIO;
        }
    }

    public String getGender(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.GENDER;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.GENDER;
        } else {
            return BotConstantENG.GENDER;
        }
    }

    public String getBirthdate(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.BIRTHDATE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.BIRTHDATE;
        } else {
            return BotConstantENG.BIRTHDATE;
        }
    }

    public String getEmail(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.EMAIL;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.EMAIL;
        } else {
            return BotConstantENG.EMAIL;
        }
    }

    public String getPassword(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.PASSWORD;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.PASSWORD;
        } else {
            return BotConstantENG.PASSWORD;
        }
    }

    public String getSavedPosts(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.SAVED_POSTS;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.SAVED_POSTS;
        } else {
            return BotConstantENG.SAVED_POSTS;
        }
    }

    public String getSettings(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.SETTINGS;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.SETTINGS;
        } else {
            return BotConstantENG.SETTINGS;
        }
    }

    public String getLanguage(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.LANGUAGE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.LANGUAGE;
        } else {
            return BotConstantENG.LANGUAGE;
        }
    }

    public String getMale(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return Gender.MALE.getValueRu();
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return Gender.MALE.getValueUz();
        } else {
            return Gender.MALE.getValueEng();
        }
    }

    public String getFemale(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return Gender.FEMALE.getValueRu();
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return Gender.FEMALE.getValueUz();
        } else {
            return Gender.FEMALE.getValueEng();
        }
    }

    public String getENG(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return Language.ENG.getValueRu();
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return Language.ENG.getValueUz();
        } else {
            return Language.ENG.getValueEng();
        }
    }

    public String getRU(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return Language.RU.getValueRu();
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return Language.RU.getValueUz();
        } else {
            return Language.RU.getValueEng();
        }
    }

    public String getUZ(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return Language.UZ.getValueRu();
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return Language.UZ.getValueUz();
        } else {
            return Language.UZ.getValueEng();
        }
    }

    public String getEnterYourPicture(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.ENTER_YOUR_PICTURE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.ENTER_YOUR_PICTURE;
        } else {
            return BotConstantENG.ENTER_YOUR_PICTURE;
        }
    }

    public String getEnterYourName(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.ENTER_YOUR_NAME;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.ENTER_YOUR_NAME;
        } else {
            return BotConstantENG.ENTER_YOUR_NAME;
        }
    }

    public String getEnterYourUsername(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.ENTER_YOUR_USERNAME;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.ENTER_YOUR_USERNAME;
        } else {
            return BotConstantENG.ENTER_YOUR_USERNAME;
        }
    }

    public String getEnterYourBio(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.ENTER_YOUR_BIO;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.ENTER_YOUR_BIO;
        } else {
            return BotConstantENG.ENTER_YOUR_BIO;
        }
    }

    public String getEnterYourBirthdate(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.ENTER_YOUR_BIRTHDATE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.ENTER_YOUR_BIRTHDATE;
        } else {
            return BotConstantENG.ENTER_YOUR_BIRTHDATE;
        }
    }

    public String getEnterYourLink(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.ENTER_YOUR_LINK;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.ENTER_YOUR_LINK;
        } else {
            return BotConstantENG.ENTER_YOUR_LINK;
        }
    }

    public String getChooseYourGender(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.CHOOSE_YOUR_GENDER;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.CHOOSE_YOUR_GENDER;
        } else {
            return BotConstantENG.CHOOSE_YOUR_GENDER;
        }
    }

    public String getEnterYourEmail(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.ENTER_YOUR_EMAIL;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.ENTER_YOUR_EMAIL;
        } else {
            return BotConstantENG.ENTER_YOUR_EMAIL;
        }
    }

    public String getEnterYourPassword(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.ENTER_YOUR_PASSWORD;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.ENTER_YOUR_PASSWORD;
        } else {
            return BotConstantENG.ENTER_YOUR_PASSWORD;
        }
    }

    public String getChatWith(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.CHAT_WITH;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.CHAT_WITH;
        } else {
            return BotConstantENG.CHAT_WITH;
        }
    }

    public String getEnterUsernameOfUserWhoYouWantToFind(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.ENTER_USERNAME_OF_USER_WHO_YOU_WANT_TO_FIND;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.ENTER_USERNAME_OF_USER_WHO_YOU_WANT_TO_FIND;
        } else {
            return BotConstantENG.ENTER_USERNAME_OF_USER_WHO_YOU_WANT_TO_FIND;
        }
    }

    public String getUserFound(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.USER_FOUND;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.USER_FOUND;
        } else {
            return BotConstantENG.USER_FOUND;
        }
    }

    public String getUserNotFound(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.USER_NOT_FOUND;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.USER_NOT_FOUND;
        } else {
            return BotConstantENG.USER_NOT_FOUND;
        }
    }

    public String getYouHaveSuccessfullyFollowed(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.YOU_HAVE_SUCCESSFULLY_FOLLOWED;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.YOU_HAVE_SUCCESSFULLY_FOLLOWED;
        } else {
            return BotConstantENG.YOU_HAVE_SUCCESSFULLY_FOLLOWED;
        }
    }

    public String getYouHaveSuccessfullyUnfollowed(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.YOU_HAVE_SUCCESSFULLY_UNFOLLOWED;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.YOU_HAVE_SUCCESSFULLY_UNFOLLOWED;
        } else {
            return BotConstantENG.YOU_HAVE_SUCCESSFULLY_UNFOLLOWED;
        }
    }

    public String getYouMustAuthenticate(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.YOU_MUST_AUTHENTICATE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.YOU_MUST_AUTHENTICATE;
        } else {
            return BotConstantENG.YOU_MUST_AUTHENTICATE;
        }
    }

    public String getEnterYourPhoneNumber(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.ENTER_YOUR_PHONE_NUMBER;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.ENTER_YOUR_PHONE_NUMBER;
        } else {
            return BotConstantENG.ENTER_YOUR_PHONE_NUMBER;
        }
    }

    public String getYouAreSuccessfullyAuthenticated(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.YOU_ARE_SUCCESSFULLY_AUTHENTICATED;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.YOU_ARE_SUCCESSFULLY_AUTHENTICATED;
        } else {
            return BotConstantENG.YOU_ARE_SUCCESSFULLY_AUTHENTICATED;
        }
    }

    public String getYourPhoneNumberOrPasswordIsIncorrect(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.YOUR_PHONE_NUMBER_OR_PASSWORD_IS_INCORRECT;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.YOUR_PHONE_NUMBER_OR_PASSWORD_IS_INCORRECT;
        } else {
            return BotConstantENG.YOUR_PHONE_NUMBER_OR_PASSWORD_IS_INCORRECT;
        }
    }

    public String getPostedNewPost(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.POSTED_NEW_POST;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.POSTED_NEW_POST;
        } else {
            return BotConstantENG.POSTED_NEW_POST;
        }
    }

    public String getFollowedYou(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.FOLLOWED_YOU;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.FOLLOWED_YOU;
        } else {
            return BotConstantENG.FOLLOWED_YOU;
        }
    }

    public String getPassedLikeToYourPost(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.PASSED_LIKE_TO_YOUR_POST;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.PASSED_LIKE_TO_YOUR_POST;
        } else {
            return BotConstantENG.PASSED_LIKE_TO_YOUR_POST;
        }
    }

    public String getWroteCommentToYourPost(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.WROTE_COMMENT_TO_YOUR_POST;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.WROTE_COMMENT_TO_YOUR_POST;
        } else {
            return BotConstantENG.WROTE_COMMENT_TO_YOUR_POST;
        }
    }

    public String getWroteMessageToYou(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.WROTE_MESSAGE_TO_YOU;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.WROTE_MESSAGE_TO_YOU;
        } else {
            return BotConstantENG.WROTE_MESSAGE_TO_YOU;
        }
    }

    public String getTime(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.TIME;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.TIME;
        } else {
            return BotConstantENG.TIME;
        }
    }

    public String getEnterTitleOfPost(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.ENTER_TITLE_OF_POST;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.ENTER_TITLE_OF_POST;
        } else {
            return BotConstantENG.ENTER_TITLE_OF_POST;
        }
    }

    public String getEnterLocationOfPost(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.ENTER_LOCATION_OF_POST;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.ENTER_LOCATION_OF_POST;
        } else {
            return BotConstantENG.ENTER_LOCATION_OF_POST;
        }
    }

    public String getEnterPhotoOrVideoForPost(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.ENTER_PHOTO_OR_VIDEO_FOR_POST;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.ENTER_PHOTO_OR_VIDEO_FOR_POST;
        } else {
            return BotConstantENG.ENTER_PHOTO_OR_VIDEO_FOR_POST;
        }
    }

    public String getPostIsSuccessfullyAdded(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.POST_IS_SUCCESSFULLY_ADDED;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.POST_IS_SUCCESSFULLY_ADDED;
        } else {
            return BotConstantENG.POST_IS_SUCCESSFULLY_ADDED;
        }
    }

    public String getYouHaveSuccessfullyRegistered(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.YOU_HAVE_SUCCESSFULLY_REGISTERED;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.YOU_HAVE_SUCCESSFULLY_REGISTERED;
        } else {
            return BotConstantENG.YOU_HAVE_SUCCESSFULLY_REGISTERED;
        }
    }

    public String getFirstlyChoose(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.FIRSTLY_CHOOSE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.FIRSTLY_CHOOSE;
        } else {
            return BotConstantENG.FIRSTLY_CHOOSE;
        }
    }

    public String getPleaseShareYourPhoneNumber(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.PLEASE_SHARE_YOUR_PHONE_NUMBER;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.PLEASE_SHARE_YOUR_PHONE_NUMBER;
        } else {
            return BotConstantENG.PLEASE_SHARE_YOUR_PHONE_NUMBER;
        }
    }

    public String getLocation(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.LOCATION;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.LOCATION;
        } else {
            return BotConstantENG.LOCATION;
        }
    }

    public String getTitle(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.TITLE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.TITLE;
        } else {
            return BotConstantENG.TITLE;
        }
    }

    public String getComments(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.COMMENTS;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.COMMENTS;
        } else {
            return BotConstantENG.COMMENTS;
        }
    }

    public String getCommentsAreNotExist(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.COMMENTS_ARE_NOT_EXIST;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.COMMENTS_ARE_NOT_EXIST;
        } else {
            return BotConstantENG.COMMENTS_ARE_NOT_EXIST;
        }
    }

    public String getNoMessages(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.NO_MESSAGES;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.NO_MESSAGES;
        } else {
            return BotConstantENG.NO_MESSAGES;
        }
    }

    public String getChooseLanguage(Long chatId) {
        if (Language.RU.equals(getUserLanguage(chatId))) {
            return BotConstantRU.CHOOSE_LANGUAGE;
        } else if (Language.UZ.equals(getUserLanguage(chatId))) {
            return BotConstantUZ.CHOOSE_LANGUAGE;
        } else {
            return BotConstantENG.CHOOSE_LANGUAGE;
        }
    }
}