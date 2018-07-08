package com.timezerg.api.mapper.provider;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by xnx on 2018/5/25.
 */
public class NationMapperProvider {

    public static String getList(final Map<String,Object> map){
        String searchtitle = (String) map.get("searchtitle");

        StringBuffer sql = new StringBuffer("(SELECT n.* FROM t_timezerg_nation n WHERE 1 = 1 AND n.AD = 0 ");
        if (!StringUtils.isBlank(searchtitle)){
            sql.append(" AND n.title like CONCAT('%',#{searchtitle},'%') ");
        }
        sql.append("ORDER BY n.cdate DESC LIMIT 99999999) ");
        sql.append("UNION ALL ");
        sql.append("(SELECT n2.* FROM t_timezerg_nation n2 WHERE 1 = 1 AND n2.AD = 1 ");
        if (!StringUtils.isBlank(searchtitle)){
            sql.append(" AND n2.title like CONCAT('%',#{searchtitle},'%') ");
        }
        sql.append("ORDER BY n2.cdate ASC LIMIT 99999999) ");
        sql.append("limit #{start},#{limit}");

        return sql.toString();
    }

    public static String getListTotal(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("select count(*) from t_timezerg_nation");

        String searchtitle = (String) map.get("searchtitle");

        if (!StringUtils.isBlank(searchtitle)){
            sql.append(" where title like CONCAT('%',#{searchtitle},'%') ");
        }

        return sql.toString();
    }

    public static String selectListByFid(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("SELECT * FROM t_timezerg_nation ");

        Object[] pids = (Object[]) map.get("fids");

        if (pids != null && pids.length >0){

            sql.append("WHERE fid IN (");

            for (int x = 0 ; x < pids.length ; x++){
                sql.append(pids[x]);
                sql.append(",");
            }
            Integer i = sql.lastIndexOf(",");
            sql.deleteCharAt(i);
            sql.append(") ");
        } else {
            sql.append("WHERE 1 != 1");
        }

        return sql.toString();
    }


}
