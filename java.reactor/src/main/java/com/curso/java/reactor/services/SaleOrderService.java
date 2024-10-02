package com.curso.java.reactor.services;

import com.curso.java.reactor.models.SaleOrder;
import com.curso.java.reactor.models.SaleOrderProduct;
import com.curso.java.reactor.models.dto.*;
import com.curso.java.reactor.repository.SaleOrderProductRepository;
import com.curso.java.reactor.repository.SaleOrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
public class SaleOrderService {
    private final SaleOrderRepository saleOrderRepository;
    private final SaleOrderProductRepository saleOrderProductRepository;
    private final ProductService productService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


    public SaleOrderService(SaleOrderRepository saleOrderRepository,
                            SaleOrderProductRepository saleOrderProductRepository, ProductService productService) {
        this.saleOrderRepository = saleOrderRepository;
        this.saleOrderProductRepository = saleOrderProductRepository;
        this.productService = productService;
    }

    public Flux<SaleOrderDTO> listSaleOrders() {
        return saleOrderRepository.findAll()
                .flatMap(saleOrder -> getSaleDetail(saleOrder.getId())
                        .map(saleDetail -> SaleOrderDTO.builder()
                                .saleOrderId((long) saleOrder.getId())
                                .state(saleOrder.getState())
                                .purchaseCart(saleDetail)
                                .totalAmount(totalAmountDTO.builder()
                                        .total(saleOrder.getTotal())
                                        .build())
                                .build()
                        )
                );
    }

    public Flux<SaleOrderDTO> listSaleOrdersBetweenDates(String initDate, String endDate) {
        LocalDateTime startDate = LocalDateTime.parse(initDate, formatter);
        LocalDateTime endDateFormated = LocalDateTime.parse(endDate, formatter);
        return saleOrderRepository.findAllByUpdatedAtBetween(startDate, endDateFormated)
                .flatMap(saleOrder -> getSaleDetail(saleOrder.getId())
                        .map(saleDetail -> SaleOrderDTO.builder()
                                .saleOrderId((long) saleOrder.getId())
                                .state(saleOrder.getState())
                                .purchaseCart(saleDetail)
                                .totalAmount(totalAmountDTO.builder()
                                        .total(saleOrder.getTotal())
                                        .build())
                                .build()
                        )
                );
    }

    public Flux<SaleOrderDTO> getTopFiveSalesBetweenDates(String initDate, String endDate) {
        LocalDateTime startDate = LocalDateTime.parse(initDate, formatter);
        LocalDateTime endDateFormated = LocalDateTime.parse(endDate, formatter);

        return saleOrderRepository.findAllByUpdatedAtBetween(startDate, endDateFormated)
                .flatMap(saleOrder -> saleOrderProductRepository.findBySaleOrderId((long) saleOrder.getId())
                        .collectList()
                        .map(products -> new ProductQuantityDTO(saleOrder.getId(), products.stream()
                                .mapToInt(SaleOrderProduct::getQuantity).sum()))
                )
                .sort(Comparator.comparingInt(ProductQuantityDTO::getQuantity).reversed())
                .take(5)
                .flatMap(quantityProductDTO ->
                        saleOrderRepository.findById((long) quantityProductDTO.getProductId())
                                .flatMap(saleOrder -> getSaleDetail(saleOrder.getId())
                                        .map(saleDetail -> SaleOrderDTO.builder()
                                                .saleOrderId((long) saleOrder.getId())
                                                .state(saleOrder.getState())
                                                .purchaseCart(saleDetail)
                                                .totalAmount(totalAmountDTO.builder()
                                                        .total(saleOrder.getTotal())
                                                        .build())
                                                .build())
                                )
                );
    }


    public Mono<SaleOrderDTO> getSaleOrderById(Long saleOrderId) {
        return saleOrderRepository.findById(saleOrderId)
                .flatMap(saleOrder -> getSaleDetail(saleOrder.getId())
                        .map(saleDetail -> SaleOrderDTO.builder()
                                .saleOrderId((long) saleOrder.getId())
                                .state(saleOrder.getState())
                                .purchaseCart(saleDetail)
                                .totalAmount(totalAmountDTO.builder()
                                        .total(saleOrder.getTotal())
                                        .build())
                                .build()
                        )
                );
    }

