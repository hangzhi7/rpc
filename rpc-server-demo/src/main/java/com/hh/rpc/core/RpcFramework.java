package com.hh.rpc.core;

import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * RpcFramework 简单框架
 *
 * @author hang.yuan 2021/10/14 14:01
 */

@Slf4j
public class RpcFramework {

    /**
     * 暴露服务
     *
     * @param objects 服务实现
     * @param port    服务端口
     * @throws Exception
     */
    public static void export(final Object[] objects, int port) throws Exception {
        if (objects == null && objects.length >0) {
            throw new IllegalArgumentException("service instance == null");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port " + port);
        }
        for (Object service : objects) {
            log.info("Export service " + service.getClass().getName() + " on port " + port);
        }
        //前面都是验证调用是否符合规则，这里在被调用端开一个服务。以下就是死循环执行。
        ServerSocket server = new ServerSocket(port);
        for (; ; ) {
            try {
                final Socket socket = server.accept();
                //对每个请求new一个线程，匿名类
                new Thread(() -> {
                    try {
                        try {
                            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                            try {
                                String methodName = input.readUTF();
                                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                                Object[] arguments = (Object[]) input.readObject();
                                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());//接收client传来的方法名、參数类型、參数
                                try {
                                    Method method=null;
                                    Object service=null;
                                    for (Object serviceSub : objects) {
                                        try {
                                            method = serviceSub.getClass().getMethod(methodName, parameterTypes);
                                        }catch (Throwable t) {}
                                        //在本地生成相应的方法，
                                        if (method != null){
                                            service=serviceSub;
                                            break;
                                        }
                                    }
                                    Object result = method.invoke(service, arguments);//调用
                                    output.writeObject(result);//返回结果
                                } catch (Throwable t) {
                                    output.writeObject(t);
                                } finally {
                                    output.close();
                                }
                            } finally {
                                input.close();
                            }
                        } finally {
                            socket.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
