package com.timezerg.api.mapper;

import com.timezerg.api.model.PeriodReference;
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
public interface PeriodReferenceMapper {

    @Insert("insert into t_timezerg_period_reference values (#{id},#{pid},#{rid})")
    int add(PeriodReference periodReference);

    @Select("select * from t_timezerg_period_reference where pid = #{pid} and rid = #{rid}")
    PeriodReference selectByPidAndRid(PeriodReference periodReference);

    @Select("select * from t_timezerg_period_reference where id = #{id}")
    PeriodReference selectById(String id);

    @Delete("delete from t_timezerg_period_reference where rid = #{rid}")
    void deleteByRid(String rid);

    @Delete("delete from t_timezerg_period_reference where pid = #{pid}")
    void deleteByPid(String pid);

    @Delete("delete from t_timezerg_period_reference where id = #{id}")
    void deleteById(String id);

    @Select("SELECT pr.*,p.title FROM t_timezerg_period_reference pr LEFT JOIN t_timezerg_period p ON pr.pid = p.id WHERE pr.rid = #{rid}")
    List<HashMap> selectByRid(String rid);

    @Select("SELECT pr.*,r.title FROM t_timezerg_period_reference pr LEFT JOIN t_timezerg_reference r ON pr.rid = r.id WHERE pr.pid = #{pid}")
    List<HashMap> selectByPid(String pid);

}
