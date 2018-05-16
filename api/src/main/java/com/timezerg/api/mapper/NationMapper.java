package com.timezerg.api.mapper;

import com.timezerg.api.model.Nation;
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
public interface NationMapper {

    @Insert("insert into t_timezerg_nation values (#{id},#{pid},#{title},#{content},#{cover},#{cdate},#{edate},#{ddate},#{AD},#{eAD})")
    int add(Nation nation);

    @Select("(SELECT n.* FROM t_timezerg_nation n WHERE 1 = 1 AND n.AD = 0 ORDER BY n.cdate DESC LIMIT 99999999)" +
            "UNION ALL " +
            "(SELECT n2.* FROM t_timezerg_nation n2 WHERE 1 = 1 AND n2.AD = 1 ORDER BY n2.cdate ASC LIMIT 99999999) limit #{array[0]},#{array[1]}")
    List<HashMap> getList(Object[] params);

    @Select("select count(*) from t_timezerg_nation")
    Long getListTotal(Object[] params);


    @Select("select * from t_timezerg_nation where title like CONCAT('%',#{title},'%') limit 0,10")
    List<Nation> selectLikeByTitle(String title);

    @Select("select * from t_timezerg_nation where id = #{id}")
    Nation selectById(String id);


    @Update("update t_timezerg_nation set pid = #{pid} ,title = #{title},content = #{content},cover = #{cover},cdate = #{cdate},edate = #{edate},ddate = #{ddate},AD = #{AD},eAD = #{eAD}" +
            " where id = #{id}")
    int update(Nation nation);


}
