package com.programacion.reactiva.actividad_final.repository;

import com.programacion.reactiva.actividad_final.model.PurchaseOrderProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PurchaseOrderProductRepository extends ReactiveCrudRepository<PurchaseOrderProduct, Long> {
    public Flux<PurchaseOrderProduct> findAllByOrderId(long orderId);
    public Mono<Void> deleteAllByOrderId(long orderId);
}
