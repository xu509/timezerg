package com.timezerg.app;

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

		Node node = nodeService.selectById("929148402938155008");

		System.out.println(node.getTitle());

	}

}
