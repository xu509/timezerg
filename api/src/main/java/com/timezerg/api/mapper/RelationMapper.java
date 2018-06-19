package com.timezerg.api.mapper;

import com.timezerg.api.model.Relation;
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
public interface RelationMapper {

    @Insert("insert into t_timezerg_relation values (#{id},#{title},#{tid})")
    int add(Relation relation);

    @Select("select * from t_timezerg_relation order by id desc")
    List<Relation> selectAll();

    @Select("select * from t_timezerg_relation where id = #{id}")
    Relation selectById(String id);

    @Select("select * from t_timezerg_relation where title = #{title}")
    Relation selectByTitle(String title);

    @Select("select n.*,t.title as ttitle from t_timezerg_relation n left join t_timezerg_relation t on n.tid = t.id where 1 = 1 order by n.id desc limit #{array[0]},#{array[1]}")
    List<HashMap> getList(Object[] params);

    @Select("select count(*) from t_timezerg_relation")
    Long getTotal();

    @Update("update t_timezerg_relation set title = #{title},tid = #{tid} where id = #{id}")
    void update(Relation relation);

}
