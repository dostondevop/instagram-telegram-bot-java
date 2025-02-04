package com.zuhriddin.bot.botservice;

import com.zuhriddin.bot.botutil.BotConstant;
import com.zuhriddin.bot.botutil.languages.BotConstantENG;
import com.zuhriddin.bot.botutil.BotUtil;
import com.zuhriddin.bot.factory.ChatFactory;
import com.zuhriddin.bot.factory.MessageFactory;
import com.zuhriddin.enumeration.PostType;
import com.zuhriddin.model.Chat;
import com.zuhriddin.model.Message;
import com.zuhriddin.model.User;
import com.zuhriddin.service.ChatService;
import com.zuhriddin.service.MessageService;
import com.zuhriddin.service.UserService;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.*;

public class ChatBotService {
    private static final ChatService chatService = new ChatService();
    private static final UserService userService = new UserService();
    private static final MessageService messageService = new MessageService();

    private static final Map<Long, Integer> indexMap = new HashMap<>();
    private static final Map<Long, List<Integer>> messageIdMap = new HashMap<>();
    private static final Map<Long, UUID> chatIdMap = new HashMap<>();

    public void addChat(Long chatId, UUID toUserId) {
        User user = userService.getUserByChatId(chatId);
        if (!chatService.hasChat(user.getId(), toUserId)) {
            Chat chat = ChatFactory.buildChat(user.getId(), toUserId);
            chatService.add(chat);
            chatIdMap.put(chatId, chat.getId());
        }
    }

    public boolean hasChat(UUID userId, UUID toUserId) {
        return chatService.hasChat(userId, toUserId);
    }

    public SendMessage printChatName(Long chatId, UUID userId) {
        User toUser = userService.get(userId);
        String text = "*=============== " + BotConstant.getChatWith(chatId) + toUser.getUsername() + " ===============*";
        ReplyKeyboard replyKeyboard = BotUtil.buildReplyKeyboardMarkup(List.of(BotConstant.getBackPage(chatId)), 1);
        SendMessage sendMessage = BotUtil.buildSendMessage(chatId, text, replyKeyboard);
        sendMessage.setParseMode(ParseMode.MARKDOWN);
        return sendMessage;
    }

    private List<List<Message>> getMessagesListGroupedByTen(Long chatId, UUID toUserId) {
        User user = userService.getUserByChatId(chatId);
        Chat chat = chatService.getChatByUser1IdAndUser2Id(user.getId(), toUserId);
        chatIdMap.put(chatId, chat.getId());
        List<Message> messageList = messageService.getMessageListByChatId(chat.getId());
        return BotUtil.getListGroupedByTen(messageList);
    }

    public List<Message> getMessageList(Long chatId, UUID toUserId) {
        List<List<Message>> lists = getMessagesListGroupedByTen(chatId, toUserId);

        if (!lists.isEmpty()) {
            int indexOfCurrentList = indexMap.get(chatId);
            return lists.get(indexOfCurrentList);
        }
        return new ArrayList<>();
    }

    public String constructMessage(Message message) {
        User user = userService.get(message.getFromId());
        return  "*" + user.getUsername() + "* \t" + (message.getPostType() == null ? message.getText() : "") +
                "\t\t" + message.getCreatedDate();
    }

    public UUID getToUserId(Long chatId) {
        User user = userService.getUserByChatId(chatId);
        UUID oneToOneChatId = chatIdMap.get(chatId);
        Chat chat = chatService.get(oneToOneChatId);
        if (user.getId().equals(chat.getUser1Id())) {
            return chat.getUser2Id();
        } else {
            return chat.getUser1Id();
        }
    }

    public int getLengthOfLists(Long chatId, UUID toUserId) {
        return getMessagesListGroupedByTen(chatId, toUserId).size();
    }

    public void setIndexMap(Long chatId, Integer index) {
        indexMap.put(chatId, index);
    }

    public int getIndexMap(Long chatId) {
        return indexMap.get(chatId);
    }

    public void setMessageIdMap(Long chatId, Integer messageId) {
        List<Integer> messageIdList = messageIdMap.getOrDefault(chatId, new ArrayList<>());
        messageIdList.add(messageId);
        messageIdMap.put(chatId, messageIdList);
    }

    public List<Integer> getMessageIdList(Long chatId) {
        return messageIdMap.get(chatId);
    }

    public void saveMessage(Long chatId, String text, String path, PostType postType) {
        User user = userService.getUserByChatId(chatId);
        Message message = MessageFactory.buildMessage(text, user.getId(), chatIdMap.get(chatId), path, postType);
        messageService.add(message);
    }

    public void deleteChatFromMap(Long chatId) {
        indexMap.remove(chatId);
        messageIdMap.remove(chatId);
        chatIdMap.remove(chatId);
    }

    public void deleteMessageIdList(Long chatId) {
        messageIdMap.remove(chatId);
    }
}