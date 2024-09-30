package com.curso_java.tienda.services;

import com.curso_java.tienda.dtos.OrdenCompraDTO;
import com.curso_java.tienda.dtos.OrdenCompraProductoDTO;
import com.curso_java.tienda.entities.OrdenCompra;
import com.curso_java.tienda.entities.OrdenCompraProducto;
import com.curso_java.tienda.repositories.*;
import com.curso_java.tienda.requests.OrdenCompraRequest;
import com.curso_java.tienda.requests.OrdenProductoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrdenCompraService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private OrdenCompraProductoRepository ordenCompraProductoRepository;

    /**
     * Crea una nueva orden de compra a partir de una solicitud de orden de compra.
     *
     * @param request La solicitud de orden de compra que contiene los datos necesarios para crear la orden.
     * @return Un Mono que emite el DTO de la orden de compra creada.
     */
    public Mono<OrdenCompraDTO> createOrdenCompra(OrdenCompraRequest request) {
        return empresaRepository.findById(request.getEmpresaId())
                // Si no se encuentra la empresa, retornar un error
                .switchIfEmpty(Mono.error(new RuntimeException("Empresa no encontrada")))
                .flatMap(empresa -> {
                    // Crear la nueva orden de compra
                    OrdenCompra nuevaOrden = new OrdenCompra();
                    nuevaOrden.setEmpresaId(request.getEmpresaId());
                    nuevaOrden.setVendedorId(request.getVendedorId());
                    nuevaOrden.setTotal(BigDecimal.ZERO);
                    nuevaOrden.setCreatedAt(LocalDateTime.now());
                    nuevaOrden.setUpdatedAt(LocalDateTime.now());

                    // Guardar la nueva orden de compra en el repositorio
                    return ordenCompraRepository.save(nuevaOrden)
                            .flatMap(ordenGuardada -> {
                                // Procesar los productos comprados
                                return Flux.fromIterable(request.getProductos())
                                        .flatMap(productoRequest ->
                                                productoRepository.findById(productoRequest.getProductoId())
                                                        // Si no se encuentra el producto, retornar un empty
                                                        .switchIfEmpty(Mono.empty())
                                                        .flatMap(producto -> {

                                                            // Crear la relación en OrdenesComprasProductos
                                                            OrdenCompraProducto ordenCompraProducto = new OrdenCompraProducto();
                                                            ordenCompraProducto.setOrdenId(ordenGuardada.getId());
                                                            ordenCompraProducto.setProductoId(producto.getId());
                                                            ordenCompraProducto.setCantidad(productoRequest.getCantidad());
                                                            ordenCompraProducto.setPrecioUnitario(producto.getPrecio());
                                                            ordenCompraProducto.setCreatedAt(LocalDateTime.now());

                                                            // Guardar la orden de compra producto en el repositorio
                                                            return ordenCompraProductoRepository.save(ordenCompraProducto);
                                                        })
                                        ).then(ordenCompraRepository.findById(ordenGuardada.getId())); // Obtener la orden de compra guardada
                            });
                })
                .flatMap(ordenActualizada -> {
                    // Recalcular el total de la orden de compra
                    return ordenCompraProductoRepository.findAllByOrdenId(ordenActualizada.getId())
                            .collectList()
                            .flatMap(productos -> {
                                BigDecimal total = productos.stream()
                                        .map(p -> p.getPrecioUnitario().multiply(BigDecimal.valueOf(p.getCantidad())))
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                // Actualizar el total y la fecha de modificación de la orden de compra
                                ordenActualizada.setTotal(total);
                                ordenActualizada.setUpdatedAt(LocalDateTime.now());
                                return ordenCompraRepository.save(ordenActualizada).map(ordenCompra -> toOrdenCompraDTO(ordenCompra));
                            });
                });
    }

    /**
     * Actualiza una orden de compra existente con una lista de productos.
     *
     * @param ordenId El ID de la orden de compra a actualizar.
     * @param productoRequests La lista de solicitudes de productos a actualizar en la orden de compra.
     * @return Un Mono que emite el DTO de la orden de compra actualizada.
     */
    public Mono<OrdenCompraDTO> updateOrdenCompra(String ordenId, List<OrdenProductoRequest> productoRequests) {
        return ordenCompraRepository.findById(ordenId)
                .switchIfEmpty(Mono.empty())
                .flatMap(ordenCompra -> {
                    //Obtener los productos existentes en la orden
                    return ordenCompraProductoRepository.findAllByOrdenId(ordenId)
                            .collectList()
                            .flatMap(productosExistentes -> {
                                //Procesar cada producto en la lista de requests de forma reactiva
                                return Flux.fromIterable(productoRequests)
                                        .flatMap(productoRequest -> {
                                            // Buscar si el producto ya está en la orden
                                            OrdenCompraProducto productoExistente = productosExistentes.stream()
                                                    .filter(p -> p.getProductoId().equals(productoRequest.getProductoId()))
                                                    .findFirst()
                                                    .orElse(null);

                                            if (productoExistente != null) {
                                                // Si el producto ya está en la orden, actualizar la cantidad
                                                productoExistente.setCantidad(productoRequest.getCantidad());
                                                productoExistente.setUpdatedAt(LocalDateTime.now());
                                                return Mono.just(productoExistente);
                                            } else {
                                                // Si el producto no está en la orden, buscarlo en la base de datos y agregarlo
                                                return productoRepository.findById(productoRequest.getProductoId())
                                                        .switchIfEmpty(Mono.error(new RuntimeException("Producto no disponible: " + productoRequest.getProductoId())))
                                                        .map(producto -> {
                                                            // Crear un nuevo producto en la orden
                                                            OrdenCompraProducto nuevoProducto = new OrdenCompraProducto();
                                                            nuevoProducto.setOrdenId(ordenId);
                                                            nuevoProducto.setProductoId(productoRequest.getProductoId());
                                                            nuevoProducto.setCantidad(productoRequest.getCantidad());
                                                            nuevoProducto.setPrecioUnitario(producto.getPrecio());
                                                            nuevoProducto.setCreatedAt(LocalDateTime.now());
                                                            nuevoProducto.setUpdatedAt(LocalDateTime.now());
                                                            productosExistentes.add(nuevoProducto);
                                                            return nuevoProducto;
                                                        });
                                            }
                                        })
                                        .then(Mono.defer(() -> {
                                            //Calcular el nuevo total
                                            BigDecimal nuevoTotal = productosExistentes.stream()
                                                    .map(p -> p.getPrecioUnitario().multiply(BigDecimal.valueOf(p.getCantidad())))
                                                    .reduce(BigDecimal.ZERO, BigDecimal::add);

                                            ordenCompra.setTotal(nuevoTotal);

                                            //Guardar la orden actualizada y sus productos
                                            return ordenCompraRepository.save(ordenCompra)
                                                    .thenMany(ordenCompraProductoRepository.saveAll(productosExistentes))
                                                    .then(Mono.just(toOrdenCompraDTO(ordenCompra)));
                                        }));
                            });
                });
    }


    /**
     * Obtiene todas las órdenes de compra.
     *
     * @return Flux que emite la lista de OrdenCompraDTO correspondientes a todas las órdenes de compra.
     */
    public Flux<OrdenCompraDTO> getOrdenesCompra() {
        // Obtener todas las órdenes de compra y convertirlas a DTO
        return ordenCompraRepository.findAll().map(ordenCompra -> toOrdenCompraDTO(ordenCompra))
                .switchIfEmpty(Mono.empty());
    }

    /**
     * Obtiene todos los productos asociados a una orden de compra específica.
     *
     * @param ordenId El ID de la orden de compra cuyos productos se desean obtener.
     * @return Un Flux que emite la lista de OrdenCompraProductoDTO correspondientes a los productos de la orden de compra.
     */
    public Flux<OrdenCompraProductoDTO> getAllProductosByOrdenCompra(String ordenId) {
        // Obtener todos los productos asociados a la orden de compra y convertirlos a DTO
        return ordenCompraProductoRepository.findAllByOrdenId(ordenId).map(ordenCompraProducto -> toOrdenCompraProductoDTO(ordenCompraProducto))
                .switchIfEmpty(Mono.empty());
    }

    /**
     * Convierte una entidad OrdenCompraProducto a su correspondiente DTO OrdenCompraProductoDTO.
     *
     * @param ordenCompraProducto La entidad OrdenCompraProducto a convertir.
     * @return El DTO OrdenCompraProductoDTO correspondiente a la entidad proporcionada.
     */
    private OrdenCompraProductoDTO toOrdenCompraProductoDTO(OrdenCompraProducto ordenCompraProducto) {
        // Crear y devolver un OrdenCompraProductoDTO con los datos de la entidad OrdenCompraProducto
        return new OrdenCompraProductoDTO(ordenCompraProducto.getId(), ordenCompraProducto.getOrdenId(), ordenCompraProducto.getProductoId(), ordenCompraProducto.getCantidad());
    }

    /**
     * Convierte una entidad OrdenCompra a su correspondiente DTO OrdenCompraDTO.
     *
     * @param ordenCompra La entidad OrdenCompra a convertir.
     * @return El DTO OrdenCompraDTO correspondiente a la entidad proporcionada.
     */
    private OrdenCompraDTO toOrdenCompraDTO(OrdenCompra ordenCompra) {
        // Crear y devolver un OrdenCompraDTO con los datos de la entidad OrdenCompra
        return new OrdenCompraDTO(ordenCompra.getId(), ordenCompra.getEmpresaId(), ordenCompra.getVendedorId(), ordenCompra.getTotal());
    }
}