package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.*;
import com.angel.react.api.shop.repository.SalesOrderDetailRepository;
import com.angel.react.api.shop.repository.SalesOrderRepository;
import com.angel.react.api.shop.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final SalesOrderDetailRepository salesOrderDetailRepository;
    private final StockRepository stockRepository;

    public Flux<SalesOrderEntity> getAll() {
        return salesOrderRepository.findAll();
    }

    public Flux<SalesOrderEntity> getByDates(String dateInit, String dateEnd) throws ParseException {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateI = formater.parse(dateInit);
        Date dateE = formater.parse(dateEnd);
        return salesOrderRepository.findByDateBetween(dateI, dateE);
    }

    public Mono<SalesOrderFull> getDetailsByReference(String reference) {
        Mono<SalesOrderEntity> salesOrderEntityMono =
                salesOrderRepository.findByReference(reference)
                        .doOnNext(p -> log.info("Sale order getted, reference: {}", reference))
                        .switchIfEmpty(Mono.defer(() -> {
                            log.error("Sale order REFERENCE {} not found", reference);
                            return Mono.empty();
                        }));

        Flux<SalesOrderDetailEntity> salesOrderDetailEntityFlux =
                salesOrderDetailRepository.findByReference(reference)
                        .doOnComplete(() -> log.info("Sale order details getted, reference: {}", reference));

        return Mono.zip(salesOrderEntityMono, salesOrderDetailEntityFlux.collectList())
                .map(tuple -> new SalesOrderFull(tuple.getT1(), tuple.getT2()));
    }

    private Mono<StockEntity> checkStock(SalesOrderDetailEntity detail, SalesOrderEntity savedOrder) {
        return stockRepository.findStockByIdProduct(detail.getIdProduct())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Not found in stock, product id: " + detail.getIdProduct())))
                .flatMap(stockSummary -> {
                    if (stockSummary.getStock() >= detail.getQuantity()) {
                        // Hay suficiente stock, actualizar el stock
                        return stockRepository.save(StockEntity.builder()
                                        .idProduct(detail.getIdProduct())
                                        .quantity(detail.getQuantity() * -1)
                                        .type("sale")
                                        .reference(savedOrder.getReference())
                                        .idSalesOrder(savedOrder.getId())
                                        .build()
                                )
                                .doOnNext(p -> log.info("Stock updated, product: {}, id:{}", detail.getNameProduct(), p.getId()));
                    } else {
                        // No hay suficiente stock, retornar un error
                        return Mono.error(new IllegalArgumentException("Insufficient stock for product id: " + detail.getIdProduct()));
                    }
                });
    }


    public Mono<SalesOrderFull> create(SalesOrderFull sale) {
        List<String> errors = new ArrayList<>();
        return salesOrderRepository.save(sale.getSalesOrderEntity())
                .flatMap(savedOrder -> {
                    log.info("Sale order created reference {}", savedOrder.getReference());

                    // Crear un flujo de detalles para verificar el stock
                    Flux<SalesOrderDetailEntity> detailsToSave = Flux.fromIterable(sale.getSalesOrderDetailEntities())
                            .flatMap(item -> checkStock(item, savedOrder)// Validar stock
                                    .map(stock -> {
                                        // Hay stock suficiente, agregar la referencia de la orden de venta
                                        item.setReference(savedOrder.getReference());
                                        item.setIdSalesOrder(savedOrder.getId());
                                        return item;
                                    })
                                    .doOnError(error -> {
                                        errors.add("Error for product id: " + error.getMessage());
                                    })
                            )
                            .onErrorContinue((error, obj) -> {
                            })
                            .doFinally(signalType -> {
                                // Log de todos los errores al final del procesamiento
                                if (!errors.isEmpty()) {
                                    log.error("The following errors occurred during stock processing: ");
                                    errors.forEach(log::error);
                                } else {
                                    log.info("No errors occurred during stock processing.");
                                }
                            });

                    // Guardar los detalles de la orden si todo tiene stock
                    Flux<SalesOrderDetailEntity> savedOrderDetail = detailsToSave
                            .collectList()
                            .flatMapMany(salesOrderDetailRepository::saveAll)
                            .doOnNext(p -> log.info("Item created, id: {} - sale order [{}]", p.getId(), p.getReference()))
                            .doOnError(p -> log.info("Error creating item: [{}]", p.getMessage()));

                    // Respuesta final de la orden completa
                    return Mono.zip(Mono.just(savedOrder), savedOrderDetail.collectList())
                            .map(tuple -> new SalesOrderFull(tuple.getT1(), tuple.getT2()));
                })
                .onErrorResume(error -> {
                    log.info("Error detail: {}", error.getMessage());
                    if(!error.getMessage().contains("sales_order_reference_unique"))
                        deleteByReference(sale.getSalesOrderEntity().getReference()).subscribe();
                    return Mono.empty();
                })
                .doFinally(p -> {
                    //errores de stock
                    if(!errors.isEmpty()) {
                        log.info("Error creating sale order reference {}", sale.getSalesOrderEntity().getReference());
                        deleteByReference(sale.getSalesOrderEntity().getReference()).subscribe();
                    }
                });
    }

    public Mono<ResponseEntity<String>> deleteByReference(String reference) {
        return salesOrderRepository.findByReference(reference)
                .flatMap(salesOrder -> salesOrderRepository.deleteByReference(reference)
                        .then(Mono.fromCallable(() -> {
                            log.info("Sale order ID {} deleted", reference);
                            return ResponseEntity.ok("Sale deleted ok");
                        }))
                )
                .doFinally(System.out::println)
                .timeout(Duration.ofSeconds(10))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Error deleting: sale order ID {} not found", reference);
                    return Mono.just(
                            ResponseEntity
                                    .status(NOT_FOUND)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body("{\"Error\": \"refence " + reference + " Not found\"}"));
                }))
                .doOnError(error -> log.error("Error deleting sale order ID {}: {}", reference, error.getMessage()));
    }
}
