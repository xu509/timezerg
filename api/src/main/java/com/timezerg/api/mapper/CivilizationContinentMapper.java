package com.timezerg.api.mapper;

import com.timezerg.api.model.CivilizationContinent;
import com.timezerg.api.model.NodeCivilization;
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
public interface CivilizationContinentMapper {

    @Insert("insert into t_timezerg_civilization_continent values (#{id},#{civilizationid},#{continentid})")
    int add(CivilizationContinent civilizationContinent);


    @Select("SELECT cc.*,c.title FROM t_timezerg_civilization_continent cc LEFT JOIN t_timezerg_continent c ON cc.continentid = c.id WHERE cc.civilizationid = #{cid}")
    List<HashMap> selectByCivilizationId(String cid);

    @Delete("DELETE FROM t_timezerg_civilization_continent WHERE civilizationid = #{cid}")
    int deleteByCivilizationId(String cid);


}
