package com.zuhriddin.bot.botservice;

import com.zuhriddin.bot.botutil.BotConstant;
import com.zuhriddin.bot.botutil.languages.BotConstantENG;
import com.zuhriddin.bot.botutil.BotUtil;
import com.zuhriddin.enumeration.UserState;
import com.zuhriddin.model.User;
import com.zuhriddin.service.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginBotService {
    private static final UserStateBotService userStateBotService = new UserStateBotService();
    private static final UserService userService = new UserService();
    private static final Map<Long, String> loginMap = new HashMap<>();

    public SendMessage loginPage(Long chatId) {
        setLoginStep(chatId, "");

        ReplyKeyboardMarkup replyKeyboardMarkup = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getLogin(chatId)), 1);
        SendMessage sendMessage = BotUtil.buildSendMessage(chatId, BotConstant.getYouMustAuthenticate(chatId));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public String getLoginStep(Long chatId) {
        return loginMap.get(chatId);
    }

    public void setLoginStep(Long chatId, String text) {
        loginMap.put(chatId, text);
    }

    public SendMessage enterLogin(Long chatId, String text) {
        loginMap.put(chatId, text);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterYourPhoneNumber(chatId));
    }

    public SendMessage checkPhoneNumber(Long chatId, String text) {
        loginMap.put(chatId, text);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterYourPassword(chatId));
    }

    public SendMessage login(Long chatId, String text) {
        String phoneNumber = loginMap.get(chatId);
        User login = userService.login(chatId, phoneNumber, text);
        loginMap.remove(chatId);
        if (login != null) {
            userStateBotService.setUserState(chatId, UserState.HOME);
            return BotUtil.buildSendMessage(chatId, BotConstant.getYouAreSuccessfullyAuthenticated(chatId));
        } else {
            loginMap.remove(chatId);
            return BotUtil.buildSendMessage(chatId, BotConstant.getYourPhoneNumberOrPasswordIsIncorrect(chatId));
        }
    }

    public boolean checkLogin(Long chatId, String text) {
        String phoneNumber = loginMap.get(chatId);
        User login = userService.login(chatId, phoneNumber, text);
        return login != null;
    }

    public void deleteLoginFromMap(Long chatId) {
        loginMap.remove(chatId);
    }
}