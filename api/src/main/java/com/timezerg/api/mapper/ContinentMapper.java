package com.timezerg.api.mapper;

import com.timezerg.api.model.Civilization;
import com.timezerg.api.model.Continent;
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
public interface ContinentMapper {

    @Insert("insert into t_timezerg_continent values (#{id},#{title})")
    int add(Continent continent);

    @Select("select * from t_timezerg_continent where title like CONCAT('%',#{title},'%') limit 0,10")
    List<Continent> selectLikeByTitle(String title);
}
