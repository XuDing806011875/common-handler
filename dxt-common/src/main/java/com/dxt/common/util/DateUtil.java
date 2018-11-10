package com.dxt.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    /**
     * 获取前后相差天数
     * @param firstCal
     * @param secordCal
     * @return
     */
    public static long getDiffDay(Calendar firstCal, Calendar secordCal) {
        long diffTime = firstCal.getTimeInMillis() - secordCal.getTimeInMillis();
        long diffDay = diffTime / 86400000L;
        return diffDay;
    }

    /**
     * 获取前后相差天数
     * @param firstCal
     * @param secordCal
     * @return
     */
    public static long getDiffDay(Date firstCal, Date secordCal) {
        long diffTime = firstCal.getTime() - secordCal.getTime();
        long diffDay = diffTime / 86400000L;
        return diffDay;
    }

    /**
     * 判断时间戳是不是当天
     * @param timeStamp
     * @return
     */
    public static boolean isToday(Long timeStamp) {
        if (timeStamp != null) {
            Long now = Long.valueOf(System.currentTimeMillis());
            if (now.longValue() / 86400000L == timeStamp.longValue() / 86400000L) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断时间戳是不是昨天
     * @param timeStamp
     * @return
     */
    public static boolean isYesTerday(Long timeStamp) {
        if (timeStamp != null) {
            Calendar cal = Calendar.getInstance();
            cal.add(6, -1);
            if (cal.getTimeInMillis() / 86400000L == timeStamp.longValue() / 86400000L) {
                return true;
            }
        }
        return false;
    }

    /**
     * date转string 格式yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormat.format(date);
    }

    /**
     * date转string 格式yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateTimeFormat.format(date);
    }

    /**
     * string转date 格式yyyy-MM-dd
     * @param date
     * @return
     */
    public static Date parserDate(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        return dateFormat.parse(date);
    }

    /**
     * string转date 格式自定义
     * @param date
     * @param pattern
     * @return
     */
    public static Date parser(String date, String pattern) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setLenient(false);
        return dateFormat.parse(date);
    }

    /**
     * string转date 格式yyyy-MM-dd HH:mm:ss
     * @param dateTime
     * @return
     */
    public static Date parserDateTime(String dateTime) throws ParseException {
        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateTimeFormat.setLenient(false);
        return dateTimeFormat.parse(dateTime);
    }

    /**
     * 当前时间转string 格式yyyyMM
     * @return
     */
    public static String getCurrentYearMonth() {
        DateFormat yyyyMMFormat = new SimpleDateFormat("yyyyMM");
        return yyyyMMFormat.format(new Date());
    }

    /**
     * date转string 格式yyyyMM
     * @param date
     * @return
     */
    public static String getYearMonth_YYYYMM(Date date) {
        DateFormat yyyyMMFormat = new SimpleDateFormat("yyyyMM");
        return yyyyMMFormat.format(date);
    }

    /**
     * 在date上加年限
     * @param date
     * @param year
     * @return
     */
    public static Date addYear(Date date, Integer year) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(1, year.intValue());
        return calendar.getTime();
    }

    /**
     * 在当前时间上加年限
     * @param year
     * @return
     */
    public static Date addYear(Integer year) {
        return addYear(new Date(), year);
    }

    /**
     * 在date上加月份
     * @param date
     * @param month
     * @return
     */
    public static Date addMonth(Date date, Integer month) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(2, month.intValue());
        return calendar.getTime();
    }

    /**
     * 在当前时间上加月份
     * @param month
     * @return
     */
    public static Date addMonth(Integer month) {
        return addMonth(new Date(), month);
    }

    /**
     * 在当前时间上加天数
     * @param day
     * @return
     */
    public static Date addDay(Integer day) {
        return addDay(new Date(), day);
    }

    /**
     * 在date上加天数
     * @param date
     * @param day
     * @return
     */
    public static Date addDay(Date date, Integer day) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(5, day.intValue());
        return calendar.getTime();
    }

    /**
     * 在当前时间上加小时
     * @param hour
     * @return
     */
    public static Date addHour(Integer hour) {
        return addHour(new Date(), hour);
    }

    /**
     * 在date上加小时
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour(Date date, Integer hour) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(11, hour.intValue());
        return calendar.getTime();
    }

    /**
     * 在date上加分钟
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute(Date date, Integer minute) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(12, minute.intValue());
        return calendar.getTime();
    }

    /**
     * 在当前时间上加分钟
     * @param minute
     * @return
     */
    public static Date addMinute(Integer minute) {
        return addMinute(new Date(), minute);
    }

    /**
     * 在date上加秒
     * @param date
     * @param second
     * @return
     */
    public static Date addSecond(Date date, Integer second) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(13, second.intValue());
        return calendar.getTime();
    }

    /**
     * 在当前时间上加秒
     * @param second
     * @return
     */
    public static Date addSecond(Integer second) {
        return addSecond(new Date(), second);
    }

    /**
     * 获取当前日期格式yyyy-MM-dd
     * @return
     */
    public static String getToday() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }
}
