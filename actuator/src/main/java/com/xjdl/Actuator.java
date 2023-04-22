package com.xjdl;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class Actuator {
	public static void main(String[] args) {
		SpringApplication.run(Actuator.class, args);
	}
}
