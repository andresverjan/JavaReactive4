package com.example.demo.controler;

import com.example.demo.model.OrdenCompra;
import com.example.demo.service.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ordenescompra")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    // Registrar una nueva orden de compra
    @PostMapping("/registrar")
    public Mono<String> registrarOrden() {
        return ordenCompraService.registrarOrden();
    }

    // Editar una orden de compra
    @PutMapping("/editar/{ordenId}")
    public Mono<OrdenCompra> editarOrden(@PathVariable Long ordenId, @RequestParam String estado) {
        return ordenCompraService.editarOrden(ordenId, estado);
    }

    // Cancelar una orden de compra
    @DeleteMapping("/cancelar/{ordenId}")
    public Mono<ResponseEntity<Object>> cancelarOrden(@PathVariable Long ordenId) {
        return ordenCompraService.cancelarOrden(ordenId)
                .then(Mono.just(ResponseEntity.noContent().build()))  // 204 No Content si la eliminación fue exitosa
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));  // 404 Not Found si no se encuentra la orden
    }

    // Listar todas las órdenes de compra
    @GetMapping("/listar")
    public Flux<OrdenCompra> listarOrdenes() {
        return ordenCompraService.listarOrdenes();
    }
}