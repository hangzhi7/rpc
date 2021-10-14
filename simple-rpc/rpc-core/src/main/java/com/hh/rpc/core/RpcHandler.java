package com.hh.rpc.core;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 请求处理
 *
 * @author hang.yuan 2021/10/14 15:01
 */
public class RpcHandler implements Runnable {

    private final Socket socket;
    private final Object[] objects;

    public RpcHandler(Socket socket, Object[] objects) {
        this.socket = socket;
        this.objects = objects;
    }

    @Override
    public void run() {
        try {
            try {
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                try {
                    String methodName = input.readUTF();
                    Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                    Object[] arguments = (Object[]) input.readObject();
                    //接收client传来的方法名、參数类型、參数
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                    try {
                        Method method = null;
                        Object service = null;
                        for (Object serviceSub : objects) {
                            try {
                                method = serviceSub.getClass().getMethod(methodName, parameterTypes);
                            } catch (Throwable t) {
                            }
                            //在本地生成相应的方法，
                            if (method != null) {
                                service = serviceSub;
                                break;
                            }
                        }
                        //调用
                        Object result = method.invoke(service, arguments);
                        //返回结果
                        output.writeObject(result);
                    } catch (Exception t) {
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
    }
}
