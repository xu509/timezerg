package com.timezerg.api.mapper;

import com.timezerg.api.mapper.provider.ReferenceMapperProvider;
import com.timezerg.api.model.Reference;
import org.apache.ibatis.annotations.*;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface ReferenceMapper {

    @Insert("insert into t_timezerg_reference values (#{id},#{pid},#{title},#{content},#{type})")
    int add(Reference reference);

    @SelectProvider(type= ReferenceMapperProvider.class,method = "getList")
    List<HashMap> getList(@Param("searchtitle") String searchtitle, @Param("start") Integer start, @Param("limit") Integer limit);

    @SelectProvider(type= ReferenceMapperProvider.class,method = "getListTotal")
    Long getListTotal(@Param("searchtitle") String searchtitle);

    @Select("select * from t_timezerg_reference where title like CONCAT('%',#{title},'%') limit 0,10")
    List<Reference> selectLikeByTitle(String title);

    @Select("select * from t_timezerg_reference where id = #{id}")
    Reference selectById(String id);

    @Select("select * from t_timezerg_reference where title = #{title}")
    Reference selectByTitle(String title);

    @Update("update t_timezerg_reference set title = #{title},content = #{content},type = #{type},pid = #{pid}" +
            " where id = #{id}")
    int update(Reference reference);


}
