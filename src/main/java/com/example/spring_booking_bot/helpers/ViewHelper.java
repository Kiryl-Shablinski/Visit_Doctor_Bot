package com.example.spring_booking_bot.helpers;

import com.example.spring_booking_bot.models.BookModel;
import com.example.spring_booking_bot.repos.BookRepo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Collections;
import java.util.List;

@Component
public class ViewHelper {
    private static ViewHelper viewHelper = null;

    final
    BookRepo bookRepo;

    public ViewHelper(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
        viewHelper = this;
    }

    public static List<BookModel> getBookList(String tgId){
        return viewHelper.bookRepo.findBookModelsByTgId(tgId);
    }

    public static List<String> getDoctorTime(DoctorEnum doctorEnum){
        List<BookModel> bookModels= viewHelper.bookRepo.findBookModelsByDoctorEnum(doctorEnum);
        return bookModels.stream()
                .map(BookModel::getTime)
                .toList();
    }

    public static SendMessage getTimeSendMessage(List<String> listTime, SendMessage sendMessage){
        KeyboardRow row = new KeyboardRow();
       for (String time : listTime){
        KeyboardButton button = new KeyboardButton(time.concat(" отменить запись"));
           row.add(button);
       }
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
       replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
                replyKeyboardMarkup.setKeyboard(Collections.singletonList(row));
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
