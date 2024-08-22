package com.example.r2dbc;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DatabaseService {

    private final DatabaseClient databaseClient;

    public DatabaseService(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    public Mono<Void> testConnection() {
        return databaseClient.sql("SELECT 1")
                .fetch()
                .first()
                .then();
    }
}
