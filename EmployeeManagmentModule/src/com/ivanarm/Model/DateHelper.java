/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivanarm.Model;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Ivan Kožul
 */
public class DateHelper {

    private static final int WORKING_HOURS = 8;
    private static final double MINUTES_TO_HOURS_RATIO = 0.0166667;

    private static final double REGULAR_WORKING_HOURS_RATIO = 25;
    private static final double OVERTIME_WORKING_HOURS_RATIO = 50;

    public static LocalTime timeCalculator(LocalDateTime d1, LocalDateTime d2) {
        int hours = d2.getHour()- d1.getHour();
        int minutes = d2.getMinute() - d1.getMinute();
        int seconds = d2.getSecond()- d1.getSecond();
        LocalTime t;
        t = LocalTime.of(hours, minutes, seconds, seconds);
        return t;
    }

    public static double paycheckCalculator(LocalDateTime d1, LocalDateTime d2) {
        LocalTime time = timeCalculator(d1, d2);
        double t;
        //If Employee is working overtime
        if ((time.getHour() - WORKING_HOURS) > 0 && time.getMinute() >= 0) {
            t = ((WORKING_HOURS + (time.getMinute() * MINUTES_TO_HOURS_RATIO)) * REGULAR_WORKING_HOURS_RATIO);
            t += (OVERTIME_WORKING_HOURS_RATIO * (time.getHour() - WORKING_HOURS));
            return (double) Math.round(t * 10d) / 10d;
        } else if ((time.getHour() >= WORKING_HOURS)) {
            t = (time.getHour() + (time.getMinute() * MINUTES_TO_HOURS_RATIO)) * REGULAR_WORKING_HOURS_RATIO;
            return (double) Math.round(t * 10d) / 10d;
        } else {
            return 0;
        }
    }

    static String GetDateTimeFormat() {
        return "yyyy/MM/dd hh:mm:ss";
    }
}
