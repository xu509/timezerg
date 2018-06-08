package com.timezerg.api.mapper;

import com.timezerg.api.model.Institution;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface InstitutionMapper {

    @Insert("insert into t_timezerg_institution values (#{id},#{title},#{content},#{type})")
    int add(Institution institution);

    @Select("select * from t_timezerg_institution where id = #{id}")
    Institution selectById(String id);

    @Select("select n.* from t_timezerg_institution n where 1 = 1 order by n.id desc limit #{array[0]},#{array[1]}")
    List<HashMap> getList(Object[] params);

    @Select("select count(*) from t_timezerg_institution")
    Long getListTotal(Object[] params);

    @Select("select * from t_timezerg_institution where title like CONCAT('%',#{title},'%') limit 0,10")
    List<Institution> selectLikeByTitle(String title);


}
