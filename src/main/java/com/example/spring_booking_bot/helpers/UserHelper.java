package com.example.spring_booking_bot.helpers;

import com.example.spring_booking_bot.models.UserModel;
import com.example.spring_booking_bot.repos.UserRepo;
import org.springframework.stereotype.Component;

@Component
public class UserHelper {
   final
   UserRepo userRepo;
    private static UserHelper helper = null;

    public UserHelper(UserRepo userRepo){
        helper = this;
        this.userRepo = userRepo;
    }

    public static void saveUser(UserModel user){
        helper.userRepo.save(user);
    }
}
