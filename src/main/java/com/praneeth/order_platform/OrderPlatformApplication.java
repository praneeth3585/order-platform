package com.praneeth.order_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
@EnableKafka
@SpringBootApplication


public class OrderPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderPlatformApplication.class, args);
	}

}
