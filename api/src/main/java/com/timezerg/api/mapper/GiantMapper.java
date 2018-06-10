package com.timezerg.api.mapper;

import com.timezerg.api.model.Giant;
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
public interface GiantMapper {

    @Insert("insert into t_timezerg_giant values (#{id},#{name},#{content})")
    int add(Giant gaint);

    @Select("select * from t_timezerg_giant where name like CONCAT('%',#{name},'%') limit 0,10")
    List<Giant> selectLikeByTitle(String name);

    @Select("select * from t_timezerg_giant order by id desc limit #{array[0]},#{array[1]}")
    List<HashMap> getList(Object[] params);

    @Select("select count(*) from t_timezerg_giant")
    Long getListTotal(Object[] params);

    @Select("select * from t_timezerg_giant where id = #{id}")
    Giant selectById(String id);

    @Update("update t_timezerg_giant set name = #{name},content = #{content} where id = #{id}")
    void update(Giant giant);

}
