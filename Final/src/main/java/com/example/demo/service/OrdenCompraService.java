package com.example.demo.service;

import com.example.demo.model.Carrito;
import com.example.demo.model.OrdenCompra;
import com.example.demo.model.OrdenProducto;
import com.example.demo.repository.CarritoRepository;
import com.example.demo.repository.OrdenCompraRepository;
import com.example.demo.repository.OrdenProductoRepository;
import com.example.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdenCompraService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private OrdenProductoRepository ordenProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    // Crear una nueva orden de compra
    public Mono<String> registrarOrden() {
        return carritoRepository.findAll()
                .collectList()
                .flatMap(carritoItems -> {
                    if (carritoItems.isEmpty()) {
                        return Mono.error(new IllegalArgumentException("El carrito está vacío"));
                    }

                    // Calcular el total de la orden
                    double total = carritoItems.stream()
                            .mapToDouble(item -> item.getPrice() * item.getQuantity())
                            .sum();

                    // Crear la nueva orden
                    OrdenCompra orden = new OrdenCompra();
                    orden.setFechaOrden(LocalDateTime.now());
                    orden.setEstado("Comprada");
                    orden.setTotal(total);

                    // Guardar la orden en la base de datos
                    return ordenCompraRepository.save(orden)
                            .flatMap(savedOrden -> {
                                // Crear listas para los productos, cantidades y precios
                                List<Long> productoIds = carritoItems.stream()
                                        .map(Carrito::getProductoId)
                                        .collect(Collectors.toList());

                                List<Integer> cantidades = carritoItems.stream()
                                        .map(Carrito::getQuantity)
                                        .collect(Collectors.toList());

                                List<Double> precios = carritoItems.stream()
                                        .map(Carrito::getPrice)
                                        .collect(Collectors.toList());

                                // Crear un nuevo OrdenProducto con las listas
                                OrdenProducto ordenProducto = new OrdenProducto();
                                ordenProducto.setOrdenId(savedOrden.getId());
                                ordenProducto.setProductoIds(productoIds);
                                ordenProducto.setCantidades(cantidades);
                                ordenProducto.setPrecios(precios);

                                // Guardar los productos de la orden y actualizar el stock (incrementar el stock en lugar de reducirlo)
                                return Flux.fromIterable(carritoItems)
                                        .flatMap(carritoItem -> {
                                            return productoRepository.findById(carritoItem.getProductoId())
                                                    .flatMap(producto -> {
                                                        // Incrementar el stock del producto
                                                        producto.setStock(producto.getStock() + carritoItem.getQuantity());
                                                        return productoRepository.save(producto);
                                                    });
                                        })
                                        .then(ordenProductoRepository.save(ordenProducto))  // Guardar el ordenProducto
                                        .then(Mono.just("Orden de compra registrada con éxito con ID: " + savedOrden.getId()));
                            });
                });
    }

    // Editar el estado de una orden de compra
    public Mono<OrdenCompra> editarOrden(Long ordenId, String nuevoEstado) {
        return ordenCompraRepository.findById(ordenId)
                .flatMap(orden -> {
                    orden.setEstado(nuevoEstado);
                    return ordenCompraRepository.save(orden);
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("La orden no existe")));
    }

    // Cancelar una orden de compra (eliminar la orden)
    public Mono<Void> cancelarOrden(Long ordenId) {
        return ordenCompraRepository.findById(ordenId)
                .flatMap(orden -> {
                    // Actualizar el stock para devolver los productos al inventario
                    return ordenProductoRepository.findByOrdenId(ordenId)
                            .flatMap(ordenProducto -> {
                                List<Long> productoIds = ordenProducto.getProductoIds();
                                List<Integer> cantidades = ordenProducto.getCantidades();
                                return Flux.range(0, productoIds.size())
                                        .flatMap(index -> {
                                            Long productoId = productoIds.get(index);
                                            Integer cantidad = cantidades.get(index);
                                            return productoRepository.findById(productoId)
                                                    .flatMap(producto -> {
                                                        producto.setStock(producto.getStock() + cantidad);  // Restablecer el stock
                                                        return productoRepository.save(producto);
                                                    });
                                        })
                                        .then();
                            })
                            .then(ordenProductoRepository.deleteByOrdenId(ordenId))  // Eliminar los productos asociados
                            .then(ordenCompraRepository.delete(orden));  // Eliminar la orden
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("La orden no existe")));
    }

    // Listar todas las órdenes de compra
    public Flux<OrdenCompra> listarOrdenes() {
        return ordenCompraRepository.findAll();
    }
}