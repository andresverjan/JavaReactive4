package valko.co.cartmanagament.repository.product;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import valko.co.cartmanagament.model.products.Product;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Flux<Product> findByNameContainingIgnoreCase(String name);
}
