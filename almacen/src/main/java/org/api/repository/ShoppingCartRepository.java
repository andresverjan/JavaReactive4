package org.api.repository;

import org.api.model.ShoppingCart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ShoppingCartRepository extends ReactiveCrudRepository<ShoppingCart, Long> {

}
