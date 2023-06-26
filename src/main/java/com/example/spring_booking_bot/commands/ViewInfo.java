package com.example.spring_booking_bot.commands;

import com.example.spring_booking_bot.helpers.DoctorEnum;
import com.example.spring_booking_bot.helpers.ViewBookList;
import com.example.spring_booking_bot.models.BookModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class ViewInfo implements WorkerCommand{

    @Override
    public  SendMessage start(Update update) {
       SendMessage sendMessage = new SendMessage();
       String text = update.getMessage().getText();
        if (!text.startsWith("/command2")) {
            return null;
        }
        sendMessage.setChatId(update.getMessage().getChatId().toString());
        String tgId = update.getMessage().getFrom().getId().toString();
            List<BookModel> records = ViewBookList.getBookList(tgId);
        if (records.isEmpty()){
            sendMessage.setText("Вы не записаны ни к одному доктору");
            return sendMessage;
        }
        StringBuilder stb = new StringBuilder();
        for (BookModel item : records){
            String doctor = item.getDoctorEnum().name();
            String time = item.getTime();
            stb.append(doctor).append(" : ").append(time).append("\n");
        }
        sendMessage.setText(stb.toString());
        return sendMessage;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}
