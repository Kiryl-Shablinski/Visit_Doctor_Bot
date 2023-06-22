package com.example.spring_booking_bot.helpers;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TimeControl {
    private List<String> times;

    public TimeControl(){
        times = setCollection();
    }

    private List<String> setCollection(){
        List<String> list = new ArrayList<>();
        for (int i = 10; i < 19 ; i++) {
            list.add(i + ".00");
        }
        return list;
    }
}
