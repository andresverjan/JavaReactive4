package com.store.shopping.drivenAdapters.shoppingCart;

import com.store.shopping.DTO.ShoppingCartDTO;
import com.store.shopping.drivenAdapters.shoppingCart.data.ShoppingCartData;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ShoppingCartRepository extends ReactiveCrudRepository<ShoppingCartData, Integer> {
    @Modifying
    @Query("UPDATE shoppingCart SET status='R' where id=:id and buyer=:buyer and product=:product")
    Mono<Void> removeProduct(Integer id,String buyer,Integer product);
    @Query("SELECT * FROM shoppingCart WHERE buyer=:buyer AND status LIKE 'A'")
    Flux<ShoppingCartData> getActiveItemsByBuyer(String buyer);
    @Query("SELECT s.id as id,s.product as product,p.name AS name,s.quantity AS quantity, p.price AS price " +
            "FROM products AS p JOIN shoppingCart as s ON p.id=s.product " +
            "where buyer=:buyer and status like 'A';")
    Flux<ShoppingCartDTO> getItemsToBuyByBuyer(String buyer);
}
