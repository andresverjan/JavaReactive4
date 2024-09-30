package com.programacion.reactiva.actividad_final.controllers;

import com.programacion.reactiva.actividad_final.model.Order;
import com.programacion.reactiva.actividad_final.model.OrderProduct;
import com.programacion.reactiva.actividad_final.model.dto.RequestBodyDTO;
import com.programacion.reactiva.actividad_final.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PutMapping("/{orderId}")
    public Mono<Order> updateOrder(@PathVariable int orderId, @RequestBody RequestBodyDTO body) {
        return orderService.updateOrder(orderId, body.getProductId(), body.getQuantity());
    }

    @DeleteMapping("/{orderId}")
    public Mono<Void> cancelOrder(@PathVariable long orderId) {
        return orderService.cancelOrder(orderId);
    }

    @GetMapping
    public Flux<Order> listOrders() {
        return orderService.listOrders();
    }

    @GetMapping("/products/{orderId}")
    public Flux<OrderProduct> listOrderProducts(@PathVariable long orderId) {
        return orderService.listOrderProducts(orderId);
    }
}
