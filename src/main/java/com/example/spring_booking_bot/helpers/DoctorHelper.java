package com.example.spring_booking_bot.helpers;

import com.example.spring_booking_bot.models.BookModel;
import com.example.spring_booking_bot.repos.BookRepo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DoctorHelper {

    final
    BookRepo bookRepo;
private static DoctorHelper doctorHelper = null;


    public DoctorHelper(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
        doctorHelper = this;
    }
    public void save(BookModel bookModel){
        doctorHelper.bookRepo.save(bookModel);
    }

    public static List<String> getFreeTimes(DoctorEnum doctorEnum){
        TimeControl timeControl = new TimeControl();
        List<BookModel> bookModelList =
                doctorHelper.bookRepo.findBookModelsByDoctor(doctorEnum);
        List<String> freeTimes = timeControl.getTimes();
        List<String> timeBookModelList = bookModelList.stream()
                .map(BookModel::getTime)
                .toList();

     freeTimes = freeTimes.stream()
             .filter(x -> !timeBookModelList.contains(x))
             .toList();
      /*  List<String> list = new ArrayList<>();
        for (BookModel b : bookModelList){
            for (String str : freeTimes){
                if (b.getTime().equals(str)){
                    list.add(b.getTime());
                }
            }
        }
       */
        return freeTimes;

    }

}
