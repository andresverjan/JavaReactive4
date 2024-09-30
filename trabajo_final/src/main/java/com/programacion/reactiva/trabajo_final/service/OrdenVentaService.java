package com.programacion.reactiva.trabajo_final.service;

import com.programacion.reactiva.trabajo_final.model.OrdenVenta;
import com.programacion.reactiva.trabajo_final.model.OrdenVentaProducto;
import com.programacion.reactiva.trabajo_final.model.dto.*;
import com.programacion.reactiva.trabajo_final.repository.OrdenVentaProductoRepository;
import com.programacion.reactiva.trabajo_final.repository.OrdenVentaRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
public class OrdenVentaService {
    private final OrdenVentaRepository ordenVentaRepository;
    private final OrdenVentaProductoRepository ordenVentaProductoRepository;
    private final ProductoService productoService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");



    public OrdenVentaService(OrdenVentaRepository ordenVentaRepository,
                             OrdenVentaProductoRepository ordenVentaProductoRepository, ProductoService productoService) {
        this.ordenVentaRepository = ordenVentaRepository;
        this.ordenVentaProductoRepository = ordenVentaProductoRepository;
        this.productoService = productoService;
    }

    public Flux<OrdenVentaDTO> listarOrdenesVenta() {
        return ordenVentaRepository.findAll()
                .flatMap(ordenVenta -> obtenerDetalleVenta(ordenVenta.getId())
                        .map(detalleVenta -> OrdenVentaDTO.builder()
                                .ordenVentaId((long) ordenVenta.getId())
                                .estado(ordenVenta.getEstado())
                                .detalleCompras(detalleVenta)
                                .valorTotal(ValorTotalDTO.builder()
                                        .valorTotal(ordenVenta.getTotal())
                                        .build())
                                .build()
                        )
                );
    }

    public Flux<OrdenVentaDTO> listarOrdenesVentaEntreFechas(String fechaInicio, String fechaFin) {
        LocalDateTime startDate = LocalDateTime.parse(fechaInicio, formatter);
        LocalDateTime endDate = LocalDateTime.parse(fechaFin, formatter);
        return ordenVentaRepository.findAllByUpdatedAtBetween(startDate, endDate)
                .flatMap(ordenVenta -> obtenerDetalleVenta(ordenVenta.getId())
                        .map(detalleVenta -> OrdenVentaDTO.builder()
                                .ordenVentaId((long) ordenVenta.getId())
                                .estado(ordenVenta.getEstado())
                                .detalleCompras(detalleVenta)
                                .valorTotal(ValorTotalDTO.builder()
                                        .valorTotal(ordenVenta.getTotal())
                                        .build())
                                .build()
                        )
                );
    }

    public Flux<OrdenVentaDTO> listarTop5VentasEntreFechas(String fechaInicio, String fechaFin) {
        LocalDateTime startDate = LocalDateTime.parse(fechaInicio, formatter);
        LocalDateTime endDate = LocalDateTime.parse(fechaFin, formatter);

        return ordenVentaRepository.findAllByUpdatedAtBetween(startDate, endDate)
                .flatMap(ordenVenta -> ordenVentaProductoRepository.findByOrdenVentaId((long) ordenVenta.getId())
                        .collectList()
                        .map(productos -> new ProductoCantidadDTO(ordenVenta.getId(), productos.stream()
                                .mapToInt(OrdenVentaProducto::getCantidad).sum()))
                )
                .sort(Comparator.comparingInt(ProductoCantidadDTO::getCantidad).reversed())
                .take(5)
                .flatMap(productoCantidadDTO ->
                        ordenVentaRepository.findById((long) productoCantidadDTO.getProductoId())
                                .flatMap(ordenVenta -> obtenerDetalleVenta(ordenVenta.getId())
                                        .map(detalleVenta -> OrdenVentaDTO.builder()
                                                .ordenVentaId((long) ordenVenta.getId())
                                                .estado(ordenVenta.getEstado())
                                                .detalleCompras(detalleVenta)
                                                .valorTotal(ValorTotalDTO.builder()
                                                        .valorTotal(ordenVenta.getTotal())
                                                        .build())
                                                .build())
                                )
                );
    }


    public Mono<OrdenVentaDTO> obtenerOrdenVentaPorId(Long ordenVentaId){
        return ordenVentaRepository.findById(ordenVentaId)
                .flatMap(ordenVenta -> obtenerDetalleVenta(ordenVenta.getId())
                        .map(detalleVenta -> OrdenVentaDTO.builder()
                                .ordenVentaId((long) ordenVenta.getId())
                                .estado(ordenVenta.getEstado())
                                .detalleCompras(detalleVenta)
                                .valorTotal(ValorTotalDTO.builder()
                                        .valorTotal(ordenVenta.getTotal())
                                        .build())
                                .build()
                        )
                );
    }

