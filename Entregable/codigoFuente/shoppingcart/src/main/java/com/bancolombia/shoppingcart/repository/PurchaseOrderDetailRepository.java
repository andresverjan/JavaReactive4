package com.bancolombia.shoppingcart.repository;

import com.bancolombia.shoppingcart.entity.PurchaseOrderDetail;
import com.bancolombia.shoppingcart.entity.SalesOrderDetail;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PurchaseOrderDetailRepository extends ReactiveCrudRepository<PurchaseOrderDetail, Long> {
    @Query("select id, orderid, productid, amount, price, createdat, updatedat from purchase_order_detail where orderid=$1")
    Flux<PurchaseOrderDetail> findByOrderId(Long orderid);
    @Query("delete from purchase_order_detail where orderid=$1")
    Mono<Void> deleteAllByOrderId(Long orderid);
}
