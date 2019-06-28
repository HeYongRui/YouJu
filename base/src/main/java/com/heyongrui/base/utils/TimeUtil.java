package com.heyongrui.base.utils;

import android.text.TextUtils;

import com.blankj.utilcode.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by lambert on 2018/11/5.
 */

public class TimeUtil {
    public final static String SEC = "yyyy-MM-dd HH:mm:ss";
    public final static String MIN = "yyyy-MM-dd HH:mm";
    public final static String MIN_TWO = "MM-dd HH:mm";
    public final static String HOURS_MIN = "HH:mm";
    public final static String MIN_SEC = "mm:ss";
    public final static String DAY_ONE = "yyyy-MM-dd";
    public final static String DAY_TWO = "yyyy.MM.dd";
    public final static String DAY_THREE = "MM-dd";
    public final static String DAY_FOUR = "MM月dd日";
    public final static String DAY_FIVE = "yyyy年MM月dd日";
    public final static long nd = 1000 * 24 * 60 * 60;
    public final static long nh = 1000 * 60 * 60;
    public final static long nm = 1000 * 60;
    public final static long ns = 1000;

    /**
     * 将日期转成指定格式的日期字符串
     */
    public static String getDateString(Date date, String targetDateType) {
        if (date == null) {
            throw new NullPointerException("date is NullPointerException");
        }
        if (TextUtils.isEmpty(targetDateType)) {
            throw new NullPointerException("please set a target date format");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(targetDateType);
        String datestring = sdf.format(date);
        return datestring;
    }

    /**
     * 将日期字符串转成指定格式的日期
     */
    public static Date getDate(String dateString, String originTimeFormat) {
        Date date = null;
        if (!StringUtils.isTrimEmpty(dateString)) {
            DateFormat dateFormat = new SimpleDateFormat(originTimeFormat);
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 判断是否是下午
     */
    public static boolean isPM() {
        GregorianCalendar ca = new GregorianCalendar();
        int isPM = ca.get(GregorianCalendar.AM_PM);
        return isPM == 1;
    }

    /**
     * 获取指定日期的星期几(0...6代表周日到周六)
     */
    public static int getWeek(Date date) {
        if (date == null) {
            throw new NullPointerException("date is NullPointerException");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int weekday = c.get(Calendar.DAY_OF_WEEK) - 1;
        return weekday < 0 ? 0 : weekday;
    }

    /**
     * 将毫秒转换成目标格式的字符串
     */
    public static String msToString(long millisecond, String targetDateType) {
        SimpleDateFormat formatter = new SimpleDateFormat(targetDateType);
        return formatter.format(millisecond);
    }

    /**
     * 将时间格式的字符串转换成对应的毫秒
     */
    public static long stringToms(String timeString) {
        if (timeString.matches("([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])")) {
            //正则判断是否满足HH:mm:ss格式
            String[] my = timeString.split(":");
            int hour = Integer.parseInt(my[0]);
            int min = Integer.parseInt(my[1]);
            int sec = Integer.parseInt(my[2]);
            long totalSec = hour * 3600 + min * 60 + sec;
            return totalSec;
        } else {
            throw new IllegalArgumentException("timeString is Illegal");
        }
    }

    /**
     * 根据时间戳获取与当前时间的差值
     */
    public static String getDistanceTime(long timestamp) {
        long day = 0;
        long hour = 0;
        long min = 0;
//        long sec = 0;
        long currentTimeMillis = System.currentTimeMillis();
        long diff = currentTimeMillis - timestamp;
        day = Math.abs(diff / (24 * 60 * 60 * 1000));
        hour = Math.abs((diff / (60 * 60 * 1000) - day * 24));
        min = Math.abs(((diff / (60 * 1000)) - day * 24 * 60 - hour * 60));
//        sec = Math.abs((diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60));
        if (day == 0) {
            if (hour == 0) {
                return min + "分钟" + (diff > 0 ? "前" : "后");
            } else {
                return hour + "小时" + (diff > 0 ? "前" : "后");
            }
        } else if (day == 1) {
            return diff > 0 ? "昨天" : "明天";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(DAY_ONE);
            return sdf.format(new Date(timestamp));
        }
    }
}
