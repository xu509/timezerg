package com.timezerg.api.mapper;

import com.timezerg.api.model.GiantTag;
import com.timezerg.api.model.NationTag;
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
public interface NationTagMapper {

    @Insert("insert into t_timezerg_nation_tag values (#{id},#{nid},#{tid})")
    int add(NationTag nationTag);

    @Select("select * from t_timezerg_nation_tag where nid = #{nid} and tid = #{tid}")
    NationTag selectByNidAndTid(NationTag nationTag);

    @Select("SELECT nt.*,t.title FROM t_timezerg_nation_tag nt LEFT JOIN t_timezerg_tag t ON nt.tid = t.id WHERE nt.nid = #{nid}")
    List<HashMap> selectTagsByNid(String nid);

    @Delete("DELETE FROM t_timezerg_nation_tag WHERE nid = #{nid}")
    int deleteByNId(String nid);

    @Delete("DELETE FROM t_timezerg_nation_tag WHERE id = #{id}")
    int deleteById(String id);


}
