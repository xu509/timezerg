package com.timezerg.app.mapper;

import com.timezerg.app.model.Node;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface NodeMapper {

    @Insert("insert into t_timezerg_node values (#{id},#{title},#{content},#{cover},#{cdate})")
    int add (Node node);

    @Select("select * from t_timezerg_node where id = #{id}")
    Node selectById(String id);

    @Select("select n.* from t_timezerg_node n where 1 = 1 order by n.cdate desc limit #{array[0]},#{array[1]}")
    List<HashMap> getList(Object[] params);



}