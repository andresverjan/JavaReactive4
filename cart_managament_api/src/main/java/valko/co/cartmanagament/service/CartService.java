package valko.co.cartmanagament.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import valko.co.cartmanagament.exception.NotFoundException;
import valko.co.cartmanagament.model.cart.Cart;
import valko.co.cartmanagament.model.cart.CartItem;
import valko.co.cartmanagament.repository.cart.CartItemRepository;
import valko.co.cartmanagament.repository.cart.CartRepository;
import valko.co.cartmanagament.repository.product.ProductRepository;
import valko.co.cartmanagament.repository.users.UserRepository;
import valko.co.cartmanagament.web.cart.dto.CartDTO;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private static final String USER_NOT_FOUND = "User not found with id: ";
    public static final String CART_NOT_FOUND = "Cart for user not  with id: ";
    private static final String PRODUCT_NOT_FOUND = "Product not found with id: ";

    public Mono<CartDTO> addProductToCart(CartDTO cartDto) {

        Mono<Cart> cartMono = userRepository
                .findById(cartDto.userId())
                .switchIfEmpty(Mono.error(new NotFoundException(USER_NOT_FOUND.concat(String.valueOf(cartDto.userId())))))
                .flatMap(user -> cartRepository.findByUserId(user.id())
                        .defaultIfEmpty(Cart.builder()
                                .userId(user.id())
                                .build())
                        .flatMap(cartRepository::save));

        return cartMono.flatMap(cartSaved -> Flux.fromIterable(cartDto.items())
                .flatMap(cartItemDto -> productRepository.findById(cartItemDto.product())
                        .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND.concat(String.valueOf(cartItemDto.product())))))
                        .flatMap(product -> cartItemRepository.findCartItemByCartIdAndProductId(cartSaved.id(), product.id())
                                .defaultIfEmpty(CartItem.builder()
                                        .cartId(cartSaved.id())
                                        .product(product.id())
                                        .amount(cartItemDto.amount())
                                        .build())
                                .map(cartItem -> cartItem.toBuilder().amount(cartItemDto.amount()).build())
                        )
                )
                .collectList()
                .flatMapMany(cartItemRepository::saveAll)
                .collectList()
                .map(savedCartItems -> cartDto.toBuilder().items(savedCartItems).build())
        );
    }

    public Mono<CartDTO> removeProductFromCart(Integer userId,
                                               Integer productId) {

        return cartRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new NotFoundException(CART_NOT_FOUND.concat(String.valueOf(userId)))))
                .flatMap(cart -> cartItemRepository.removeProductFromCart(productId, cart.id())
                        .then(cartItemRepository.findCartItemByCartId(cart.id())
                                .collectList()
                                .map(list -> CartDTO.builder()
                                        .userId(userId)
                                        .items(list)
                                        .build()))
                );
    }

    public Mono<CartDTO> getCartContent(Integer userId) {
        return cartRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new NotFoundException(CART_NOT_FOUND.concat(String.valueOf(userId)))))
                .flatMap(cart -> cartItemRepository.findCartItemByCartId(cart.id())
                        .collectList()
                        .map(list -> CartDTO.builder()
                                .userId(userId)
                                .items(list)
                                .build()));
    }

    public Mono<Void> emptyCart(Integer userId) {
        return cartRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new NotFoundException(CART_NOT_FOUND.concat(String.valueOf(userId)))))
                .flatMap(cart -> cartRepository.deleteByUserId(userId));
    }

    public Mono<Double> calculateTotal(Integer userId) {
        return cartRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new NotFoundException(CART_NOT_FOUND.concat(String.valueOf(userId)))))
                .flatMapMany(cart -> cartItemRepository.findCartItemByCartId(cart.id()))
                .flatMap(cartItem -> productRepository.findById(cartItem.product())
                        .map(product -> product.price() * cartItem.amount()))
                .reduce(0.0, Double::sum);
    }
}
