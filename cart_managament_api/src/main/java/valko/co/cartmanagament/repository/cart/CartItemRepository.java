package valko.co.cartmanagament.repository.cart;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import valko.co.cartmanagament.model.cart.CartItem;

@Repository
public interface CartItemRepository extends ReactiveCrudRepository<CartItem, Long> {
    @Query("""
            DELETE FROM cart_items 
            WHERE cart_id=:cartId AND product_id=:productId
            """)
    Mono<Void> removeProductFromCart(Integer productId, Integer cartId);

    @Query("""
            SELECT cti.id, cti.cart_id, cti.product_id, cti.amount
            FROM cart_items cti WHERE cti.cart_id =:cartId
                        """)
    Flux<CartItem> findCartItemByCartId(Integer cartId);

    @Query("""
            SELECT cti.id, cti.cart_id, cti.product_id, cti.amount
            FROM cart_items cti WHERE cti.cart_id =:cartId 
            AND product_id=:productId
            """)
    Mono<CartItem> findCartItemByCartIdAndProductId(Integer cartId, Integer productId);

}
