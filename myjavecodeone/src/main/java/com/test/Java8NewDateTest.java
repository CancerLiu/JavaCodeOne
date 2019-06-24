package com.test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Java8NewDateTest {

    public void testOne() {
        LocalDateTime dateTime1 = LocalDateTime.now();
        LocalDate date1 = LocalDate.now();
        LocalTime time1 = LocalTime.now();

        LocalDate date2 = date1.plusDays(1);
        LocalTime time2 = time1.plusHours(2);
        LocalDateTime dateTime2 = dateTime1.plusHours(3);
        System.out.println(Duration.between(dateTime1, dateTime2));
        System.out.println(Duration.from(ChronoUnit.DAYS.getDuration()).toHours());
    }


    public static void main(String[] args) {
        Java8NewDateTest java8NewDateTest = new Java8NewDateTest();
        java8NewDateTest.testOne();
    }
}
