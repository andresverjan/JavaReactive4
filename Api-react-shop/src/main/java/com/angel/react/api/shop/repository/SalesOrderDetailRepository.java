package com.angel.react.api.shop.repository;

import com.angel.react.api.shop.model.SalesOrderDetailEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface SalesOrderDetailRepository extends R2dbcRepository<SalesOrderDetailEntity, String> {
    Flux<SalesOrderDetailEntity> findByReference(String reference);
}
