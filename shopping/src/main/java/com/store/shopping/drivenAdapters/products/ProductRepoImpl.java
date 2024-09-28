package com.store.shopping.drivenAdapters.products;

import com.store.shopping.drivenAdapters.clients.ClientsRepoImpl;
import com.store.shopping.drivenAdapters.products.data.ProductDataMapper;
import com.store.shopping.entities.IProductRepository;
import com.store.shopping.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductRepoImpl implements IProductRepository {
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ProductDataMapper productDataMapper;
    @Override
    public Mono<Product> getProduct(Integer id) {
        return productRepository.findById(id)
                .map(productDataMapper::toModel);
    }

    @Override
    public Flux<Product> getProducts() {
        return productRepository.findAll()
                .map(productDataMapper::toModel);
    }

    @Override
    public Mono<Product> getProductByName(String name) {
        return productRepository.getProductByName(name)
                .map(productDataMapper::toModel);
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        return productRepository.save(productDataMapper.toRepository(product))
                .map(productDataMapper::toModel);
    }

    @Override
    public Mono<Product> updateProduct(Integer id, Product product) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("El producto no fue encontrado")))
                .flatMap(data->{
                    data.setName(product.getName());
                    data.setPrice(product.getPrice());
                    data.setStock(product.getStock());
                    return productRepository.save(data);
                })
                .map(productDataMapper::toModel)
                .onErrorResume(e -> Mono.error(
                        new ClientsRepoImpl.CustomServiceException("Error actualizando persona "+e)
                ));
    }

    @Override
    public Mono<String> deleteProduct(Integer id) {
        return productRepository.deleteById(id)
                .then(Mono.just("Producto eliminado con exito"));
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
