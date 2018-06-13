package com.timezerg.api.mapper;

import com.timezerg.api.mapper.provider.GiantMapperProvider;
import com.timezerg.api.model.Giant;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface GiantMapper {

    @Insert("insert into t_timezerg_giant values (#{id},#{pid},#{name},#{content})")
    int add(Giant gaint);

    @Select("select * from t_timezerg_giant where name like CONCAT('%',#{name},'%') limit 0,10")
    List<Giant> selectLikeByTitle(String name);

    @Select("select * from t_timezerg_giant where name = #{name}")
    Giant selectByName(String name);

    @SelectProvider(type= GiantMapperProvider.class,method = "getList")
    List<HashMap> getList(@Param("name") String name, @Param("start") Integer start, @Param("limit") Integer limit);

    @SelectProvider(type= GiantMapperProvider.class,method = "getListTotal")
    Long getListTotal(@Param("name") String name);

    @Select("select * from t_timezerg_giant where id = #{id}")
    Giant selectById(String id);

    @Select("select * from t_timezerg_giant where pid = #{pid}")
    List<Giant> selectByPid(String pid);

    @Select("select count(0) from t_timezerg_giant where pid = #{pid}")
    Long selectCountByPid(String pid);

    @Update("update t_timezerg_giant set name = #{name},pid = #{pid},content = #{content} where id = #{id}")
    void update(Giant giant);

}
