package com.timezerg.api.mapper.provider;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by xnx on 2018/5/25.
 */
public class NationPeriodMapperProvider {

    public static String selectAllNationBeanByPid(final Map<String,Object> map){
        Object[] pids = (Object[]) map.get("pids");


        StringBuffer sql = new StringBuffer("SELECT n.* FROM t_timezerg_period p " +
                "LEFT JOIN t_timezerg_nation_period np ON p.id = np.pid " +
                "RIGHT JOIN t_timezerg_nation n ON np.nid = n.id " +
                "WHERE 1 = 1 ");

        if (pids.length > 0){
            sql.append("and p.id in (");
            for (int x = 0 ; x < pids.length ; x++){
                sql.append(pids[x]);
                sql.append(",");
            }
            Integer i = sql.lastIndexOf(",");
            sql.deleteCharAt(i);
            sql.append(") ");
        }else {
            sql.append("AND 2 != 2 ");
        }

        sql.append("group by n.id");
        return sql.toString();
    }

}
