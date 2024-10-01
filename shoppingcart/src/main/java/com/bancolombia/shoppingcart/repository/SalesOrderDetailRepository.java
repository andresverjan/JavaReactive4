package com.bancolombia.shoppingcart.repository;

import com.bancolombia.shoppingcart.entity.CartDetail;
import com.bancolombia.shoppingcart.entity.SalesOrderDetail;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SalesOrderDetailRepository extends ReactiveCrudRepository<SalesOrderDetail, Long> {
    @Query("select id, orderid, productid, amount, salesPrice, amountdiscount, amounttax, createdat, updatedat from sales_order_detail where orderid=$1")
    Flux<SalesOrderDetail> findByOrderId(Long orderid);
    @Query("delete from sales_order_detail where orderid=$1")
    Mono<Void> deleteAllByOrderId(Long orderid);
}
