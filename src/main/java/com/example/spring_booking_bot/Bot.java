package com.example.spring_booking_bot;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.Collections;


@Component
public class Bot extends TelegramLongPollingBot {
  /*  final
    UserRepo userRepo;
    public Bot(UserRepo userRepo){
        this.userRepo = userRepo;
    }

   */

    @Override
    public String getBotUsername() {
        return "spring_visitdoctor_bot";
    }

    @Override
    public String getBotToken() {
        return "6122079098:AAHtx4nt11TfmWjydtM8iTK6p3RR_SA_8-U";
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        KeyboardRow k = new KeyboardRow();
        //if (userRepo.findUserModelByTgId(update.getMessage().getFrom().getId().toString()) == null){
            k.add(new KeyboardButton("Log In"));

            k.add(new KeyboardButton("записаться к врачу"));

            sendMessage.setChatId(update.getMessage().getChatId().toString());
        sendMessage.setText("выберите действие");

            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setKeyboard(Collections.singletonList(k));
            sendMessage.setReplyMarkup(replyKeyboardMarkup);

        /*    List<WorkerCommand> list = new ArrayList<>();
                list.add(new LoginCommand());
            for (WorkerCommand w : list){
                if(w.start(update)!= null){
                    sendMessage = w.start(update);
                    break;
                }
            }

         */
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
    }


}
