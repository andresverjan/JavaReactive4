package com.example.r2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class R2dbcApplication  {

	private static DatabaseService databaseService;

    public R2dbcApplication(DatabaseService databaseService) {
        R2dbcApplication.databaseService = databaseService;
    }


    public static void main(String[] args)  {
		SpringApplication.run(R2dbcApplication.class, args);

		databaseService.testConnection()
				.doOnSuccess(aVoid -> System.out.println("Connection test successful!"))
				.doOnError(throwable -> System.err.println("Connection test failed: " + throwable.getMessage()))
				.subscribe();
	}
}
