package com.timezerg.api.util;

import com.timezerg.api.model.Nation;
import com.timezerg.api.model.Node;

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

    public static String getDisplayDateStr(Date cdate,Integer AD,Date ddate,Integer eAD,boolean showDetail){

        if (cdate == null)
            return null;

        String showStr = "";

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(cdate);

        if (AD == Node.BC_VALUE){
            showStr += "前 ";
        }
        showStr += calendar.get(Calendar.YEAR) + " ";
        showStr += "年 ";

        if (showDetail){
            //TODO 详细
        }

        if (ddate != null){

            showStr += "- ";
            showStr += ( eAD == Node.BC_VALUE ? "前 " : "");

            Calendar eCalendar = Calendar.getInstance();
            eCalendar.clear();
            eCalendar.setTime(ddate);
            showStr += eCalendar.get(Calendar.YEAR) + " ";
            showStr += "年 ";
        }

        return showStr;
    }



}
