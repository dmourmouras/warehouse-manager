package com.dm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class WarehouseManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseManagerApplication.class, args);
	}

}
