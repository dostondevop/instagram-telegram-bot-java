package com.doston.bot.botservice;

import com.doston.bot.botutil.BotConstant;
import com.doston.bot.botutil.BotUtil;
import com.doston.enumeration.RegisterStep;
import com.doston.enumeration.UserState;
import com.doston.model.User;
import com.doston.service.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;

import java.util.*;

public class RegisterBotService {
    private static final UserStateBotService userStateBotService = new UserStateBotService();
    private static final UserService userService = new UserService();
    private static final Map <Long, User> userMap = new HashMap<>();

    public boolean hasUser(Long chatId) {
        User user = userService.getUserByChatId(chatId);
        return user != null;
    }

    public boolean hasUserInMap(Long chatId) {
        return userMap.get(chatId) != null;
    }

    public RegisterStep getRegisterStep(Long chatId) {
        User user = userMap.getOrDefault(chatId, null);

        if (user != null) {
            return user.getRegisterStep();
        }
        return RegisterStep.FIRST;
    }

    public SendMessage firstMethod(Long chatId) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setRegisterStep(RegisterStep.NAME);
        userMap.put(chatId, user);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterYourName(chatId));
    }

    public SendMessage nameMethod(Long chatId, String text) {
        User user = userMap.get(chatId);
        user.setName(text);
        user.setRegisterStep(RegisterStep.USERNAME);
        userMap.put(chatId, user);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterYourUsername(chatId));
    }

    public SendMessage userNameMethod(Long chatId, String text) {
        User user = userMap.get(chatId);
        user.setUsername(text);
        user.setRegisterStep(RegisterStep.PHONE_NUMBER);
        userMap.put(chatId, user);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterYourPhoneNumber(chatId));
    }

    public SendMessage phoneNumberMethod(Long chatId, String text) {
        User user = userMap.get(chatId);
        user.setPhoneNumber(text);
        user.setRegisterStep(RegisterStep.PASSWORD);
        userMap.put(chatId, user);
        return BotUtil.buildSendMessage(chatId, BotConstant.getEnterYourPassword(chatId));
    }

    public SendMessage passwordMethod(Long chatId, String text) {
        User user = userMap.get(chatId);
        user.setPassword(text);
        user.setChatId(chatId);
        user.setCreatedDate(new Date());
        user.setRegisterStep(RegisterStep.FINAL);
        userMap.put(chatId, user);
        userService.add(user);
        userStateBotService.setUserState(chatId, UserState.LOGIN);
        return BotUtil.buildSendMessage(chatId, BotConstant.getYouHaveSuccessfullyRegistered(chatId));
    }

    public SendMessage automaticRegister(Contact contact, Long chatId) {
        User user = User.builder()
                .chatId(chatId)
                .name(contact.getFirstName())
                .username(contact.getLastName())
                .phoneNumber(contact.getPhoneNumber())
                .build();
        userService.add(user);
        return BotUtil.buildSendMessage(chatId, "Hey you " + user.getName() + ", instagramga nima bor darsingni qilmaysanmi!!!");
    }

    public SendMessage manuallyOrAutomaticRequest(Long chatId) {
        List<String> stringList = new ArrayList<>();
        stringList.add(BotConstant.getAutomaticallyRegister(chatId));
        stringList.add(BotConstant.getManuallyRegister(chatId));

        SendMessage sendMessage = BotUtil.buildSendMessage(chatId, BotConstant.getFirstlyChoose(chatId));
        sendMessage.setReplyMarkup(BotUtil.buildReplyKeyboardMarkup(stringList, 2));
        return sendMessage;
    }

    public void deleteRegisterFromMap(Long chatId) {
        userMap.remove(chatId);
    }
}