package com.example.spring_booking_bot.models;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name = "telegram_user")
@Data
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="username")
    String userName;

    @Column(name = "telegram_id")
    String tgId;

    @Column(name = "name")
    String name;
}
