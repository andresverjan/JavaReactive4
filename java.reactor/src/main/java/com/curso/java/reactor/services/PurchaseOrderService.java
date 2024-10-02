package com.curso.java.reactor.services;

import com.curso.java.reactor.models.PurchaseOrder;
import com.curso.java.reactor.models.PurchaseOrderProduct;
import com.curso.java.reactor.models.dto.PurchaseOrderDTO;
import com.curso.java.reactor.models.dto.ProductQuantityDTO;
import com.curso.java.reactor.repository.PurchaseOrderProductRepository;
import com.curso.java.reactor.repository.PurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PurchaseOrderService {
    private  final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderProductRepository purchaseOrderProductRepository;
    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);


    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository,
                           PurchaseOrderProductRepository purchaseOrderProductRepository,
                           ProductService productService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderProductRepository = purchaseOrderProductRepository;
        this.productService = productService;
    }

    public Flux<PurchaseOrderDTO> listPurchaseOrders(){
        return purchaseOrderRepository.findAll()
                .flatMap(purchaseOrder -> getPurchaseDetail((long) purchaseOrder.getId())
                        .map(purchaseDetail -> PurchaseOrderDTO.builder()
                                .purchaseId((long) purchaseOrder.getId())
                                .state(purchaseOrder.getState())
                                .items(purchaseDetail.getItems())
                                .total(purchaseOrder.getTotal())
                                .build()
                        )
                );
    }

    public Flux<PurchaseOrderDTO> getPurchaseOrdersBetweenDates(String initDate, String endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(initDate, formatter);
        LocalDateTime endDateFormatted = LocalDateTime.parse(endDate, formatter);
        return purchaseOrderRepository.findAllByUpdatedAtBetween(startDate, endDateFormatted)
                .flatMap(purchaseOrder -> getPurchaseDetail((long) purchaseOrder.getId())
                        .map(purchaseDetail -> PurchaseOrderDTO.builder()
                                .purchaseId((long) purchaseOrder.getId())
                                .state(purchaseOrder.getState())
                                .items(purchaseDetail.getItems())
                                .total(purchaseOrder.getTotal())
                                .build()
                        )
                );
    }

    public Mono<PurchaseOrderDTO> registerPurchaseOrder(List<ProductQuantityDTO> products, Double total) {
        logger.info("Registering purchase order with products: {} and total: {}", products, total);
        return createPurchaseOrder(total)
                .doOnNext(purchaseOrder -> logger.info("Purchase order created: {}", purchaseOrder))
                .flatMap(purchaseOrder ->
                        createPurchaseProductOrder(products, purchaseOrder.getId())
                                .collectList()
                                .doOnNext(purchaseProductsOrder -> logger.info("Purchase order products created: {}", purchaseProductsOrder))
                                .flatMap(purchaseProductsOrder ->
                                        updateStockProducts(products).then(
                                                productService.mapItemsPurchased(products)
                                                        .doOnNext(response -> logger.info("Items mapped: {}", response))
                                                        .map(response -> PurchaseOrderDTO.builder()
                                                                .purchaseId((long) purchaseOrder.getId())
                                                                .items(response.getItems())
                                                                .state(purchaseOrder.getState())
                                                                .total(purchaseOrder.getTotal())
                                                                .build()
                                                        )
                                        )
                                )
                );
    }


    private Mono<PurchaseOrder> createPurchaseOrder(Double total) {
        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .total(total)
                .state("SUCCESS")
                .build();
        return purchaseOrderRepository.save(purchaseOrder)
                .doOnSuccess(savedPurchaseOrder -> logger.info("Purchase order saved in the database: {}", savedPurchaseOrder))
                .doOnError(error -> logger.error("Error saving the purchase order: ", error));
    }

    private Flux<PurchaseOrderProduct> createPurchaseProductOrder(List<ProductQuantityDTO> products, int purchaseOrderId){
        return Flux.fromIterable(products)
                .flatMap(product -> {
                    PurchaseOrderProduct purchaseOrderProduct = PurchaseOrderProduct.builder()
                            .purchaseOrderId(purchaseOrderId)
                            .productId(product.getProductId())
                            .quantity(product.getQuantity())
                            .build();
                    return purchaseOrderProductRepository.save(purchaseOrderProduct);
                });
    }

    public Mono<Void> updateStockProducts(List<ProductQuantityDTO> products) {
    return Flux.fromIterable(products)
            .flatMap(product -> productService
                    .findProductStock((long)product.getProductId(), - product.getQuantity())
                    .flatMap(productStock -> {
                        int newStock = productStock.getStock() + product.getQuantity();
                        return productService.updateStock((long) productStock.getId(), newStock);
                    })
            )
            .then();
}

    public Mono<PurchaseOrderDTO> getPurchaseDetail(Long purchaseOrderId){
        return purchaseOrderProductRepository.findByPurchaseOrderId(purchaseOrderId)
                .flatMap(purchaseOrderProduct ->
                        productService.findProduct((long) purchaseOrderProduct.getProductId())
                        .map(product -> ProductQuantityDTO.builder()
                                .productId(product.getId())
                                .quantity(purchaseOrderProduct.getQuantity())
                                .build())
                )
                .collectList()
                .flatMap(productService::mapItemsPurchased);
    }




}
