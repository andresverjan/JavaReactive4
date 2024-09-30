package com.programacion.reactiva.actividad_final.controllers;
import com.programacion.reactiva.actividad_final.model.Order;
import com.programacion.reactiva.actividad_final.model.dto.RequestBodyDTO;
import com.programacion.reactiva.actividad_final.model.dto.CartTotalDTO;
import com.programacion.reactiva.actividad_final.model.dto.ShoppingCartDTO;
import com.programacion.reactiva.actividad_final.service.CartService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public Mono<ShoppingCartDTO> addItemToCart(@RequestBody RequestBodyDTO body) {
        return cartService.addItemToCart(body.getUserId(), body.getProductId(), body.getQuantity());
    }

    @DeleteMapping("/remove")
    public Mono<ShoppingCartDTO> removeItemFromCart(@RequestParam int cartId, @RequestParam int productId) {
        return cartService.removeItemFromCart(cartId, productId);
    }

    @PutMapping("/update")
    public Mono<ShoppingCartDTO> updateItemQuantity(@RequestBody RequestBodyDTO body) {
        return cartService.updateItemQuantity(body.getCartId(), body.getProductId(), body.getQuantity());
    }

    @GetMapping("/{id}")
    public Mono<ShoppingCartDTO> getCartContents(@PathVariable int id) {
        return cartService.getCartContents(id);
    }

    @DeleteMapping("/empty/{id}")
    public Mono<Void> emptyCart(@PathVariable int id) {
        return cartService.emptyCart(id);
    }

    @GetMapping("/total/{id}")
    public Mono<CartTotalDTO> calculateCartTotal(@PathVariable long id) {
        return cartService.calculateCartTotal(id);
    }

    @GetMapping("/sell/{id}")
    public Mono<Order> createOrder(@PathVariable int id) {
        return cartService.createOrder(id);
    }
}

