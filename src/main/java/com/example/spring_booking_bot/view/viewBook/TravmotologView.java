package com.example.spring_booking_bot.view.viewBook;

import com.example.spring_booking_bot.commands.WorkerCommand;
import com.example.spring_booking_bot.helpers.DeleteHelper;
import com.example.spring_booking_bot.helpers.DoctorEnum;
import com.example.spring_booking_bot.helpers.ViewHelper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class TravmotologView implements WorkerCommand {
    @Override
    public SendMessage start(Update update) {
        if (!update.getMessage().getText().equals("Запись к травмотологу")){
            return null;
        }
        return sendDefaultMessage(update);

    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Время вашей записи");
        DeleteHelper.getDeleteHelper().setDoctorEnum(DoctorEnum.TRAVMOTOLOG);
        List<String> listTime = ViewHelper.getDoctorTime(DoctorEnum.TRAVMOTOLOG);
        return ViewHelper.getTimeSendMessage(listTime,sendMessage);
    }
}
