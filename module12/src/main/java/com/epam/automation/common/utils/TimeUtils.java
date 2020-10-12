package com.epam.automation.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeUtils {
    public static ZonedDateTime getNowZoneTime() {
        return ZonedDateTime.now();
    }

    public static Instant getNowInstant() {
        return Instant.now();
    }

    public static String getStrNowZoneTime() {
        return ZonedDateTime.now().toString();
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getLocalFormatTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    public static String getLocalFormatTime(int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        String date;
        if(day == 0)
            date = dateFormat.format(new Date());
        else {
            calendar.add(Calendar.DAY_OF_MONTH, day);
            date = dateFormat.format(calendar.getTime());
        }
        return date;
    }

    public static String getLocalFormatMonth(int month) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        String date;
        if(month == 0)
            date = dateFormat.format(new Date());
        else {
            calendar.add(Calendar.MONTH, month);
            date = dateFormat.format(calendar.getTime());
        }
        return date;
    }

    public static String getLocalFormatDay(int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String date;
        if(day == 0)
            date = dateFormat.format(new Date());
        else {
            calendar.add(Calendar.DAY_OF_MONTH, day);
            date = dateFormat.format(calendar.getTime());
        }
        return date;
    }

    public static String getZonedFormatDay(int day) {
        ZonedDateTime now = ZonedDateTime.now();
        if(day > 0)
            return now.plusDays(day).toInstant().toString();
        else if(day == 0)
            return now.toInstant().toString();
        else
            return now.minusDays(Math.abs(day)).toInstant().toString();
    }

    /**
     * yyyy-MM-dd
     * @return
     */
    public static String getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1); //得到前一天
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = df.format(date);
        return startDate;
    }

    /**
     * yyyy-MM-dd
     * @return
     */
    public static String getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1); //得到后一天
        Date date = calendar.getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String endDate = df.format(date);
        return endDate;
    }

    public static List<String> getPreviewMonth() {
        String month = TimeUtils.getLocalFormatMonth(0);
        int m = Integer.valueOf(month.substring(5));
        List<String> list = new ArrayList<>();
        while(m>0) {
            if(m>=10)
                list.add(month.substring(0, 4) + "-" + m);
            else
                list.add(month.substring(0, 4) + "-0" + m);
            m--;
        }
        return list;
    }

    public static String getYear() {
        String month = TimeUtils.getLocalFormatMonth(0);
        return month.substring(0, 4);
    }

    public static String getMonth() {
        String month = TimeUtils.getLocalFormatMonth(0);
        return month.substring(5);
    }
}
