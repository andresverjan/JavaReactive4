package org.api.service;

import org.api.model.SaleProduct;
import org.api.model.SalesOrder;
import org.api.model.SaleDto;
import org.api.repository.PurchaseProductRepository;
import org.api.repository.SaleProductRepository;
import org.api.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SalesRepository ordenRepository;

    @Autowired
    private SaleProductRepository productoRepository;

    @Autowired
    private PurchaseProductRepository purchaseProductRepository;

    public Mono<SalesOrder> crearOrden(SalesOrder orden) {
        orden.setCreatedAt(LocalDateTime.now());
        return ordenRepository.save(orden)
                .flatMap(savedOrden -> {
                    List<Mono<SaleProduct>> relaciones = orden.getProductos().stream()
                            .map(producto -> {
                                SaleProduct saleProduct = new SaleProduct(savedOrden.getId(), producto.getProductoId(), producto.getCantidad());
                                return purchaseProductRepository.save(saleProduct);
                            })
                            .collect(Collectors.toList());
                    return Mono.when(relaciones);
                })
                .then(Mono.just(orden));
    }

    public Mono<SalesOrder> editarOrden(Long id, SalesOrder ordenActualizada) {
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

    public Flux<SalesOrder> listarOrdenes() {
        return ordenRepository.findAll();
    }

    public Flux<SaleDto> listarOrdenesPorProducto(Long productoId) {
        return purchaseProductRepository.findOrdenesByProductoId(productoId);
    }
}
