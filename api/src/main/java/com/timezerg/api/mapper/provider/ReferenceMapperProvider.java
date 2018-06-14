package com.timezerg.api.mapper.provider;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by xnx on 2018/5/25.
 */
public class ReferenceMapperProvider {

    public static String getList(final Map<String,Object> map){
        String searchtitle = (String) map.get("searchtitle");

        StringBuffer sql = new StringBuffer("SELECT r.* FROM t_timezerg_reference r WHERE 1 = 1 ");
        if (!StringUtils.isBlank(searchtitle)){
            sql.append("AND r.title like CONCAT('%',#{searchtitle},'%') ");
        }
        sql.append("ORDER BY r.id DESC ");
        sql.append("limit #{start},#{limit}");

        return sql.toString();
    }

    public static String getListTotal(final Map<String,Object> map){
        StringBuffer sql = new StringBuffer("select count(*) from t_timezerg_reference");

        String searchtitle = (String) map.get("searchtitle");

        if (!StringUtils.isBlank(searchtitle)){
            sql.append(" where title like CONCAT('%',#{searchtitle},'%') ");
        }

        return sql.toString();
    }

}
