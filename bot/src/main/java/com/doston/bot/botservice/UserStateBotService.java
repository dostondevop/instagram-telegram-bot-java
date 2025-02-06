package com.doston.bot.botservice;

import com.doston.enumeration.UserState;
import com.doston.service.UserService;

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

    public void setCurrentHostIdAndPrevHostId(Long chatId, UUID currentHostId, UUID prevHostId) {
        Map<UUID, UUID> hostUserMap = stepHostUserMap.getOrDefault(chatId, new HashMap<>());
        hostUserMap.put(currentHostId, prevHostId);
        stepHostUserMap.put(chatId, hostUserMap);
    }

    public UUID getCurrentHostId(Long chatId) {
        return currentHostMap.get(chatId);
    }

    public UUID getCurrentPostId(Long chatId) {
        return currentPostMap.get(chatId);
    }

    public void setCurrentPostId(Long chatId, UUID postId) {
        currentPostMap.put(chatId, postId);
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