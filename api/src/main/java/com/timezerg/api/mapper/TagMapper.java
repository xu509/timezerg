package com.timezerg.api.mapper;

import com.timezerg.api.model.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface TagMapper {

    @Insert("insert into t_timezerg_tag values (#{id},#{title})")
    int add(Tag tag);

    @Select("select * from t_timezerg_tag where title like CONCAT('%',#{title},'%') limit 0,10")
    List<Tag> selectLikeByTitle(String title);

    @Select("select * from t_timezerg_tag where id = #{id}")
    Tag selectById(String id);

    @Select("select * from t_timezerg_tag where title = #{title}")
    Tag selectByTitle(String title);

}
