package com.timezerg.api.mapper;

import com.timezerg.api.mapper.provider.NationMapperProvider;
import com.timezerg.api.mapper.provider.PeriodMapperProvider;
import com.timezerg.api.model.Nation;
import com.timezerg.api.model.Period;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface PeriodMapper {

    @Insert("insert into t_timezerg_period values (#{id},#{pid},#{title},#{content},#{cover},#{cdate},#{edate},#{ddate},#{AD},#{eAD})")
    int add(Period period);

    @SelectProvider(type= PeriodMapperProvider.class,method = "getList")
    List<HashMap> getList(@Param("searchtitle") String searchtitle, @Param("start") Integer start, @Param("limit") Integer limit);

    @SelectProvider(type= PeriodMapperProvider.class,method = "getListTotal")
    Long getListTotal(@Param("searchtitle") String searchtitle);

    @Select("select * from t_timezerg_period where title like CONCAT('%',#{title},'%') limit 0,10")
    List<Period> selectLikeByTitle(String title);

    @Select("select * from t_timezerg_period where id = #{id}")
    Period selectById(String id);

    @Select("select * from t_timezerg_period where title = #{title}")
    Period selectByTitle(String title);

    @SelectProvider(type= PeriodMapperProvider.class,method = "selectListByPid")
    List<Period> selectListByPid(@Param("pids") Object[] pids);

    @Select("select * from t_timezerg_period where pid = #{pid}")
    List<Period> selectByPid(String pid);

    @Update("update t_timezerg_period set title = #{title},pid = #{pid},content = #{content},cover = #{cover},cdate = #{cdate},edate = #{edate},ddate = #{ddate},AD = #{AD},eAD = #{eAD}" +
            " where id = #{id}")
    int update(Period period);


}
