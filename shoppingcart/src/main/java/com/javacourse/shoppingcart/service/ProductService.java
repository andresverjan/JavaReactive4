package com.javacourse.shoppingcart.service;

import com.javacourse.shoppingcart.model.Product;
import com.javacourse.shoppingcart.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Flux<Product> getProducts(){
        return productRepository.findAll()
                .doOnNext(product -> System.out.println("Data: "+product))
                .doOnError(System.err::println);

    }

    public Mono<Product> create(Product product){
        product.setCreatedDate(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Mono<Product> getProductById(Long id){
        if(id==null){
            return Mono.empty();
        }
        return productRepository.findById(id)
                .doOnNext(product -> System.out.println("Data get: "+product));
    }

    public Mono<String>update(Product product){
        if(product.getId() != null){
            product.setUpdatedDate(LocalDateTime.now());
            return productRepository.save(product)
                    .doOnNext(product1 -> System.out.println("Data updating: "+product1))
                    .then(Mono.just("Product updated"));
        }
        return Mono.just("Product is not present");
    }

    public Mono<Void> deletePersonById(Long id){
        if(id == null){
            return Mono.error(new IllegalArgumentException("Id cannot be null"));
        }
        return productRepository.deleteById(id)
                .doOnNext(unused -> System.out.println("Data delete "+id));
    }

    public Mono<Product> getProductByName(String name){
        if(name==null){
            System.out.println("Nulo");
            return Mono.empty();
        }
       return productRepository.findByName(name)
               .doOnNext(product -> System.out.println("Data get by name: "+product))
               .doOnError(throwable -> System.out.println("Error: "+throwable.getMessage()))
               .switchIfEmpty(Mono.empty());
    }
}
