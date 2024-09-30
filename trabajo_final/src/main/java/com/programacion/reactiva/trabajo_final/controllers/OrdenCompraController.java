package com.programacion.reactiva.trabajo_final.controllers;

import com.programacion.reactiva.trabajo_final.model.dto.OrdenCompraDTO;
import com.programacion.reactiva.trabajo_final.model.dto.OrdenCompraRequestDTO;
import com.programacion.reactiva.trabajo_final.service.OrdenCompraService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orden-compra")
public class OrdenCompraController {
    private final OrdenCompraService ordenCompraService;

    public OrdenCompraController(OrdenCompraService ordenCompraService) {
        this.ordenCompraService = ordenCompraService;
    }

    @GetMapping
    public Flux<OrdenCompraDTO> listarOrdenesCompra(){
        return ordenCompraService.listarOrdenesComra();
    }


    @PostMapping
    public Mono<OrdenCompraDTO> createOrdenCompra(@RequestBody OrdenCompraRequestDTO ordenCompraRequestDTO){
        return ordenCompraService.registrarOrdenCompra(ordenCompraRequestDTO.getProductos(), ordenCompraRequestDTO.getTotal());
    }

    @GetMapping("/reporte")
    public Flux<OrdenCompraDTO> listarOrdenesCompraPorFecha(@RequestParam String fechaInicio, @RequestParam String fechaFin){
        return ordenCompraService.listarOrdenesCompraEntreFechas(fechaInicio, fechaFin);
    }

}
