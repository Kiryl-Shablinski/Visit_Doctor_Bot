package com.example.spring_booking_bot;


import com.example.spring_booking_bot.commands.BookCommand;
import com.example.spring_booking_bot.commands.LoginCommand;
import com.example.spring_booking_bot.commands.WorkerCommand;
import com.example.spring_booking_bot.config.BotConfig;
import com.example.spring_booking_bot.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Component
public class Bot extends TelegramLongPollingBot {

    final    BotConfig botConfig;
    final UserRepo userRepo;

    public Bot( BotConfig botConfig, UserRepo userRepo){
        this.botConfig = botConfig;
        this.userRepo = userRepo;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        KeyboardRow k = new KeyboardRow();
        if (userRepo.findUserModelByTgId(update.getMessage().getFrom().getId().toString()) == null) {
          k.add(new KeyboardButton("Log In"));
        }
        k.add(new KeyboardButton("Записаться к врачу"));

        sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("выберите действие");

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setKeyboard(Collections.singletonList(k));
            sendMessage.setReplyMarkup(replyKeyboardMarkup);

            List<WorkerCommand> list = new ArrayList<>();
                list.add(new LoginCommand());
                list.add(new BookCommand());
            for (WorkerCommand w : list){
                if(w.start(update)!= null){
                    sendMessage = w.start(update);
                    break;
                }
            }

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
               e.printStackTrace();
            }
    }


}
