package com.timezerg.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.timezerg.app.model.Node;
import com.timezerg.app.service.NodeService;
import com.timezerg.app.util.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimezergAppApplicationTests {


	@Autowired
	NodeService nodeService;

	@Test
	@Rollback(false)
	public void contextLoads() {
//		Node node = new Node();
//		node.setId(Utils.generateId());
//		node.setTitle("test");
//		node.setContent("content");
//		node.setCdate(new Date());
//		nodeService.add(node);

//		Node node = nodeService.selectById("929148402938155008");

		JSONObject params = new JSONObject();
		params.put("start",0);
		params.put("size",2);

		List d = (List) nodeService.getList(params);

		System.out.println(JSON.toJSON(d));

	}

}
