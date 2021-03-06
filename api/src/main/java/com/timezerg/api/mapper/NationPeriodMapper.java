package com.timezerg.api.mapper;

import com.timezerg.api.mapper.provider.NationPeriodMapperProvider;
import com.timezerg.api.model.Nation;
import com.timezerg.api.model.NationPeriod;
import com.timezerg.api.model.Period;
import org.apache.ibatis.annotations.*;

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

    @Select("select * from t_timezerg_nation_period where id = #{id}")
    NationPeriod selectById(String id);

    @Select("SELECT np.*,n.title FROM t_timezerg_nation_period np LEFT JOIN t_timezerg_nation n ON np.nid = n.id WHERE np.pid = #{pid}")
    List<HashMap> selectNationByPid(String pid);

    @Select("SELECT np.*,p.title FROM t_timezerg_nation_period np LEFT JOIN t_timezerg_period p ON np.pid = p.id WHERE np.nid = #{nid}")
    List<HashMap> selectByNid(String nid);

    @SelectProvider(type= NationPeriodMapperProvider.class,method = "selectAllNationBeanByPid")
    List<Nation> selectNationBeanByPid(@Param("pids") Object[] pids);

    @Select("SELECT p.* from t_timezerg_nation_period np LEFT JOIN t_timezerg_period p on np.pid = p.id where np.nid = #{nid}")
    List<Period> selectPeriodByNid(String nid);

    @Delete("DELETE FROM t_timezerg_nation_period WHERE nid = #{nid}")
    int deleteByNId(String nid);

    @Delete("DELETE FROM t_timezerg_nation_period WHERE pid = #{pid}")
    int deleteByPId(String pid);

    @Delete("DELETE FROM t_timezerg_nation_period WHERE id = #{id}")
    int deleteById(String id);


}
