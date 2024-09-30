package com.curso_java.tienda.controllers;

import com.curso_java.tienda.dtos.OrdenCompraDTO;
import com.curso_java.tienda.dtos.OrdenCompraProductoDTO;
import com.curso_java.tienda.dtos.ResponseData;
import com.curso_java.tienda.requests.OrdenCompraRequest;
import com.curso_java.tienda.requests.OrdenProductoRequest;
import com.curso_java.tienda.services.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Controlador REST para gestionar las órdenes de compra.
 */
@RestController
@RequestMapping("/ordenes-compras")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    /**
     * Crea una nueva orden de compra.
     *
     * @param ordenCompra La solicitud de orden de compra.
     * @return Un Mono que emite la respuesta con la orden de compra creada.
     */
    @PostMapping("/create")
    public Mono<ResponseData<OrdenCompraDTO>> createOrdenCompra(@RequestBody OrdenCompraRequest ordenCompra) {
        return ordenCompraService.createOrdenCompra(ordenCompra)
                .map(orden -> new ResponseData<>("Orden de compra creada", orden))
                .defaultIfEmpty(new ResponseData<>("Error al crear la orden de compra", null));
    }

    /**
     * Actualiza una orden de compra existente.
     *
     * @param id El ID de la orden de compra a actualizar.
     * @param productos La lista de productos a actualizar en la orden de compra.
     * @return Un Mono que emite la respuesta con la orden de compra actualizada.
     */
    @PutMapping("/update/{id}")
    public Mono<ResponseData<OrdenCompraDTO>> updateOrdenCompra(@PathVariable String id, @RequestBody List<OrdenProductoRequest> productos) {
        return ordenCompraService.updateOrdenCompra(id, productos)
                .map(orden -> new ResponseData<>("Orden de compra editada", orden))
                .defaultIfEmpty(new ResponseData<>("Orden de compra no encontrada", null));
    }

    /**
     * Obtiene todas las órdenes de compra.
     *
     * @return Un Flux que emite la lista de órdenes de compra.
     */
    @GetMapping("/get-all")
    public Flux<ResponseData<OrdenCompraDTO>> getOrdenesCompra() {
        return ordenCompraService.getOrdenesCompra()
                .map(orden -> new ResponseData<>("Lista de órdenes de compra", orden))
                .defaultIfEmpty(new ResponseData<>("Orden de compra no encontrada", null));
    }

    /**
     * Obtiene todos los productos de una orden de compra específica.
     *
     * @param id El ID de la orden de compra.
     * @return Un Mono que emite la lista de productos en la orden de compra.
     */
    @GetMapping("/get-all/{id}/products")
    public Mono<ResponseData<List<OrdenCompraProductoDTO>>> getAllProductosByOrdenCompra(@PathVariable String id) {
        return ordenCompraService.getAllProductosByOrdenCompra(id)
                .collectList()
                .map(producto -> new ResponseData<>("Lista de productos en la orden de compra", producto))
                .defaultIfEmpty(new ResponseData<>("Orden de compra no encontrada", null));
    }

}