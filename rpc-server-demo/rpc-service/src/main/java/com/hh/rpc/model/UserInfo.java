package com.hh.rpc.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/20
 * @Modified By :
 */
@Data
public class UserInfo implements Serializable {
    private String name;
    private int age;
    private String sex;
}
