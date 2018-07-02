package com.timezerg.api.mapper;

import com.timezerg.api.mapper.provider.NodeCivilizationMapperProvider;
import com.timezerg.api.model.NodeCivilization;
import com.timezerg.api.model.NodeNation;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface NodeCivilizationMapper {

    @Insert("insert into t_timezerg_node_civilization values (#{id},#{nid},#{cid},#{level})")
    int add(NodeCivilization nodeCivilization);

    @Select("SELECT nc.*,c.title FROM t_timezerg_node_civilization nc LEFT JOIN t_timezerg_civilization c ON nc.cid = c.id WHERE nc.nid = #{nid}")
    List<HashMap> selectByNodeId(String nid);

    @Select("SELECT nc.cid,c.title FROM t_timezerg_node_civilization nc " +
            "LEFT JOIN t_timezerg_civilization c ON nc.cid = c.id " +
            "WHERE nc.nid = #{nid}")
    List<HashMap> selectCivilizationByNid(String nid);

    @Delete("DELETE FROM t_timezerg_node_civilization WHERE nid = #{nid}")
    int deleteByNodeId(String nid);

    @SelectProvider(type= NodeCivilizationMapperProvider.class,method = "getNodes")
    List<HashMap> selectNodesByCid(@Param("cid") String cid ,@Param("level") Integer level,@Param("title") String title,@Param("start") Integer start, @Param("limit") Integer limit);

    @SelectProvider(type= NodeCivilizationMapperProvider.class,method = "getNodesTotal")
    Long selectNodesTotalByCid(@Param("cid") String cid ,@Param("level") Integer level,@Param("title") String title);

    @Update("UPDATE t_timezerg_node_civilization SET level = #{level} WHERE id = #{id}")
    void updateLevel(NodeCivilization nodeCivilization);

    @Select("SELECT * FROM t_timezerg_node_civilization WHERE id = #{id}")
    NodeCivilization selectById(String id);

    @Select("SELECT * FROM t_timezerg_node_civilization WHERE nid = #{nid} AND cid = #{cid}")
    NodeCivilization selectByNidAndCid(NodeCivilization nodeCivilization);

    @Select("SELECT count(0) FROM t_timezerg_node_civilization WHERE nid = #{nid} And level = #{level}")
    Long selectCountByNidAndLevel(NodeCivilization nodeCivilization);

}
