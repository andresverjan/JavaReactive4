package valko.co.cartmanagament.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import valko.co.cartmanagament.exception.NotFoundException;
import valko.co.cartmanagament.model.buy.PurchaseOrder;
import valko.co.cartmanagament.model.products.Product;
import valko.co.cartmanagament.repository.product.ProductRepository;
import valko.co.cartmanagament.repository.purchaseorder.PurchaseOrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final ProductRepository productRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public Mono<PurchaseOrder> createPurchaseOrder(List<Integer> productIds) {
        return productRepository.findAllById(productIds)
                .collectList()
                .flatMap(products -> {
                    if (products.isEmpty()) {
                        return Mono.error(new IllegalArgumentException("No valid products found for the given IDs"));
                    }

                    double total = calculateTotal(products);

                    PurchaseOrder order = PurchaseOrder.builder()
                            .products(products)
                            .creationDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .total(total)
                            .build();

                    return updateStockAfterPurchase(products)
                            .then(purchaseOrderRepository.save(order));
                });
    }

    public Mono<PurchaseOrder> updatePurchaseOrder(int id, List<Integer> newProductIds) {
        return purchaseOrderRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Order with ID " + id + " not found")))
                .flatMap(existingOrder -> productRepository.findAllById(newProductIds)
                        .collectList()
                        .flatMap(newProducts -> {

                            if (newProducts.isEmpty()) {
                                return Mono.error(new IllegalArgumentException("No valid products found for the given IDs"));
                            }

                            double newTotal = calculateTotal(newProducts);
                            PurchaseOrder updatedOrder = existingOrder.toBuilder()
                                    .products(newProducts)
                                    .updatedDate(LocalDateTime.now())
                                    .total(newTotal)
                                    .build();

                            return updateStockAfterPurchase(newProducts)
                                    .then(purchaseOrderRepository.save(updatedOrder));
                        }));
    }

    public Mono<Void> cancelPurchaseOrder(int id) {
        return purchaseOrderRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Order with ID " + id + " not found")))
                .flatMap(order -> revertStock(order.products())
                        .then(purchaseOrderRepository.delete(order)));
    }

    public Flux<PurchaseOrder> listAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    private double calculateTotal(List<Product> products) {
        return products.stream()
                .mapToDouble(Product::price)
                .sum();
    }

    private Mono<Void> updateStockAfterPurchase(List<Product> products) {
        return Flux.fromIterable(products)
                .flatMap(product -> {
                    Product updatedProduct = product.toBuilder()
                            .stock(product.stock() - 1)
                            .build();
                    return productRepository.save(updatedProduct);
                })
                .then();
    }

    private Mono<Void> revertStock(List<Product> products) {
        return Flux.fromIterable(products)
                .flatMap(product -> {
                    Product updatedProduct = product.toBuilder()
                            .stock(product.stock() + 1)
                            .build();
                    return productRepository.save(updatedProduct);
                })
                .then();
    }
}
