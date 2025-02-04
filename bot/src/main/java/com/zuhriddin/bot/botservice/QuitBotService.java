package com.zuhriddin.bot.botservice;

import com.zuhriddin.model.Comment;
import com.zuhriddin.service.CommentService;
import com.zuhriddin.service.NotificationService;
import com.zuhriddin.service.PostService;
import com.zuhriddin.service.UserService;

import java.util.*;

public class QuitBotService {

    private static final Map<Long, List<Integer>> messageIdMap = new HashMap<>();
    private static final ChatBotService chatBotService = new ChatBotService();
    private static final CommentBotService commentBotService = new CommentBotService();
    private static final FollowBotService followBotService = new FollowBotService();
    private static final HomeBotService homeBotService = new HomeBotService();
    private static final LoginBotService loginBotService = new LoginBotService();
    private static  final PostBotService postBotService = new PostBotService();
    private static final RegisterBotService registerBotService = new RegisterBotService();
    private static final UserStateBotService userStateBotService = new UserStateBotService();

    public List<Integer> getMessageIdList(Long chatId) {
        return messageIdMap.get(chatId);
    }

    public void setMessageIdToMap (Long chatId, Integer messageId) {
        List<Integer> messageIdList = messageIdMap.getOrDefault(chatId, new ArrayList<>());
        messageIdList.add(messageId);
        messageIdMap.put(chatId, messageIdList);
    }

    public void deleteAllMessages (Long chatId) {
        chatBotService.deleteChatFromMap(chatId);
        commentBotService.deleteCommentFromMap(chatId);
        followBotService.deleteFollowFromMap(chatId);
        homeBotService.deleteHomeFromMap(chatId);
        loginBotService.deleteLoginFromMap(chatId);
        postBotService.deletePostFromMap(chatId);
        registerBotService.deleteRegisterFromMap(chatId);
        userStateBotService.deleteUserStateFromMap(chatId);
    }
}
