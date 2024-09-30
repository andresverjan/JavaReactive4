package org.example.actividadfinal.repository;

import org.example.actividadfinal.model.PurchaseOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends ReactiveCrudRepository<PurchaseOrder, Long> {
}
