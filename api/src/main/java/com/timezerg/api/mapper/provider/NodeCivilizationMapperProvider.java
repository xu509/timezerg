package com.timezerg.api.mapper.provider;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by xnx on 2018/5/25.
 */
public class NodeCivilizationMapperProvider {

    public static String getNodes(final Map<String,Object> map){
        Integer level = (Integer) map.get("level");

        StringBuffer sql = new StringBuffer("SELECT t.* FROM (");
        sql.append("(SELECT n.* FROM t_timezerg_node_civilization nc " +
                "LEFT JOIN t_timezerg_node n ON nc.nid = n.id ");
        sql.append("WHERE nc.cid = #{cid} ");
        if (level != null){
            sql.append("and nc.level = #{level} ");
        }
        sql.append("AND n.ad = 0 ORDER BY n.cdate DESC LIMIT 0,9999) ");
        sql.append("UNION ALL ");
        sql.append("(SELECT n.* FROM t_timezerg_node_civilization nc " +
                "LEFT JOIN t_timezerg_node n ON nc.nid = n.id " +
                "WHERE nc.cid = #{cid} ");
        if (level != null){
            sql.append("and nc.level = #{level} ");
        }
         sql.append("AND n.ad = 1 ORDER BY n.cdate ASC LIMIT 0,9999) " +
                ") t");
        sql.append(" LIMIT #{start},#{limit}");
        return sql.toString();
    }

    public static String getNodesTotal(final Map<String,Object> map){
        Integer level = (Integer) map.get("level");

        StringBuffer sql = new StringBuffer("SELECT COUNT(*) FROM t_timezerg_node_civilization WHERE 1=1 ");

        if (level != null){
            sql.append("and level = #{level} ");
        }

        sql.append("and cid = #{cid}");
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
//        StringBuffer sql = new StringBuffer("SELECT count(*) from ((SELECT n.* FROM t_timezerg_node n LEFT JOIN t_timezerg_node_civilization nc ON n.id = nc.nid WHERE 1 = 1 AND n.Ad = 0 ");
//
//        Object[] civilizations = (Object[]) map.get("cids");
//        String searchtitle = (String) map.get("searchtitle");
//
//        if (civilizations != null && civilizations.length >0){
//            sql.append("AND nc.cid in(");
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
//        sql.append("(SELECT n.* FROM t_timezerg_node n LEFT JOIN t_timezerg_node_civilization nc ON n.id = nc.nid WHERE 1 = 1 AND n.Ad = 1 ");
//        if (civilizations != null && civilizations.length >0){
//            sql.append("AND nc.cid in(");
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
