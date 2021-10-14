package com.hh.rpc.client;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/27
 * @Modified By :
 */
public interface AsyncRpcCallback {

    void success(Object result);

    void fail(Exception e);

}