    public Flux<OrdenVentaDTO> listarOrdenesVentaPorProducto (Long productoId){
        return ordenVentaProductoRepository.findByProductoId(productoId)
                .flatMap(ordenVentaProducto -> ordenVentaRepository.findById((long)ordenVentaProducto.getOrdenVentaId())
                        .flatMap(ordenVenta -> obtenerDetalleVenta(ordenVenta.getId())
                                .map(detalleVenta -> OrdenVentaDTO.builder()
                                        .ordenVentaId((long) ordenVenta.getId())
                                        .estado(ordenVenta.getEstado())
                                        .detalleCompras(detalleVenta)
                                        .valorTotal(ValorTotalDTO.builder()
                                                .valorTotal(ordenVenta.getTotal())
                                                .build())
                                        .build()
                                )
                        )
                );
    }

    public Mono<OrdenVentaDTO> cambiarEstadoOrdenVenta(Long ordenVentaId, String estado){
        return ordenVentaRepository.findById(ordenVentaId)
                .flatMap(ordenVenta -> {
                    ordenVenta.setEstado(estado);
                    return ordenVentaRepository.save(ordenVenta);
                })
                .flatMap(ordenVenta -> obtenerDetalleVenta(ordenVenta.getId())
                        .map(detalleVenta -> OrdenVentaDTO.builder()
                                .ordenVentaId((long) ordenVenta.getId())
                                .estado(ordenVenta.getEstado())
                                .detalleCompras(detalleVenta)
                                .valorTotal(ValorTotalDTO.builder()
                                        .valorTotal(ordenVenta.getTotal())
                                        .build())
                                .build()
                        )
                );
    }

    public Mono<VentaDTO> crearOrdenVentaDirecta(List<ProductoCantidadDTO> productos, Double envio) {
        return verificarStockProductos(productos).
                then(productoService.obtenerValorTotal(productos, envio))
                .flatMap(valorTotal -> crearOrdenVenta(valorTotal.getValorTotal())
                        .flatMap(ordenVenta -> actualizarStockProductos(productos)
                                .thenMany(crearOrdenVentaProducto(productos, ordenVenta.getId()))
                                .collectList()
                                .flatMap(ordenVentaProducto -> productoService.mapearItemsVentas(productos)
                                        .map(comprasDTO -> ventaResponse(ordenVenta, comprasDTO, valorTotal))
                                )
                        )
                );
    }



    public Mono<OrdenVenta> crearOrdenVenta(Double valorTotal) {
        OrdenVenta ordenVenta = OrdenVenta.builder()
                .total(valorTotal)
                .estado("PENDIENTE")
                .build();
        return ordenVentaRepository.save(ordenVenta);
    }

    public Flux<OrdenVentaProducto> crearOrdenVentaProducto(List<ProductoCantidadDTO> productos, int ordenVentaId) {
        return Flux.fromIterable(productos)
                .flatMap(producto -> {
                    OrdenVentaProducto ordenVentaProducto = OrdenVentaProducto.builder()
                            .ordenVentaId(ordenVentaId)
                            .productoId(producto.getProductoId())
                            .cantidad(producto.getCantidad())
                            .build();
                    return ordenVentaProductoRepository.save(ordenVentaProducto);
                });
    }

    public VentaDTO ventaResponse(OrdenVenta ordenVenta,
                                   CarritoComprasDTO carritoComprasDTO,
                                   ValorTotalDTO valorTotalDTO) {
        return VentaDTO.builder()
                .ordenVenta(OrdenVentaDTO.builder()
                        .ordenVentaId((long) ordenVenta.getId())
                        .estado(ordenVenta.getEstado())
                        .detalleCompras(carritoComprasDTO)
                        .valorTotal(valorTotalDTO)
                        .build())
                .build();
    }

    private Mono<Void> actualizarStockProductos(List<ProductoCantidadDTO> productos) {
        return Flux.fromIterable(productos)
                .flatMap(producto ->
                        productoService.consultarStockProducto((long) producto.getProductoId(), producto.getCantidad())
                                .flatMap(productoStock ->
                                        productoService.actualizarStock((long) productoStock.getId(), productoStock.getStock() - producto.getCantidad())
                                )
                )
                .then();
    }

    private Mono<Void> verificarStockProductos(List<ProductoCantidadDTO> productos){
        return Flux.fromIterable(productos)
                .flatMap(producto ->
                        productoService.consultarStockProducto((long)producto.getProductoId(), producto.getCantidad()))
                .then();
    }

    public Mono<CarritoComprasDTO> obtenerDetalleVenta(int ordenVentaId){
        return ordenVentaProductoRepository.findByOrdenVentaId((long)ordenVentaId)
                .flatMap(ordenVentaProducto -> productoService.buscarProducto((long)ordenVentaProducto.getProductoId())
                        .map(producto -> ProductoCantidadDTO.builder()
                                .productoId(producto.getId())
                                .cantidad(ordenVentaProducto.getCantidad())
                                .build())
                )
                .collectList()
                .flatMap(productoService::mapearItemsVentas);
    }
}
