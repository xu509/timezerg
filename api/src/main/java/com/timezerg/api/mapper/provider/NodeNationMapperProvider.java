package com.timezerg.api.mapper.provider;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by xnx on 2018/5/25.
 */
public class NodeNationMapperProvider {

    public static String selectNodesByNationIds(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("SELECT n.* FROM t_timezerg_node_nation nn ");
        sql.append("LEFT JOIN t_timezerg_node n ON nn.nodeid = n.id ");

        Object[] nids = (Object[]) map.get("nids");

        if (nids != null && nids.length >0){

            sql.append("WHERE nn.nationid IN (");

            for (int x = 0 ; x < nids.length ; x++){
                sql.append(nids[x]);
                sql.append(",");
            }
            Integer i = sql.lastIndexOf(",");
            sql.deleteCharAt(i);
            sql.append(") ");
        }
        sql.append("group by n.id");
        return sql.toString();
    }


}
