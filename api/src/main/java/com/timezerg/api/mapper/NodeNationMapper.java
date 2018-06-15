package com.timezerg.api.mapper;

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
public interface NodeNationMapper {

    @Insert("insert into t_timezerg_node_nation values (#{id},#{nodeid},#{nationid})")
    int add(NodeNation nodeNation);

    @Select("SELECT nn.*,na.title FROM t_timezerg_node_nation nn LEFT JOIN t_timezerg_nation na ON nn.nationid = na.id WHERE nn.nodeid = #{nodeid}")
    List<HashMap> selectByNodeId(String nodeid);

    @Delete("DELETE FROM t_timezerg_node_nation WHERE nodeid = #{nodeid}")
    int deleteByNodeId(String nodeid);

    @Select("select * from t_timezerg_node_nation where id = #{id}")
    NodeNation selectById(String id);

    @Select("select * from t_timezerg_node_nation where nodeid = #{nodeid} and nationid = #{nationid}")
    NodeNation selectByNodeIdAndNationId(NodeNation nodeNation);

    @Delete("delete from t_timezerg_node_nation where id = #{id}")
    int deleteById(String id);

}
