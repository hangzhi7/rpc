package com.hh.rpc.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/20
 * @Modified By :
 */
@Slf4j
public class NettyRpcFramework {
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
        if (interfaceClass == null)
            throw new IllegalArgumentException("Interface class == null");
        if (!interfaceClass.isInterface())
            throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");
        if (host == null || host.length() == 0)
            throw new IllegalArgumentException("Host == null!");
        if (port <= 0 || port > 65535)
            throw new IllegalArgumentException("Invalid port " + port);
        log.info("Get remote service {} from server {} " ,interfaceClass.getName(),port);
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass},
                new InvocationHandler() {//用动态代理的方法进行包装，看起来是在调用一个方法，事实上在内部通过socket通信传到server。并接收执行结果
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        ClassInfo classInfo = new ClassInfo();
                        classInfo.setClassName(interfaceClass.getName());
                        classInfo.setMethodName(method.getName());
                        classInfo.setObjects(args);
                        classInfo.setTypes(method.getParameterTypes());

                        ResultHandler resultHandler = new ResultHandler();
                        EventLoopGroup group = new NioEventLoopGroup();
                        try {
                            Bootstrap b = new Bootstrap();
                            b.group(group)
                                    .channel(NioSocketChannel.class)
                                    .option(ChannelOption.TCP_NODELAY, true)
                                    .handler(new ChannelInitializer<SocketChannel>() {
                                        @Override
                                        public void initChannel(SocketChannel ch) throws Exception {
                                            ChannelPipeline pipeline = ch.pipeline();
                                            pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                                            pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                                            pipeline.addLast("encoder", new ObjectEncoder());
                                            pipeline.addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                                            pipeline.addLast("handler", resultHandler);
                                        }
                                    });

                            ChannelFuture future = b.connect(host,port).sync();
                            future.channel().writeAndFlush(classInfo).sync();
                            future.channel().closeFuture().sync();
                        } finally {
                            group.shutdownGracefully();
                        }
                        return resultHandler.getResponse();
                    }
                });
    }

}
