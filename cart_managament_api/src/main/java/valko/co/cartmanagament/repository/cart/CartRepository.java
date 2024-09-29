package valko.co.cartmanagament.repository.cart;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import valko.co.cartmanagament.model.cart.Cart;

@Repository
public interface CartRepository extends ReactiveCrudRepository<Cart, Integer> {

    Mono<Cart> findByUserId(Integer userId);

    Mono<Void> deleteByUserId(Integer userId);
}
