package com.hh.rpc.service.impl;

import com.hh.rpc.model.UserInfo;
import com.hh.rpc.serrvice.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author : Hang.Yuan
 * @Date : Created in 2018/10/20
 * @Modified By :
 */
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService{

    @Override
    public UserInfo getUser(String name) {
        UserInfo userInfo =new UserInfo();
        userInfo.setAge(10);
        userInfo.setSex("男");
        userInfo.setName("yh");
        if(userInfo.getName().equals(name)){
            return userInfo;
        }else {
            return null;
        }

    }



}
