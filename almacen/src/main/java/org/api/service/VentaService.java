package org.api.service;

import org.api.model.OrdenProducto;
import org.api.model.OrdenVenta;
import org.api.model.VentaDto;
import org.api.repository.OrdenProductoRepository;
import org.api.repository.ProductRepository;
import org.api.repository.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaService {

    @Autowired
    private VentasRepository ordenRepository;

    @Autowired
    private ProductRepository productoRepository;

    @Autowired
    private OrdenProductoRepository ordenProductoRepository;

    public Mono<OrdenVenta> crearOrden(OrdenVenta orden) {
        return ordenRepository.save(orden)
                .flatMap(savedOrden -> {
                    List<Mono<OrdenProducto>> relaciones = orden.getProductos().stream()
                            .map(producto -> {
                                OrdenProducto ordenProducto = new OrdenProducto(savedOrden.getId(), producto.getProductoId(), producto.getCantidad());
                                return ordenProductoRepository.save(ordenProducto);
                            })
                            .collect(Collectors.toList());
                    return Mono.when(relaciones);
                })
                .then(Mono.just(orden));
    }

    public Mono<OrdenVenta> editarOrden(Long id, OrdenVenta ordenActualizada) {
        return ordenRepository.findById(id)
                .flatMap(existingOrden -> {
                    existingOrden.setProductos(ordenActualizada.getProductos());
                    existingOrden.setEstado("Editada");
                    return ordenRepository.save(existingOrden);
                });
    }

    public Mono<Void> cancelarOrden(Long id) {
        return ordenRepository.findById(id)
                .flatMap(existingOrden -> {
                    existingOrden.setEstado("Cancelada");
                    return ordenRepository.save(existingOrden);
                }).then();
    }

    public Flux<OrdenVenta> listarOrdenes() {
        return ordenRepository.findAll();
    }

    public Flux<VentaDto> listarOrdenesPorProducto(Long productoId) {
        return ordenProductoRepository.findOrdenesByProductoId(productoId);
    }
}
