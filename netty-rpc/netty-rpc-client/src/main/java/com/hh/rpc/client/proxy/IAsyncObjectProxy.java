package com.hh.rpc.client.proxy;

import com.hh.rpc.client.NettyRpcFuture;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/27
 * @Modified By :
 */
public interface IAsyncObjectProxy {
    NettyRpcFuture call(String funcName, Object... args);
}
