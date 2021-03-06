package com.timezerg.api.mapper;

import com.timezerg.api.model.Dynasty;
import com.timezerg.api.model.Node;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface DynastyMapper {

    @Insert("insert into t_timezerg_dynasty values (#{id},#{title},#{cover},#{cdate},#{ddate})")
    int add(Dynasty dynasty);

    @Select("select * from t_timezerg_node where id = #{id}")
    Node selectById(String id);

    @Select("select n.* from t_timezerg_node n where 1 = 1 order by n.cdate desc limit #{array[0]},#{array[1]}")
    List<HashMap> getList(Object[] params);

    @Select("select count(*) from t_timezerg_node")
    Long getListTotal(Object[] params);


}
