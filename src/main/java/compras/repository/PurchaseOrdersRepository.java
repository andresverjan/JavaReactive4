package compras.repository;

import compras.model.OrdersEntity;
import compras.model.PurchaseOrdersEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface PurchaseOrdersRepository extends ReactiveCrudRepository<PurchaseOrdersEntity, Integer> {
    Flux<PurchaseOrdersEntity> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}
