package com.example.demo.controller;

import com.example.demo.models.Orders;
import com.example.demo.models.ProductOrder;
import com.example.demo.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    //creacion
    @PostMapping("/{cartId}")
    public Mono<Orders> createOrder(@PathVariable Long cartId) {
        return ordersService.saveOrder(cartId);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteOrder(@PathVariable Long id) {
        return ordersService.deleteOrder(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
    @GetMapping
    public Flux<Orders> getAllPersons() {
        return ordersService.getAllOrders();
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Orders>> updateOrder(@PathVariable Long id, @RequestBody Orders orders) {
        return ordersService.updateOrder(id, orders)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    @GetMapping("/{id}")
    public Flux<ProductOrder> getProductOrder(@PathVariable Long id) {
        return ordersService.getProductOrder(id);
    }


}
