package com.example.EntregaFinal.service;

import com.example.EntregaFinal.model.Orden;
import com.example.EntregaFinal.model.OrdenProducto;
import com.example.EntregaFinal.repository.OrdenProductoRepository;
import com.example.EntregaFinal.repository.OrdenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrdenService {

    private final OrdenRepository ordenRepository;
    private final OrdenProductoRepository ordenProductoRepository;

    public OrdenService(OrdenRepository ordenRepository, OrdenProductoRepository ordenProductoRepository) {
        this.ordenRepository = ordenRepository;
        this.ordenProductoRepository = ordenProductoRepository;
    }

    public Mono<Orden> crearOrden(String cliente) {
        Orden orden = new Orden();
        orden.setCliente(cliente);
        orden.setFecha(LocalDateTime.now());
        return ordenRepository.save(orden);
    }


    // Agregar un producto a una orden
    public Mono<OrdenProducto> agregarProductoALaOrden(Long ordenId, Long productoId, int cantidad) {
        OrdenProducto ordenProducto = new OrdenProducto();
        ordenProducto.setOrdenId(ordenId);
        ordenProducto.setProductoId(productoId);
        ordenProducto.setCantidad(cantidad);
        return ordenProductoRepository.save(ordenProducto);
    }

    // Obtener todos los productos de una orden
    public Flux<OrdenProducto> obtenerProductosDeLaOrden(Long ordenId) {
        return ordenProductoRepository.findByOrdenId(ordenId);
    }

}
