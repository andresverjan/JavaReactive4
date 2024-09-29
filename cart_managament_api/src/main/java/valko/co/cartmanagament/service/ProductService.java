package valko.co.cartmanagament.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import valko.co.cartmanagament.exception.NotFoundException;
import valko.co.cartmanagament.model.products.Product;
import valko.co.cartmanagament.repository.product.ProductRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private static final String PRODUCT_NOT_FOUND = "Product not found with id: ";

    public Mono<Product> saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Mono<Product> updateProduct(Integer id, Product product) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND.concat(String.valueOf(id)))))
                .flatMap(existingProduct -> {
                    Product updatedProduct = new Product(
                            existingProduct.id(),
                            product.name(),
                            product.price(),
                            product.description(),
                            product.imageUrl(),
                            product.stock(),
                            existingProduct.createdAt(),
                            LocalDateTime.now()
                    );
                    return productRepository.save(updatedProduct);
                });
    }

    public Flux<Product> listAllProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> findProductById(Integer id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND.concat(String.valueOf(id)))));
    }

    public Flux<Product> findProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Mono<Product> updateProductStock(Integer id, int newStock) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND.concat(String.valueOf(id)))))
                .flatMap(existingProduct -> {
                    Product updatedProduct = new Product(
                            existingProduct.id(),
                            existingProduct.name(),
                            existingProduct.price(),
                            existingProduct.description(),
                            existingProduct.imageUrl(),
                            newStock,
                            existingProduct.createdAt(),
                            LocalDateTime.now()
                    );
                    return productRepository.save(updatedProduct);
                });
    }

    public Mono<Void> deleteProductById(Integer id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(PRODUCT_NOT_FOUND.concat(String.valueOf(id)))))
                .flatMap(productRepository::delete);
    }

}
