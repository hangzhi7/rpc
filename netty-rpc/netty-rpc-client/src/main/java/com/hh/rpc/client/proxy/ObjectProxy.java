package com.hh.rpc.client.proxy;

import com.hh.rpc.client.ConnectManage;
import com.hh.rpc.client.NettyRpcClientHandler;
import com.hh.rpc.client.NettyRpcFuture;
import com.hh.rpc.util.rpc.NettyRpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/27
 * @Modified By :
 */
@Slf4j
public class ObjectProxy<T> implements InvocationHandler, IAsyncObjectProxy {

    private Class<T> clazz;

    public ObjectProxy(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if ("equals".equals(name)) {
                return proxy == args[0];
            } else if ("hashCode".equals(name)) {
                return System.identityHashCode(proxy);
            } else if ("toString".equals(name)) {
                return proxy.getClass().getName() + "@" +
                        Integer.toHexString(System.identityHashCode(proxy)) +
                        ", with InvocationHandler " + this;
            } else {
                throw new IllegalStateException(String.valueOf(method));
            }
        }

        NettyRpcRequest request = new NettyRpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        // Debug
        log.debug(method.getDeclaringClass().getName()+"--"+method.getName());
        for (int i = 0; i < method.getParameterTypes().length; ++i) {
            log.debug(method.getParameterTypes()[i].getName());
        }
        for (int i = 0; i < args.length; ++i) {
            log.debug(args[i].toString());
        }

        NettyRpcClientHandler handler = ConnectManage.getInstance().chooseHandler();
        NettyRpcFuture rpcFuture = handler.sendRequest(request);
        return rpcFuture.get();
    }

    @Override
    public NettyRpcFuture call(String funcName, Object... args) {
        NettyRpcClientHandler handler = ConnectManage.getInstance().chooseHandler();
        NettyRpcRequest request = createRequest(this.clazz.getName(), funcName, args);
        NettyRpcFuture rpcFuture = handler.sendRequest(request);
        return rpcFuture;
    }

    private NettyRpcRequest createRequest(String className, String methodName, Object[] args) {
        NettyRpcRequest request = new NettyRpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setClassName(className);
        request.setMethodName(methodName);
        request.setParameters(args);

        Class[] parameterTypes = new Class[args.length];
        // Get the right class type
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = getClassType(args[i]);
        }
        request.setParameterTypes(parameterTypes);
//        Method[] methods = clazz.getDeclaredMethods();
//        for (int i = 0; i < methods.length; ++i) {
//            // Bug: if there are 2 methods have the same name
//            if (methods[i].getName().equals(methodName)) {
//                parameterTypes = methods[i].getParameterTypes();
//                request.setParameterTypes(parameterTypes); // get parameter types
//                break;
//            }
//        }

        log.debug(className);
        log.debug(methodName);
        for (int i = 0; i < parameterTypes.length; ++i) {
            log.debug(parameterTypes[i].getName());
        }
        for (int i = 0; i < args.length; ++i) {
            log.debug(args[i].toString());
        }

        return request;
    }

    private Class<?> getClassType(Object obj) {
        Class<?> classType = obj.getClass();
        String typeName = classType.getName();
        switch (typeName) {
            case "java.lang.Integer":
                return Integer.TYPE;
            case "java.lang.Long":
                return Long.TYPE;
            case "java.lang.Float":
                return Float.TYPE;
            case "java.lang.Double":
                return Double.TYPE;
            case "java.lang.Character":
                return Character.TYPE;
            case "java.lang.Boolean":
                return Boolean.TYPE;
            case "java.lang.Short":
                return Short.TYPE;
            case "java.lang.Byte":
                return Byte.TYPE;
        }

        return classType;
    }
}
