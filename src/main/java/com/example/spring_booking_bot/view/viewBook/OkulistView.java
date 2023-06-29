package com.example.spring_booking_bot.view.viewBook;

import com.example.spring_booking_bot.commands.WorkerCommand;
import com.example.spring_booking_bot.helpers.DoctorEnum;
import com.example.spring_booking_bot.helpers.ViewHelper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


@Component public class OkulistView implements WorkerCommand {
    @Override
    public SendMessage start(Update update) {
        if (!update.getMessage().getText().equals("Запись к окулисту")){
            return null;
        }
        return sendDefaultMessage(update);

    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Время вашей записи");
        List<String> listTime = ViewHelper.getDoctorTime(DoctorEnum.OKULIST);
        return ViewHelper.getTimeSendMessage(listTime,sendMessage);
    }
}
