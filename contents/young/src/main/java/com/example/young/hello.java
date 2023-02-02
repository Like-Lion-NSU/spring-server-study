package com.example.young;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class hello {
    @RequestMapping("/hello")
    @ResponseBody

    public String hello(){
        return "hello world123";}

}
