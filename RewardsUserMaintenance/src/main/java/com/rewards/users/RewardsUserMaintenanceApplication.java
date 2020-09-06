package com.rewards.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = {"com.rewards.users.security",
		"com.rewards.users.controller" ,
		"com.rewards.users.service" ,
		"com.rewards.users.serviceimpl" ,
		"com.rewards.users.repositories",
		"com.rewards.users.config",
		"com.rewards.users.utils",
		"com.rewards.users.entities",
		"com.rewards.users.models"})
@EnableDiscoveryClient
public class RewardsUserMaintenanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardsUserMaintenanceApplication.class, args);
	}

	/* If we don’t specify, it will use plain text. */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
