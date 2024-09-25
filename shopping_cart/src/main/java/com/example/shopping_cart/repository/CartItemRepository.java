package com.example.shopping_cart.repository;

import com.example.shopping_cart.model.CartItem;
import com.example.shopping_cart.model.TopProductReport;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface CartItemRepository extends ReactiveCrudRepository<CartItem, Integer> {
    Mono<CartItem> findByProductIdAndCartId(Integer idItem,Integer idCart);

    Flux<CartItem> findAllByCartId(Integer id);

    Mono<String> deleteAllByCartId(Integer id);
    @Query("SELECT ci.product_id, CAST(SUM(ci.quantity) AS INTEGER) AS totalQuantitySold " +
            "FROM cart_item ci " +
            "JOIN shopping_cart sc ON ci.cart_id = sc.id " +
            "JOIN sales_orders so ON so.shopping_cart_id = sc.id " +
            "WHERE so.status = 'COMPLETADA' " +
            "AND so.created_at BETWEEN :startDate AND :endDate " +
            "GROUP BY ci.product_id " +
            "ORDER BY totalQuantitySold DESC " +
            "LIMIT 5")
    Flux<TopProductReport> findTopProductsByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
