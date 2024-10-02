package com.curso.java.reactor.controllers;

import com.curso.java.reactor.models.CartProduct;
import com.curso.java.reactor.models.dto.PurchaseCartDTO;
import com.curso.java.reactor.models.dto.CartProductDTO;
import com.curso.java.reactor.models.dto.totalAmountDTO;
import com.curso.java.reactor.models.dto.SaleDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.curso.java.reactor.services.CartService;

@RestController
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public Mono<CartProductDTO> addProductsToCart(@RequestBody CartProduct cartProduct) {
        return cartService.addProductsToCart(cartProduct);
    }

    @PutMapping("/update/product")
    public Mono<CartProductDTO> updateProductQuantity(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        return cartService.updateProductQuantity(cartId, productId, quantity);
    }

    @GetMapping("/{id}")
    public Flux<PurchaseCartDTO> listProductsOfCart(@PathVariable Long id) {
        return cartService.getCart(id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCart(@PathVariable Long id) {
        return cartService.deleteCart(id);
    }

    @DeleteMapping("/product")
    public Mono<Void> deleteProductInCart(@RequestParam Long cartId, @RequestParam Long productId) {
        return cartService.deleteProductInCart(cartId, productId);
    }

    @GetMapping("/checkout")
    public Mono<totalAmountDTO> checkout(@RequestParam Long cartId, @RequestParam double shipment) {
        return cartService.getTotalOfCart(cartId, shipment);
    }

    @PostMapping("/sale")
    public Mono<SaleDTO> saveASale(@RequestParam Long cartId, @RequestParam double shipment) {
        return cartService.saveASale(cartId, shipment);
    }

}
