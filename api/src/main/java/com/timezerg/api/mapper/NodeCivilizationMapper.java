package com.timezerg.api.mapper;

import com.timezerg.api.model.NodeCivilization;
import com.timezerg.api.model.NodeNation;
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
public interface NodeCivilizationMapper {

    @Insert("insert into t_timezerg_node_civilization values (#{id},#{nid},#{cid})")
    int add(NodeCivilization nodeCivilization);


    @Select("SELECT nc.*,c.title FROM t_timezerg_node_civilization nc LEFT JOIN t_timezerg_civilization c ON nc.cid = c.id WHERE nc.nid = #{nid}")
    List<HashMap> selectByNodeId(String nid);

    @Delete("DELETE FROM t_timezerg_node_civilization WHERE nid = #{nid}")
    int deleteByNodeId(String nid);


}
