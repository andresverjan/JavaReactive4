package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.*;
import com.angel.react.api.shop.repository.CartRepository;
import com.angel.react.api.shop.repository.SalesOrderDetailRepository;
import com.angel.react.api.shop.repository.SalesOrderRepository;
import com.angel.react.api.shop.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final SalesOrderDetailRepository salesOrderDetailRepository;
    private final StockRepository stockRepository;
    private final CartRepository cartRepository;

    public Flux<SalesOrderEntity> getAll() {
        return salesOrderRepository.findAll()
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("No sales orders found")))
                .doOnComplete(() -> log.info("Find all sales orders success"))
                .doOnError(error -> log.error("Error finding all sales orders: {}", error.getMessage()));
    }

    public Flux<SalesOrderEntity> getByDates(String dateInit, String dateEnd) throws ParseException {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateI = formater.parse(dateInit);
        Date dateE = formater.parse(dateEnd);
        return salesOrderRepository.findByDateBetween(dateI, dateE)
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("No sales orders found")))
                .doOnComplete(() -> log.info("Find sales orders success between dates [{} - {}]", dateInit, dateEnd))
                .doOnError(error -> log.error("Error finding sales orders: {}", error.getMessage()));
    }

    public Mono<SalesOrderFull> getDetailsByReference(String reference) {
        Mono<SalesOrderEntity> salesOrderEntityMono =
                salesOrderRepository.findByReference(reference)
                        .doOnNext(p -> log.info("Sale order found, reference: {}", reference))
                        .switchIfEmpty(Mono.defer(() -> {
                            log.error("Sale order REFERENCE {} not found", reference);
                            return Mono.empty();
                        }));

        Flux<SalesOrderDetailEntity> salesOrderDetailEntityFlux =
                salesOrderDetailRepository.findByReference(reference)
                        .doOnComplete(() -> log.info("Sale order details found, reference: {}", reference))
                        .doOnError(error -> log.error("Error finding sale order details: {}", error.getMessage()));

        return Mono.zip(salesOrderEntityMono, salesOrderDetailEntityFlux.collectList())
                .map(tuple -> new SalesOrderFull(tuple.getT1(), tuple.getT2()));
    }

    public Mono<SalesOrderFull> create(SalesOrderFull sale) {
        List<String> errors = new ArrayList<>();

        return Flux.fromIterable(sale.getSalesOrderDetailEntities())
                .flatMap(item -> validateStock(item)
                        .onErrorResume(error -> {
                            // Guardar el error en la lista y registrar log
                            errors.add("Error :" + error.getMessage());
                            return Mono.empty();  // Detener el flujo para este item, pero continuar con el resto
                        })
                )
                .collectList()  // Recoger la lista de validaciones (solo los exitosos)
                .flatMap(validationResults -> {
                    if (!errors.isEmpty()) {
                        // Si hay errores de stock, registrar los errores y detener el flujo
                        log.error("Stock validation failed. Errors: {}", errors);
                        return Mono.empty();
                    }

                    // Si la validación fue exitosa, proceder a guardar la venta
                    return salesOrderRepository.save(sale.getSalesOrderEntity())
                            .doOnSuccess(savedOrder -> log.info("Sale order created with reference {}", savedOrder.getReference()))
                            .flatMap(savedOrder -> {
                                // Guardar los detalles de la orden de venta
                                sale.getSalesOrderDetailEntities().forEach(item -> {
                                    item.setReference(savedOrder.getReference());
                                    item.setIdSalesOrder(savedOrder.getId());
                                });

                                return salesOrderDetailRepository.saveAll(sale.getSalesOrderDetailEntities())
                                        .collectList()  // Recoger los detalles guardados
                                        .doOnNext(savedDetails -> log.info("Sales order details saved for reference {}", savedOrder.getReference()))
                                        .flatMap(savedDetails -> {
                                            // Eliminar el carrito del cliente
                                            return cartRepository.deleteByIdClient(sale.getSalesOrderEntity().getIdClient())
                                                    .doOnSuccess(unused -> log.info("Cart deleted for client: {}", savedOrder.getNameClient()))
                                                    .doOnError(error -> log.error("Error deleting cart for client: {}", savedOrder.getNameClient()))
                                                    .then(Mono.just(new SalesOrderFull(savedOrder, savedDetails)))
                                                    .flatMap(orderFull -> {
                                                        // Si todo fue exitoso, actualizar el stock
                                                        return Flux.fromIterable(sale.getSalesOrderDetailEntities())
                                                                .flatMap(item -> stockRepository.findStockByIdProduct(item.getIdProduct())
                                                                        .flatMap(stockSummary -> {
                                                                            // Actualizar el stock
                                                                            return stockRepository.save(
                                                                                    StockEntity.builder()
                                                                                            .idProduct(item.getIdProduct())
                                                                                            .quantity(item.getQuantity() * -1)  // Disminuir el stock
                                                                                            .type("sale")
                                                                                            .reference(sale.getSalesOrderEntity().getReference())
                                                                                            .idSalesOrder(sale.getSalesOrderEntity().getId())
                                                                                            .build()
                                                                            ).doOnNext(p -> log.info("Stock updated, product: {}", item.getNameProduct()));
                                                                        })
                                                                )
                                                                .then(Mono.just(orderFull));  // Devolver la orden completa
                                                    });
                                        })
                                        .onErrorResume(error -> {
                                            // Si falla al guardar los detalles, eliminar la orden de venta
                                            log.error("Failed to save sales order details. Deleting sale order reference {}", savedOrder.getReference());
                                            return salesOrderRepository.delete(savedOrder)
                                                    .then(Mono.error(new RuntimeException("Failed to save sales order details, sale order deleted.")));
                                        });
                            });
                })
                .onErrorResume(error -> {
                    log.error("Error processing sale: {}", error.getMessage());
                    return Mono.empty();
                });
    }

    private Mono<Void> validateStock(SalesOrderDetailEntity detail) {
        return stockRepository.findStockByIdProduct(detail.getIdProduct())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Not found in stock, product id: " + detail.getIdProduct())))
                .flatMap(stockSummary -> {
                    if (stockSummary.getStock() >= detail.getQuantity()) {
                        // El producto tiene suficiente stock, continuar
                        return Mono.empty();
                    } else {
                        // No hay suficiente stock, retornar un error
                        return Mono.error(new IllegalArgumentException("Insufficient stock for product id: " + detail.getIdProduct()));
                    }
                });
    }

    public Mono<ResponseEntity<String>> deleteByReference(String reference) {
        return salesOrderRepository.findByReference(reference)
                .flatMap(salesOrder -> salesOrderRepository.deleteByReference(reference)
                        .then(Mono.fromCallable(() -> {
                            log.info("Sale order ID {} deleted", reference);
                            return ResponseEntity.ok("deleted ok");
                        }))
                )
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Error deleting: sale order ID {} not found", reference);
                    return Mono.just(ResponseEntity.notFound().build());
                }))
                .doOnError(error -> log.error("Error deleting sale order ID {}: {}", reference, error.getMessage()));
    }
}
