package com.programacion.reactiva.trabajo_final.service;

import com.programacion.reactiva.trabajo_final.model.OrdenCompra;
import com.programacion.reactiva.trabajo_final.model.OrdenCompraProducto;
import com.programacion.reactiva.trabajo_final.model.dto.OrdenCompraDTO;
import com.programacion.reactiva.trabajo_final.model.dto.ProductoCantidadDTO;
import com.programacion.reactiva.trabajo_final.repository.OrdenCompraProductoRepository;
import com.programacion.reactiva.trabajo_final.repository.OrdenCompraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrdenCompraService {
    private  final OrdenCompraRepository ordenCompraRepository;
    private final  OrdenCompraProductoRepository ordenCompraProductoRepository;
    private final ProductoService productoService;
    private static final Logger logger = LoggerFactory.getLogger(OrdenCompraService.class);


    public OrdenCompraService(OrdenCompraRepository ordenCompraRepository,
                              OrdenCompraProductoRepository ordenCompraProductoRepository,
                              ProductoService productoService) {
        this.ordenCompraRepository = ordenCompraRepository;
        this.ordenCompraProductoRepository = ordenCompraProductoRepository;
        this.productoService = productoService;
    }

    public Flux<OrdenCompraDTO>listarOrdenesComra(){
        return ordenCompraRepository.findAll()
                .flatMap(ordenCompra -> obtenerDetalleCompras((long)ordenCompra.getId())
                        .map(detalleCompra -> OrdenCompraDTO.builder()
                                .compraId((long)ordenCompra.getId())
                                .estado(ordenCompra.getEstado())
                                .items(detalleCompra.getItems())
                                .total(ordenCompra.getTotal())
                                .build()
                        )
                );
    }

    public Flux<OrdenCompraDTO> listarOrdenesCompraEntreFechas(String fechaInicio, String fechaFin){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(fechaInicio, formatter);
        LocalDateTime endDate = LocalDateTime.parse(fechaFin, formatter);
        return ordenCompraRepository.findAllByUpdatedAtBetween(startDate, endDate)
                .flatMap(ordenCompra -> obtenerDetalleCompras((long)ordenCompra.getId())
                        .map(detalleCompra -> OrdenCompraDTO.builder()
                                .compraId((long)ordenCompra.getId())
                                .estado(ordenCompra.getEstado())
                                .items(detalleCompra.getItems())
                                .total(ordenCompra.getTotal())
                                .build()
                        )
                );
    }







    public Mono<OrdenCompraDTO> registrarOrdenCompra(List<ProductoCantidadDTO> productos, Double total) {
        logger.info("Registrando orden de compra con productos: {} y total: {}", productos, total);
        return crearOrdenCompra(total)
                .doOnNext(ordenCompra -> logger.info("Orden de compra creada: {}", ordenCompra))
                .flatMap(ordenCompra ->
                        crearOrdenCompraProducto(productos, ordenCompra.getId())
                                .collectList()
                                .doOnNext(ordenCompraProductos -> logger.info("Productos de la orden de compra creados: {}", ordenCompraProductos))
                                .flatMap(ordenCompraProductos ->
                                        actualizarStockProductos(productos).then(
                                                productoService.mapearItemsCompras(productos)
                                                        .doOnNext(response -> logger.info("Items mapeados: {}", response))
                                                        .map(response -> OrdenCompraDTO.builder()
                                                                .compraId((long) ordenCompra.getId())
                                                                .items(response.getItems())
                                                                .estado(ordenCompra.getEstado())
                                                                .total(ordenCompra.getTotal())
                                                                .build()
                                                        )
                                        )
                                )
                );
    }

    private Mono<OrdenCompra> crearOrdenCompra(Double total){
        OrdenCompra ordenCompra = OrdenCompra.builder()
                .total(total)
                .estado("RECIBIDA")
                .build();
        return ordenCompraRepository.save(ordenCompra)
                .doOnSuccess(savedOrdenCompra -> logger.info("Orden de compra guardada en la base de datos: {}", savedOrdenCompra))
                .doOnError(error -> logger.error("Error al guardar la orden de compra: ", error));
    }

    private Flux<OrdenCompraProducto> crearOrdenCompraProducto(List<ProductoCantidadDTO> productos, int ordenCompraId){
        return Flux.fromIterable(productos)
                .flatMap(producto -> {
                    OrdenCompraProducto ordenCompraProducto = OrdenCompraProducto.builder()
                            .ordenCompraId(ordenCompraId)
                            .productoId(producto.getProductoId())
                            .cantidad(producto.getCantidad())
                            .build();
                    return ordenCompraProductoRepository.save(ordenCompraProducto);
                });
    }

    public Mono<Void> actualizarStockProductos(List<ProductoCantidadDTO> productos) {
    return Flux.fromIterable(productos)
            .flatMap(producto -> productoService
                    .consultarStockProducto((long)producto.getProductoId(), -producto.getCantidad())
                    .flatMap(productoStock -> {
                        int nuevoStock = productoStock.getStock() + producto.getCantidad();
                        return productoService.actualizarStock((long) productoStock.getId(), nuevoStock);
                    })
            )
            .then();
}

    public Mono<OrdenCompraDTO> obtenerDetalleCompras(Long ordenCompraId){
        return ordenCompraProductoRepository.findByOrdenCompraId(ordenCompraId)
                .flatMap(ordenCompraProducto ->
                        productoService.buscarProducto((long)ordenCompraProducto.getProductoId())
                        .map(producto -> ProductoCantidadDTO.builder()
                                .productoId(producto.getId())
                                .cantidad(ordenCompraProducto.getCantidad())
                                .build())
                )
                .collectList()
                .flatMap(productoService::mapearItemsCompras);
    }




}
