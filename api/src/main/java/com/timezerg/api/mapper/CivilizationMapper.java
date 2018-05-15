package com.timezerg.api.mapper;

import com.timezerg.api.model.Civilization;
import com.timezerg.api.model.Nation;
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
public interface CivilizationMapper {

    @Insert("insert into t_timezerg_civilization values (#{id},#{pid},#{title},#{content},#{cover},#{cdate})")
    int add(Civilization civilization);

    @Select("select * from t_timezerg_civilization where id = #{id}")
    Civilization selectById(String id);

    @Select("select n.* from t_timezerg_civilization n where 1 = 1 order by n.cdate desc limit #{array[0]},#{array[1]}")
    List<HashMap> getList(Object[] params);

    @Select("select count(*) from t_timezerg_civilization")
    Long getListTotal(Object[] params);

    @Select("select * from t_timezerg_civilization where title like CONCAT('%',#{title},'%') limit 0,10")
    List<Civilization> selectLikeByTitle(String title);

    @Update("update t_timezerg_civilization set title = #{title},pid = #{pid},content = #{content},cover = #{cover},cdate = #{cdate} where id = #{id}")
    int update(Civilization civilization);
}
