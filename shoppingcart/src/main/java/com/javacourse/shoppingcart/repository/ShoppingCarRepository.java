package com.javacourse.shoppingcart.repository;

import com.javacourse.shoppingcart.model.ShoppingCar;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCarRepository extends ReactiveCrudRepository<ShoppingCar,Long> {
}
