package com.timezerg.app.service;

import com.timezerg.app.mapper.NodeMapper;
import com.timezerg.app.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xnx on 2018/5/4.
 * 时间点服务类
 *
 */
@Service
public class NodeService {

    @Autowired
    NodeMapper nodeMapper;


    public int add(Node node){
        return nodeMapper.add(node);
    }

    public Node selectById(String id){
        return nodeMapper.selectById(id);
    }

}
