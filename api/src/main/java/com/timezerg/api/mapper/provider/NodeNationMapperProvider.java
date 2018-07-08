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
        }else {
            sql.append("WHERE 1 != 1");
        }
        sql.append("group by n.id");
        return sql.toString();
    }

    public static String getNodes(final Map<String,Object> map){
        Integer level = (Integer) map.get("level");
        String title = (String) map.get("title");

        StringBuffer sql = new StringBuffer("SELECT t.* FROM (");
        sql.append("(SELECT n.*,nn.id as nnid,nn.level as l FROM t_timezerg_node_nation nn " +
                "LEFT JOIN t_timezerg_node n ON nn.nodeid = n.id ");
        sql.append("WHERE nn.nationid = #{nid} ");
        if (level != null && level != 0){
            sql.append("and nn.level = #{level} ");
        }
        if (!StringUtils.isBlank(title)){
            sql.append(" AND n.title like CONCAT('%',#{title},'%') ");
        }
        sql.append("AND n.ad = 0 ORDER BY n.cdate DESC LIMIT 0,9999) ");
        sql.append("UNION ALL ");
        sql.append("(SELECT n.*,nn.id as nnid,nn.level as l FROM t_timezerg_node_nation nn " +
                "LEFT JOIN t_timezerg_node n ON nn.nodeid = n.id " +
                "WHERE nn.nationid = #{nid} ");
        if (level != null && level != 0){
            sql.append("and nn.level = #{level} ");
        }
        if (!StringUtils.isBlank(title)){
            sql.append(" AND n.title like CONCAT('%',#{title},'%') ");
        }
        sql.append("AND n.ad = 1 ORDER BY n.cdate ASC LIMIT 0,9999) " +
                ") t");
        sql.append(" LIMIT #{start},#{limit}");
        return sql.toString();
    }

    public static String getNodesTotal(final Map<String,Object> map){
        Integer level = (Integer) map.get("level");
        String title = (String) map.get("title");

        StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM t_timezerg_node_nation nn " +
                "LEFT JOIN t_timezerg_node n ON nn.nodeid = n.id " +
                "WHERE 1=1 ");

        if (level != null && level != 0){
            sql.append("and nn.level = #{level} ");
        }
        if (!StringUtils.isBlank(title)){
            sql.append(" AND n.title like CONCAT('%',#{title},'%') ");
        }

        sql.append("and nn.nationid = #{nid}");
        return sql.toString();
    }



}
