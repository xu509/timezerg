package com.timezerg.api.mapper;

import com.alibaba.fastjson.JSONArray;
import com.timezerg.api.mapper.provider.NodeMapperProvider;
import com.timezerg.api.model.Node;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface NodeMapper {

    @Insert("insert into t_timezerg_node values (#{id},#{title},#{content},#{cover},#{cdate},#{ddate},#{AD},#{level})")
    int add(Node node);

    @Select("select * from t_timezerg_node where id = #{id}")
    Node selectById(String id);

    @SelectProvider(type= NodeMapperProvider.class,method = "getList")
    List<HashMap> getList(@Param("cids") Object[] cids, @Param("start") Integer start, @Param("limit") Integer limit);

    @SelectProvider(type= NodeMapperProvider.class,method = "getListTotal")
    Long getListTotal(@Param("cids") Object[] cids);

    @Update("update t_timezerg_node set title = #{title},content = #{content},cover = #{cover},cdate = #{cdate},ddate = #{ddate},ad = #{AD},level = #{level} where id = #{id}")
    int update(Node node);


}
