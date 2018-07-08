package com.timezerg.api.mapper;

import com.timezerg.api.model.NodeTag;
import com.timezerg.api.model.Tag;
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
public interface NodeTagMapper {

    @Insert("insert into t_timezerg_node_tag values (#{id},#{nid},#{tid})")
    int add(NodeTag nodeTag);

    @Select("SELECT nt.*,t.title FROM t_timezerg_node_tag nt LEFT JOIN t_timezerg_tag t ON nt.tid = t.id WHERE nt.nid = #{nid}")
    List<HashMap> selectByNid(String nid);

    @Select("SELECT t.* FROM t_timezerg_node_tag nt LEFT JOIN t_timezerg_tag t ON nt.tid = t.id WHERE nt.nid = #{nid}")
    List<Tag> selectTagsByNid(String nid);

    @Delete("DELETE FROM t_timezerg_node_tag WHERE nid = #{nid}")
    int deleteByNid(String nid);

    @Select("select * from t_timezerg_node_tag where id = #{id}")
    NodeTag selectById(String id);

    @Select("select * from t_timezerg_node_tag where nid = #{nid} and tid = #{tid}")
    NodeTag selectByNidAndTid(NodeTag nodeTag);

    @Delete("delete from t_timezerg_node_tag where id = #{id}")
    int deleteById(String id);

}
