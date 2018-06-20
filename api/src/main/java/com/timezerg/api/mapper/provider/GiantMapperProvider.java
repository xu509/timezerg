package com.timezerg.api.mapper.provider;

import java.util.Map;

/**
 * Created by xnx on 2018/5/25.
 */
public class GiantMapperProvider {

    public static String getList(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("select * from t_timezerg_giant where 1 = 1 ");

        Object name = map.get("name");
        if (name == null){
            sql.append("and pid is null ");
        }
        if (name != null){
            sql.append("and name like CONCAT('%',#{name},'%') ");
        }
        sql.append("order by id desc limit #{start},#{limit} ");

        return sql.toString();
    }


    public static String getListByPeriod(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("SELECT g.* FROM t_timezerg_giant g " +
                "LEFT JOIN t_timezerg_giant_nation gn ON g.id = gn.gid " +
                "LEFT JOIN t_timezerg_nation_period np ON np.nid = gn.nid " +
                "WHERE 1 = 1 and np.pid in (");

        Object[] periods = (Object[]) map.get("pids");
        if (periods != null && periods.length >0){
            for (Object period : periods){
                sql.append(period);
                sql.append(",");
            }

            Integer i = sql.lastIndexOf(",");
            sql.deleteCharAt(i);
            sql.append(")");
        }
        sql.append(" ORDER BY g.cdate ASC ");
        sql.append(" LIMIT #{start},#{limit}");
        return sql.toString();
    }


    public static String getListByNation(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("SELECT g.* FROM t_timezerg_giant g " +
                "LEFT JOIN t_timezerg_giant_nation gn ON g.id = gn.gid " +
                "WHERE 1 = 1 and gn.nid in (");

        Object[] nids = (Object[]) map.get("nids");
        if (nids != null && nids.length >0){
            for (Object nid : nids){
                sql.append(nid);
                sql.append(",");
            }

            Integer i = sql.lastIndexOf(",");
            sql.deleteCharAt(i);
            sql.append(")");
        }
        sql.append(" and g.pid is null");
        sql.append(" ORDER BY g.cdate ASC ");
        sql.append(" LIMIT #{start},#{limit}");
        return sql.toString();
    }

    public static String getListTotal(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("select count(*) from t_timezerg_giant where 1 = 1 ");

        Object name = map.get("name");
        if (name == null){
            sql.append("and pid is null ");
        }
        if (name != null){
            sql.append("and name like CONCAT('%',#{name},'%') ");
        }
        sql.append("order by id desc ");

        return sql.toString();
    }

    public static String getListTotalByPeriod(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("SELECT count(*) FROM t_timezerg_giant g " +
                "LEFT JOIN t_timezerg_giant_nation gn ON g.id = gn.gid " +
                "LEFT JOIN t_timezerg_nation_period np ON np.nid = gn.nid " +
                "WHERE 1 = 1 and np.pid in (");

        Object[] periods = (Object[]) map.get("pids");
        if (periods != null && periods.length >0){
            for (Object period : periods){
                sql.append(period);
                sql.append(",");
            }

            Integer i = sql.lastIndexOf(",");
            sql.deleteCharAt(i);
            sql.append(")");
        }
        return sql.toString();
    }

    public static String getListTotalByNation(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("SELECT count(*) FROM t_timezerg_giant g " +
                "LEFT JOIN t_timezerg_giant_nation gn ON g.id = gn.gid " +
                "WHERE 1 = 1 and gn.nid in (");

        Object[] nations = (Object[]) map.get("nids");
        if (nations != null && nations.length >0){
            for (Object nation : nations){
                sql.append(nation);
                sql.append(",");
            }

            Integer i = sql.lastIndexOf(",");
            sql.deleteCharAt(i);
            sql.append(")");
        }
        return sql.toString();
    }


}
