package com.timezerg.api.mapper.provider;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by xnx on 2018/5/25.
 */
public class PeriodMapperProvider {

    public static String getList(final Map<String,Object> map){
        String searchtitle = (String) map.get("searchtitle");

        StringBuffer sql = new StringBuffer("(SELECT p.* FROM t_timezerg_period p WHERE 1 = 1 AND p.AD = 0 ");
        if (!StringUtils.isBlank(searchtitle)){
            sql.append(" AND p.title like CONCAT('%',#{searchtitle},'%') ");
        }
        sql.append("ORDER BY p.cdate DESC LIMIT 99999999) ");
        sql.append("UNION ALL ");
        sql.append("(SELECT p2.* FROM t_timezerg_period p2 WHERE 1 = 1 AND p2.AD = 1 ");
        if (!StringUtils.isBlank(searchtitle)){
            sql.append(" AND p2.title like CONCAT('%',#{searchtitle},'%') ");
        }
        sql.append("ORDER BY p2.cdate ASC LIMIT 99999999) ");
        sql.append("limit #{start},#{limit}");

        return sql.toString();
    }

    public static String getListTotal(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("select count(*) from t_timezerg_period");

        String searchtitle = (String) map.get("searchtitle");

        if (!StringUtils.isBlank(searchtitle)){
            sql.append(" where title like CONCAT('%',#{searchtitle},'%') ");
        }

        return sql.toString();
    }

    public static String selectListByPid(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("SELECT * FROM t_timezerg_period ");

        Object[] pids = (Object[]) map.get("pids");

        if (pids != null && pids.length >0){

            sql.append("WHERE pid IN (");

            for (int x = 0 ; x < pids.length ; x++){
                sql.append(pids[x]);
                sql.append(",");
            }
            Integer i = sql.lastIndexOf(",");
            sql.deleteCharAt(i);
            sql.append(") ");
        }else {
            sql.append(" WHERE 1 != 1");
        }

        return sql.toString();
    }


}
