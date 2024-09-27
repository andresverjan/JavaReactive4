package org.api.repository;

import org.api.model.ShoppingCart;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends ReactiveCrudRepository<ShoppingCart, Long> {

}
