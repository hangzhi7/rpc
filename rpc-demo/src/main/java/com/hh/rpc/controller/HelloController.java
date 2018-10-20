package com.hh.rpc.controller;

import com.hh.rpc.service.consumer.ConsumerService;
import com.hh.rpc.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/20
 * @Modified By :
 */
@RestController
public class HelloController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/hello")
    public String hello(String name) throws Exception {
        return consumerService.say(name);
    }

    @GetMapping("/getUser")
    public UserInfo getUser(String name) throws Exception {
        return consumerService.getUser(name);

    }
}
