package com.timezerg.api.mapper.provider;

import java.util.Map;

/**
 * Created by xnx on 2018/5/25.
 */
public class NodeMapperProvider {

    public static String getList(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("(SELECT n.* FROM t_timezerg_node n LEFT JOIN t_timezerg_node_civilization nc ON n.id = nc.nid WHERE 1 = 1 AND n.Ad = 0 ");

        Object[] civilizations = (Object[]) map.get("cids");

        if (civilizations != null && civilizations.length >0){
            sql.append("AND nc.cid in(");
            for (int x = 0 ; x < civilizations.length ; x++){
                sql.append(civilizations[x]);
                sql.append(",");
            }
            Integer i = sql.lastIndexOf(",");
            sql.deleteCharAt(i);
            sql.append(")");
        }
        sql.append(" ORDER BY n.cdate DESC LIMIT 99999999)");
        sql.append(" UNION ALL ");
        sql.append("(SELECT n.* FROM t_timezerg_node n LEFT JOIN t_timezerg_node_civilization nc ON n.id = nc.nid WHERE 1 = 1 AND n.Ad = 1 ");
        if (civilizations != null && civilizations.length >0){
            sql.append("AND nc.cid in(");
            for (int x = 0 ; x < civilizations.length ; x++){
                sql.append(civilizations[x]);
                sql.append(",");
            }
            Integer i = sql.lastIndexOf(",");
            sql.deleteCharAt(i);
            sql.append(")");
        }
        sql.append(" ORDER BY n.cdate ASC LIMIT 99999999)");
        sql.append(" LIMIT #{start},#{limit}");


        return sql.toString();
    }

    public static String getListTotal(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("SELECT count(*) from ((SELECT n.* FROM t_timezerg_node n LEFT JOIN t_timezerg_node_civilization nc ON n.id = nc.nid WHERE 1 = 1 AND n.Ad = 0 ");

        Object[] civilizations = (Object[]) map.get("cids");

        if (civilizations != null && civilizations.length >0){
            sql.append("AND nc.cid in(");
            for (int x = 0 ; x < civilizations.length ; x++){
                sql.append(civilizations[x]);
                sql.append(",");
            }
            Integer i = sql.lastIndexOf(",");
            sql.deleteCharAt(i);
            sql.append(")");
        }
        sql.append(" ORDER BY n.cdate DESC LIMIT 99999999)");
        sql.append(" UNION ALL ");
        sql.append("(SELECT n.* FROM t_timezerg_node n LEFT JOIN t_timezerg_node_civilization nc ON n.id = nc.nid WHERE 1 = 1 AND n.Ad = 1 ");
        if (civilizations != null && civilizations.length >0){
            sql.append("AND nc.cid in(");
            for (int x = 0 ; x < civilizations.length ; x++){
                sql.append(civilizations[x]);
                sql.append(",");
            }
            Integer i = sql.lastIndexOf(",");
            sql.deleteCharAt(i);
            sql.append(")");
        }
        sql.append(" ORDER BY n.cdate ASC LIMIT 99999999)) t");

        return sql.toString();
    }

}
