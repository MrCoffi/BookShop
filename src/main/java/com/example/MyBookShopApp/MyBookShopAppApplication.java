package com.example.MyBookShopApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class MyBookShopAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBookShopAppApplication.class, args);
	}

}
