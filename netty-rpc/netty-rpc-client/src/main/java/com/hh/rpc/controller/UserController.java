package com.hh.rpc.controller;

import com.hh.rpc.model.User;
import com.hh.rpc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/28
 * @Modified By :
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getName")
    public String getName(String name){
        String hello = userService.hello(name);
        return hello;
    }
    @GetMapping("/getNames")
    public String getName() throws InterruptedException, ExecutionException, TimeoutException {
        User user=new User();
        user.setAge(13);
        user.setName("xiaomm");
        user.setSex("man");
        String hello = userService.hello(user);
        return hello;
    }
}
