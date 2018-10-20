package com.hh.rpc.service.impl;

import com.hh.rpc.serrvice.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/20
 * @Modified By :
 */
@Slf4j
@Service("helloService")
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String content) {
        String providerContent= content+"经过服务端出来";
        System.out.println(providerContent);
        return providerContent;
    }
}
