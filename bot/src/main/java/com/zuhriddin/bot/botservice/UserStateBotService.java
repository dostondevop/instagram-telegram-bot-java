package com.zuhriddin.bot.botservice;

import com.zuhriddin.enumeration.UserState;
import com.zuhriddin.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserStateBotService {
    private static final UserService userService = new UserService();

    private static final Map<Long, UserState> userStateMap = new HashMap<>();
    private static final Map<Long, Map<UUID, UUID>> stepHostUserMap = new HashMap<>();
    private static final Map<Long, UUID> currentHostMap = new HashMap<>();
    private static final Map<Long, UUID> currentPostMap = new HashMap<>();

    public UserState getUserState(Long chatId) {
        return userStateMap.get(chatId);
    }

    public void setUserState(Long chatId, UserState userState) {
        userStateMap.put(chatId, userState);
    }

    public void deleteUserStateFromMap(Long chatId) {
        userStateMap.remove(chatId);
    }

    public UUID getPrevHostId(Long chatId, UUID currentHostId) {
        return stepHostUserMap.get(chatId).get(currentHostId);
    }

    public void setCurrentHostIdAndPrevHostId(Long chatId, UUID currentHostId, UUID prevHostId) {
        Map<UUID, UUID> hostUserMap = stepHostUserMap.getOrDefault(chatId, new HashMap<>());
        hostUserMap.put(currentHostId, prevHostId);
        stepHostUserMap.put(chatId, hostUserMap);
    }

    public void deletePassedStep(Long chatId, UUID currentHostId) {
        Map<UUID, UUID> hostUserMap = stepHostUserMap.get(chatId);
        hostUserMap.remove(currentHostId);
        stepHostUserMap.put(chatId, hostUserMap);
    }

    public UUID getCurrentHostId(Long chatId) {
        return currentHostMap.get(chatId);
    }

    public void setCurrentHostId(Long chatId, UUID currentHostId) {
        currentHostMap.put(chatId, currentHostId);
    }

    public void deleteCurrentHostId(Long chatId) {
        currentHostMap.remove(chatId);
    }

    public UUID getCurrentPostId(Long chatId) {
        return currentPostMap.get(chatId);
    }

    public void setCurrentPostId(Long chatId, UUID postId) {
        currentPostMap.put(chatId, postId);
    }

    public void deleteCurrentPostId(Long chatId) {
        currentPostMap.remove(chatId);
    }

    public void setCurrentStepByCheckingCurrentHostId(Long chatId, UUID currentHostId) {
        UUID prevHostId = currentHostMap.get(chatId);
        if (prevHostId == null) {
            prevHostId = userService.getUserByChatId(chatId).getId();
        }
        setCurrentHostIdAndPrevHostId(chatId, currentHostId, prevHostId);
        currentHostMap.put(chatId, currentHostId);
    }
}