package com.timezerg.api.mapper;

import com.timezerg.api.model.Giant;
import com.timezerg.api.model.GiantNation;
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
public interface GiantNationMapper {

    @Insert("insert into t_timezerg_giant_nation values (#{id},#{gid},#{nid})")
    int add(GiantNation giantNation);

    @Select("select * from t_timezerg_giant_nation where gid = #{gid} and nid = #{nid}")
    GiantNation selectByGidAndNid(GiantNation giantNation);

    @Delete("delete from t_timezerg_giant_nation where nid = #{nid}")
    void deleteByNid(String nid);

    @Delete("delete from t_timezerg_giant_nation where gid = #{gid}")
    void deleteByGid(String gid);


    @Select("SELECT gn.*,g.name FROM t_timezerg_giant_nation gn LEFT JOIN t_timezerg_giant g ON gn.gid = g.id WHERE gn.nid = #{nid}")
    List<HashMap> selectByNid(String nid);

    @Select("SELECT gn.*,n.title FROM t_timezerg_giant_nation gn LEFT JOIN t_timezerg_nation n ON gn.nid = n.id WHERE gn.gid = #{gid}")
    List<HashMap> selectByGid(String gid);

    @Select("SELECT nc.cid,c.title FROM t_timezerg_node_civilization nc " +
            "LEFT JOIN t_timezerg_civilization c ON nc.cid = c.id " +
            "WHERE nc.nid = #{nid}")
    List<HashMap> selectCivilizationByNid(String nid);


}
