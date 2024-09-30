package com.programacion.reactiva.actividad_final.repository;

import com.programacion.reactiva.actividad_final.model.PurchaseOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface PurchaseOrderRepository extends ReactiveCrudRepository<PurchaseOrder, Long> {
    Flux<PurchaseOrder> findAllByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
