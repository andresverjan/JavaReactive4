package com.curso_java.tienda.controllers;

import com.curso_java.tienda.dtos.OrdenCompraDTO;
import com.curso_java.tienda.dtos.OrdenVentaDTO;
import com.curso_java.tienda.dtos.ResponseData;
import com.curso_java.tienda.services.ReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador REST para generar reportes de órdenes de compra y venta.
 */
@RestController
@RequestMapping("/reportes")
public class ReportesController {

    @Autowired
    private ReportesService reportesService;

    /**
     * Genera un reporte de órdenes de compra en un rango de fechas específico.
     *
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin La fecha de fin del rango.
     * @return Un Mono que emite la respuesta con la lista de órdenes de compra en el rango de fechas especificado.
     */
    @GetMapping("/compras")
    public Mono<ResponseData<List<OrdenCompraDTO>>> generarReporteCompras(@RequestParam("fechaInicio") LocalDateTime fechaInicio,
                                                                          @RequestParam("fechaFin") LocalDateTime fechaFin) {
        return reportesService.generarReporteCompras(fechaInicio, fechaFin)
                .collectList()
                .map(ordenCompra -> new ResponseData<>("Ordenes de compra creadas", ordenCompra));
    }

    /**
     * Genera un reporte de órdenes de venta en un rango de fechas específico.
     *
     * @param fechaInicio La fecha de inicio del rango.
     * @param fechaFin La fecha de fin del rango.
     * @return Un Mono que emite la respuesta con la lista de órdenes de venta en el rango de fechas especificado.
     */
    @GetMapping("/ventas")
    public Mono<ResponseData<List<OrdenVentaDTO>>> generarReporteVentas(@RequestParam("fechaInicio") LocalDateTime fechaInicio,
                                                                        @RequestParam("fechaFin") LocalDateTime fechaFin) {
        return reportesService.generarReporteVentas(fechaInicio, fechaFin)
                .collectList()
                .map(ordenVenta -> new ResponseData<>("Ordenes de venta creadas", ordenVenta));
    }
}
