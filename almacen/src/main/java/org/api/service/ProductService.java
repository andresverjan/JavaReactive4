package org.api.service;

import org.api.model.Product;
import org.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Mono<Product> getProductById(Long id) {
        return repository.findById(id);
    }

    public Flux<Product> getAllProducts() {
        return repository.findAll();
    }

    public Mono<Product> getProductByName(String name) {
        return repository.findByName(name);
    }

    public Mono<Product> addProduct(Product producto){
        return repository.save(producto);
    }

    public Mono<Void> deleteById(Long id){
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NullPointerException("El producto no existe")))
                .flatMap(repository::delete);
    }

}
