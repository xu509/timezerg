package com.timezerg.api.mapper;

import com.timezerg.api.model.ReferenceTag;
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
public interface ReferenceTagMapper {

    @Insert("insert into t_timezerg_reference_tag values (#{id},#{rid},#{tid})")
    int add(ReferenceTag referenceTag);

    @Select("select * from t_timezerg_reference_tag where rid = #{rid} and tid = #{tid}")
    ReferenceTag selectByRidAndTid(ReferenceTag referenceTag);

    @Select("select * from t_timezerg_reference_tag where id = #{id}")
    ReferenceTag selectById(String id);

    @Select("SELECT rt.*,t.title FROM t_timezerg_reference_tag rt LEFT JOIN t_timezerg_tag t ON rt.tid = t.id WHERE rt.rid = #{rid}")
    List<HashMap> selectTagsByRid(String rid);

    @Delete("DELETE FROM t_timezerg_reference_tag WHERE rid = #{rid}")
    int deleteByRid(String rid);

    @Delete("DELETE FROM t_timezerg_reference_tag WHERE id = #{id}")
    int deleteById(String id);


}
