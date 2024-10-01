package com.javacourse.shoppingcart.repository;

import com.javacourse.shoppingcart.model.ProductCar;
import com.javacourse.shoppingcart.model.ShoppingCar;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductCarRepository extends ReactiveCrudRepository<ProductCar,Long> {
    Flux<ProductCar> findAllByShoppingCar(ShoppingCar shoppingCar);

    Mono<Void> deleteAllByShoppingCar(ShoppingCar shoppingCar);
}
