package com.hh.rpc.core;

import com.hh.rpc.util.MultiThreadPool;
import com.hh.rpc.util.SpringContextUtil;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/20
 * @Modified By :
 */

public class RpcServer {

    private String[] serviceNames;

    public RpcServer(String[] serviceNames) {
        this.serviceNames = serviceNames;
    }

    public void init() throws Exception {
        List<Object> objectList = new ArrayList<>();
        for (String serviceName : serviceNames) {
            Object bean = SpringContextUtil.getBean(serviceName);
            objectList.add(bean);
        }
        ThreadPoolTaskExecutor instance = MultiThreadPool.getInstance();
        Object[] objects = objectList.toArray();
        RpcFramework.export(instance,objects, 18888);
    }

}
