package com.valko.actividad4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConnectionDemo.class)
public class Actividad4Application {

	@Autowired
	private ServiceDemo serviceDemo;
	public static void main(String[] args) {
		SpringApplication.run(Actividad4Application.class, args);
	}
}
