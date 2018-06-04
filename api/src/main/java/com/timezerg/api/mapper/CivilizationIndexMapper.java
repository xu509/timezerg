package com.timezerg.api.mapper;

import com.timezerg.api.model.CivilizationIndex;
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
public interface CivilizationIndexMapper {

    @Insert("insert into t_timezerg_civilization_index values (#{id},#{cid},#{cdate})")
    int add(CivilizationIndex civilizationIndex);

    @Delete("delete from t_timezerg_civilization_index where id = #{id}")
    void delete(String id);

    @Select("select * from t_timezerg_civilization_index where cid = #{cid}")
    CivilizationIndex selectByCid(String cid);

    @Select("select * from t_timezerg_civilization_index where id = #{id}")
    CivilizationIndex selectById(String id);

    @Select("select ci.*,c.title from t_timezerg_civilization_index ci left join t_timezerg_civilization c on c.id = ci.cid order by ci.cdate desc")
    List<HashMap> selectList();

}
