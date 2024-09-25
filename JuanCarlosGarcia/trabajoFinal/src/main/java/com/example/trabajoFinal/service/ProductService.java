package com.example.trabajoFinal.service;

import com.example.trabajoFinal.exception.CustomException;
import com.example.trabajoFinal.model.Product;
import com.example.trabajoFinal.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<Product> getAll() {
        return productRepository.findAll();
    }

    public Mono<Product> getProductById(Integer id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException("Producto no encontrado")));
    }


    public Mono<Product> getProductByName(String name) {
        return productRepository.findByName(name)
                .switchIfEmpty(Mono.error(new CustomException("Producto no encontrado")));
    }

    public Mono<Product> save(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Mono<Product> update(Integer id, Product product) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException("Producto no encontrado")))
                .flatMap(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setImageUrl(product.getImageUrl());
                    existingProduct.setStock(product.getStock());
                    existingProduct.setUpdatedAt(LocalDateTime.now());
                    return productRepository.save(existingProduct);
                });
    }

    public Mono<String> delete(Integer id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException("Producto no encontrado")))
                .flatMap(product -> productRepository.delete(product)
                        .then(Mono.just("Producto eliminado")));
    }

    public Mono<Product> modifyStock(Integer id, int stock) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException("Producto no encontrado")))
                .flatMap(existingProduct -> {
                    existingProduct.setStock(stock);
                    return productRepository.save(existingProduct);
                });
    }
}