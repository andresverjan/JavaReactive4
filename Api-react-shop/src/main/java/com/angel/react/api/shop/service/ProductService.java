package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.ProductEntity;
import com.angel.react.api.shop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return productRepository.findAll();
    }

    public Mono<ProductEntity> getById(Long id){
        if(id == null){
            return Mono.empty();
        }

        return productRepository.findById(id)
                .doOnNext(p -> log.info("Product found, id: {}", id));
    }

    public Mono<ProductEntity> create(ProductEntity product){
        product.setCreatedAt(LocalDate.now());
        product.setUpdatedAt(LocalDate.now());
        return productRepository.save(product)
                .doOnNext(p -> log.info("Product created, id: {}", product.getId()));
    }

    public Mono<ProductEntity> update(ProductEntity product) throws ParseException {
        product.setUpdatedAt(LocalDate.now());
        return productRepository.save(product)
                .doOnNext(p -> log.info("Product update, id: {}", product.getId()));
    }

    public Mono<Void> deleteById(Long id) {
        if(id == null){
            return Mono.empty();
        }

        return productRepository.deleteById(id)
                .doOnNext(p -> log.info("Product deleted, id: {}", id));
    }
}
