package compras.controller;

import compras.model.PurchaseOrdersEntity;
import compras.model.ShoppingCartEntity;
import compras.service.PurchaseOrdersService;
import compras.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/compras")
@AllArgsConstructor
public class PurchaseOrdersController {
    private final PurchaseOrdersService purchaseOrdersService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PurchaseOrdersEntity> createPurchaseOrder(@RequestBody PurchaseOrdersEntity purchaseOrder) {
        return purchaseOrdersService.createPurchaseOrder(purchaseOrder);
    }

    // Endpoint para editar una orden de compra existente
    @PutMapping("/edit/{orderId}")
    public Mono<ResponseEntity<PurchaseOrdersEntity>> updatePurchaseOrder(
            @PathVariable Integer orderId,
            @RequestBody PurchaseOrdersEntity purchaseOrder) {
        return purchaseOrdersService.updatePurchaseOrder(orderId, purchaseOrder)
                .map(updatedOrder -> new ResponseEntity<>(updatedOrder, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // Endpoint para cancelar una orden de compra existente
    @DeleteMapping("/cancel/{orderId}")
    public Mono<ResponseEntity<Void>> cancelPurchaseOrder(@PathVariable Integer orderId) {
        return purchaseOrdersService.cancelPurchaseOrder(orderId)
                .then(Mono.just(ResponseEntity.noContent().<Void>build())) // Construye la respuesta con el tipo Void explícito
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Endpoint para listar todas las órdenes de compra
    @GetMapping("/list")
    public Flux<PurchaseOrdersEntity> listAllPurchaseOrders() {
        return purchaseOrdersService.listAllPurchaseOrders();
    }

    // Endpoint para obtener una orden de compra específica por su ID
    @GetMapping("/get/{orderId}")
    public Mono<ResponseEntity<PurchaseOrdersEntity>> getPurchaseOrderById(@PathVariable Integer orderId) {
        return purchaseOrdersService.getPurchaseOrderById(orderId)
                .map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
