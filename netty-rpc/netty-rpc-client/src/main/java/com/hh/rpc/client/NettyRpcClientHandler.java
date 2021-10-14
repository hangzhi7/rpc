package com.hh.rpc.client;

import com.hh.rpc.util.rpc.NettyRpcRequest;
import com.hh.rpc.util.rpc.NettyRpcResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/27
 * @Modified By :
 */
@Slf4j
public class NettyRpcClientHandler extends SimpleChannelInboundHandler<NettyRpcResponse> {

    private ConcurrentHashMap<String, NettyRpcFuture> pendingRPC = new ConcurrentHashMap<>();

    private volatile Channel channel;

    private SocketAddress remotePeer;

    public Channel getChannel() {
        return channel;
    }

    public SocketAddress getRemotePeer() {
        return remotePeer;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.remotePeer = this.channel.remoteAddress();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, NettyRpcResponse response) throws Exception {
        String requestId = response.getRequestId();
        NettyRpcFuture rpcFuture = pendingRPC.get(requestId);
        if (rpcFuture != null) {
            pendingRPC.remove(requestId);
            rpcFuture.done(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("client caught exception", cause);
        ctx.close();
    }

    public void close() {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    public NettyRpcFuture sendRequest(NettyRpcRequest request) {
        final CountDownLatch latch = new CountDownLatch(1);
        NettyRpcFuture rpcFuture = new NettyRpcFuture(request);
        pendingRPC.put(request.getRequestId(), rpcFuture);
        channel.writeAndFlush(request).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                latch.countDown();
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

        return rpcFuture;
    }
}
