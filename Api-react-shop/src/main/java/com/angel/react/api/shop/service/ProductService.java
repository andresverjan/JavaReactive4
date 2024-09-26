package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.PersonEntity;
import com.angel.react.api.shop.model.ProductEntity;
import com.angel.react.api.shop.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
                .doOnNext(p -> System.out.println("Persona encontrada, id: " + id));
    }

    public Mono<ProductEntity> create(ProductEntity product){
        return productRepository.save(product)
                .doOnNext(p -> System.out.println("product creada, id: " + product.getId()));
    }

    public Mono<ProductEntity> update(ProductEntity person){
        return productRepository.save(person)
                .doOnNext(p -> System.out.println("Persona actualizada, id: " + person.getId()));
    }

    public Mono<Void> deleteById(Long id) {
        if(id == null){
            return Mono.empty();
        }

        return productRepository.deleteById(id)
                .doOnNext(p -> System.out.println("Persona eliminada, id: " + id));
    }
}
