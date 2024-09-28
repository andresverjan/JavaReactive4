package com.store.shopping.controllers;

import com.store.shopping.DTO.TotalCartDTO;
import com.store.shopping.entities.Product;
import com.store.shopping.entities.ProductsInCart;
import com.store.shopping.entities.ShoppingCart;
import com.store.shopping.useCase.ShoppingCartService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }
    @PostMapping
    public Mono<ShoppingCart> AddItemToCart(@RequestBody ShoppingCart shoppingCart) {
        return shoppingCartService.AddItemToCart(shoppingCart);
    }
    @GetMapping("/items/{buyer}")
    public Flux<ProductsInCart> getCartItems(@PathVariable String buyer) {
        return shoppingCartService.getShoppingCartItemsActiveByBuyer(buyer);
    }
    @GetMapping("/sell/{buyer}")
    public Flux<Tuple2<Product,ShoppingCart>> sellItemsBuyer(@PathVariable String buyer) {
        return shoppingCartService.sellCartItemsByBuyer(buyer);
    }
    @PutMapping("/{id}")
    public Mono<ShoppingCart> updateQuantity(@PathVariable Integer id,@RequestBody ShoppingCart shoppingCart) {

        return shoppingCartService.updateQuantity(id, shoppingCart);
    }
    @DeleteMapping("/{id}")
    public Mono<ShoppingCart> deleteItemToCart(@PathVariable Integer id) {
        return shoppingCartService.removeItemToCart(id);
    }
    @DeleteMapping("empty/{buyer}")
    public Flux<ShoppingCart> emptyCart(@PathVariable String buyer) {
        return shoppingCartService.emptyCart(buyer);
    }
    @GetMapping("/total/{buyer}")
    public Mono<TotalCartDTO> totalCartValue(@PathVariable String buyer) {
        return shoppingCartService.getCartPrice(buyer)
                .map(data->{
                    return TotalCartDTO.builder()
                            .products(data.getT1())
                            .total(data.getT2())
                            .build();
                });
    }
}
