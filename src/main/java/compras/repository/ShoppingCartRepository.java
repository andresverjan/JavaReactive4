package compras.repository;

import compras.model.OrdersEntity;
import compras.model.ShoppingCartEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ShoppingCartRepository extends ReactiveCrudRepository<ShoppingCartEntity, Integer> {
    // Buscar órdenes de venta por nombre de producto
    Flux<OrdersEntity> findByProduct(String product);

    // Buscar órdenes de venta por estado
    Flux<OrdersEntity> findByStatus(String status);

    // Buscar órdenes de venta por nombre de producto y estado
    Flux<OrdersEntity> findByProductAndStatus(String product, String status);

    // Obtener la orden de venta más reciente
    Mono<OrdersEntity> findTopByOrderByCreatedAtDesc();
}
