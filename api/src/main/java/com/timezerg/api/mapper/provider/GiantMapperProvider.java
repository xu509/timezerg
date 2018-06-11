package com.timezerg.api.mapper.provider;

import java.util.Map;

/**
 * Created by xnx on 2018/5/25.
 */
public class GiantMapperProvider {

    public static String getList(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("select * from t_timezerg_giant");

        Object name = map.get("name");
        if (name != null){
            sql.append(" where name like CONCAT('%',#{name},'%') ");
        }
        sql.append(" order by id desc limit #{start},#{limit}");

        return sql.toString();
    }

    public static String getListTotal(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("select count(*) from t_timezerg_giant");

        Object name = map.get("name");
        if (name != null){
            sql.append(" where name like CONCAT('%',#{name},'%') ");
        }
        sql.append(" order by id desc");

        return sql.toString();
    }

}
