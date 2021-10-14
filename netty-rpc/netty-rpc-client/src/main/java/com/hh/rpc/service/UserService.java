package com.hh.rpc.service;

import com.hh.rpc.client.NettyRpcClient;
import com.hh.rpc.client.NettyRpcFuture;
import com.hh.rpc.client.proxy.IAsyncObjectProxy;
import com.hh.rpc.model.User;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/28
 * @Modified By :
 */
@Service
public class UserService {


    public String hello(String name){
        HelloService helloService = NettyRpcClient.create(HelloService.class);
        String hello = helloService.hello(name);
        return hello;
    }

    public String hello(User user) throws InterruptedException, ExecutionException, TimeoutException {
        IAsyncObjectProxy async = NettyRpcClient.createAsync(HelloService.class);
        NettyRpcFuture helloService = async.call("hello", user);
        String hello = (String)helloService.get(3000, TimeUnit.MILLISECONDS);
        return hello;
    }
}
