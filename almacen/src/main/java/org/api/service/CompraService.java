package org.api.service;

import org.api.model.OrdenCompra;
import org.api.model.OrdenCompraProducto;
import org.api.model.OrdenProducto;
import org.api.model.Product;
import org.api.repository.ComprasRepository;
import org.api.repository.OrdenProductoRepository;
import org.api.repository.OrdenVentaProductoRepository;
import org.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CompraService {

    @Autowired
    private ComprasRepository ordenCompraRepository;

    @Autowired
    private OrdenVentaProductoRepository ordenProductoRepository;

    @Autowired
    private ProductRepository productRepository;

    public Mono<OrdenCompra> crearOrdenCompra(OrdenCompra ordenCompra) {
        return ordenCompraRepository.save(ordenCompra)
                .flatMap(savedOrden -> {
                    return Flux.fromIterable(savedOrden.getProductos())
                            .flatMap(producto -> {
                                Mono<Void> stockUpdate = actualizarStock(producto.getProductoId(), producto.getCantidad());
                                OrdenCompraProducto ordenProducto = new OrdenCompraProducto();
                                ordenProducto.setOrdenId(savedOrden.getId());
                                ordenProducto.setProductoId(producto.getProductoId());
                                ordenProducto.setCantidad(producto.getCantidad());
                                Mono<OrdenCompraProducto> saveProducts = ordenProductoRepository.save(ordenProducto);
                                return stockUpdate.then(saveProducts);
                            })
                            .then(Mono.just(savedOrden)); // Devuelve la orden guardada
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

    public Flux<OrdenCompra> listarOrdenes() {
        return ordenCompraRepository.findAll();
    }
}