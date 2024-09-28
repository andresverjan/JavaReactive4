package com.store.shopping.drivenAdapters.products;

import com.store.shopping.drivenAdapters.products.data.ProductData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<ProductData, Integer> {
    @Query("SELECT * FROM products WHERE name=:name")
    Mono<ProductData> getProductByName(String name);
}
