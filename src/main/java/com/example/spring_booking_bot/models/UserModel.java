package com.example.spring_booking_bot.models;

import com.example.spring_booking_bot.helpers.DoctorEnum;
import jakarta.persistence.*;
import lombok.Data;



@Entity
@Table(name = "telegram_user", uniqueConstraints = @UniqueConstraint(columnNames = {"telegram_id"}))
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

    @Column(name = "wanted_doc")
    @Enumerated
    DoctorEnum doctorEnum;
}
