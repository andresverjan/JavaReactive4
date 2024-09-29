package com.curso_java.tienda.services;

import com.curso_java.tienda.dtos.CarritoProductoDTO;
import com.curso_java.tienda.dtos.ItemCarritoDTO;
import com.curso_java.tienda.entities.CarritoProducto;
import com.curso_java.tienda.entities.Producto;
import com.curso_java.tienda.repositories.CarritoProductoRepository;
import com.curso_java.tienda.repositories.OrdenVentaRepository;
import com.curso_java.tienda.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Servicio para gestionar los productos en el carrito de compras.
 */
@Service
public class CarritoProductoService {

    @Autowired
    private CarritoProductoRepository carritoProductoRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private OrdenVentaRepository ordenVentaRepository;
    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    /**
     * Agrega un producto al carrito.
     *
     * @param carritoId  ID del carrito.
     * @param productoId ID del producto.
     * @param cantidad   Cantidad del producto a agregar.
     * @return Mono con el DTO del producto agregado.
     */
    public Mono<CarritoProductoDTO> addProducto(String carritoId, String productoId, int cantidad) {
        // Buscar el producto por su ID
        return productoRepository.findById(productoId)
                .flatMap(productoEncontrado ->
                        // Buscar si el producto ya está en el carrito
                        carritoProductoRepository.findByCarritoIdAndProductoId(carritoId, productoId)
                                .flatMap(carritoProducto -> {

                                    // Si ya existe el producto en el carrito, actualizamos la cantidad
                                    carritoProducto.setCantidad(carritoProducto.getCantidad() + cantidad);
                                    carritoProducto.setUpdatedAt(LocalDateTime.now());

                                    // Guardar el producto actualizado en el carrito
                                    return carritoProductoRepository.save(carritoProducto).then(Mono.just(toCarritoProductoDTO(carritoProducto, productoEncontrado)));
                                })
                                .switchIfEmpty(Mono.defer(() -> {

                                    // Si el producto no está en el carrito, lo agregamos como nuevo
                                    CarritoProducto nuevoProducto = new CarritoProducto(carritoId, productoId, cantidad, LocalDateTime.now(), LocalDateTime.now());

                                    // Insertar el nuevo producto en el carrito
                                    return r2dbcEntityTemplate.insert(CarritoProducto.class).using(nuevoProducto).then(Mono.just(toCarritoProductoDTO(nuevoProducto, productoEncontrado)));
                                }))
                );
    }

    /**
     * Elimina un producto del carrito.
     *
     * @param carritoId  ID del carrito.
     * @param productoId ID del producto.
     * @return Mono con el DTO del producto eliminado.
     */
    public Mono<CarritoProductoDTO> deleteProducto(String carritoId, String productoId) {
        // Buscar el producto en el carrito por carritoId y productoId
        return carritoProductoRepository.findByCarritoIdAndProductoId(carritoId, productoId)
                .flatMap(carritoProducto ->
                        // Buscar los detalles del producto por productoId
                        productoRepository.findById(productoId).flatMap(producto -> {

                            // Convertir las entidades a DTO
                            CarritoProductoDTO carritoProductoDTO = toCarritoProductoDTO(carritoProducto, producto);

                            // Eliminar el producto del carrito y devolver el DTO
                            return carritoProductoRepository.delete(carritoProducto).then(Mono.just(carritoProductoDTO));
                        })
                )
                // Si no se encuentra el producto en el carrito, devolver un Mono vacío
                .switchIfEmpty(Mono.empty());
    }

    /**
     * Actualiza la cantidad de un producto en el carrito.
     *
     * @param carritoId    ID del carrito.
     * @param productoId   ID del producto.
     * @param nuevaCantidad Nueva cantidad del producto.
     * @return Mono con el DTO del producto actualizado.
     */
    public Mono<CarritoProductoDTO> updateCantidad(String carritoId, String productoId, int nuevaCantidad) {
        // Buscar el producto en el carrito por carritoId y productoId
        return carritoProductoRepository.findByCarritoIdAndProductoId(carritoId, productoId)
                .flatMap(carritoProducto ->
                        // Buscar los detalles del producto por productoId
                        productoRepository.findById(productoId).flatMap(producto -> {

                            // Calcular la nueva cantidad del producto en el carrito
                            int cantidadActual = (nuevaCantidad > 0) ? carritoProducto.getCantidad() + nuevaCantidad : carritoProducto.getCantidad() - Math.abs(nuevaCantidad);

                            carritoProducto.setCantidad(cantidadActual);
                            carritoProducto.setUpdatedAt(LocalDateTime.now());

                            // Si la cantidad actual es menor o igual a 0, eliminar el producto del carrito
                            if (cantidadActual <= 0) {
                                return carritoProductoRepository.delete(carritoProducto).then(Mono.just(toCarritoProductoDTO(carritoProducto, producto)));
                            }

                            // Guardar el producto actualizado en el carrito
                            return carritoProductoRepository.save(carritoProducto).then(Mono.just(toCarritoProductoDTO(carritoProducto, producto)));
                        })
                )
                // Si no se encuentra el producto en el carrito, devolver un Mono vacío
                .switchIfEmpty(Mono.empty());
    }

