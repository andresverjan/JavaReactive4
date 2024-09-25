package com.example.shopping_cart.controllers;

import com.example.shopping_cart.model.*;
import com.example.shopping_cart.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
//.doOnSuccess(savedItem -> System.out.println("Item saved successfully: " + savedItem))
    private final ShoppingCartService shoppingCartService;

    @PostMapping("/addItem/")
    public Mono<CartItem> createCartItem(@RequestBody CartItemRequest requestBody){
        return shoppingCartService.createCartItem(requestBody.getCartItem(),requestBody.getIdClient());
    }
    @DeleteMapping("/deleteItem/")
    public Mono<String> deleteCartItem(@RequestBody CartItemRequest requestBody){
        return  shoppingCartService.deleteCartItem(requestBody.getCartItem().getProductId(),requestBody.getIdClient());
    }

    @PutMapping("/updateQuantityItem/")
    public Mono<CartItem> updateQuantityItem(@RequestBody CartItemRequest requestBody){
        return shoppingCartService.updateQuantityItem(requestBody.getCartItem().getProductId(),requestBody.getCartItem().getQuantity(),requestBody.getIdClient());
    }
    @GetMapping("/allItems/{id}")
    public Flux<CartItem> allItemsByClient(@PathVariable Integer id){return shoppingCartService.listItemsFromShoppingCart(id);}

    @DeleteMapping("/clearShoppingCart/{id}")
    public Mono<ShoppingCart> clearShoppingCartByClient(@PathVariable Integer id){return shoppingCartService.clearShoppingCart(id);}

    //ordenes de venta
    @GetMapping("/preSaleOrder/")
    public Mono<SalesOrder> preSaleOrder(@RequestBody SalesOrder requestSaleOrder){return shoppingCartService.preSalesOrder(requestSaleOrder);}
    @PostMapping("/saleOrderConfirm/")
    public Mono<Object> registerSalesOrder(@RequestBody Integer idSaleOrder){return shoppingCartService.registerSalesOrder(idSaleOrder);}

    // listar ordenes de venta
    @GetMapping("/listSaleOrderByClient/")
    public Flux<SalesOrder> listSaleOrderByCLient(@RequestBody Integer clientId){return shoppingCartService.listSalesOrderByClientId(clientId);}
    //cancelar orden de venta
    @PutMapping("/cancelSaleOrder/")
    public Mono<SalesOrder> cancelSaleOrder(@RequestBody Integer saleOrderId){return shoppingCartService.updateSaleOrderCanceled(saleOrderId);}

    //top5 productos
    @GetMapping("/report")
    public Flux<TopProductReport> getTopProducts(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return shoppingCartService.generateTopProductReport(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
    }
}
