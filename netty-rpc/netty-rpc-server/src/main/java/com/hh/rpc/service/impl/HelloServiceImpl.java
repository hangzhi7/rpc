package com.hh.rpc.service.impl;

import com.hh.rpc.model.User;
import com.hh.rpc.server.NettyRpcService;
import com.hh.rpc.service.HelloService;

@NettyRpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {
    public HelloServiceImpl() {
    }

    @Override
    public String hello(String name) {
        return "Hello! " + name;
    }

    @Override
    public String hello(User user) {
        return "Hello! " + user.getName() + "-" + user.getSex() + "-" + user.getAge();
    }
}
