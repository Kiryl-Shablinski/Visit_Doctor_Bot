package com.example.spring_booking_bot.helpers;

import com.example.spring_booking_bot.repos.BookRepo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteHelper {
    @Getter
    private static DeleteHelper deleteHelper = null;

    @Getter
    @Setter
    private DoctorEnum doctorEnum;

    @Autowired
    BookRepo bookRepo;

    public DeleteHelper(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
        deleteHelper = this;
    }

    public static void deleteVisit(String tgId, DoctorEnum doctorEnum, String time){
        deleteHelper.bookRepo.deleteBookModelByTgIdAndDoctorEnumAndTime(tgId, doctorEnum, time);
    }
}
