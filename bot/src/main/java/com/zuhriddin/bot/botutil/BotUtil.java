package com.zuhriddin.bot.botutil;

import com.zuhriddin.bot.botservice.RegisterBotService;
import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class BotUtil {
    public static ReplyKeyboardMarkup buildReplyKeyboardMarkup(List<String> data, int partsRow) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> rows = makeReplyKeyboardRow(data, partsRow);
        replyKeyboardMarkup.setKeyboard(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

    private static List<KeyboardRow> makeReplyKeyboardRow(List<String> data, int partsRow) {
        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        for (int i = 1; i <= data.size(); i++) {
            row.add(new KeyboardButton(data.get(i - 1)));
            if (i % partsRow == 0) {
                rows.add(row);
                row = new KeyboardRow();
            }
        }
        if (data.size() % partsRow != 0) {
            rows.add(row);
        }
        return rows;
    }

    public static <T> InlineKeyboardMarkup inlineKeyboardMarkup(List<T> callBackData, List<String> text, int partsRow) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        for (int i = 1; i <= text.size(); i++) {
            if (callBackData.size() == text.size()) {
                row.add(createInlineKeyboardButton(callBackData.get(i - 1), text.get(i - 1)));
            } else {
                row.add(createInlineKeyboardButton(callBackData.get(0), text.get(i - 1)));
            }

            if (i % partsRow == 0) {
                rows.add(row);
                row = new ArrayList<>();
            }
        }
        if (text.size() % partsRow != 0) {
            rows.add(row);
        }
        inlineKeyboardMarkup.setKeyboard(rows);
        return inlineKeyboardMarkup;
    }

    public static <T> InlineKeyboardButton createInlineKeyboardButton(T calBackData, String text) {
        InlineKeyboardButton button = new InlineKeyboardButton();

        button.setText(text);
        button.setCallbackData(text + "#" + calBackData);

        return button;
    }

    public static ReplyKeyboardMarkup getRequestPhoneNumberKeyboard() {
        KeyboardButton button = new KeyboardButton("Share your phone number");
        button.setRequestContact(true);

        KeyboardRow row = new KeyboardRow();
        row.add(button);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(row);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(keyboardRows);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        return keyboardMarkup;
    }

    public SendMessage buildSendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }

    public SendMessage buildSendMessage(Long chatId, String text, ReplyKeyboard replyKeyboard) {
        SendMessage sendMessage = buildSendMessage(chatId, text);
        sendMessage.setReplyMarkup(replyKeyboard);
        return sendMessage;
    }

    public SendMessage buildSendMessage(Long chatId, ReplyKeyboard replyKeyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(replyKeyboard);
        return sendMessage;
    }

    public SendPhoto buildSendPhoto(Long chatId, String text, String path, ReplyKeyboard replyKeyboard) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setCaption(text);
        sendPhoto.setPhoto(new InputFile(new File(path)));
        sendPhoto.setReplyMarkup(replyKeyboard);
        return sendPhoto;
    }

    public SendVideo buildSendVideo(Long chatId, String text, String path, ReplyKeyboard replyKeyboard) {
        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(chatId);
        sendVideo.setCaption(text);
        sendVideo.setVideo(new InputFile(path));
        sendVideo.setReplyMarkup(replyKeyboard);
        return sendVideo;
    }

    public EditMessageText buildEditMessageText(Long chatId, String text, Integer messageId, InlineKeyboardMarkup inlineKeyboardMarkup) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(messageId);
        editMessageText.setText(text);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    public DeleteMessage buildDeleteMessage(Long chatId, Integer messageId) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId);
        deleteMessage.setMessageId(messageId);
        return deleteMessage;
    }

    public <T> List<List<T>> getListGroupedByTen(List<T> list) {
        List<List<T>> lists = new ArrayList<>();

        List<T> uuidList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            uuidList.add(list.get(i));
            if ((list.size() - i) % 5 == 0) {
                lists.add(uuidList);
                uuidList = new ArrayList<>();
            }
        }
        if (list.size() % 5 != 0) {
            lists.add(uuidList);
        }
        return lists;
    }
}