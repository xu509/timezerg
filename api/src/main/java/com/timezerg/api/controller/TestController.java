package com.timezerg.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.timezerg.api.service.TagService;
import com.timezerg.api.util.Result;
import com.timezerg.api.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xnx on 2018/5/10.
 */

@RestController
public class TestController {

    @CrossOrigin
    @PostMapping("/api/book/detail")
    public Object bookDetail(@RequestBody JSONObject params){
        //

        String content = "<p>test111ddd</p>";

        content += "<table><th>名字</th><th>年龄</th><th>性别</th><th>哈哈哈</th>";
        content += "<tr><td>11</td><td>222</td><td>333</td><td>444</td></tr></table>";




        return new Result(ResultMessage.OK,content);
    }

}
