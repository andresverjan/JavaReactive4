package compras.repository;

import compras.model.CartItemEntity;
import compras.model.OrdersEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Repository
public interface CartItemRepository extends ReactiveCrudRepository<CartItemEntity, Integer> {
    Flux<CartItemEntity> findByCartId(Integer cartId);

    @Query(value = "SELECT * FROM carrito_item m WHERE m.id_carrito = :cartId AND m.id_producto = :productId")
    Mono<CartItemEntity> findByCartProductId(@Param("cartId")Integer cartId, @Param("productId") Integer productId);
}
