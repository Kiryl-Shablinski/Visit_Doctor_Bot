package com.example.spring_booking_bot.repos;

import com.example.spring_booking_bot.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserModel,Long> {
    UserModel findUserModelByTgId(String id);
}
