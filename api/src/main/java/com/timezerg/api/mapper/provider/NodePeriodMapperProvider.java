package com.timezerg.api.mapper.provider;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by xnx on 2018/5/25.
 */
public class NodePeriodMapperProvider {

    public static String getNodes(final Map<String,Object> map){
        Integer level = (Integer) map.get("level");
        String title = (String) map.get("title");

        StringBuffer sql = new StringBuffer("SELECT t.* FROM (");
        sql.append("(SELECT n.*,nc.id as npid,nc.level as l FROM t_timezerg_node_period nc " +
                "LEFT JOIN t_timezerg_node n ON nc.nid = n.id ");
        sql.append("WHERE nc.pid = #{pid} ");
        if (level != null && level != 0){
            sql.append("and nc.level = #{level} ");
        }
        if (!StringUtils.isBlank(title)){
            sql.append(" AND n.title like CONCAT('%',#{title},'%') ");
        }
        sql.append("AND n.ad = 0 ORDER BY n.cdate DESC LIMIT 0,9999) ");
        sql.append("UNION ALL ");
        sql.append("(SELECT n.*,nc.id as npid,nc.level as l FROM t_timezerg_node_period nc " +
                "LEFT JOIN t_timezerg_node n ON nc.nid = n.id " +
                "WHERE nc.pid = #{pid} ");
        if (level != null && level != 0){
            sql.append("and nc.level = #{level} ");
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

        StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM t_timezerg_node_period nc " +
                "LEFT JOIN t_timezerg_node n ON nc.nid = n.id " +
                "WHERE 1=1 ");

        if (level != null && level != 0){
            sql.append("and nc.level = #{level} ");
        }
        if (!StringUtils.isBlank(title)){
            sql.append(" AND n.title like CONCAT('%',#{title},'%') ");
        }

        sql.append("and nc.pid = #{pid}");
        return sql.toString();
    }

//    public static String getListByTitle(final Map<String,Object> map){
//        StringBuffer sql = new StringBuffer("select * from t_timezerg_node where 1 = 1 ");
//
//        String searchtitle = (String) map.get("searchtitle");
//
//        if (!StringUtils.isBlank(searchtitle)){
//            sql.append(" and title like CONCAT('%',#{searchtitle},'%') ");
//        }
//
//        sql.append(" ORDER BY id desc ");
//        sql.append(" LIMIT #{start},#{limit}");
//
//
//        return sql.toString();
//    }
//
//    public static String getListTotalByTitle(final Map<String,Object> map){
//        StringBuffer sql = new StringBuffer("SELECT count(*) from ((SELECT n.* FROM t_timezerg_node n LEFT JOIN t_timezerg_node_period nc ON n.id = nc.nid WHERE 1 = 1 AND n.Ad = 0 ");
//
//        Object[] civilizations = (Object[]) map.get("pids");
//        String searchtitle = (String) map.get("searchtitle");
//
//        if (civilizations != null && civilizations.length >0){
//            sql.append("AND nc.pid in(");
//            for (int x = 0 ; x < civilizations.length ; x++){
//                sql.append(civilizations[x]);
//                sql.append(",");
//            }
//            Integer i = sql.lastIndexOf(",");
//            sql.deleteCharAt(i);
//            sql.append(")");
//        }
//        if (!StringUtils.isBlank(searchtitle)){
//            sql.append(" AND n.title like CONCAT('%',#{searchtitle},'%') ");
//        }
//        sql.append(" ORDER BY n.cdate DESC LIMIT 99999999)");
//        sql.append(" UNION ALL ");
//        sql.append("(SELECT n.* FROM t_timezerg_node n LEFT JOIN t_timezerg_node_period nc ON n.id = nc.nid WHERE 1 = 1 AND n.Ad = 1 ");
//        if (civilizations != null && civilizations.length >0){
//            sql.append("AND nc.pid in(");
//            for (int x = 0 ; x < civilizations.length ; x++){
//                sql.append(civilizations[x]);
//                sql.append(",");
//            }
//            Integer i = sql.lastIndexOf(",");
//            sql.deleteCharAt(i);
//            sql.append(")");
//        }
//        if (!StringUtils.isBlank(searchtitle)){
//            sql.append(" AND n.title like CONCAT('%',#{searchtitle},'%') ");
//        }
//        sql.append(" ORDER BY n.cdate ASC LIMIT 99999999)) t");
//
//        return sql.toString();
//    }

}
