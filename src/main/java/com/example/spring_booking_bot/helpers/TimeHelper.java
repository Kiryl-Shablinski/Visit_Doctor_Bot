package com.example.spring_booking_bot.helpers;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class TimeHelper {
    public static SendMessage getSendMessage(List<String> listTime, SendMessage sendMessage) {
        KeyboardRow k1 = new KeyboardRow();
        k1.add(new KeyboardButton(listTime.get(0)));
        k1.add(new KeyboardButton(listTime.get(1)));
        List<KeyboardRow> listRow = new ArrayList<>();
        listRow.add(k1);
        KeyboardRow k2 = new KeyboardRow();
        if (listTime.size() > 2){
            for (int i = 2; i < listTime.size(); i++) {
                k2.add(new KeyboardButton(listTime.get(i)));
            }
        }
        listRow.add(k2);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(listRow);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
