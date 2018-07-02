package com.timezerg.api.mapper;

import com.timezerg.api.mapper.provider.NodePeriodMapperProvider;
import com.timezerg.api.model.NodePeriod;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface NodePeriodMapper {

    @Insert("insert into t_timezerg_node_period values (#{id},#{nid},#{pid},#{level})")
    int add(NodePeriod nodePeriod);

    @Select("SELECT nc.*,c.title FROM t_timezerg_node_period nc LEFT JOIN t_timezerg_civilization c ON nc.pid = c.id WHERE nc.nid = #{nid}")
    List<HashMap> selectByNodeId(String nid);

    @Select("SELECT nc.pid,c.title FROM t_timezerg_node_period nc " +
            "LEFT JOIN t_timezerg_civilization c ON nc.pid = c.id " +
            "WHERE nc.nid = #{nid}")
    List<HashMap> selectCivilizationByNid(String nid);

    @Delete("DELETE FROM t_timezerg_node_period WHERE nid = #{nid}")
    int deleteByNodeId(String nid);

    @SelectProvider(type= NodePeriodMapperProvider.class,method = "getNodes")
    List<HashMap> selectNodesByPid(@Param("pid") String pid, @Param("level") Integer level, @Param("title") String title, @Param("start") Integer start, @Param("limit") Integer limit);

    @SelectProvider(type= NodePeriodMapperProvider.class,method = "getNodesTotal")
    Long selectNodesTotalByPid(@Param("pid") String pid, @Param("level") Integer level, @Param("title") String title);

    @Update("UPDATE t_timezerg_node_period SET level = #{level} WHERE id = #{id}")
    void updateLevel(NodePeriod nodePeriod);

    @Select("SELECT * FROM t_timezerg_node_period WHERE id = #{id}")
    NodePeriod selectById(String id);

    @Select("SELECT * FROM t_timezerg_node_period WHERE nid = #{nid} AND pid = #{pid}")
    NodePeriod selectByNidAndPid(NodePeriod nodePeriod);

    @Select("SELECT count(0) FROM t_timezerg_node_period WHERE nid = #{nid} And level = #{level}")
    Long selectCountByNidAndLevel(NodePeriod nodePeriod);

}
