package com.timezerg.api.mapper;

import com.timezerg.api.model.Giant;
import com.timezerg.api.model.GiantRelation;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface GiantRelationMapper {

    @Insert("insert into t_timezerg_giant_relation values (#{id},#{fid},#{tid},#{rid},#{detail})")
    int add(GiantRelation giantRelation);

    @Select("select gr.*,g.name,r.title from t_timezerg_giant_relation gr left join t_timezerg_giant g on gr.tid = g.id left join t_timezerg_relation r on gr.rid = r.id where gr.fid = #{fid}")
    List<HashMap> selectByFId(String fid);

    @Select("select * from t_timezerg_giant_relation where id = #{id}")
    GiantRelation selectById(String id);

    @Update("update t_timezerg_giant_relation set detail = #{detail} where id = #{id}")
    void updateDetailById(GiantRelation giantRelation);

    @Delete("delete from t_timezerg_giant_relation where id = #{id}")
    void deleteById(String id);

    @Select("select * from t_timezerg_giant_relation where fid = #{fid} and tid = #{tid}")
    GiantRelation selectByFidAndTid(GiantRelation giantRelation);

    @Select("select * from t_timezerg_giant_relation where fid = #{fid} and tid = #{tid} and rid = #{rid}")
    GiantRelation selectByFidTidAndRid(GiantRelation giantRelation);


}
