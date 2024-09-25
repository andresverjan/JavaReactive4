package com.example.shopping_cart.service;

import com.example.shopping_cart.model.Product;
import com.example.shopping_cart.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public Flux<Product> getProducts(){
        return productRepository.findAll()
                .doOnNext(product -> System.out.println("Data Product:  "+product));
    }
    public Mono<Product> createProduct(Product product){
        //manejo de errores en calida de data del producto
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }
    public Mono<String> updateProduct(Product product){
        if (product.getId()!=null){
            return productRepository.findById(product.getId())
                    .flatMap(existingProduct -> {
                            product.setCreatedAt(existingProduct.getCreatedAt());
                            product.setUpdatedAt(LocalDateTime.now());
                            System.out.println(product.toString());
                            return productRepository.save(product)
                                    .doOnNext(product2 -> {
                                        System.out.println("Data product update: "+product2);
                                    })
                                    .then(Mono.just("update product data"))
                                    .onErrorResume(e->{
                                        String error="Error: "+e;
                                        return Mono.just(error);

                                    });
                                    //.onErrorReturn("Error general update");
                    })
                    .switchIfEmpty(Mono.just("product not found"));

        }else {
            return Mono.just("Product not exist");
        }
    }

    public Mono<String> deleteProductById(Integer id){
        if (id == null){
            return Mono.error(new IllegalArgumentException("ID no puede ser nullo"));
        }else{
            return productRepository.findById(id)
                    .flatMap(product -> productRepository.deleteById(id)
                            .doOnNext(deleted -> System.out.println("Product Delete: " + product))
                            .then(Mono.just("Id Product delete: "+id))
                            .onErrorResume(e -> {
                                System.out.println("Error during deletion: " + e.getMessage());
                                return Mono.just("Error during deletion: " + e.getMessage());
                            }))
                    .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found with ID: " + id)))
                    .onErrorReturn(IllegalArgumentException.class, "Error: Product not found or invalid ID");
        }

    }
    public Mono<Product> getProductById(Integer id){
        if (id==null){
            return Mono.empty();
        }

        return productRepository.findById(id)
                .doOnNext(product -> System.out.println("Data product by id:  "+product))
                //.then(Mono.just("person search"))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found with ID: " + id)))
                .onErrorResume(e -> {
                    System.out.println("Error during search: " + e.getMessage());
                    return Mono.empty();
                });
        //.onErrorReturn("Person not found with ID: " + id);

    }

    public Flux<Product> getProductByName(String name){
        if (name==null){
            return Flux.empty();
        }
        return productRepository.findByName(name)
                .doOnNext(product -> System.out.println("Data product by id:  "+product))
                //.then(Mono.just("person search"))
                .switchIfEmpty(Flux.error(new IllegalArgumentException("Product not found with name: " + name)))
                .onErrorResume(e -> {
                    System.out.println("Error during search: " + e.getMessage());
                    return Flux.empty();
                });
    }

    public Mono<Product> updateProductStock(Integer id, Integer newStock){
        if (id==null&& newStock<=0){
            return Mono.empty();
        }
        return productRepository.findById(id)
                .flatMap(product->{
                    product.setStock(newStock);
                    product.setUpdatedAt(LocalDateTime.now());
                    return productRepository.save(product);
                })
                .switchIfEmpty(Mono.empty());
    }

}
