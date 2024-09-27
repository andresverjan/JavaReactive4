package org.api.service;

import org.api.model.PurchaseDto;
import org.api.model.PurchaseOrder;
import org.api.model.PurchaseProduct;
import org.api.repository.PurchaseProductRepository;
import org.api.repository.PurchaseRepository;
import org.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private PurchaseProductRepository purchaseProductRepository;

    @Autowired
    private ProductRepository productRepository;

    public Mono<PurchaseOrder> crearOrdenCompra(PurchaseOrder ordenCompra) {
        return purchaseRepository.save(ordenCompra)
                .flatMap(savedOrden -> {
                    return Flux.fromIterable(savedOrden.getProductos())
                            .flatMap(producto -> {
                                Mono<Void> stockUpdate = actualizarStock(producto.getProductoId(), producto.getCantidad());
                                PurchaseProduct ordenProducto = new PurchaseProduct();
                                ordenProducto.setOrdenId(savedOrden.getId());
                                ordenProducto.setProductoId(producto.getProductoId());
                                ordenProducto.setCantidad(producto.getCantidad());
                                Mono<PurchaseProduct> saveProducts = purchaseProductRepository.save(ordenProducto);
                                return stockUpdate.then(saveProducts);
                            })
                            .then(Mono.just(savedOrden));
                });
    }

    private Mono<Void> actualizarStock(Long productoId, Integer cantidad) {
        return productRepository.findById(productoId)
                .flatMap(producto -> {
                    producto.setStock(producto.getStock() + cantidad);
                    return productRepository.save(producto);
                })
                .then();
    }

    public Flux<PurchaseOrder> listarOrdenes() {
        return purchaseRepository.findAll();
    }

    public Flux<PurchaseOrder> obtenerReportesCompras(LocalDateTime startDate, LocalDateTime endDate) {
        return purchaseRepository.findAllByCreatedAtBetween(startDate, endDate);
    }

}