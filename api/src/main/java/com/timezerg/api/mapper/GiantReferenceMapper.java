package com.timezerg.api.mapper;

import com.timezerg.api.model.GiantReference;
import com.timezerg.api.model.GiantReference;
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
public interface GiantReferenceMapper {

    @Insert("insert into t_timezerg_giant_reference values (#{id},#{gid},#{rid})")
    int add(GiantReference giantReference);

    @Select("select * from t_timezerg_giant_reference where gid = #{gid} and rid = #{rid}")
    GiantReference selectByGidAndRid(GiantReference giantReference);

    @Select("select * from t_timezerg_giant_reference where id = #{id}")
    GiantReference selectById(String id);

    @Delete("delete from t_timezerg_giant_reference where rid = #{rid}")
    void deleteByRid(String rid);

    @Delete("delete from t_timezerg_giant_reference where gid = #{gid}")
    void deleteByGid(String gid);

    @Delete("delete from t_timezerg_giant_reference where id = #{id}")
    void deleteById(String id);

    @Select("SELECT nr.*,g.name FROM t_timezerg_giant_reference nr LEFT JOIN t_timezerg_giant g ON nr.gid = g.id WHERE nr.rid = #{rid}")
    List<HashMap> selectByRid(String rid);

    @Select("SELECT nr.*,r.title FROM t_timezerg_giant_reference nr LEFT JOIN t_timezerg_reference r ON nr.rid = r.id WHERE nr.gid = #{gid}")
    List<HashMap> selectByGid(String gid);

}
