package com.hh.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.demo.nettyrpc"})
public class NettyRpcClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyRpcClientApplication.class, args);
	}
}
