package com.timezerg.api.util;

import com.timezerg.api.config.AppConfig;
import com.timezerg.api.model.Node;
import com.timezerg.api.model.Period;

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


    public static String getLevelStr(Integer level){
        String str;
        if (level == null){
            str = "未归类";
        } else if (level.equals(AppConfig.KEY_VALUE.Level_Very_Important)){
            str = "非常重要";
        } else {
            str = "其他";
        }
        return str;
    }


    public static boolean checkNodeDateInPeriod(Date nodeDate,Integer nodeAD, Period period){
        Date startTime = period.getCdate();
        Integer startAD = period.getAD();
        Date endTime = period.getEdate();
        Integer endAD = period.geteAD();

        if (startTime == null || endTime == null)
            return false;

        if (nodeAD.equals(AppConfig.KEY_VALUE.BC) && startAD.equals(AppConfig.KEY_VALUE.AD)){
            return false;
        }

        if (nodeAD.equals(AppConfig.KEY_VALUE.AD) && endAD.equals(AppConfig.KEY_VALUE.BC)){
            return false;
        }

        if (nodeAD.equals(AppConfig.KEY_VALUE.BC) && endAD.equals(AppConfig.KEY_VALUE.BC)){
            //当时代与节点都在公元前
            if (nodeDate.after(startTime))
                return false;
            else if (nodeDate.before(endTime))
                return false;
            else
                return true;
        }

        if (nodeAD.equals(AppConfig.KEY_VALUE.AD) && startAD.equals(AppConfig.KEY_VALUE.AD)){
            //当时代与节点都在公元后
            if (nodeDate.after(endTime))
                return false;
            else if (nodeDate.before(startTime))
                return false;
            else
                return true;
        }

        if (startAD.equals(AppConfig.KEY_VALUE.BC) && endAD.equals(AppConfig.KEY_VALUE.AD)){
            //当时代跨越公元前后
            if (nodeAD.equals(AppConfig.KEY_VALUE.BC)){
                return nodeDate.before(startTime);
            }else {
                return nodeDate.before(endTime);
            }
        }

        return true;
    }



}
