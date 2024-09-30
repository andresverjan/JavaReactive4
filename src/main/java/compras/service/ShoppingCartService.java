package compras.service;

import compras.model.CartItemEntity;
import compras.model.OrdersEntity;
import compras.model.ShoppingCartEntity;
import compras.repository.CartItemRepository;
import compras.repository.OrdersRepository;
import compras.repository.ProductRepository;
import compras.repository.ShoppingCartRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository orderRepository;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                               ProductRepository productRepository,
                               OrdersRepository orderRepository,
                               CartItemRepository cartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Mono<ShoppingCartEntity> addItemToCart(Integer cartId, Integer productId, Integer quantity) {
        return shoppingCartRepository.findById(cartId)
            .switchIfEmpty(Mono.error(new RuntimeException("Carrito no encontrado")))
            .flatMap(cart -> productRepository.findById(productId).flatMap(productEntity -> {
                        CartItemEntity newItem = new CartItemEntity(null, cart.getId(), productId, quantity,
                                productEntity.getPrice());
                        return cartItemRepository.save(newItem).thenReturn(cart);
                    })
                    .thenReturn(cart));
    }

    public Mono<ShoppingCartEntity> addCart(Integer productId) {
        ShoppingCartEntity newCart = new ShoppingCartEntity(null, LocalDateTime.now(), null, "Creado", productId);
        return shoppingCartRepository.save(newCart);
    }

    public Mono<ShoppingCartEntity> findById(Integer id) {
        return shoppingCartRepository.findById(id)
                .flatMap(cart -> cartItemRepository.findByCartId(cart.getId())
                        .collectList()
                        .map(items -> {
                            cart.setItems(items); // Asegúrate de tener un método setter en ShoppingCartEntity
                            return cart;
                        }));
    }

    // Aquí puedes incluir tu método removeItemFromCart
    public Mono<Void> removeItem(Integer cartId, Integer productId) {
        return cartItemRepository.findByCartProductId(cartId, productId)
                .switchIfEmpty(Mono.error(new RuntimeException("Item no encontrado")))
                .flatMap(cartItemRepository::delete);
    }

    // Aquí puedes incluir tu método removeItemFromCart
    public Mono<Void> removeItemFromCart(Integer cartId, Integer productId) {
        return findById(cartId)
                .switchIfEmpty(Mono.error(new RuntimeException("Carrito no encontrado")))
                .flatMap(cart -> {
                    System.out.println("Items antes de la eliminación: " + cart.getItems());
                    cart.removeItem(productId);
                    return shoppingCartRepository.delete(cart);
                });
    }

    public Mono<CartItemEntity> updateItemQuantity(CartItemEntity cartItemEntity) {
        return cartItemRepository.findById(cartItemEntity.getId())
                .switchIfEmpty(Mono.error(new RuntimeException("Carrito no encontrado")))
                .flatMap(cart -> {
                    cart.setQuantity(cartItemEntity.getQuantity());
                    return cartItemRepository.save(cart);
                });
    }

    public Flux<CartItemEntity> getCartContents(Integer cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    public Mono<ShoppingCartEntity> emptyCart(Integer cartId) {
        return shoppingCartRepository.findById(cartId)
                .switchIfEmpty(Mono.error(new RuntimeException("Carrito no encontrado")))
                .doOnNext(ShoppingCartEntity::emptyCart)
                .flatMap(shoppingCartRepository::save);
    }

    public Mono<Double> calculateTotal(Integer cartId) {
        return cartItemRepository.findByCartId(cartId)
                .flatMap(item -> productRepository.findById(item.getProductId())
                        .map(product -> {
                            System.out.println("precio *** " + product.getPrice());
                            item.setPrice(product.getPrice());
                            return item;
                        }))
                .collectList()
                .flatMap(items -> {
                    ShoppingCartEntity cart = new ShoppingCartEntity();
                    cart.setItems(items);
                    return Mono.just(cart.calculateTotal());
                });
    }

    public Mono<OrdersEntity> registerOrder(Integer cartId) {
        return shoppingCartRepository.findById(cartId)
                .switchIfEmpty(Mono.error(new RuntimeException("El carrito no existe")))
                .flatMap(cart -> validateStockAndUpdate(cart))
                .flatMap(cart -> saveOrder(cart));
    }

    private Mono<ShoppingCartEntity> validateStockAndUpdate(ShoppingCartEntity cart) {
        return Flux.fromIterable(cart.getItems())
                .flatMap(cartItem -> productRepository.findById(cartItem.getProductId())
                        .flatMap(product -> {
                            if (product.getStock() < cartItem.getQuantity()) {
                                return Mono.error(new RuntimeException("No hay suficiente stock para el producto: " + product.getName()));
                            }
                            // Actualizar el stock del producto
                            product.setStock(product.getStock() - cartItem.getQuantity());
                            System.out.println("Producto actualizado: " + product.getName() + " - Stock restante: " + product.getStock());
                            return productRepository.save(product);
                        }))
                .then(Mono.just(cart));
    }

    private Mono<OrdersEntity> saveOrder(ShoppingCartEntity cart) {
        return cartItemRepository.findAll().collectList().flatMap(cartItems -> {
            Integer totalAmount = cartItems.size();
            OrdersEntity newOrder = new OrdersEntity();
            newOrder.setCartId(cart.getId());
            System.out.println("Cantidad total de productos en la orden: " + totalAmount);
            newOrder.setTotalAmount(totalAmount);
            newOrder.setStatus("Creada");
            newOrder.setCreatedAt(LocalDateTime.now());
            return orderRepository.save(newOrder);
        });

    }
}
