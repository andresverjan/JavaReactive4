package com.example.demo.service;

import com.example.demo.models.Product;

import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Mono<Product> createProduct(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    public Mono<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }


    public Mono<Product> updateProduct(Long id, Product product) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setImageUrl(product.getImageUrl());
                    existingProduct.setStock(product.getStock());
                    existingProduct.setCreatedAt(existingProduct.getCreatedAt());
                    existingProduct.setUpdatedAt(LocalDateTime.now());
                    return productRepository.save(existingProduct);
                });
    }
    public Mono<Product> updateProductStock(Long id, int stock) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    existingProduct.setStock(stock);
                    return productRepository.save(existingProduct);
                });
    }

    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }

}
