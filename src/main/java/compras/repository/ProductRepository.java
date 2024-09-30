package compras.repository;

import compras.model.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Integer> {
    Flux<ProductEntity> findByNameContainingIgnoreCase(String name);
}
