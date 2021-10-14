package com.hh.rpc.util;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/27
 * @Modified By :
 */
public interface Constant {

    /**时间**/
    String YYYYMMDDHHMMSS="yyyy-MM-dd HH:mm:ss";


    /**zookeeper*/
    int ZK_SESSION_TIMEOUT = 5000;
    String ZK_REGISTRY_PATH = "/registry";
    String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data";
}
