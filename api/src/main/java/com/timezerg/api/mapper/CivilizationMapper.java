package com.timezerg.api.mapper;

import com.timezerg.api.mapper.provider.CivilizationMapperProvider;
import com.timezerg.api.model.Civilization;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface CivilizationMapper {

    @Insert("insert into t_timezerg_civilization values (#{id},#{pid},#{title},#{content},#{cover},#{cdate},#{sort})")
    int add(Civilization civilization);

    @Select("select * from t_timezerg_civilization where id = #{id}")
    Civilization selectById(String id);

    @Select("select n.* from t_timezerg_civilization n where 1 = 1 order by n.sort asc limit #{array[0]},#{array[1]}")
    List<HashMap> getList(Object[] params);

    @Select("select count(*) from t_timezerg_civilization")
    Long getListTotal(Object[] params);

    @Select("select * from t_timezerg_civilization where title like CONCAT('%',#{title},'%') limit 0,10")
    List<Civilization> selectLikeByTitle(String title);

    @Update("update t_timezerg_civilization set title = #{title},pid = #{pid},content = #{content},cover = #{cover},cdate = #{cdate} where id = #{id}")
    int update(Civilization civilization);

    @Update("update t_timezerg_civilization set sort = #{array[0]} where id = #{array[1]}")
    int updateSortById(Object[] params);

    @Select("select max(sort) from t_timezerg_civilization")
    int selectMaxSort();

    @Select("select min(sort) from t_timezerg_civilization")
    int selectMinSort();

//    @Select("(SELECT n.* FROM t_timezerg_node n " +
//            "LEFT JOIN t_timezerg_node_civilization nc ON n.id = nc.nid " +
//            "WHERE nc.cid = ${array[0]} AND n.ad = 0 " +
//            "<if test = \"array[1] != 0 \"> and n.level <= #{array[1]} </if>" +
//            "ORDER BY n.cdate DESC LIMIT 0,9999) " +
//            "UNION ALL " +
//            "(SELECT n.* FROM t_timezerg_node n " +
//            "LEFT JOIN t_timezerg_node_civilization nc ON n.id = nc.nid " +
//            "WHERE nc.cid = ${array[0]} AND n.ad = 1 " +
//            "<if test = \"array[1] != 0 \"> and n.level <= #{array[1]} </if> " +
//            "ORDER BY n.cdate ASC LIMIT 0,9999)")
    @SelectProvider(type= CivilizationMapperProvider.class,method = "selectTimeLine")
    List<HashMap> selectTimeLine(@Param("cid") String cid,@Param("level") Integer level,@Param("start") Integer start,@Param("limit") Integer limit);

}
