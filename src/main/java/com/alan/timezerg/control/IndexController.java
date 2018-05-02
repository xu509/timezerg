package com.alan.timezerg.control;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xnx on 2018/5/2.
 */

@RestController
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "Hello Spring Boot111";
    }

}
