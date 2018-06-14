package com.timezerg.api.mapper;

import com.timezerg.api.model.NodeReference;
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
public interface NodeReferenceMapper {

    @Insert("insert into t_timezerg_node_reference values (#{id},#{nid},#{rid})")
    int add(NodeReference nodeReference);

    @Select("select * from t_timezerg_node_reference where nid = #{nid} and rid = #{rid}")
    NodeReference selectByNidAndRid(NodeReference nodeReference);

    @Select("select * from t_timezerg_node_reference where id = #{id}")
    NodeReference selectById(String id);

    @Delete("delete from t_timezerg_node_reference where rid = #{rid}")
    void deleteByRid(String rid);

    @Delete("delete from t_timezerg_node_reference where nid = #{nid}")
    void deleteByNid(String nid);

    @Delete("delete from t_timezerg_node_reference where id = #{id}")
    void deleteById(String id);

    @Select("SELECT nr.*,n.title FROM t_timezerg_node_reference nr LEFT JOIN t_timezerg_node n ON nr.nid = n.id WHERE nr.rid = #{rid}")
    List<HashMap> selectByRid(String rid);

    @Select("SELECT nr.*,r.title FROM t_timezerg_node_reference nr LEFT JOIN t_timezerg_reference r ON nr.rid = r.id WHERE nr.nid = #{nid}")
    List<HashMap> selectByNid(String nid);

}
