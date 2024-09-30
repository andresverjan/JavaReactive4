package compras.repository;

import compras.model.OrdersEntity;
import compras.model.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface OrdersRepository extends ReactiveCrudRepository<OrdersEntity, Integer> {
    Flux<OrdersEntity> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    Flux<OrdersEntity> findAllByProduct(String product);

}
