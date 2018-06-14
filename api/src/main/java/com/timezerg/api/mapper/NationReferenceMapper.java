package com.timezerg.api.mapper;

import com.timezerg.api.model.NationReference;
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
public interface NationReferenceMapper {

    @Insert("insert into t_timezerg_nation_reference values (#{id},#{nid},#{rid})")
    int add(NationReference nationReference);

    @Select("select * from t_timezerg_nation_reference where nid = #{nid} and rid = #{rid}")
    NationReference selectByNidAndRid(NationReference nationReference);

    @Select("select * from t_timezerg_nation_reference where id = #{id}")
    NationReference selectById(String id);

    @Delete("delete from t_timezerg_nation_reference where rid = #{rid}")
    void deleteByRid(String rid);

    @Delete("delete from t_timezerg_nation_reference where nid = #{nid}")
    void deleteByNid(String nid);

    @Delete("delete from t_timezerg_nation_reference where id = #{id}")
    void deleteById(String id);

    @Select("SELECT nr.*,n.title FROM t_timezerg_nation_reference nr LEFT JOIN t_timezerg_nation n ON nr.nid = n.id WHERE nr.rid = #{rid}")
    List<HashMap> selectByRid(String rid);

    @Select("SELECT nr.*,r.title FROM t_timezerg_nation_reference nr LEFT JOIN t_timezerg_reference r ON nr.rid = r.id WHERE nr.nid = #{nid}")
    List<HashMap> selectByNid(String nid);

}
