package com.angel.react.api.shop.repository;

import com.angel.react.api.shop.model.SalesOrderEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Repository
public interface SalesOrderRepository extends R2dbcRepository<SalesOrderEntity, Long> {
    Mono<SalesOrderEntity> findByReference(String reference);
    Flux<SalesOrderEntity> findByDateBetween(Date dateInit, Date dateEnd);
    Mono<Void> deleteByReference(String reference);
}
