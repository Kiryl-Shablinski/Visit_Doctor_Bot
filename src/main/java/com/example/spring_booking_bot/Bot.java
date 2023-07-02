package com.example.spring_booking_bot;


import com.example.spring_booking_bot.commands.*;
import com.example.spring_booking_bot.commands.bookCommand.*;
import com.example.spring_booking_bot.config.BotConfig;
import com.example.spring_booking_bot.view.ViewDoctor;
import com.example.spring_booking_bot.repos.UserRepo;
import com.example.spring_booking_bot.view.viewBook.*;
import org.springframework.stereotype.Component;
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

    final BotConfig botConfig;
    final UserRepo userRepo;

    public Bot(BotConfig botConfig, UserRepo userRepo) {
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

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(Collections.singletonList(k));

        List<WorkerCommand> list = new ArrayList<>();
        list.add(new LoginCommand());
        list.add(new BookCommand());
        list.add(new TerapevtBookCommand());
        list.add(new TravmotologBookCommand());
        list.add(new HirurgBookCommand());
        list.add(new OkulistBookCommand());
        list.add(new GinekologBookCommand());
        list.add(new AllergologBookCommand());
        list.add(new ChooseTime());
        list.add(new ViewDoctor());
        list.add(new AllergologView());
        list.add(new GinekologView());
        list.add(new HirurgView());
        list.add(new OkulistView());
        list.add(new TerapevtView());
        list.add(new TravmotologView());
        list.add(new DeleteCommand());
        for (WorkerCommand w : list) {
            sendMessage = w.start(update);
            if (sendMessage != null) {
                break;
            }
        }
        if (sendMessage == null) {
            sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            sendMessage.setText("выберите действие");
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