    public Flux<SaleOrderDTO> getSaleOrderByProduct(Long productId) {
        return saleOrderProductRepository.findByProductId(productId)
                .flatMap(saleOrderProduct -> saleOrderRepository.findById((long) saleOrderProduct.getSaleOrderId())
                        .flatMap(saleOrder -> getSaleDetail(saleOrder.getId())
                                .map(saleDetail -> SaleOrderDTO.builder()
                                        .saleOrderId((long) saleOrder.getId())
                                        .state(saleOrder.getState())
                                        .purchaseCart(saleDetail)
                                        .totalAmount(totalAmountDTO.builder()
                                                .total(saleOrder.getTotal())
                                                .build())
                                        .build()
                                )
                        )
                );
    }

    public Mono<SaleOrderDTO> setStateSaleOrder(Long saleOrderId, String state) {
        return saleOrderRepository.findById(saleOrderId)
                .flatMap(saleOrder -> {
                    saleOrder.setState(state);
                    return saleOrderRepository.save(saleOrder);
                })
                .flatMap(saleOrder -> getSaleDetail(saleOrder.getId())
                        .map(saleDetail -> SaleOrderDTO.builder()
                                .saleOrderId((long) saleOrder.getId())
                                .state(saleOrder.getState())
                                .purchaseCart(saleDetail)
                                .totalAmount(totalAmountDTO.builder()
                                        .total(saleOrder.getTotal())
                                        .build())
                                .build()
                        )
                );
    }

    public Mono<SaleDTO> createDirectSalesOrder(List<ProductQuantityDTO> products, Double shipment) {
        return checkStock(products).
                then(productService.getTotal(products, shipment))
                .flatMap(valorTotal -> createSaleOrder(valorTotal.getTotal())
                        .flatMap(saleOrder -> updateStock(products)
                                .thenMany(createSaleOrderProduct(products, saleOrder.getId()))
                                .collectList()
                                .flatMap(saleOrderProduct -> productService.mapperSaleItems(products)
                                        .map(purchasesDTO -> saleResponse(saleOrder, purchasesDTO, valorTotal))
                                )
                        )
                );
    }


    public Mono<SaleOrder> createSaleOrder(Double totalAmount) {
        SaleOrder saleOrder = SaleOrder.builder()
                .total(totalAmount)
                .state("PENDING")
                .build();
        return saleOrderRepository.save(saleOrder);
    }

    public Flux<SaleOrderProduct> createSaleOrderProduct(List<ProductQuantityDTO> products, int saleOrderId) {
        return Flux.fromIterable(products)
                .flatMap(product -> {
                    SaleOrderProduct saleOrderProduct = SaleOrderProduct.builder()
                            .saleOrderId(saleOrderId)
                            .productId(product.getProductId())
                            .quantity(product.getQuantity())
                            .build();
                    return saleOrderProductRepository.save(saleOrderProduct);
                });
    }

    public SaleDTO saleResponse(SaleOrder saleOrder,
                                PurchaseCartDTO purchaseCartDTO,
                                totalAmountDTO totalAmountDTO) {
        return SaleDTO.builder()
                .saleOrder(SaleOrderDTO.builder()
                        .saleOrderId((long) saleOrder.getId())
                        .state(saleOrder.getState())
                        .purchaseCart(purchaseCartDTO)
                        .totalAmount(totalAmountDTO)
                        .build())
                .build();
    }

    private Mono<Void> updateStock(List<ProductQuantityDTO> products) {
        return Flux.fromIterable(products)
                .flatMap(product ->
                        productService.findProductStock((long) product.getProductId(), product.getQuantity())
                                .flatMap(stock ->
                                        productService.updateStock((long) stock.getId(), stock.getStock() - product.getQuantity())
                                )
                )
                .then();
    }

    private Mono<Void> checkStock(List<ProductQuantityDTO> products) {
        return Flux.fromIterable(products)
                .flatMap(product ->
                        productService.findProductStock((long) product.getProductId(), product.getQuantity()))
                .then();
    }

    public Mono<PurchaseCartDTO> getSaleDetail(int saleOrderId) {
        return saleOrderProductRepository.findBySaleOrderId((long) saleOrderId)
                .flatMap(saleOrderProduct -> productService.findProduct((long) saleOrderProduct.getProductId())
                        .map(product -> ProductQuantityDTO.builder()
                                .productId(product.getId())
                                .quantity(saleOrderProduct.getQuantity())
                                .build())
                )
                .collectList()
                .flatMap(productService::mapperSaleItems);
    }
}
