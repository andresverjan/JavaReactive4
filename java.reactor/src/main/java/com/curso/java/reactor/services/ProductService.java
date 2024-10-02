package com.curso.java.reactor.services;

import com.curso.java.reactor.exceptions.BusinessException;
import com.curso.java.reactor.models.Product;
import com.curso.java.reactor.models.dto.*;
import com.curso.java.reactor.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<Product> getProducts() {
        return productRepository.findAll();
    }

    public Mono<Product> createProduct(Product product) {
        return productRepository.save(product)
                .doOnNext(p -> logger.info("Product created: " + p));
    }


    public Mono<Product> editProduct(Long id, Product product) {
        return productRepository.findById(id)
                .flatMap(editProduct -> {
                    editProduct.setName(product.getName());
                    editProduct.setPrice(product.getPrice());
                    editProduct.setDescription(product.getDescription());
                    editProduct.setImageUrl(product.getImageUrl());
                    editProduct.setStock(product.getStock());
                    return productRepository.save(editProduct);
                });
    }

    public Mono<Product> findProductStock(Long productId, int quantity) {
        return productRepository.findById(productId)
                .switchIfEmpty(Mono.error(new BusinessException(404, "Product not found")))
                .flatMap(product -> {
                    if (product.getStock() < quantity) {
                        logger.error("Insufficient stock for product: {}", product);
                        return Mono.error(new BusinessException(400, "Insufficient stock for the selected product. Current stock: " + product.getStock()));
                    }
                    return Mono.just(product);
                });
    }


    public Mono<Product> findProduct(Long id){
        return productRepository.findById(id);
    }

    public Flux<Product> findProductByName(String name){
        return productRepository.findByNameContaining(name);
    }

    public Mono<Void> deleteProduct(Long id){
        return productRepository.deleteById(id);
    }

    public Mono<Product> updateStock(Long id, int quantity){
        return productRepository.findById(id)
                .flatMap(product -> {
                   logger.info("Updating Stock: {} " , product.getName());
                    product.setStock(quantity);
                    return productRepository.save(product);
                });
    }

    public Mono<totalAmountDTO> getTotal(List<ProductQuantityDTO> productsDTOList, Double shipment) {
        return Flux.fromIterable(productsDTOList)
                .flatMap(product -> productRepository.findById((long)product.getProductId())
                        .map(p -> p.getPrice() * product.getQuantity())
                )
                .reduce(0.0, Double::sum)
                .map(total -> {
                    Double taxes = total * 0.17;
                    return   totalAmountDTO.builder()
                            .taxes(taxes)
                            .shipmentCost(shipment)
                            .subtotal(total)
                            .total(total + taxes + shipment).build();
                });
    }

    public Mono<CartProductDTO> getProductCart(ProductQuantityDTO productCart) {
        return productRepository.findById((long) productCart.getProductId())
                .map(product ->
                        CartProductDTO.builder()
                                .product(ProductDTO.builder()
                                        .id(product.getId())
                                        .name(product.getName())
                                        .price(product.getPrice())
                                        .description(product.getDescription())
                                        .imageUrl(product.getImageUrl())
                                        .build())
                                .quantity(productCart.getQuantity())
                                .build());
    }

    public Mono<PurchaseCartDTO> mapperSaleItems(List<ProductQuantityDTO> products) {
        return Flux.fromIterable(products)
                .flatMap(this::getProductCart)
                .collectList()
                .map(cartProductsDTO ->
                        PurchaseCartDTO.builder()
                                .items(cartProductsDTO)
                                .build());
    }

    public Mono<PurchaseOrderProductDTO> getPurchaseProductDTO(ProductQuantityDTO productQuantityDTO) {
        return productRepository.findById((long) productQuantityDTO.getProductId())
                .map(product ->
                        PurchaseOrderProductDTO.builder()
                                .product(ProductDTO.builder()
                                        .id(product.getId())
                                        .name(product.getName())
                                        .price(product.getPrice())
                                        .description(product.getDescription())
                                        .imageUrl(product.getImageUrl())
                                        .build())
                                .quantity(productQuantityDTO.getQuantity())
                                .build());

    }

    public Mono<PurchaseOrderDTO> mapItemsPurchased(List<ProductQuantityDTO> products) {
        return Flux.fromIterable(products)
                .flatMap(this::getPurchaseProductDTO)
                .collectList()
                .map(purchaseProductsDTO ->
                        PurchaseOrderDTO.builder()
                                .items(purchaseProductsDTO)
                                .build());
    }
}
