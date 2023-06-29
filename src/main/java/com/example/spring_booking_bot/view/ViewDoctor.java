package com.example.spring_booking_bot.view;

import com.example.spring_booking_bot.commands.WorkerCommand;
import com.example.spring_booking_bot.helpers.DoctorEnum;
import com.example.spring_booking_bot.helpers.ViewHelper;
import com.example.spring_booking_bot.models.BookModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ViewDoctor implements WorkerCommand {

    @Override
    public  SendMessage start(Update update) {
       String text = update.getMessage().getText();
        if (!text.startsWith("/command2")) {
            return null;
        }
        return sendDefaultMessage(update);
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
       SendMessage sendMessage = new SendMessage();
        KeyboardRow k = new KeyboardRow();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        String tgId = update.getMessage().getFrom().getId().toString();
        List<BookModel> records = ViewHelper.getBookList(tgId);
        if (records.isEmpty()){
            sendMessage.setText("Вы не записаны ни к одному доктору");
            return sendMessage;
        }
        Set<DoctorEnum> doctorList = records.stream()
                .map(BookModel::getDoctorEnum)
                .collect(Collectors.toSet());
        List<KeyboardRow> rows = new ArrayList<>();
        int countButton = 0;
        rows.add(k);
                for (DoctorEnum doctor : doctorList){
                    if (countButton == 3){
                        k = new KeyboardRow();
                        rows.add(k);
                        countButton = 0;
                    }
                    k.add(new KeyboardButton(getNameDoctor(doctor)));
                    countButton++;
                }
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setKeyboard(rows);
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setText("Список ваших записей");
        return sendMessage;
    }

    private String getNameDoctor(DoctorEnum doctorEnum){
        switch (doctorEnum){
            case TERAPEVT -> {
                return "Запись к терапевту";
            }
            case OKULIST -> {
                return "Запись к окулисту";

            }
            case HIRURG ->{
                return "Запись к хирургу";
            }
            case TRAVMOTOLOG -> {
                return "Запись к травмотологу";

            }
            case GINEKOLOG -> {
                return "Запись к гинекологу";

            }
            case ALLERGOLOG -> {
                return "Запись к аллергологу";

            }
        }
        return "Данного доктора нет в списке";
    }
}
