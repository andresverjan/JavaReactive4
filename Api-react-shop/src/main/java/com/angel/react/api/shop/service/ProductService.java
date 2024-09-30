package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.ProductEntity;
import com.angel.react.api.shop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.time.LocalDate;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Flux<ProductEntity> getAll(){
        return productRepository.findAll()
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("No products found")))
                .doOnComplete(() -> log.info("Find all products success"))
                .doOnError(error -> log.error("Error finding all products: {}", error.getMessage()));
    }

    public Mono<ProductEntity> getById(Long id){
        return productRepository.findById(id)
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("Product not found, id: {}", id)))
                .doOnNext(p -> log.info("Product found, id: {}", id))
                .doOnError(error -> log.error("Error finding the product ID {}: {}", id, error.getMessage()));
    }

    public Mono<ProductEntity> create(ProductEntity product){
        product.setCreatedAt(LocalDate.now());
        product.setUpdatedAt(LocalDate.now());
        return productRepository.save(product)
                .doOnNext(p -> log.info("Product created, id: {}", product.getId()))
                .doOnError(error -> log.error("Error creating the product REF {}: {}", product.getReference(), error.getMessage()));
    }

    public Mono<ProductEntity> update(ProductEntity product) throws ParseException {
        product.setUpdatedAt(LocalDate.now());
        return productRepository.save(product)
                .doOnNext(p -> log.info("Product update, id: {}", product.getId()))
                .doOnError(error -> log.error("Error updating the product ID {}: {}", product.getId(), error.getMessage()));
    }

    public Mono<ResponseEntity<String>> deleteById(Long id) {
        return productRepository.findById(id)
                .flatMap(item -> productRepository.deleteById(id)
                        .then(Mono.fromCallable(() -> {
                            log.info("Product [{}] deleted", item.getName());
                            return ResponseEntity.ok("deleted ok");
                        }))
                )
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Product ID {} not found, error deleting", id);
                    return Mono.just(ResponseEntity.notFound().build());
                }))
                .doOnError(error -> log.error("Error deleting product ID {}: {}", id, error.getMessage()));
    }
}
