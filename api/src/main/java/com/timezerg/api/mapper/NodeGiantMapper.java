package com.timezerg.api.mapper;

import com.timezerg.api.model.NodeGiant;
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
public interface NodeGiantMapper {

    @Insert("insert into t_timezerg_node_giant values (#{id},#{nid},#{gid})")
    int add(NodeGiant nodeGiant);

    @Select("SELECT ng.*,g.name FROM t_timezerg_node_giant ng LEFT JOIN t_timezerg_giant g ON ng.gid = g.id WHERE ng.nid = #{nid}")
    List<HashMap> selectByNodeId(String nid);

    @Delete("DELETE FROM t_timezerg_node_giant WHERE nid = #{nid}")
    int deleteByNodeId(String nid);


}
