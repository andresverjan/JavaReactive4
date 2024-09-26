package com.example.demo.service;

import com.example.demo.model.Carrito;
import com.example.demo.model.OrdenProducto;
import com.example.demo.model.OrdenVenta;
import com.example.demo.repository.CarritoRepository;
import com.example.demo.repository.OrdenProductoRepository;
import com.example.demo.repository.OrdenVentaRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenVentaService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private OrdenVentaRepository ordenVentaRepository;

    @Autowired
    private OrdenProductoRepository ordenProductoRepository;

    // Servicio para listar todas las órdenes de un usuario
    public Flux<OrdenVenta> listarOrdenes(Long userId) {
        return ordenVentaRepository.findAllByUserId(userId);  // Busca todas las órdenes por userId
    }

    public Mono<String> registrarOrden(Long userId) {
        return carritoRepository.findByUserId(userId)
                .collectList()
                .flatMap(carritoItems -> {
                    if (carritoItems.isEmpty()) {
                        return Mono.error(new IllegalArgumentException("El carrito está vacío"));
                    }

                    // Calcular el total de la orden
                    double total = carritoItems.stream()
                            .mapToDouble(item -> item.getPrice() * item.getQuantity())
                            .sum();

                    // Crear una lista de productoIds, cantidades y precios desde los items del carrito
                    List<Long> productoIds = carritoItems.stream()
                            .map(Carrito::getProductoId)
                            .collect(Collectors.toList());

                    List<Integer> cantidades = carritoItems.stream()
                            .map(Carrito::getQuantity)
                            .collect(Collectors.toList());

                    List<Double> precios = carritoItems.stream()
                            .map(Carrito::getPrice)
                            .collect(Collectors.toList());

                    // Crear la orden de venta con la lista de productoIds y cantidades
                    OrdenVenta nuevaOrden = new OrdenVenta(userId, LocalDateTime.now(), "Creada", total, productoIds, cantidades);

                    return ordenVentaRepository.save(nuevaOrden)
                            .flatMap(ordenGuardada -> {
                                System.out.println("Orden creada con ID: " + ordenGuardada.getId());

                                // Actualizar el stock de productos en función de las cantidades compradas
                                return Flux.fromIterable(carritoItems)
                                        .flatMap(item -> productoRepository.findById(item.getProductoId())
                                                .flatMap(producto -> {
                                                    if (producto.getStock() < item.getQuantity()) {
                                                        return Mono.error(new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getName()));
                                                    }
                                                    // Restar la cantidad comprada del stock
                                                    producto.setStock(producto.getStock() - item.getQuantity());
                                                    return productoRepository.save(producto);
                                                }))
                                        .then(Mono.defer(() -> {
                                            // Registrar los productos en la tabla `orden_producto` como listas
                                            OrdenProducto ordenProducto = new OrdenProducto(
                                                    ordenGuardada.getId(),
                                                    productoIds,  // Lista de producto_ids
                                                    cantidades,   // Lista de cantidades
                                                    precios       // Lista de precios
                                            );
                                            return ordenProductoRepository.save(ordenProducto);
                                        }))
                                        .then(carritoRepository.deleteByUserId(userId))  // Limpiar el carrito después de registrar la orden
                                        .then(Mono.just("Orden creada con éxito, ID de la orden: " + ordenGuardada.getId()));
                            });
                });
    }

    // Servicio para editar una orden cambiando su estado
    public Mono<OrdenVenta> editarOrden(Long ordenId, String nuevoEstado) {
        return ordenVentaRepository.findById(ordenId)
                .flatMap(orden -> {
                    orden.setEstado(nuevoEstado);
                    return ordenVentaRepository.save(orden);
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("La orden no existe")));
    }

    // Servicio para cancelar una orden
    public Mono<Void> cancelarOrdenPorUsuario(Long userId) {
        return ordenVentaRepository.findAllByUserId(userId)
                .collectList()  // Recoger todas las órdenes en una lista
                .flatMap(ordenes -> {
                    if (ordenes.isEmpty()) {
                        // Si no hay órdenes, lanzamos un error claro
                        return Mono.error(new IllegalArgumentException("No se encontraron órdenes para este usuario"));
                    }
                    // Eliminar todas las órdenes del usuario
                    return ordenVentaRepository.deleteAll(ordenes)
                            .doOnSuccess(aVoid -> System.out.println("Órdenes del usuario " + userId + " eliminadas exitosamente"))
                            .doOnError(e -> System.err.println("Error al eliminar órdenes: " + e.getMessage()));
                });
    }
}