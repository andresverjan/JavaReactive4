package com.bancolombia.shoppingcart.controller;

import com.bancolombia.shoppingcart.dto.CartDTO;
import com.bancolombia.shoppingcart.dto.CartDetailAmountDTO;
import com.bancolombia.shoppingcart.dto.CartTotalPurchaseDTO;
import com.bancolombia.shoppingcart.dto.SalesOrderDTO;
import com.bancolombia.shoppingcart.entity.Cart;
import com.bancolombia.shoppingcart.entity.CartDetail;
import com.bancolombia.shoppingcart.service.CartService;
import com.bancolombia.shoppingcart.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v1/carts")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private SalesOrderService salesOrderService;

    @GetMapping()
    public Flux<CartDTO> getAllCarts(){
        return  cartService.findAll();
    }
    @GetMapping(value = "{id}")
    public Mono<CartDTO> getCartById(@PathVariable Long id){
        return cartService.findById(id);
    }

    @PostMapping
    public Mono<CartDTO> create(@RequestBody Cart cart){
        return cartService.createCart(cart);
    }

    @PutMapping
    public Mono<CartDTO> update(@RequestBody Cart cart){
        return cartService.updateCart(cart);
    }

    @DeleteMapping(value = "{id}")
    public Mono<Void> delete(@PathVariable Long id){
        return cartService.deleteCart(id);
    }

    @DeleteMapping(value = "{id}/empty")
    public Mono<Void> emptyCart(@PathVariable Long id){
        return cartService.deleteAllCartDetail(id);
    }

    @PostMapping("/cartdetail")
    public Mono<Void> createCartDetailForCart(@RequestBody CartDetail cartDetail){
        return cartService.createCartDetailForCart(cartDetail);
    }

    @DeleteMapping("/cartdetail/{id}")
    public Mono<Void> deleteCartDetail(@PathVariable Long id){
        return cartService.deleteCartDetail(id);
    }

    @PutMapping("/cartdetail")
    public Mono<CartDTO> updateCartDetailAmount(@RequestBody CartDetailAmountDTO cartDetailAmountDTO){
        return cartService.updateCartDetailAmount(cartDetailAmountDTO);
    }

    @GetMapping("{id}/totalCart")
    public Mono<CartTotalPurchaseDTO> getTotalCartById(@PathVariable Long id){
        return cartService.calcTotalCart(id);
    }

    @PostMapping("{id}/salesOrder")
    public Mono<?> createCartDetailForCart(@PathVariable Long id){
        return cartService.registerSalesOrderOfCart(id);
    }
}
