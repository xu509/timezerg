package com.timezerg.api.mapper.provider;

import java.util.Map;

/**
 * Created by xnx on 2018/5/25.
 */
public class CivilizationMapperProvider {

    public static String selectTimeLine(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("select t.* from ((SELECT n.* FROM t_timezerg_node n " +
                "LEFT JOIN t_timezerg_node_civilization nc ON n.id = nc.nid " +
                "WHERE nc.cid = #{cid} AND n.ad = 0 ");
        if ((Integer)map.get("level") != 0){
            sql .append("and n.level <= #{level} ");
        }
        sql.append("ORDER BY n.cdate DESC LIMIT 0,9999)" +
                " UNION ALL " +
                "(SELECT n.* FROM t_timezerg_node n " +
                "LEFT JOIN t_timezerg_node_civilization nc ON n.id = nc.nid " +
                "WHERE nc.cid = #{cid} AND n.ad = 1 ");
        if ((Integer)map.get("level") != 0){
            sql .append("and n.level <= #{level} ");
        }
        sql.append("ORDER BY n.cdate ASC LIMIT 0,9999)) t limit #{start},#{limit}");

        return sql.toString();
    }


}
