package com.hh.rpc.client;

import com.demo.nettyrpc.util.rpc.NettyRpcDecoder;
import com.demo.nettyrpc.util.rpc.NettyRpcEncoder;
import com.demo.nettyrpc.util.rpc.NettyRpcRequest;
import com.demo.nettyrpc.util.rpc.NettyRpcResponse;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/27
 * @Modified By :
 */
public class NettyRpcClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline cp = socketChannel.pipeline();
        cp.addLast(new NettyRpcEncoder(NettyRpcRequest.class));
        cp.addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0));
        cp.addLast(new NettyRpcDecoder(NettyRpcResponse.class));
        cp.addLast(new NettyRpcClientHandler());
    }
}