    /**
     * Obtiene todos los productos en el carrito.
     *
     * @param carritoId ID del carrito.
     * @return Flux con la lista de items en el carrito.
     */
    public Flux<ItemCarritoDTO> getAllItems(String carritoId) {
        // Buscar todos los productos en el carrito por carritoId
        return carritoProductoRepository.findAllByCarritoId(carritoId)
                .flatMap(carritoProducto ->
                        // Buscar los detalles del producto por productoId
                        productoRepository.findById(carritoProducto.getProductoId())
                                // Mapear los detalles del producto y la cantidad en el carrito a un DTO
                                .map(producto -> new ItemCarritoDTO(producto.getNombre(), producto.getPrecio(), producto.getDescripcion(), carritoProducto.getCantidad()))
                )
                // Si no se encuentran productos en el carrito, devolver un Flux vacío
                .switchIfEmpty(Flux.empty());
    }

    /**
     * Vacía el carrito de compras.
     *
     * @param carritoId ID del carrito.
     * @return Flux con la lista de productos eliminados del carrito.
     */
    public Flux<CarritoProductoDTO> clearCarrito(String carritoId) {
        // Buscar todos los productos en el carrito por carritoId
        return carritoProductoRepository.findAllByCarritoId(carritoId)
                .flatMap(productoEncontrado ->
                        // Buscar los detalles del producto por productoId
                        productoRepository.findById(productoEncontrado.getProductoId())
                                .flatMap(producto -> {
                                    // Eliminar el producto del carrito y devolver el DTO
                                    return carritoProductoRepository.delete(productoEncontrado)
                                            .then(Mono.just(toCarritoProductoDTO(productoEncontrado, producto))); // Retorna el DTO después de eliminar el producto
                                })
                )
                // Si no se encuentran productos en el carrito, devolver un Flux vacío
                .switchIfEmpty(Flux.empty());
    }

    /**
     * Calcula el total del carrito de compras.
     *
     * @param carritoId ID del carrito.
     * @return Mono con el total del carrito.
     */
    public Mono<BigDecimal> getTotal(String carritoId) {
        // Buscar todos los productos en el carrito por carritoId
        return carritoProductoRepository.findAllByCarritoId(carritoId)
                .flatMap(carritoProducto -> {

                    // Obtener el ID del producto y la cantidad en el carrito
                    String productoId = carritoProducto.getProductoId();
                    int cantidad = carritoProducto.getCantidad();

                    // Buscar los detalles del producto por productoId y calcular el precio total para la cantidad en el carrito
                    return productoRepository.findById(productoId).map(producto -> producto.getPrecio().multiply(BigDecimal.valueOf(cantidad)));
                })
                // Sumar los precios totales de todos los productos en el carrito
                .reduce(BigDecimal.ZERO, (acumulador, valorActual) -> acumulador.add(valorActual))
                // Si no se encuentran productos en el carrito, devolver un Mono con valor BigDecimal.ZERO
                .switchIfEmpty(Mono.just(BigDecimal.ZERO));
    }

    /**
     * Convierte un CarritoProducto y Producto a un CarritoProductoDTO.
     *
     * @param carritoProducto Entidad CarritoProducto.
     * @param producto        Entidad Producto.
     * @return DTO de CarritoProducto.
     */
    public CarritoProductoDTO toCarritoProductoDTO(CarritoProducto carritoProducto, Producto producto) {
        // Crear un ItemCarritoDTO con los detalles del producto y la cantidad en el carrito
        ItemCarritoDTO itemCarritoDTO = new ItemCarritoDTO(producto.getNombre(), producto.getPrecio(), producto.getDescripcion(), carritoProducto.getCantidad());

        // Crear y devolver un CarritoProductoDTO con el ID del carritoProducto, el ID del carrito y el ItemCarritoDTO
        return new CarritoProductoDTO(carritoProducto.getId(), carritoProducto.getCarritoId(), itemCarritoDTO);
    }
}