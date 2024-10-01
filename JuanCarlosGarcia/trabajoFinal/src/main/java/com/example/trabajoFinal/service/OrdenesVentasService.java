package com.example.trabajoFinal.service;

import com.example.trabajoFinal.exception.CustomException;
import com.example.trabajoFinal.model.OrdenesVentas;
import com.example.trabajoFinal.repository.OrdenesVentasRepository;
import com.example.trabajoFinal.repository.PersonaRepository;
import com.example.trabajoFinal.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class OrdenesVentasService {

    private final OrdenesVentasRepository ordenVentaRepository;
    private final PersonaRepository personaRepository;
    private final ProductRepository productRepository;


    public OrdenesVentasService(OrdenesVentasRepository ordenCompraRepository, PersonaRepository personaRepository,
                                ProductRepository productRepository) {
        this.ordenVentaRepository = ordenCompraRepository;
        this.personaRepository = personaRepository;
        this.productRepository = productRepository;
    }

    // Crear una nueva orden de venta
    public Mono<OrdenesVentas> createOrdenesVentas(OrdenesVentas ordenesVentas) {
        return personaRepository.findById(ordenesVentas.getPersonaId())
                .switchIfEmpty(Mono.error(new CustomException("Persona no encontrada")))
                .then(productRepository.findById(ordenesVentas.getProductId()))
                .switchIfEmpty(Mono.error(new CustomException("Producto no encontrado")))
                .flatMap(product -> {
                    if (product.getStock() < ordenesVentas.getCantidad()) {
                        return Mono.error(new CustomException("No hay suficiente stock del producto, " +
                                "stock actual " + product.getStock()));
                    }
                    product.setStock(product.getStock() - ordenesVentas.getCantidad());
                    return productRepository.save(product);
                })
                .then(Mono.defer(() -> {
                    ordenesVentas.setCreatedAt(LocalDateTime.now());
                    ordenesVentas.setEstado("Creacion");
                    return ordenVentaRepository.save(ordenesVentas);
                }));
    }


    // Crear una nueva orden de venta
    public Mono<OrdenesVentas> updateOrdenesVentas(int id, OrdenesVentas saleOrder) {
        return ordenVentaRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException("Orden no encontrada")))
                .flatMap(existingSaleOrder -> {
                    if ("Cancelada".equals(existingSaleOrder.getEstado())) {
                        return Mono.error(new CustomException("No se puede editar una orden cancelada"));
                    }
                    int oldQuantity = existingSaleOrder.getCantidad();
                    int newQuantity = saleOrder.getCantidad();
                    existingSaleOrder.setProductId(saleOrder.getProductId());
                    existingSaleOrder.setCantidad(newQuantity);
                    existingSaleOrder.setUpdateAt(LocalDateTime.now());
                    existingSaleOrder.setEstado("Editada");
                    return productRepository.findById(existingSaleOrder.getProductId())
                            .flatMap(product -> {
                                int stockDifference = oldQuantity - newQuantity;
                                if (product.getStock() + stockDifference < 0) {
                                    return Mono.error(new CustomException("No hay suficiente stock del producto, " +
                                            "stock actual " + product.getStock()));
                                }
                                product.setStock(product.getStock() + stockDifference);
                                return productRepository.save(product);
                            })
                            .then(ordenVentaRepository.save(existingSaleOrder));
                });
    }

    // Cancelar una orden de venta
    public Mono<String> cancelOrdenesVentas(int id) {
        return ordenVentaRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException("Orden no encontrada")))
                .flatMap(existingSaleOrder -> {
                    if ("Cancelada".equals(existingSaleOrder.getEstado())) {
                        return Mono.error(new CustomException("La orden se encuentra cancelada"));
                    }
                    existingSaleOrder.setEstado("Cancelada");
                    existingSaleOrder.setUpdateAt(LocalDateTime.now());
                    return productRepository.findById(existingSaleOrder.getProductId())
                            .flatMap(product -> {
                                product.setStock(product.getStock() + existingSaleOrder.getCantidad());
                                return productRepository.save(product);
                            })
                            .then(ordenVentaRepository.save(existingSaleOrder));
                })
                .then(Mono.just("Orden eliminada"));
    }

    // Listar todas las ordenes de venta
    public Flux<OrdenesVentas> listOrdenesVentas() {
        return ordenVentaRepository.findAll();
    }

    // Listar ordenes de venta por producto
    public Flux<OrdenesVentas> listOrdenesVentasByProduct(int productId) {
        return ordenVentaRepository.findByProductId(productId);
    }
}
