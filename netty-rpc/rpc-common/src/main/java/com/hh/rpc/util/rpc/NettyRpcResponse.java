package com.hh.rpc.util.rpc;

import lombok.Data;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/27
 * @Modified By :
 */
@Data
public class NettyRpcResponse {

    private String requestId;
    private String error;
    private Object result;

    public boolean isError() {
        return error != null;
    }
}
