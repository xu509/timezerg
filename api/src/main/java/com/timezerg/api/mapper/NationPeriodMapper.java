package com.timezerg.api.mapper;

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
public interface NationPeriodMapper {

    @Insert("insert into t_timezerg_nation_period values (#{id},#{nid},#{pid})")
    int add(NationPeriod nationPeriod);

    @Select("select * from t_timezerg_nation_period where nid = #{nid} and pid = #{pid}")
    NationPeriod selectByNidAndPid(NationPeriod nationPeriod);

    @Select("SELECT np.*,n.title FROM t_timezerg_nation_period np LEFT JOIN t_timezerg_nation n ON np.nid = n.id WHERE np.pid = #{pid}")
    List<HashMap> selectNationByPid(String pid);

    @Delete("DELETE FROM t_timezerg_nation_period WHERE nid = #{nid}")
    int deleteByNId(String nid);

    @Delete("DELETE FROM t_timezerg_nation_period WHERE pid = #{pid}")
    int deleteByPId(String pid);

    @Delete("DELETE FROM t_timezerg_nation_period WHERE id = #{id}")
    int deleteById(String id);


}
