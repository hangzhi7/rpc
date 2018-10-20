package com.hh.rpc.core;

import com.hh.rpc.utils.SpringContextUtil;

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
        List<Object> objectList =new ArrayList<>();
        for (String serviceName : serviceNames) {
            Object bean = SpringContextUtil.getBean(serviceName);
            objectList.add(bean);
        }
        Object[] objects = objectList.toArray();
        RpcFramework.export(objects, 18888);
    }

}
