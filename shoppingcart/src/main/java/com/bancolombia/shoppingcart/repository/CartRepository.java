package com.bancolombia.shoppingcart.repository;

import com.bancolombia.shoppingcart.entity.Cart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends ReactiveCrudRepository<Cart, Long> {

}
