package org.api.service;

import org.api.model.PurchaseOrder;
import org.api.model.PurchaseProduct;
import org.api.repository.PurchaseRepository;
import org.api.repository.OrdenVentaProductoRepository;
import org.api.repository.SaleProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository ordenCompraRepository;

    @Autowired
    private OrdenVentaProductoRepository ordenProductoRepository;

    @Autowired
    private SaleProductRepository saleProductRepository;

    public Mono<PurchaseOrder> crearOrdenCompra(PurchaseOrder ordenCompra) {
        return ordenCompraRepository.save(ordenCompra)
                .flatMap(savedOrden -> {
                    return Flux.fromIterable(savedOrden.getProductos())
                            .flatMap(producto -> {
                                Mono<Void> stockUpdate = actualizarStock(producto.getProductoId(), producto.getCantidad());
                                PurchaseProduct ordenProducto = new PurchaseProduct();
                                ordenProducto.setOrdenId(savedOrden.getId());
                                ordenProducto.setProductoId(producto.getProductoId());
                                ordenProducto.setCantidad(producto.getCantidad());
                                Mono<PurchaseProduct> saveProducts = ordenProductoRepository.save(ordenProducto);
                                return stockUpdate.then(saveProducts);
                            })
                            .then(Mono.just(savedOrden)); // Devuelve la orden guardada
                });
    }

    private Mono<Void> actualizarStock(Long productoId, Integer cantidad) {
        return saleProductRepository.findById(productoId)
                .flatMap(producto -> {
                    producto.setStock(producto.getStock() + cantidad);
                    return saleProductRepository.save(producto);
                })
                .then();
    }

    public Flux<PurchaseOrder> listarOrdenes() {
        return ordenCompraRepository.findAll();
    }
}