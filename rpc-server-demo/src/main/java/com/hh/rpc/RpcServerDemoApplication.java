package com.hh.rpc;

import com.hh.rpc.core.RpcServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpcServerDemoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RpcServerDemoApplication.class, args);
		String[] serviceNames={"helloService","userService"};
		new RpcServer(serviceNames).init();
	}
}
