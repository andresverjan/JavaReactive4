package com.curso.java.reactor.repository;

import com.curso.java.reactor.models.PurchaseOrderProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PurchaseOrderProductRepository extends ReactiveCrudRepository<PurchaseOrderProduct, Long> {
    Flux<PurchaseOrderProduct> findByPurchaseOrderId(Long id);

}
