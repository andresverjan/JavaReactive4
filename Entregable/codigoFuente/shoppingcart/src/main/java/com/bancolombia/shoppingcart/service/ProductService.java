package com.bancolombia.shoppingcart.service;

import com.bancolombia.shoppingcart.entity.Product;
import com.bancolombia.shoppingcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<Product> allProducts(){
        return  productRepository.findAll();
    }

    public Mono<Product> findById(Long id){
        return productRepository.findById(id);
    }

    public Mono<Product> findByName(String name){
        return productRepository.findByName(name);
    }

    public Mono<Product> create(Product productNew){
        return productRepository.save(productNew).flatMap(productCreated -> productRepository.findById(productCreated.getId()));
    }

    public Mono<Product> update(Long id, Product product) {
        return productRepository.findById(id)
                                .flatMap(existingProduct -> {
                                    existingProduct.setName(product.getName());
                                    existingProduct.setName(product.getName());
                                    existingProduct.setPrice(product.getPrice());
                                    existingProduct.setDescription(product.getDescription());
                                    existingProduct.setImageUrl(product.getImageUrl());
                                    existingProduct.setTaxPercentage(product.getTaxPercentage());
                                    existingProduct.setDiscountPercentage(product.getDiscountPercentage());
                                    return productRepository.save(existingProduct).flatMap(productUpdated -> productRepository.findById(productUpdated.getId()));
                                });
    }

    public Mono<Void> deleteProduct(Long id) {
        return productRepository.deleteById(id);
    }

    public Mono<Product> updateStock(Long id, double stock) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    if((existingProduct.getStock() + stock) < 0) {
                        return Mono.error(new Exception("El valor a modificar debe ser menor o igual al valor actual de stock"));
                    }
                    existingProduct.setStock(existingProduct.getStock() + stock);
                    return productRepository.save(existingProduct).flatMap(productUpdated -> productRepository.findById(productUpdated.getId()));
                });
    }
}
