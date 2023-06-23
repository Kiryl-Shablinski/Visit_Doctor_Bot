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
public class GinekologBookCommand implements WorkerCommand {
    public SendMessage start(Update update) {
        if (!update.getMessage().getText().equals("Гинеколог")){
            return null;
        }
        UserModel userModel = UserHelper.findUser(update.getMessage().getFrom().getId().toString());
        userModel.setDoctorEnum(DoctorEnum.GINEKOLOG);
        UserHelper.saveUser(userModel);
        return sendDefaultMessage(update);
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Выберите свободное время");
        List<String> listTime = DoctorHelper.getFreeTimes(DoctorEnum.GINEKOLOG);
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
