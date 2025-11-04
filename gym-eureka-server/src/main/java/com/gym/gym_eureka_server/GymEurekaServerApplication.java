package com.gym.gym_eureka_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaServer
public class GymEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymEurekaServerApplication.class, args);
	}

}
