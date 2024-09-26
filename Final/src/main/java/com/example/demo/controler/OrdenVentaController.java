package com.example.demo.controler;

import com.example.demo.model.OrdenVenta;
import com.example.demo.service.OrdenCompraService;
import com.example.demo.service.OrdenVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ordenesventa")
public class OrdenVentaController {

    @Autowired
    private OrdenVentaService ordenVentaService;

    @PostMapping("/crear/{userId}")
    public Mono<ResponseEntity<String>> crearOrden(@PathVariable Long userId) {
        return ordenVentaService.registrarOrden(userId)
                .map(message -> ResponseEntity.ok(message))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(e.getMessage())));
    }

    @PutMapping("/editar/{ordenId}")
    public Mono<OrdenVenta> editarOrden(@PathVariable Long ordenId, @RequestParam String estado) {
        return ordenVentaService.editarOrden(ordenId, estado);
    }

    @DeleteMapping("/cancelar/usuario/{userId}")
    public Mono<ResponseEntity<Object>> cancelarOrdenPorUsuario(@PathVariable Long userId) {
        return ordenVentaService.cancelarOrdenPorUsuario(userId)
                .then(Mono.just(ResponseEntity.noContent().build()))  // Devuelve un 204 No Content si la eliminación fue exitosa
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));  // Si no se encuentran órdenes, devuelve 404
    }

    // Endpoint para listar todas las órdenes de un usuario
    @GetMapping("/listar/{userId}")
    public Flux<OrdenVenta> listarOrdenes(@PathVariable Long userId) {
        return ordenVentaService.listarOrdenes(userId);
    }

}