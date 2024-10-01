package com.example.trabajoFinal.service;

import com.example.trabajoFinal.exception.CustomException;
import com.example.trabajoFinal.model.OrdenesCompra;
import com.example.trabajoFinal.repository.OrdenesComprasRepository;
import com.example.trabajoFinal.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class OrdenesComprasService {
    private final OrdenesComprasRepository ordenesComprasRepository;
    private final ProductRepository productRepository;


    public OrdenesComprasService(OrdenesComprasRepository ordenesComprasRepository, ProductRepository productRepository) {
        this.ordenesComprasRepository = ordenesComprasRepository;
        this.productRepository = productRepository;
    }

    public Mono<OrdenesCompra> createOrdenCompra(OrdenesCompra ordenesCompra) {
        return productRepository.findById(ordenesCompra.getProductId())
                .switchIfEmpty(Mono.error(new CustomException("Producto no encontrado")))
                .flatMap(product -> {
                    product.setStock(product.getStock() + ordenesCompra.getCantidad());
                    ordenesCompra.setCreatedAt(LocalDateTime.now());
                    ordenesCompra.setEstado("Creacion");
                    return productRepository.save(product)
                            .then(ordenesComprasRepository.save(ordenesCompra));
                });
    }

    public Mono<OrdenesCompra> updateOrdenCompra(int id, OrdenesCompra ordenesCompra) {
        return ordenesComprasRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException("Orden no encontrada")))
                .flatMap(existingOrder -> {
                    if ("Cancelada".equals(existingOrder.getEstado())) {
                        return Mono.error(new CustomException("La orden ya está cancelada"));
                    }
                    int productStock =  existingOrder.getCantidad();
                    existingOrder.setProductId(ordenesCompra.getProductId());
                    existingOrder.setCantidad(ordenesCompra.getCantidad());
                    existingOrder.setEstado("Editada");
                    return productRepository.findById(existingOrder.getProductId())
                            .flatMap(product -> {
                                int dife = productStock - product.getStock();
                                product.setStock(ordenesCompra.getCantidad() + Math.abs(dife));
                                return productRepository.save(product);
                            })
                            .then(ordenesComprasRepository.save(existingOrder));
                });
    }

    public Mono<String> cancelOrdenCompra(int id) {
        return ordenesComprasRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException("Orden de compra no encontrada")))
                .flatMap(ordenCompra -> {
                    ordenCompra.setEstado("Cancelada");
                    return productRepository.findById(ordenCompra.getProductId())
                            .flatMap(product -> {
                                product.setStock(product.getStock() - ordenCompra.getCantidad());
                                return productRepository.save(product);
                            })
                            .then(ordenesComprasRepository.save(ordenCompra))
                            .then(Mono.just("Orden de compra eliminada"));
                });
    }

    public Flux<OrdenesCompra> listOrdenCompra() {
        return ordenesComprasRepository.findAll();
    }
}
