package com.timezerg.api.mapper;

import com.timezerg.api.model.PeriodTag;
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
public interface PeriodTagMapper {

    @Insert("insert into t_timezerg_period_tag values (#{id},#{pid},#{tid})")
    int add(PeriodTag periodTag);

    @Select("select * from t_timezerg_period_tag where pid = #{pid} and tid = #{tid}")
    PeriodTag selectByPidAndTid(PeriodTag periodTag);

    @Select("select * from t_timezerg_period_tag where id = #{id}")
    PeriodTag selectById(String id);

    @Select("SELECT pt.* FROM t_timezerg_period_tag pt WHERE pt.pid = #{pid}")
    List<PeriodTag> selectByPid(String pid);

    @Select("select pt.*,t.title from t_timezerg_period_tag pt left join t_timezerg_tag t on pt.tid = t.id where pt.pid = #{pid}")
    List<HashMap> selectListByPid(String pid);

    @Delete("DELETE FROM t_timezerg_period_tag WHERE id = #{id}")
    int deleteById(String id);


}
