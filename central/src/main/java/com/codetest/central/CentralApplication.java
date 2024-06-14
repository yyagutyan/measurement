package com.codetest.central;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class CentralApplication {
	public static void main(String[] args) {
		SpringApplication.run(CentralApplication.class, args);
	}
}
