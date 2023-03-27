package com.dl.test.dateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;


/**
 * 日期时间和字符串互转
 */
public class DateFormat {
    public static void main(String[] args) {
        System.out.println("SimpleDateFormat");
        formatToString();
        parseToDate();

        System.out.println("DateTimeFormatter");
        ofpattern();
    }

    /**
     * DateTimeFormatter来实现互转
     */
    private static void ofpattern() {
        // 解析日期
        String str1 = "2023年 12月 23日";
        DateTimeFormatter dtfToDate = DateTimeFormatter.ofPattern("yyyy年 MM月 dd日");
        System.out.println(LocalDate.parse(str1, dtfToDate));

        // 日期转字符串
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy年 MM月 dd日 hh:mm a");
        System.out.println(now.format(dateTimeFormatter));
    }

    /**
     * 字符串变日期时间
     */
    private static void parseToDate() {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            format1.setTimeZone(TimeZone.getTimeZone("GMT-10:00"));

            SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd");

            String str1 = "2023-12-09 23:15:12:333";
            String str2 = "2023/12/09";

            System.out.println(format1.parse(str1));
            System.out.println(format2.parse(str2));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期时间变字符串
     */
    private static void formatToString() {
        Date date = new Date();
        System.out.println(date);

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat format3 = new SimpleDateFormat("HH:mm:ss");

        System.out.println(format1.format(date));
        System.out.println(format2.format(date));
        System.out.println(format3.format(date));
    }
}
