package com.lx.controller;

import com.lx.pojo.RC;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;


@Api(value = "登录接口" ,description = "登录接口")
@RestController
@RequestMapping("/eduTeacher/admin")
//@CrossOrigin
public class LoginController {

    @PostMapping("login")
    public RC login(){
        return  RC.success().data("token","admin");
    }

    @GetMapping("info")
    public RC info(){
        return RC.success().data("roles","admin123").data("name","admin").data("avatar","https://tse3-mm.cn.bing.net/th/id/OIP.OzTCtonjrdsbnZ8tbeYM4AAAAA?w=174&h=180&c=7&o=5&dpr=1.25&pid=1.7");
    }
}
