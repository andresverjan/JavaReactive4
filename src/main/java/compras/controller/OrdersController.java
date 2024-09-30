package compras.controller;

import compras.model.OrdersEntity;
import compras.model.ProductEntity;
import compras.service.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@AllArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    // Endpoint para crear una nueva orden con múltiples productos
    @PostMapping("/create")
    public Mono<ResponseEntity<OrdersEntity>> createOrder(@RequestParam Integer cartId,
                                                          @RequestBody List<ProductEntity> products) {
        return ordersService.createOrder(cartId, products)
                .map(order -> ResponseEntity.status(HttpStatus.CREATED).body(order))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    // Endpoint para editar una orden existente
    @PutMapping("/edit/{orderId}")
    public Mono<ResponseEntity<OrdersEntity>> editOrder(@PathVariable Integer orderId,
                                                        @RequestBody List<ProductEntity> updatedProducts) {
        return ordersService.editOrder(orderId, updatedProducts)
                .map(order -> ResponseEntity.ok(order))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Endpoint para cancelar una orden
    @DeleteMapping("/cancel/{orderId}")
    public Mono<Void> cancelOrder(@PathVariable Integer orderId) {
        return ordersService.cancelOrder(orderId);
    }

    // Endpoint para listar todas las órdenes
    @GetMapping("/list")
    public Flux<OrdersEntity> listAllOrders() {
        return ordersService.listAllOrders();
    }

    // Endpoint para listar órdenes por nombre de producto
    @GetMapping("/list/{productName}")
    public Flux<OrdersEntity> listOrdersByProduct(@PathVariable String productName) {
        return ordersService.listOrdersByProduct(productName);
    }
}
