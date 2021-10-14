package com.hh.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hh.rpc"})
public class NettyRpcClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyRpcClientApplication.class, args);
	}
}
