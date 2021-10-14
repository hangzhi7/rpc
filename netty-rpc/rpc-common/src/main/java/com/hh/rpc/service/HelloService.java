package com.hh.rpc.service;

import com.hh.rpc.model.User;

public interface HelloService {
    String hello(String name);
 	String hello(User user);
 }
