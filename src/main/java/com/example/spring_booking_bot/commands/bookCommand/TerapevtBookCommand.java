package com.example.spring_booking_bot.commands.bookCommand;

import com.example.spring_booking_bot.commands.WorkerCommand;
import com.example.spring_booking_bot.helpers.DoctorEnum;
import com.example.spring_booking_bot.helpers.UserHelper;
import com.example.spring_booking_bot.models.UserModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TerapevtBookCommand  implements WorkerCommand {
    @Override
    public SendMessage start(Update update) {
        if (!update.getMessage().getText().equals("Терапевт")){
            return null;
        }
        UserModel userModel = UserHelper.findUser(update.getMessage().getFrom().getId().toString());
        userModel.setDoctorEnum(DoctorEnum.TERPEVT);
        UserHelper.saveUser(userModel);
        return null;
    }

    @Override
    public SendMessage sendDefaultMessage(Update update) {
        return null;
    }
}
