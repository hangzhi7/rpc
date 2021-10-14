package com.hh.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication(scanBasePackages = {"com.demo.nettyrpc"})
public class NettyRpcApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyRpcApplication.class, args);
	}
}
