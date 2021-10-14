package com.hh.rpc.core;

import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/20
 * @Modified By :
 */
@Slf4j
public class RpcFramework {
    /**
     * 引用服务
     *
     * @param <T>            接口泛型
     * @param interfaceClass 接口类型
     * @param host           server主机名
     * @param port           server端口
     * @return 远程服务
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T refer(final Class<T> interfaceClass, final String host, final int port) throws Exception {
        if (interfaceClass == null){
            throw new IllegalArgumentException("Interface class == null");
        }

        if (!interfaceClass.isInterface()){
            throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");
        }

        if (host == null || host.length() == 0){
            throw new IllegalArgumentException("Host == null!");
        }

        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port " + port);
        }

        log.info("Get remote service {} from server {} " ,interfaceClass.getName(),port);
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                //用动态代理的方法进行包装，看起来是在调用一个方法，事实上在内部通过socket通信传到server。并接收执行结果
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {

                        Socket socket = new Socket(host, port);
                        try {
                            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                            try {
                                output.writeUTF(method.getName());
                                output.writeObject(method.getParameterTypes());
                                output.writeObject(arguments);
                                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                                try {
                                    Object result = input.readObject();
                                    if (result instanceof Throwable) {
                                        throw (Throwable) result;
                                    }
                                    return result;//返回结果
                                } finally {
                                    input.close();
                                }
                            } finally {
                                output.close();
                            }
                        } finally {
                            socket.close();
                        }
                    }
                });
    }

}
