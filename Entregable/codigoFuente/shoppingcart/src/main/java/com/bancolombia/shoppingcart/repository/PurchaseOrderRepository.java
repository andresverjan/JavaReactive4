package com.bancolombia.shoppingcart.repository;

import com.bancolombia.shoppingcart.entity.PurchaseOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends ReactiveCrudRepository<PurchaseOrder, Long> {
}
