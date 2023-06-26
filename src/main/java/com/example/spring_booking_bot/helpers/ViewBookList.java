package com.example.spring_booking_bot.helpers;

import com.example.spring_booking_bot.models.BookModel;
import com.example.spring_booking_bot.models.UserModel;
import com.example.spring_booking_bot.repos.BookRepo;
import com.example.spring_booking_bot.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ViewBookList {
    private static ViewBookList viewBookList = null;

    final
    BookRepo bookRepo;

    public ViewBookList(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
        viewBookList = this;
    }

    public static List<BookModel> getBookList(String tgId){
        return viewBookList.bookRepo.findBookModelsByTgId(tgId);
    }
}
