package com.liberty.libertylibrary.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by LinJinFeng on 2016/12/23.
 */

public class DateUtil {
    public static Date getDate(String dateStr, String pattern){
        Date date=null;
        SimpleDateFormat dateFormat= new SimpleDateFormat(pattern);
        try {
            date=dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     *
     * @param now
     * @param date
     * @return
     */
    public static boolean diffDate(Date now, Date date){
        long nowTmp=now.getTime();
        long dateTmp=date.getTime();
        long distance=nowTmp-dateTmp;
        if (distance>=0){
            return true;
        }else {
            return false;
        }
    }

    public static int compareDateInOneDate(Date now,Date date){
        long nowTimestamp=now.getTime();
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(nowTimestamp);
        Calendar calendar1=Calendar.getInstance();
        calendar1.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DATE));
        long nowDate=calendar1.getTimeInMillis();
        calendar.setTimeInMillis(date.getTime());
        calendar1.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DATE));
        long dateDate=calendar1.getTimeInMillis();
        if (nowDate==dateDate){
            return 0;
        }else if (nowDate>dateDate){
            return 1;
        }else {
            return 0;
        }
    }

    public static Date getToday(){
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(new Date());
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DATE);
        Calendar calendar1= Calendar.getInstance();
        calendar1.set(year,month,day);
        return calendar1.getTime();
    }

    public static Date getCurrentDate(){
        long time=System.currentTimeMillis();
        return new Date(time);
    }

    public static String DateToStr(Date date){
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * 计算年龄
     * @param birthday
     * @return
     */
    public static int calAge(Date birthday){
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(birthday);
        Calendar calendar1= Calendar.getInstance();
        calendar1.setTime(new Date());
        int age=calendar1.get(Calendar.YEAR)-calendar.get(Calendar.YEAR);
        int monthCompare=calendar.get(Calendar.MONTH)-calendar1.get(Calendar.MONTH);
        if (monthCompare>0){
            age--;
        }else if (monthCompare==0){
            int dayCompare=calendar.get(Calendar.DATE)-calendar1.get(Calendar.DATE);
            if (dayCompare>0){
                age--;
            }
        }
        return age;
    }
}
