package com.bookstore.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan({"com.bookstore.model"})
public class BookStoreClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreClientApplication.class, args);
	}

}
