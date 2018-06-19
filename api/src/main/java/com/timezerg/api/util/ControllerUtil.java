package com.timezerg.api.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by xnx on 2018/6/19.
 */
public class ControllerUtil {

    public static Date getDate(Integer year,Integer month,Integer day){
        if (year == null)
            return null;


        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month == null ? 0 : month);
        calendar.set(Calendar.DAY_OF_MONTH, day == null ? 1 : day);
        return calendar.getTime();
    }


}
