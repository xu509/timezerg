package com.timezerg.api.mapper;

import com.timezerg.api.model.CivilizationPeriod;
import com.timezerg.api.model.NationPeriod;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface CivilizationPeriodMapper {

    @Insert("insert into t_timezerg_civilization_period values (#{id},#{cid},#{pid})")
    int add(CivilizationPeriod civilizationPeriod);

    @Select("select * from t_timezerg_civilization_period where cid = #{cid} and pid = #{pid}")
    CivilizationPeriod selectByCidAndPid(CivilizationPeriod civilizationPeriod);

    @Select("select * from t_timezerg_civilization_period where id = #{id}")
    CivilizationPeriod selectById(String id);

    @Select("SELECT cp.*,c.title FROM t_timezerg_civilization_period cp LEFT JOIN t_timezerg_civilization c ON cp.cid = c.id WHERE cp.pid = #{pid}")
    List<HashMap> selectCivilizationByPid(String pid);

    @Delete("DELETE FROM t_timezerg_civilization_period WHERE cid = #{cid}")
    int deleteByCid(String cid);

    @Delete("DELETE FROM t_timezerg_civilization_period WHERE pid = #{pid}")
    int deleteByPid(String pid);

    @Delete("DELETE FROM t_timezerg_civilization_period WHERE id = #{id}")
    int deleteById(String id);


}
