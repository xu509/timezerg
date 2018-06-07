package com.timezerg.api.mapper;

import com.timezerg.api.model.Giant;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

}
