package com.curso_java.tienda.services;

import com.curso_java.tienda.dtos.OrdenCompraDTO;
import com.curso_java.tienda.dtos.OrdenVentaDTO;
import com.curso_java.tienda.repositories.OrdenCompraRepository;
import com.curso_java.tienda.repositories.OrdenVentaProductoRepository;
import com.curso_java.tienda.repositories.OrdenVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import java.time.LocalDateTime;

/**
 * Servicio para generar reportes de órdenes de compra y venta.
 */
@Service
public class ReportesService {

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;
    @Autowired
    private OrdenVentaRepository ordenVentaRepository;
    @Autowired
    private OrdenVentaProductoRepository ordenVentaProductoRepository;

    @Autowired
    private OrdenCompraService ordenCompraService;
    @Autowired
    private OrdenVentaService ordenVentaService;

    /**
     * Genera un reporte de órdenes de compra en un rango de fechas específico.
     *
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin La fecha de fin del rango.
     * @return Un Flux que emite la lista de OrdenCompraDTO correspondientes a las órdenes de compra en el rango de fechas especificado.
     */
    public Flux<OrdenCompraDTO> generarReporteCompras(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ordenCompraRepository.findAllByCreatedAtBetween(fechaInicio, fechaFin)
                .switchIfEmpty(Flux.empty())
                .map(ordenCompra -> ordenCompraService.toOrdenCompraDTO(ordenCompra));
    }

    /**
     * Genera un reporte de órdenes de venta en un rango de fechas específico.
     *
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin La fecha de fin del rango.
     * @return Un Flux que emite la lista de OrdenVentaDTO correspondientes a las órdenes de venta en el rango de fechas especificado.
     */
    public Flux<OrdenVentaDTO> generarReporteVentas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ordenVentaRepository.findAllByCreatedAtBetween(fechaInicio, fechaFin)
                .switchIfEmpty(Flux.empty())
                .map(ordenVenta -> ordenVentaService.toOrdenVentaDTO(ordenVenta));
    }
}
