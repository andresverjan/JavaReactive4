package com.store.shopping.useCase;

import com.store.shopping.drivenAdapters.products.ProductRepoImpl;
import com.store.shopping.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepoImpl productRepo;

    public Mono<Product> getProduct(Integer id){
        return productRepo.getProduct(id);
    }
    public Flux<Product> getProducts(){
        return productRepo.getProducts();
    }
    public Mono<Product> getProductByName(String name){
        return productRepo.getProductByName(name);
    }
    public Mono<Product> createProduct(Product product){
        return productRepo.createProduct(product);
    }
    public Mono<Product> updateProduct(Integer id,Product product){
        return  productRepo.updateProduct(id,product);
    }
    public Mono<Product> updateStock(Integer id,Integer stock){
        return productRepo.getProduct(id)
                .map(item -> {
                    item.setStock(item.getStock()+stock);
                    return item;
                })
                .flatMap(product -> productRepo.updateProduct(id, product));
    }
    public Mono<Product> sellProduct(Integer id,Integer quantity){
        System.out.println("Quantity: "+quantity);
        return productRepo.getProduct(id)
                .map(product -> {
                    if(product.getStock()<quantity){
                        throw new ProductNotFoundException("La cantidad en stock es inferior");
                    }
                    product.setStock(product.getStock()-quantity);
                    return product;
                })
                .flatMap(product -> productRepo.updateProduct(id, product))
                .onErrorResume(e -> Mono.error(new ShoppingCartService.CustomServiceException("Error en la venta de productos "+e)));
    }
    public Mono<Product> updatePrice(Integer id,Double price){
        return productRepo.getProduct(id)
                .map(product -> {
                    product.setPrice(price);
                    return product;
                })
                .flatMap(product -> productRepo.updateProduct(id, product));
    }
    public Mono<String> deleteProduct(Integer id){
        return productRepo.deleteProduct(id);
    }
    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }

    public static class CustomServiceException extends RuntimeException {
        public CustomServiceException(String message) {
            super(message);
        }
    }
}
