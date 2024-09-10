package com.valko.actividad4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
//@EnableConfigurationProperties(ConnectionDemo.class)
@EnableR2dbcRepositories
public class Actividad4Application {

	@Autowired
	private ServiceDemo serviceDemo;
	public static void main(String[] args) {
		SpringApplication.run(Actividad4Application.class, args);
	}
}
