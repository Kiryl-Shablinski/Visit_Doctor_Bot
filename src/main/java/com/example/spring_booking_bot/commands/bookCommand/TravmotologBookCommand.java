package com.example.spring_booking_bot.commands.bookCommand;

import com.example.spring_booking_bot.commands.WorkerCommand;
import com.example.spring_booking_bot.helpers.DoctorEnum;
import com.example.spring_booking_bot.helpers.DoctorHelper;
import com.example.spring_booking_bot.helpers.UserHelper;
import com.example.spring_booking_bot.models.UserModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class TravmotologBookCommand implements WorkerCommand {
    public SendMessage start(Update update) {
        if (!update.getMessage().getText().equals("Травмотолог")){
            return null;
        }
        UserModel userModel = UserHelper.findUser(update.getMessage().getFrom().getId().toString());
        userModel.setDoctorEnum(DoctorEnum.TRAVMOTOLOG);
        UserHelper.saveUser(userModel);
        return sendDefaultMessage(update);
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Выберите свободное время");
        List<String> listTime = DoctorHelper.getFreeTimes(DoctorEnum.TRAVMOTOLOG);
        //определяем количество строк с кнопками
        int keyBoardRowCount = (int) Math.ceil(listTime.size() / 2.0);
        List<KeyboardRow> listRow = new ArrayList<>();
        for (int l = 0; l < listTime.size();) {
            for (int i = 0; i < keyBoardRowCount; i++) {
                KeyboardRow row = new KeyboardRow();
                for (int j = 0; j < 2; j++) {
                    if (listTime.isEmpty()) break;
                    row.add(new KeyboardButton(listTime.get(l)));
                    listTime.remove(l);
                }
                listRow.add(row);
            }
        }

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(listRow);

        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }
}
