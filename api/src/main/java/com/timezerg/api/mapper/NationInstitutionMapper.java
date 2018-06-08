package com.timezerg.api.mapper;

import com.timezerg.api.model.NationInstitution;
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
public interface NationInstitutionMapper {

    @Insert("insert into t_timezerg_nation_institution values (#{id},#{iid},#{nid})")
    int add(NationInstitution nationInstitution);

    @Select("select * from t_timezerg_nation_institution where iid = #{iid} and nid = #{nid}")
    NationInstitution selectByGidAndNid(NationInstitution nationInstitution);

    @Delete("delete from t_timezerg_nation_institution where nid = #{nid}")
    void deleteByNid(String nid);

    @Select("SELECT ni.*,i.title,i.type FROM t_timezerg_nation_institution ni LEFT JOIN t_timezerg_institution i ON ni.iid = i.id WHERE ni.nid = #{nid}")
    List<HashMap> selectByNid(String nid);

}
