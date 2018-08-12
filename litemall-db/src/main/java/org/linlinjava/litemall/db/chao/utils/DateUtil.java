package org.linlinjava.litemall.db.chao.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by machao on 2018/8/12.
 */
public class DateUtil {
    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDayTime = getBeginDayTime(new Date());
        Date endDayTime = getEndDayTime(new Date());
        Date beginMonthTime = getBeginMonthTime(new Date(),2);
        Date endMonthTime = getEndMonthTime(new Date(),2);
        System.out.println(format.format(beginDayTime));
        System.out.println(format.format(endDayTime));
        System.out.println(format.format(beginMonthTime));
        System.out.println(format.format(endMonthTime));
    }

    /**
     * 获取当天开始的时间
     *
     * @param date 传入的时间
     * @return date
     */
    public static Date getBeginDayTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天结束的时间
     *
     * @param date 传入的时间
     * @return date
     */
    public static Date getEndDayTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取当月开始的时间
     *
     * @param date 传入的时间
     * @return date
     */
    public static Date getBeginMonthTime(Date date,int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 0 - num);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当月结束的时间
     *
     * @param date 传入的时间
     * @return date
     */
    public static Date getEndMonthTime(Date date,int num){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1 - num);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * String装换成date
     *
     * @param date 传入的时间
     * @return date
     */
    public static Date StringToDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = null;
        try {
            time = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

}