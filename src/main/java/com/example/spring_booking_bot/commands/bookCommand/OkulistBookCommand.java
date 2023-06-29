package com.example.spring_booking_bot.commands.bookCommand;

import com.example.spring_booking_bot.commands.WorkerCommand;
import com.example.spring_booking_bot.helpers.DoctorEnum;
import com.example.spring_booking_bot.helpers.DoctorHelper;
import com.example.spring_booking_bot.helpers.TimeHelper;
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
public class OkulistBookCommand implements WorkerCommand {

    public SendMessage start(Update update) {
        if (!update.getMessage().getText().equals("Окулист")){
            return null;
        }
        UserModel userModel = UserHelper.findUser(update.getMessage().getFrom().getId().toString());
        userModel.setDoctorEnum(DoctorEnum.OKULIST);
        UserHelper.saveUser(userModel);
        return sendDefaultMessage(update);
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Выберите свободное время");
        List<String> listTime = DoctorHelper.getFreeTimes(DoctorEnum.OKULIST);
        return TimeHelper.getSendMessage(listTime, sendMessage);
    }
}
