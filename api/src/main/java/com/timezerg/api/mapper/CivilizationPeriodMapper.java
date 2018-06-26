package com.timezerg.api.mapper;

import com.timezerg.api.model.CivilizationPeriod;
import com.timezerg.api.model.NationPeriod;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface CivilizationPeriodMapper {

    @Insert("insert into t_timezerg_civilization_period values (#{id},#{cid},#{pid},#{sort})")
    int add(CivilizationPeriod civilizationPeriod);

    @Select("select * from t_timezerg_civilization_period where cid = #{cid} and pid = #{pid}")
    CivilizationPeriod selectByCidAndPid(CivilizationPeriod civilizationPeriod);

    @Select("select * from t_timezerg_civilization_period where id = #{id}")
    CivilizationPeriod selectById(String id);

    @Select("SELECT cp.*,c.title FROM t_timezerg_civilization_period cp LEFT JOIN t_timezerg_civilization c ON cp.cid = c.id WHERE cp.pid = #{pid}")
    List<HashMap> selectCivilizationByPid(String pid);

    @Select("SELECT p.*,cp.id as cpid FROM t_timezerg_civilization_period cp LEFT JOIN t_timezerg_period p on cp.pid = p.id WHERE cp.cid = #{cid} AND p.pid IS NULL ORDER BY cp.sort ASC")
    List<HashMap> selectPeriodsByCid(String cid);

    @Select("SELECT * FROM t_timezerg_civilization_period ORDER BY sort ASC,id DESC")
    List<CivilizationPeriod> selectByCid(String cid);

    @Update("UPDATE t_timezerg_civilization_period SET sort = #{sort} WHERE id = #{id}")
    void updateSort(CivilizationPeriod c);

    @Delete("DELETE FROM t_timezerg_civilization_period WHERE cid = #{cid}")
    int deleteByCid(String cid);

    @Delete("DELETE FROM t_timezerg_civilization_period WHERE pid = #{pid}")
    int deleteByPid(String pid);

    @Delete("DELETE FROM t_timezerg_civilization_period WHERE id = #{id}")
    int deleteById(String id);


}
