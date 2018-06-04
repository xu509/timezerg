package com.timezerg.api.util;

import com.timezerg.api.model.Node;

import java.util.Date;

/**
 * Created by xnx on 2018/5/4.
 */
public class Utils {

    private static final IdWorker worker = new IdWorker(1);

    /**
     * 生成ID
     *
     * @return
     */
    public static String generateId() {
        return String.valueOf(worker.nextId());
    }

    public static String transferToJsonStr(boolean b){
        if (b)
            return "1";
        else
            return "0";
    }



    public static int compareTime(Integer fad,Date fdate,Integer tad,Date tdate){

//        System.out.println("from : " + DateUtil.format(fdate,DateUtil.YEAR_MONTH_DAY_TIME) + " - to :"
//                + DateUtil.format(tdate,DateUtil.YEAR_MONTH_DAY_TIME));

        if (fad.equals(tad) && (fdate.getTime() == tdate.getTime())){
                return 0;
        }
        if (fad.equals(tad) && (fad.equals(Node.AD_VALUE))){
            if (fdate.getTime() < tdate.getTime()){
                return -1;
            }else {
                return 1;
            }
        }
        if (fad.equals(tad) && (fad.equals(Node.BC_VALUE))){
            if (fdate.getTime() < tdate.getTime()){
                return 1;
            }else {
                return -1;
            }
        }
        if (fad < tad){
            return -1;
        }
        if (fad > tad){
            return 1;
        }

        return 0;

    }



}
