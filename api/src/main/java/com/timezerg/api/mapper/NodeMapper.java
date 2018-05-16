package com.timezerg.api.mapper;

import com.timezerg.api.model.Node;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface NodeMapper {

    @Insert("insert into t_timezerg_node values (#{id},#{title},#{content},#{cover},#{cdate},#{ddate},#{AD},#{level})")
    int add(Node node);

    @Select("select * from t_timezerg_node where id = #{id}")
    Node selectById(String id);

    @Select("(select n.* from t_timezerg_node n where 1 = 1 and n.Ad = 0 order by n.cdate desc) " +
            "union all" +
            "(select n.* from t_timezerg_node n where n.AD = 1 order by n.cdate asc) limit #{array[0]},#{array[1]}")
    List<HashMap> getList(Object[] params);

    @Select("select count(*) from t_timezerg_node")
    Long getListTotal(Object[] params);

    @Update("update t_timezerg_node set title = #{title},content = #{content},cover = #{cover},cdate = #{cdate},ddate = #{ddate},ad = #{AD},level = #{level} where id = #{id}")
    int update(Node node);


}
