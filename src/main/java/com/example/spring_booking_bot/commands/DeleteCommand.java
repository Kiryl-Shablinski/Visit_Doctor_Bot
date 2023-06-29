package com.example.spring_booking_bot.commands;

import com.example.spring_booking_bot.helpers.DoctorHelper;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DeleteCommand implements WorkerCommand{
    @Override
    public SendMessage start(Update update) {
        if (!update.getMessage().getText().endsWith(" отменить запись"))
        return null;
        SendMessage sendMessage = new SendMessage();
        String tgId = update.getMessage().getFrom().getId().toString();
        DoctorHelper.deleteTimes(tgId);
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("Ваша запись удалена");
        return sendMessage;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}
