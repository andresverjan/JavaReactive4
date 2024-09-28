package com.store.shopping.drivenAdapters.clients;

import com.store.shopping.drivenAdapters.clients.data.ClientData;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface ClientsRepository extends ReactiveCrudRepository<ClientData,String> {
    @Query("SELECT * FROM client WHERE id = :column1")
    Mono<ClientData> findByClientId(String id);
    @Modifying
    @Query("UPDATE client SET name=:name,lastname=:lastname,address=:address," +
            "telephone=:telephone,date_of_birth=:birth WHERE id = :id")
    Mono<Void> updateClient(String name,String lastname,String address,String telephone,LocalDate birth,String id);
}
