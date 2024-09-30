package org.example.actividadfinal.repository;

import org.example.actividadfinal.model.Shop;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ShopRepository extends R2dbcRepository<Shop, Long> {


    Flux<Shop> findByIdShop(Long id);

    Mono<Void> deleteByIdShop(Long id);

 }
