package com.bancolombia.shoppingcart.repository;

import com.bancolombia.shoppingcart.entity.CartDetail;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CartDetailRepository extends ReactiveCrudRepository<CartDetail, Long> {
    @Query("select id, cartid, productid, amount, price, amountdiscount, amounttax, shippingcost, createdat, updatedat from cart_detail where cartid=$1")
    Flux<CartDetail> findByCartId(Long cartid);
    @Query("delete from cart_detail where cartid=$1")
    Mono<Void> deleteAllByCartId(Long cartid);
}
