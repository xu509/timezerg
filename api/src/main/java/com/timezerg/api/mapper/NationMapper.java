package com.timezerg.api.mapper;

import com.timezerg.api.model.Nation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xnx on 2018/5/4.
 */
@Mapper
public interface NationMapper {

    @Insert("insert into t_timezerg_nation values (#{id},#{pid},#{title},#{content},#{cover},#{cdate},#{ddate},#{AD})")
    int add(Nation nation);

    @Select("(SELECT n.* FROM t_timezerg_nation n WHERE 1 = 1 AND n.AD = 0 ORDER BY n.cdate DESC LIMIT 99999999)" +
            "UNION ALL " +
            "(SELECT n2.* FROM t_timezerg_nation n2 WHERE 1 = 1 AND n2.AD = 1 ORDER BY n2.cdate ASC LIMIT 99999999) limit #{array[0]},#{array[1]}")
    List<HashMap> getList(Object[] params);

    @Select("select count(*) from t_timezerg_nation")
    Long getListTotal(Object[] params);


    @Select("select * from t_timezerg_nation where title like CONCAT('%',#{title},'%') limit 0,10")
    List<Nation> selectLikeByTitle(String title);

}
