package com.timezerg.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xnx on 2015/4/30.
 * 时间工具类
 */
public class DateUtil {

    public static String Manager_Date_Format = "yyyy/MM/dd HH:mm";
    public static String YEAR_MONTH_DAY_TIME = "yyyy-MM-dd HH:mm:ss";
    public static String MONTH_DAY_YEAR_TIME = "MM/dd/yyyy HH:mm:ss";
    public static String YEAR_MONTH_DAY = "yy-MM-dd";
    public static String YYYYMMDDHH = "yyyyMMddHH";

    public static String YEAR_MONTH_DAY_INFO = "yyyy-MM-dd";


    public static String MONTH_DAY = "MM-dd";
    public static Date infinateDate;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2200);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 1);
        infinateDate = calendar.getTime();
    }


    /**
     * 获取字符串类型的时间
     *
     * @param date       时间
     * @param dateFormat 格式
     */
    public static String format(Date date, String dateFormat) {
        if (date == null)
            return "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
//        calendar.
        return simpleDateFormat.format(date);
    }

    /**
     * 获取dateRanger的前半部分时间
     * dateRanger格式 ：05/13/2015 - 05/15/2015
     *
     * @param dateRanger 时间区间
     */
    public static Date getStartDate(String dateRanger) {
        String date = dateRanger.split("-")[0].trim();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date1;
        try {
            date1 = simpleDateFormat.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
        return date1;
    }

    /**
     * 获取dateRanger的后半部分时间
     * dateRanger格式 ：05/13/2015 - 05/15/2015
     *
     * @param dateRanger 时间区间
     */
    public static Date getEndDate(String dateRanger) {
        String date = dateRanger.split("-")[1].trim();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date1;
        try {
            date1 = simpleDateFormat.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
        return date1;
    }

    /**
     * 反向获取daterange字符串
     * dateRanger格式 ：05/13/2015 - 05/15/2015
     *
     * @param sDate 开始时间
     * @param eDate 结束时间
     */
    public static String getDateRange(Date sDate, Date eDate) {
        return format(sDate, "MM/dd/yyyy") + " - " + format(eDate, "MM/dd/yyyy");
    }


    /**
     * 根据字符串获取date，格式是MM/dd/yy
     *
     * @param date 字符串时间
     */
    public static Date transferStrToDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yy");
        Date date1;
        try {
            date1 = simpleDateFormat.parse(date);
        } catch (ParseException ex) {
            return null;
        }
        return date1;
    }

    /**
     * 根据字符串获取date
     *
     * @param date   字符串时间
     * @param format 格式
     */
    public static Date transferStrToDate(String date, String format) {
        if (org.apache.commons.lang3.StringUtils.isBlank(date))
            return null;


        Date date1 = null;
        try {
             SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
             date1 = simpleDateFormat.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
        return date1;
    }


    /**
     * 获取几天后的时间
     *
     * @param date 时间
     * @param days 天数
     */
    public static Date AfterDayDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        Date d = calendar.getTime();
        return d;
    }

    public static Date AfterMonthDate(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        Date d = calendar.getTime();
        return d;
    }

    public static Date AfterWeekDate(Date date, int weeks) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, weeks);
        Date d = calendar.getTime();
        return d;
    }

    public static Date afterHourDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        Date d = calendar.getTime();
        return d;
    }

    public static Date earlyDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -days);
        Date d = calendar.getTime();
        return d;
    }

    public static Date afterSecond(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        Date d = calendar.getTime();
        return d;
    }

    public static Date afterMillSecond(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, second);
        Date d = calendar.getTime();
        return d;
    }

    /**
     * 获得输入Date的当晚23：59：59
     *
     * @param date 时间
     */
    public static Date lastMinuteOfDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        //将Date转换为开始
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

//        calendar.add(Calendar.HOUR_OF_DAY, 23);
//        calendar.add(Calendar.MINUTE, 59);
        Date d = calendar.getTime();
        return d;
    }


    /**
     * 比较时间
     */
    public static Integer compareDate(Date src, Date dec) {

//        System.out.println("src : " + src + " - dec : " + dec);
        Integer result = 0;

        String ed = DateUtil.format(src, YEAR_MONTH_DAY_TIME);
        String infin = DateUtil.format(dec, YEAR_MONTH_DAY_TIME);
//        System.out.println(" ed.equals(infin) : " +  ed.equals(infin));

        if (src.before(dec)) {
            result = -1;
        } else if (dec.before(src)) {
            result = 1;
        } else if (ed.equals(infin)) {
            result = 0;
        }else
            result = null;


        return result;
    }


    /**
     * 获得现场的时间
     * 例如：15/6/2015 - 15/7/2015   ->   6月15日-7月15日，2015
     * 15/6/2015 - 15/7/2016   ->   6月15日-7月15日，2015-2016
     */
    public static String formatIncidentDate(Date bdate, Date edate) {
        Calendar bCalendar = Calendar.getInstance();
        bCalendar.setTime(bdate);
        int bMonth = bCalendar.get(Calendar.MONTH);
        int bDay = bCalendar.get(Calendar.DAY_OF_MONTH);
        int bYear = bCalendar.get(Calendar.YEAR);

        bCalendar.setTime(edate);
        int eMonth = bCalendar.get(Calendar.MONTH);
        int eDay = bCalendar.get(Calendar.DAY_OF_MONTH);
        int eYear = bCalendar.get(Calendar.YEAR);

        String rtnString = bMonth + "月" + bDay + "日" + " - " + eMonth + "月" + eDay + "日 ，";
        if (bYear == eYear)
            rtnString += bYear;
        else
            rtnString += bYear + "-" + eYear;
        return rtnString;
    }

    /**
     * 获取date中的某个域
     *
     * @param date  时间
     * @param field 某个域
     */
    public static int getDateInfo(Date date, Integer field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(field);
    }


    public static Date getDate(Integer year, Integer month, Integer day) {
//        System.out.println("year : " + year);
//        System.out.println("month : " + month);
//        System.out.println("day : " + day);
        Calendar calendar = Calendar.getInstance();
        if (year == null && month == null && day == null)
            return null;
        calendar.set(Calendar.YEAR, year);
        if (month == null)
            calendar.set(Calendar.YEAR, 1);
        else
            calendar.set(Calendar.MONTH, month - 1);
        if (day == null)
            calendar.set(Calendar.DAY_OF_MONTH, 0);
        else
            calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 1);

        return calendar.getTime();
    }

    public static Long getApartMinute(Date date,Date compareDate){

        String d1 = format(date,YEAR_MONTH_DAY_TIME);
        String d2 = format(compareDate,YEAR_MONTH_DAY_TIME);
//        System.out.println("d1 : " + d1);
//        System.out.println("d2 : " + d2);


        long time1 = date.getTime();
        long time2 = compareDate.getTime();
        long diff ;
        if(time1<time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
//        System.out.println("diff : " + diff);

        long minutes = diff / (1000 * 60);
        return minutes;
    }


    public static void main(String args[]) {
        System.out.println(getDate(1990, 5, 9));

    }



}
