package compras.controller;

import compras.model.CartItemEntity;
import compras.model.OrdersEntity;
import compras.model.ShoppingCartEntity;
import compras.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/carrito")
@AllArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    //agregar productos al carrito
    @PostMapping("/{cartId}/{productId}/{quantity}/items")
    public Mono<ResponseEntity<ShoppingCartEntity>> addItemToCart(
            @PathVariable Integer cartId,@PathVariable Integer productId,
            @PathVariable Integer quantity) {
        return shoppingCartService.addItemToCart(cartId, productId, quantity)
                .map(cart -> ResponseEntity.ok(cart))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    //crear carrito
    @PostMapping("/{productId}")
    public Mono<ResponseEntity<ShoppingCartEntity>> addCart(@PathVariable Integer productId) {
        return shoppingCartService.addCart(productId)
                .map(cart -> ResponseEntity.ok(cart))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    //vaciar carrito
    @DeleteMapping("/{cartId}/items/{productId}")
    public Mono<Void> removeItemFromCart(
            @PathVariable Integer cartId,
            @PathVariable Integer productId) {
        return shoppingCartService.removeItemFromCart(cartId, productId);
    }
    //vaciar carrito
    @DeleteMapping("/{cartId}/{productId}/deleteitem")
    public Mono<Void> removeItem(
            @PathVariable Integer cartId,
            @PathVariable Integer productId) {
        return shoppingCartService.removeItem(cartId, productId);
    }
    //actualizar la cantidad del item del carrito
    @PutMapping("/items")
    public Mono<ResponseEntity<CartItemEntity>> updateItemQuantity(
            @RequestBody CartItemEntity cartItemId) {
        return shoppingCartService.updateItemQuantity(cartItemId)
                .map(cart -> ResponseEntity.ok(cart))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    //obetener todos los items
    @GetMapping("/{cartId}/items")
    public Mono<ResponseEntity<Flux<CartItemEntity>>> getCartContents(@PathVariable Integer cartId) {
        return shoppingCartService.getCartContents(cartId)
                .collectList()
                .flatMap(items -> {
                    if (items.isEmpty()) {
                        return Mono.just(ResponseEntity.notFound().build());
                    } else {
                        return Mono.just(ResponseEntity.ok(Flux.fromIterable(items)));
                    }
                });
    }

    //calcular el total
    @GetMapping("/{cartId}/total")
    public Mono<ResponseEntity<Double>> calculateTotal(@PathVariable Integer cartId) {
        return shoppingCartService.calculateTotal(cartId)
                .map(total -> ResponseEntity.ok(total))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/orders/{cartId}")
    public Mono<ResponseEntity<OrdersEntity>> registerOrder(@PathVariable Integer cartId) {
        return shoppingCartService.registerOrder(cartId)
                .map(cart -> ResponseEntity.ok(cart))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
