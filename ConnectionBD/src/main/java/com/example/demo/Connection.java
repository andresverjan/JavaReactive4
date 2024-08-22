package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class Connection implements CommandLineRunner {

    private final DatabaseClient databaseClient;

    public Mono<Void> createTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS my_table (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL" +
                ")";
        return databaseClient.sql(createTableQuery)
                .then();
    }

    public Mono<Void> insertData(String name) {
        String insertQuery = "INSERT INTO my_table (name) VALUES ($1)";
        System.out.println("Insertar usuario: " + name);
        return databaseClient.sql(insertQuery)
                .bind(0, name)
                .then();
    }

    public Flux<String> selectAll() {
        String selectQuery = "SELECT name FROM my_table";
        System.out.println("Consulta: ");
        return databaseClient.sql(selectQuery)
                .map(row -> row.get("name", String.class))
                .all();
    }

    @Override
    public void run(String... args) throws Exception {

        createTable()
                .then(insertData("Dylan"))
                .then(insertData("Mateo"))
                .thenMany(selectAll())
                .doOnNext(name -> System.out.println("Nombre: " + name))
                .doOnError(error -> System.err.println("Error: " + error.getMessage()))
                .doOnComplete(() -> System.out.println("Exito!"))
                .subscribe();
    }


}
