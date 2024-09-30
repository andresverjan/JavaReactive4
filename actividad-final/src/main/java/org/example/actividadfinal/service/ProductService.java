package org.example.actividadfinal.service;


import lombok.AllArgsConstructor;
import org.example.actividadfinal.exceptions.ProductException;
import org.example.actividadfinal.model.Product;
import org.example.actividadfinal.model.ResponseData;
import org.example.actividadfinal.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public Mono<ResponseData> getProducts() {
        return productRepository.findAll()
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ProductException("No se encontro información de productos"))))
                .collectList()
                .flatMap(p -> Mono.just(ResponseData.builder()
                        .data(p)
                        .build()));
    }

    public Mono<ResponseData> findById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ProductException("No encontrado con id " + id))))
                .flatMap(p -> Mono.just(ResponseData.builder()
                        .data(p)
                        .build()));
    }

    public Mono<ResponseData> findByName(String name) {
        return productRepository.findProductByName(name)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ProductException("No encontrado con nombre " + name))))
                .collectList()
                .flatMap(p -> Mono.just(ResponseData.builder()
                        .data(p)
                        .build()));
    }

    public Mono<ResponseData> createProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product)
                .flatMap(p -> Mono.just(ResponseData.builder()
                        .data(p)
                        .message("Producto Creado Exitosamente!!")
                        .build()));

    }

    public Mono<ResponseData> updateProduct(Product product) {
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.findById(product.getId())
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ProductException("No encontrado id " + product.getId()))))
                .flatMap(p -> productRepository.save(product)
                    .flatMap(pr -> Mono.just(ResponseData.builder()
                        .data(pr)
                        .message("Producto Actualizado Exitosamente!!")
                        .build())
                    )
                );
    }

    public Mono<ResponseData> deleteProduct(Long id) {
        return productRepository.deleteById(id)
                .onErrorMap(p -> new ProductException("Error eliminando producto con id " + id))
                .thenReturn(ResponseData.builder().message("Producto eliminado exitosamente!").build());
    }

    public Mono<ResponseData> updateStock(Map<String, Long> map) {
        return productRepository.findById(map.get("id"))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ProductException("No encontrado id " + map.get("id")))))
                .flatMap(p -> {
                    p.setStock(map.get("stock"));
                    p.setUpdatedAt(LocalDateTime.now());
                    return Mono.just(p);
                })
                .flatMap(productRepository::save)
                .flatMap(pr -> Mono.just(ResponseData.builder()
                        .data(pr)
                        .message("Stock Actualizado Exitosamente!!")
                        .build())
                );
    }
}