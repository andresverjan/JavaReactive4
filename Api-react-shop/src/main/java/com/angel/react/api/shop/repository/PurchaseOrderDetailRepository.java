package com.angel.react.api.shop.repository;

import com.angel.react.api.shop.model.PurchaseOrderDetailEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PurchaseOrderDetailRepository extends R2dbcRepository<PurchaseOrderDetailEntity, String> {
    Flux<PurchaseOrderDetailEntity> findByReference(String reference);
}
