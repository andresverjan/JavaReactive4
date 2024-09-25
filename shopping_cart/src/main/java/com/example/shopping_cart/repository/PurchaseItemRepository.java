package com.example.shopping_cart.repository;

import com.example.shopping_cart.model.PurchaseItem;
import com.example.shopping_cart.model.TopProductReport;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PurchaseItemRepository extends ReactiveCrudRepository<PurchaseItem,Integer> {
    Flux<PurchaseItem> findAllByPurchaseOrderId(Integer purchaseId);

    Mono<PurchaseItem> findByProductIdAndPurchaseOrderId(Integer productId,Integer purchaseId);


}
