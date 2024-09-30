package com.curso_java.tienda.controllers;

import com.curso_java.tienda.dtos.ProductoDTO;
import com.curso_java.tienda.dtos.ResponseData;
import com.curso_java.tienda.entities.Producto;
import com.curso_java.tienda.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controlador REST para gestionar productos.
 */
@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /**
     * Crea un nuevo producto.
     *
     * @param producto El producto a crear.
     * @return Un Mono que emite la respuesta con el producto creado.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseData<ProductoDTO>> createProducto(@RequestBody Producto producto) {
        return productoService.saveProducto(producto)
                .map(productoGuardado -> new ResponseData<>("Producto creado exitosamente", productoGuardado));
    }

    /**
     * Obtiene todos los productos.
     *
     * @return Un Flux que emite la respuesta con la lista de todos los productos.
     */
    @GetMapping
    public Flux<ResponseData<ProductoDTO>> getAllProductos() {
        return productoService.getAllProductos()
                .map(producto -> new ResponseData<>("Producto encontrado", producto));
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id El ID del producto.
     * @return Un Mono que emite la respuesta con el producto correspondiente al ID proporcionado o un mensaje de error si no se encuentra.
     */
    @GetMapping("/{id}")
    public Mono<ResponseData<ProductoDTO>> getProductoById(@PathVariable String id) {
        return productoService.getProductoById(id)
                .map(producto -> new ResponseData<>("Producto encontrado", producto))
                .defaultIfEmpty(new ResponseData<>("Producto no encontrado", null));
    }

    /**
     * Actualiza un producto por su ID.
     *
     * @param id El ID del producto a actualizar.
     * @param producto Los datos del producto a actualizar.
     * @return Un Mono que emite la respuesta con el producto actualizado o un mensaje de error si no se encuentra.
     */
    @PutMapping("/{id}")
    public Mono<ResponseData<ProductoDTO>> updateProducto(@PathVariable String id, @RequestBody Producto producto) {
        return productoService.updateProducto(id, producto)
                .map(productoActualizado -> new ResponseData<>("Producto actualizado exitosamente", productoActualizado))
                .defaultIfEmpty(new ResponseData<>("Producto no encontrado", null));
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id El ID del producto a eliminar.
     * @return Un Mono que emite la respuesta con el producto eliminado o un mensaje de error si no se encuentra.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseData<ProductoDTO>> deleteProducto(@PathVariable String id) {
        return productoService.deleteProducto(id)
                .map(producto -> new ResponseData<>("Producto eliminado exitosamente", producto))
                .defaultIfEmpty(new ResponseData<>("Producto no encontrado", null));
    }
}