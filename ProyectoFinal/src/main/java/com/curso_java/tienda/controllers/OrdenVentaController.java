package com.curso_java.tienda.controllers;

import com.curso_java.tienda.dtos.OrdenVentaDTO;
import com.curso_java.tienda.dtos.OrdenVentaProductoDTO;
import com.curso_java.tienda.dtos.ResponseData;
import com.curso_java.tienda.requests.OrdenProductoRequest;
import com.curso_java.tienda.requests.OrdenVentaEditarRequest;
import com.curso_java.tienda.requests.OrdenVentaRequest;
import com.curso_java.tienda.services.OrdenVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Controlador REST para gestionar las órdenes de venta.
 */
@RestController
@RequestMapping("/ventas")
public class OrdenVentaController {

    @Autowired
    private OrdenVentaService ordenVentaService;

    /**
     * Crea una nueva orden de venta.
     *
     * @param ordenVenta Datos de la orden de venta a crear.
     * @return Mono con la respuesta que contiene el DTO de la orden de venta creada.
     */
    @PostMapping("/create")
    public Mono<ResponseData<OrdenVentaDTO>> createOrdenVenta(@RequestBody OrdenVentaRequest ordenVenta) {
        return ordenVentaService.createOrdenVenta(ordenVenta)
                .map(orden -> new ResponseData<>("Orden de venta creada", orden))
                .defaultIfEmpty(new ResponseData<>("Usuario no encontrado o sin perimisos", null));
    }

    /**
     * Edita una orden de venta existente.
     *
     * @param id         ID de la orden de venta a editar.
     * @param ordenVenta Datos de la orden de venta a editar.
     * @return Mono con la respuesta que contiene el DTO de la orden de venta editada.
     */
    @PutMapping("/edit/{id}")
    public Mono<ResponseData<OrdenVentaDTO>> editaOrdenVenta(@PathVariable String id, @RequestBody OrdenVentaEditarRequest ordenVenta) {
        return ordenVentaService.editaOrdenVenta(id, ordenVenta)
                .map(orden -> new ResponseData<>("Orden de venta editada", orden))
                .defaultIfEmpty(new ResponseData<>("Orden de venta no encontrada", null));
    }

    /**
     * Agrega productos a una orden de venta existente.
     *
     * @param id              ID de la orden de venta.
     * @param nuevosProductos Lista de productos a agregar.
     * @return Mono con la respuesta que contiene el DTO de la orden de venta actualizada.
     */
    @PostMapping("/{id}/add-products")
    public Mono<ResponseData<OrdenVentaDTO>> addProductosToOrdenVenta(@PathVariable String id, @RequestBody List<OrdenProductoRequest> nuevosProductos) {
        return ordenVentaService.addProductosToOrdenVenta(id, nuevosProductos)
                .map(orden -> new ResponseData<>("Productos agregados a la orden de venta", orden))
                .defaultIfEmpty(new ResponseData<>("Orden de venta no encontrada", null));
    }

    /**
     * Obtiene todas las órdenes de venta.
     *
     * @return Mono con la respuesta que contiene la lista de órdenes de venta.
     */
    @GetMapping("/get-all")
    public Mono<ResponseData<List<OrdenVentaDTO>>> getOrdenesVenta() {
        return ordenVentaService.getOrdenesVenta()
                .collectList()
                .map(orden -> new ResponseData<>("Lista de órdenes de ventas", orden));
    }

    /**
     * Obtiene los productos de una orden de venta específica.
     *
     * @param id ID de la orden de venta.
     * @return Mono con la respuesta que contiene la lista de productos en la orden de venta.
     */
    @GetMapping("/{id}/products")
    public Mono<ResponseData<List<OrdenVentaProductoDTO>>> getProductsByOrden(@PathVariable String id) {
        return ordenVentaService.getProductsByOrden(id)
                .collectList()
                .flatMap(productos -> {
                    if (productos.isEmpty()) {
                        return Mono.just(new ResponseData<>("No se encontraron productos para esta orden", null));
                    } else {
                        return Mono.just(new ResponseData<>("Lista de productos en la orden de venta", productos));
                    }
                });
    }

    /**
     * Cancela una orden de venta.
     *
     * @param id ID de la orden de venta a cancelar.
     * @return Mono con la respuesta que contiene el DTO de la orden de venta cancelada.
     */
    @PutMapping("/cancel/{id}")
    public Mono<ResponseData<OrdenVentaDTO>> cancelOrdenVenta(@PathVariable String id) {
        return ordenVentaService.cancelOrdenVenta(id)
                .map(orden -> new ResponseData<>("Orden de venta cancelada", orden))
                .defaultIfEmpty(new ResponseData<>("Orden de venta no encontrada", null));
    }
}