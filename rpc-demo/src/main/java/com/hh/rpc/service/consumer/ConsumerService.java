package com.hh.rpc.service.consumer;

import com.hh.rpc.core.RpcFramework;
import com.hh.rpc.model.UserInfo;
import com.hh.rpc.serrvice.HelloService;
import com.hh.rpc.serrvice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/20
 * @Modified By :
 */
@Service
@Slf4j
public class ConsumerService {

    @Value("${provider.server.host:127.0.0.1}")
    private String host;
    @Value("${provider.server.port:18888}")
    private Integer port;

    public String say(String name) throws Exception {
        log.info("进入say()方法");
        HelloService helloService = RpcFramework.refer(HelloService.class, host, port);
        String hello = helloService.hello(name);
        return hello;
    }

    public UserInfo getUser(String name) throws Exception {
        log.info("进入getUser()方法");
        UserService userService = RpcFramework.refer(UserService.class, host, port);
        UserInfo user = userService.getUser(name);
        return user;
    }
}
