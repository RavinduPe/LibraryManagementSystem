package com.example.LibraryManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementSystemApplication.class, args);
		System.out.println("Library Management System started successfully!");
		System.out.println("Swagger UI: http://localhost:8080/swagger-ui.html");
	}
}