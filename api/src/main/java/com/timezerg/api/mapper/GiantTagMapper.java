package com.timezerg.api.mapper;

import com.timezerg.api.model.GiantTag;
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
public interface GiantTagMapper {

    @Insert("insert into t_timezerg_giant_tag values (#{id},#{gid},#{tid})")
    int add(GiantTag giantTag);

    @Select("select * from t_timezerg_giant_tag where gid = #{gid} and tid = #{tid}")
    GiantTag selectByGidAndTid(GiantTag giantTag);

    @Select("SELECT gt.*,t.title FROM t_timezerg_giant_tag gt LEFT JOIN t_timezerg_tag t ON gt.tid = t.id WHERE gt.gid = #{gid}")
    List<HashMap> selectTagsByGid(String gid);

//    @Select("SELECT nc.cid,c.title FROM t_timezerg_node_civilization nc " +
//            "LEFT JOIN t_timezerg_civilization c ON nc.cid = c.id " +
//            "WHERE nc.nid = #{nid}")
//    List<HashMap> selectCivilizationByNid(String nid);

    @Delete("DELETE FROM t_timezerg_giant_tag WHERE gid = #{gid}")
    int deleteByGId(String gid);


}
