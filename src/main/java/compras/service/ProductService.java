package compras.service;

import compras.model.ProductEntity;
import compras.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Flux<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    public Mono<ProductEntity> findById(Integer id) {
        return productRepository.findById(id);
    }

    public Flux<ProductEntity> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Mono<ProductEntity> create(ProductEntity product) {
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Mono<ProductEntity> update(ProductEntity product) {
        return productRepository.findById(product.getId())
                .flatMap(existingProduct -> {
                    existingProduct.setName(product.getName());
                    existingProduct.setPrice(product.getPrice());
                    existingProduct.setDescription(product.getDescription());
                    existingProduct.setStock(product.getStock());
                    existingProduct.setUpdatedAt(LocalDateTime.now());
                    return productRepository.save(existingProduct);
                });
    }

    public Mono<Void> deleteById(Integer id) {
        return productRepository.deleteById(id);
    }
}
