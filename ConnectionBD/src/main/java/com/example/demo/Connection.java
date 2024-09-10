package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@AllArgsConstructor
public class Connection implements CommandLineRunner {

    private final DatabaseClient databaseClient;

    public Mono<Void> createTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS person (" +
                "    id SERIAL PRIMARY KEY," +
                "    name VARCHAR(100) NOT NULL," +
                "    age INT," +
                "    gender VARCHAR(10)," +
                "    date_of_birth DATE," +
                "    blood_type VARCHAR(5)" +
                ");";
        return databaseClient.sql(createTableQuery)
                .then();
    }

    @Override
    public void run(String... args) throws Exception {
        createTable().subscribe();
    }

}
