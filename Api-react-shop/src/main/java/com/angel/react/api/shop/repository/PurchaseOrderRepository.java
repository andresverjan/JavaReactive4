package com.angel.react.api.shop.repository;

import com.angel.react.api.shop.model.PurchaseOrderEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Repository
public interface PurchaseOrderRepository extends R2dbcRepository<PurchaseOrderEntity, Long> {
    Mono<PurchaseOrderEntity> findByReference(String reference);
    Flux<PurchaseOrderEntity> findByDateBetween(Date dateInit, Date dateEnd);
}
