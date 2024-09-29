package com.curso_java.tienda.controllers;

import com.curso_java.tienda.dtos.CarritoProductoDTO;
import com.curso_java.tienda.dtos.ItemCarritoDTO;
import com.curso_java.tienda.dtos.ResponseData;
import com.curso_java.tienda.services.CarritoProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador REST para gestionar los productos en el carrito de compras.
 */
@RestController
@RequestMapping("/carrito-productos")
public class CarritoProductoController {

    @Autowired
    private CarritoProductoService carritoProductoService;

    /**
     * Agrega un producto al carrito.
     *
     * @param carritoId  ID del carrito.
     * @param productoId ID del producto.
     * @param cantidad   Cantidad del producto a agregar.
     * @return Mono con la respuesta que contiene el DTO del producto agregado.
     */
    @PostMapping("/add")
    public Mono<ResponseData<CarritoProductoDTO>> addProducto(@RequestParam String carritoId,
                                                              @RequestParam String productoId,
                                                              @RequestParam int cantidad) {
        return carritoProductoService.addProducto(carritoId, productoId, cantidad)
                .map(carritoProducto -> new ResponseData<>("Producto agregado al carrito", carritoProducto))
                .defaultIfEmpty(new ResponseData<>("Producto no encontrado", null));
    }

    /**
     * Elimina un producto del carrito.
     *
     * @param carritoId  ID del carrito.
     * @param productoId ID del producto.
     * @return Mono con la respuesta que contiene el DTO del producto eliminado.
     */
    @DeleteMapping("/delete")
    public Mono<ResponseData<CarritoProductoDTO>> deleteProducto(@RequestParam String carritoId,
                                                                 @RequestParam String productoId) {
        return carritoProductoService.deleteProducto(carritoId, productoId)
                .map(carritoProducto -> new ResponseData<>("Producto eliminado del carrito", carritoProducto))
                .defaultIfEmpty(new ResponseData<>("Producto no encontrado", null));
    }

    /**
     * Actualiza la cantidad de un producto en el carrito.
     *
     * @param carritoId    ID del carrito.
     * @param productoId   ID del producto.
     * @param nuevaCantidad Nueva cantidad del producto.
     * @return Mono con la respuesta que contiene el DTO del producto actualizado.
     */
    @PutMapping("/update")
    public Mono<ResponseData<CarritoProductoDTO>> updateCantidad(@RequestParam String carritoId,
                                                                 @RequestParam String productoId,
                                                                 @RequestParam int nuevaCantidad) {
        return carritoProductoService.updateCantidad(carritoId, productoId, nuevaCantidad)
                .map(carritoProducto -> new ResponseData<>("Producto actualizado", carritoProducto))
                .defaultIfEmpty(new ResponseData<>("Producto no encontrado", null));
    }

    /**
     * Obtiene todos los productos en el carrito.
     *
     * @param carritoId ID del carrito.
     * @return Mono con la respuesta que contiene la lista de items en el carrito.
     */
    @GetMapping("/get-all")
    public Mono<ResponseData<List<ItemCarritoDTO>>> getAllItems(@RequestParam String carritoId) {
        return carritoProductoService.getAllItems(carritoId)
                // Recolecta los items en una lista
                .collectList()
                // Envuelve la lista en un ResponseData con un mensaje de éxito
                .map(items -> new ResponseData<>("Contenido del carrito", items));
    }

    /**
     * Vacía el carrito de compras.
     *
     * @param carritoId ID del carrito.
     * @return Mono con la respuesta que contiene la lista de productos eliminados del carrito.
     */
    @DeleteMapping("/clear")
    public Mono<ResponseData<List<CarritoProductoDTO>>> clearCarrito(@RequestParam String carritoId) {
        return carritoProductoService.clearCarrito(carritoId)
                // Recolecta todos los productos en una lista
                .collectList()
                // Procesa la lista de productos eliminados
                .flatMap(carritoVaciado -> {
                    if (carritoVaciado.isEmpty()) {
                        // Si no se encontraron productos en el carrito
                        return Mono.just(new ResponseData<>("Carrito no encontrado", null));
                    } else {
                        // Si se vació el carrito correctamente
                        return Mono.just(new ResponseData<>("Carrito vaciado", carritoVaciado));
                    }
                });
    }

    /**
     * Calcula el total del carrito de compras.
     *
     * @param carritoId ID del carrito.
     * @return Mono con la respuesta que contiene el total del carrito.
     */
    @GetMapping("/total")
    public Mono<ResponseData<BigDecimal>> getTotal(@RequestParam String carritoId) {
        return carritoProductoService.getTotal(carritoId)
                // Mapea el total calculado a un ResponseData con un mensaje adecuado
                .map(total -> {
                    if(total.equals(BigDecimal.ZERO)) {
                        // Si el total es cero, el carrito está vacío
                        return new ResponseData<>("Carrito vacío", total);
                    } else {
                        // Si el total es mayor que cero, se ha calculado el total correctamente
                        return new ResponseData<>("Total calculado", total);
                    }
                });
    }
}