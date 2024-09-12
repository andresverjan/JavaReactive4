package com.example.demo.service;

import com.example.demo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class DatabaseService {
    @Autowired
    private DatabaseClient databaseClient;

    public Mono<Void> testConnection() {
        return databaseClient.sql("SELECT 1")
                .fetch()
                .rowsUpdated()
                .then();
    }
    public Mono<Void> insertDataBd(User newUser) {
        return databaseClient.sql("INSERT INTO reactive.users (id, name, lastname, description) VALUES (:id, :name, :lastname, :description)")
                .bind("id", newUser.getId())
                .bind("name", newUser.getName())
                .bind("lastname",newUser.getLastname())
                .bind("description", newUser.getDescription())
                .fetch()
                .rowsUpdated()
                .then();
    }
}
