package com.programacion.reactiva.actividad_final.service;

import com.programacion.reactiva.actividad_final.model.Product;
import com.programacion.reactiva.actividad_final.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<Product> listProduct() {
        return productRepository.findAll();
    }

    public Mono<Product> createProduct(Product product) {
        return productRepository.save(product)
                .doOnNext(productCreated -> System.out.println("Product created: " + productCreated));
    }

    public Mono<Product> editProduct(Long id, Product product) {
        return productRepository.findById(id)
                .flatMap(productEdited -> {
                    if(product.getName()!=null) productEdited.setName(product.getName());
                    if(product.getPrice()!=null) productEdited.setPrice(product.getPrice());
                    if(product.getDescription()!=null) productEdited.setDescription(product.getDescription());
                    if(product.getImageUrl()!=null) productEdited.setImageUrl(product.getImageUrl());
                    if(product.getStock()!=null) productEdited.setStock(product.getStock());
                    return productRepository.save(productEdited);
                });
    }

    public Mono<Product> findProductById(Long id){
        return productRepository.findById(id);
    }

    public Flux<Product> findProductByName(String name){
        return productRepository.findByName(name);
    }

    public Mono<Void> deleteProduct(Long id){
        return productRepository.deleteById(id);
    }

    public Mono<Product> updateStock(Long id, int stock){
        return productRepository.findById(id)
                .flatMap(product -> {
                    System.out.println("Update stock of product: " + product.getName());
                    product.setStock(stock);
                    return productRepository.save(product);
                });
    }

}
