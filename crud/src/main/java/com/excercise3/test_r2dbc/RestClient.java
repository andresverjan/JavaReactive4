/*package com.excercise3.test_r2dbc;

import com.excercise3.test_r2dbc.drivenAdapters.postgres.ConnectionDb;
import com.excercise3.test_r2dbc.entities.Clases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clases")
public class RestClient {
    private final ConnectionDb connectionDb;
    @Autowired
    public RestClient(ConnectionDb connectionDb) {
        this.connectionDb = connectionDb;
    }
    @GetMapping("/{id}")
    public Mono<Clases> getClass(@PathVariable Long id){
        System.out.
        return connectionDb.findById(id);
    }
    @GetMapping("/all")
    public Flux<Clases> getClasses(){
        return connectionDb.findAll();
    }
    @GetMapping("/")
    public String health(){
        String vari="hola mundo";
        return vari;
    }
}
*/