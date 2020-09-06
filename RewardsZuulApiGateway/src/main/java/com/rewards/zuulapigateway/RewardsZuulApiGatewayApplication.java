package com.rewards.zuulapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication(scanBasePackages ={"com.rewards.zuulapigateway.security"})
@EnableEurekaClient
@EnableZuulProxy
public class RewardsZuulApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(RewardsZuulApiGatewayApplication.class, args);
	}

}
