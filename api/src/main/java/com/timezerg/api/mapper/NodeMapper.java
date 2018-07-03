package com.timezerg.api.mapper;

import com.alibaba.fastjson.JSONArray;
import com.timezerg.api.mapper.provider.NodeMapperProvider;
import com.timezerg.api.model.Node;
import org.apache.ibatis.annotations.*;

import java.util.Date;
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

    @Select("select * from t_timezerg_node where title = #{title}")
    Node selectByTitle(String title);

    @Select("select * from t_timezerg_node where title like CONCAT('%',#{title},'%') limit 0,10")
    List<Node> selectLikeByTitle(String title);

    @SelectProvider(type= NodeMapperProvider.class,method = "getList")
    List<HashMap> getList(@Param("cids") Object[] cids, @Param("searchtitle") String searchtitle,@Param("start") Integer start, @Param("limit") Integer limit);

//
//    @SelectProvider(type= NodeMapperProvider.class,method = "getListByTitle")
//    List<HashMap> getListByTitle(@Param("searchtitle") String searchtitle,@Param("start") Integer start, @Param("limit") Integer limit);


    @SelectProvider(type= NodeMapperProvider.class,method = "getListTotal")
    Long getListTotal(@Param("cids") Object[] cids, @Param("searchtitle") String searchtitle);

//    @SelectProvider(type= NodeMapperProvider.class,method = "getListTotalByTitle")
//    Long getListTotalByTitle(@Param("searchtitle") String searchtitle);

    @Update("update t_timezerg_node set title = #{title},content = #{content},cover = #{cover},cdate = #{cdate},ddate = #{ddate},ad = #{AD},level = #{level} where id = #{id}")
    int update(Node node);

    //73000代表200年
    @Select("select n.*,c.title as ctitle from t_timezerg_node n left join t_timezerg_node_civilization nc on n.id = nc.nid " +
            "left join t_timezerg_civilization c on nc.cid = c.id where abs(to_days(n.cdate) - to_days(#{cdate})) < 73000 and n.ad = #{AD} " +
            "and nc.cid != (select cid from t_timezerg_node_civilization where nid = #{id})")
    List<HashMap> selectCloseNode(Node node);

    @Select("SELECT *,ABS(TO_DAYS(cdate) -  TO_DAYS(#{date})) AS `min` FROM t_timezerg_node ORDER BY `min` ASC limit 0,5")
    List<HashMap> selectCloseNodeByDate(Date date);


}
