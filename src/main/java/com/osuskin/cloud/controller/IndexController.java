package com.osuskin.cloud.controller;

import com.osuskin.cloud.pojo.bo.StandardResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/hello")
    public StandardResult hello() {
        return null;
    }

    @RequestMapping("/loginpage")
    public StandardResult loginpage() {
        return null;
    }
}
