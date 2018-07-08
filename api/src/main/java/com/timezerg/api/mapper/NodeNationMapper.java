package com.timezerg.api.mapper;

import com.timezerg.api.mapper.provider.NodeNationMapperProvider;
import com.timezerg.api.model.Nation;
import com.timezerg.api.model.Node;
import com.timezerg.api.model.NodeNation;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface NodeNationMapper {

    @Insert("insert into t_timezerg_node_nation values (#{id},#{nodeid},#{nationid},#{level})")
    int add(NodeNation nodeNation);

    @Select("SELECT nn.*,na.title FROM t_timezerg_node_nation nn LEFT JOIN t_timezerg_nation na ON nn.nationid = na.id WHERE nn.nodeid = #{nodeid}")
    List<HashMap> selectByNodeId(String nodeid);

    @Delete("DELETE FROM t_timezerg_node_nation WHERE nodeid = #{nodeid}")
    int deleteByNodeId(String nodeid);

    @SelectProvider(type= NodeNationMapperProvider.class,method = "getNodes")
    List<HashMap> selectNodesByNid(@Param("nid") String nid ,@Param("level") Integer level,@Param("title") String title,@Param("start") Integer start, @Param("limit") Integer limit);

    @SelectProvider(type= NodeNationMapperProvider.class,method = "getNodesTotal")
    Long selectNodesTotalByNid(@Param("nid") String nid ,@Param("level") Integer level,@Param("title") String title);


    @Select("select * from t_timezerg_node_nation where id = #{id}")
    NodeNation selectById(String id);

    @Select("select * from t_timezerg_node_nation where nodeid = #{nodeid} and nationid = #{nationid}")
    NodeNation selectByNodeIdAndNationId(NodeNation nodeNation);

    @SelectProvider(type= NodeNationMapperProvider.class,method = "selectNodesByNationIds")
    List<Node> selectNodesByNationIds(@Param("nids") Object[] nids);

    @Select("select na.* from t_timezerg_node_nation nn left join t_timezerg_nation na on nn.nationid = na.id where nn.nodeid = #{id}")
    List<Nation> selectNationsByNodeId(String id);

    @Delete("delete from t_timezerg_node_nation where id = #{id}")
    int deleteById(String id);


    @Update("UPDATE t_timezerg_node_nation SET level = #{level} WHERE id = #{id}")
    void updateLevel(NodeNation nodeNation);


}
