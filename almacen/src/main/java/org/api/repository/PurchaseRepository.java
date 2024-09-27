package org.api.repository;


import org.api.model.PurchaseOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PurchaseRepository extends ReactiveCrudRepository<PurchaseOrder, Long> {
    Flux<PurchaseOrder> findAllByEstado(String estado);
}
