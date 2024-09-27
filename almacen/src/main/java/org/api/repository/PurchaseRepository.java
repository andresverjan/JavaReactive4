package org.api.repository;


import org.api.model.PurchaseDto;
import org.api.model.PurchaseOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface PurchaseRepository extends ReactiveCrudRepository<PurchaseOrder, Long> {
    Flux<PurchaseOrder> findAllByEstado(String estado);
    Flux<PurchaseOrder> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);


}
