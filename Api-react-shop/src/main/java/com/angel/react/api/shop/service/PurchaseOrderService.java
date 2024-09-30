package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.PurchaseOrderDetailEntity;
import com.angel.react.api.shop.model.PurchaseOrderEntity;
import com.angel.react.api.shop.model.PurchaseOrderFull;
import com.angel.react.api.shop.model.StockEntity;
import com.angel.react.api.shop.repository.PurchaseOrderDetailRepository;
import com.angel.react.api.shop.repository.PurchaseOrderRepository;
import com.angel.react.api.shop.repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderDetailRepository purchaseOrderDetailRepository;
    private final StockRepository stockRepository;

    public Flux<PurchaseOrderEntity> getAll() {
        return purchaseOrderRepository.findAll()
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("No purchase orders found")))
                .doOnComplete(() -> log.info("Find all purchase orders success"))
                .doOnError(error -> log.error("Error finding all purchase orders: {}", error.getMessage()));
    }

    public Flux<PurchaseOrderEntity> getByDates(String dateInit, String dateEnd) throws ParseException {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateI = formater.parse(dateInit);
        Date dateE = formater.parse(dateEnd);
        return purchaseOrderRepository.findByDateBetween(dateI, dateE)
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("No purchase orders found")))
                .doOnComplete(() -> log.info("Find purchase orders success between dates [{} - {}]", dateInit, dateEnd))
                .doOnError(error -> log.error("Error finding purchase orders: {}", error.getMessage()));
    }

    public Mono<PurchaseOrderFull> getDetailsByReference(String reference) {
        Mono<PurchaseOrderEntity> purchaseOrderEntityMono =
                purchaseOrderRepository.findByReference(reference)
                        .doOnNext(p -> log.info("Purchase order found, reference: {}", reference))
                        .switchIfEmpty(Mono.defer(() -> {
                            log.error("Purchase order REFERENCE {} not found", reference);
                            return Mono.empty();
                        }));

        Flux<PurchaseOrderDetailEntity> purchaseOrderDetailEntityFlux =
                purchaseOrderDetailRepository.findByReference(reference)
                        .doOnComplete(() -> log.info("Purchase order details found, reference: {}", reference))
                        .doOnError(error -> log.error("Error finding purchase order details: {}", error.getMessage()));

        return Mono.zip(purchaseOrderEntityMono, purchaseOrderDetailEntityFlux.collectList())
                .map(tuple -> new PurchaseOrderFull(tuple.getT1(), tuple.getT2()));
    }

    public Mono<PurchaseOrderFull> create(PurchaseOrderFull order) {
        return purchaseOrderRepository.save(order.getPurchaseOrderEntity())
                .flatMap(savedOrder -> {
                    log.info("Purchase order created with reference {}", savedOrder.getReference());

                    // Agregar id de purchaseOrder a los detalles de la orden de compra
                    Flux<PurchaseOrderDetailEntity> detailsToSave = Flux.fromIterable(order.getPurchaseOrderDetailEntity())
                            .map(detail -> {
                                detail.setIdPurchaseOrder(savedOrder.getId());
                                return detail;
                            });

                    // Guardar los detalles de la orden de compra
                    return purchaseOrderDetailRepository.saveAll(detailsToSave)
                            .collectList()  // Recoger los detalles guardados
                            .doOnNext(savedDetails -> log.info("Purchase order details saved for reference {}", savedOrder.getReference()))
                            .flatMap(savedDetails -> {
                                // Actualizar el stock solo si el guardado de detalles fue exitoso
                                return updateStock(savedDetails, savedOrder)
                                        .then(Mono.just(new PurchaseOrderFull(savedOrder, savedDetails))); // Devolver la orden completa
                            })
                            .onErrorResume(error -> {
                                // Si falla al guardar los detalles, eliminar la orden de compra
                                log.error("Failed to save purchase order details. Deleting purchase order reference {}", savedOrder.getReference());
                                return purchaseOrderRepository.delete(savedOrder)
                                        .then(Mono.error(new RuntimeException("Failed to save purchase order details, purchase order deleted.")));
                            });
                })
                .onErrorResume(error -> {
                    log.error("Error processing purchase: {}", error.getMessage());
                    return Mono.empty();
                });
    }

    private Mono<Void> updateStock(List<PurchaseOrderDetailEntity> details, PurchaseOrderEntity order) {
        return Flux.fromIterable(details)
                .flatMap(item -> {
                    return stockRepository.save(
                            StockEntity.builder()
                                    .idProduct(item.getIdProduct())
                                    .quantity(item.getQuantity())
                                    .type("purchase")
                                    .reference(order.getReference())
                                    .idPurchaseOrder(order.getId())
                                    .build()
                    ).doOnNext(p -> log.info("Stock updated, product: {}", item.getNameProduct()));
                })
                .then();
    }

    public Mono<ResponseEntity<String>> deleteByReference(String reference) {
        return purchaseOrderRepository.findByReference(reference)
                .flatMap(purchaseOrder -> purchaseOrderRepository.deleteByReference(reference)
                        .then(Mono.fromCallable(() -> {
                            log.info("Purchase order ID {} deleted", reference);
                            return ResponseEntity.ok("deleted ok");
                        }))
                )
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Error deleting: purchase order ID {} not found", reference);
                    return Mono.just(ResponseEntity.notFound().build());
                }))
                .doOnError(error -> log.error("Error deleting purchase order ID {}: {}", reference, error.getMessage()));
    }
}
