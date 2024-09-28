package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.PurchaseOrderDetailEntity;
import com.angel.react.api.shop.model.PurchaseOrderEntity;
import com.angel.react.api.shop.model.PurchaseOrderFull;
import com.angel.react.api.shop.repository.PurchaseOrderDetailRepository;
import com.angel.react.api.shop.repository.PurchaseOrderRepository;
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
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@AllArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    public Flux<PurchaseOrderEntity> getAll(){
        return purchaseOrderRepository.findAll();
    }

    public Flux<PurchaseOrderEntity> getByDates(String dateInit, String dateEnd) throws ParseException {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateI = formater.parse(dateInit);
        Date dateE = formater.parse(dateEnd);
        return purchaseOrderRepository.findByDateBetween(dateI, dateE);
    }

    public Mono<PurchaseOrderFull> getDetailsByReference(String reference){
       Mono<PurchaseOrderEntity> purchaseOrderEntityMono =
                purchaseOrderRepository.findByReference(reference)
                        .doOnNext(p -> log.info("Purchase order getted, reference: {}", reference))
                        .switchIfEmpty(Mono.defer(() -> {
                            log.error("Purchase order REFERENCE {} not found", reference);
                            return Mono.empty();
                        }));

        Flux<PurchaseOrderDetailEntity> purchaseOrderDetailEntityFlux =
                purchaseOrderDetailRepository.findByReference(reference)
                        .doOnComplete(() -> log.info("Purchase order details getted, reference: {}", reference));

        return Mono.zip(purchaseOrderEntityMono, purchaseOrderDetailEntityFlux.collectList())
                .map(tuple -> new PurchaseOrderFull(tuple.getT1(), tuple.getT2()));
    }

    public Mono<PurchaseOrderFull> create(PurchaseOrderFull order){
        return purchaseOrderRepository.save(order.getPurchaseOrderEntity())
                .flatMap(savedOrder -> {
                    log.info("Purchase order created reference {}", savedOrder.getReference());

                    //Add id purchaseOrder into purchaseOrderDetail items
                    Flux<PurchaseOrderDetailEntity> detailsToSave = Flux.fromIterable(order.getPurchaseOrderDetailEntity())
                            .map(detail -> {
                                detail.setIdPurchaseOrder(savedOrder.getId());
                                return detail;
                            });

                    Flux<PurchaseOrderDetailEntity> savedOrderDetail = purchaseOrderDetailRepository.saveAll(detailsToSave)
                            .doOnNext(p -> log.info("Item created, id: {} - purchase order [{}]", p.getId(), p.getReference()))
                            .doOnError(p -> log.info("Error creating item: [{}]", p.getMessage()));

                    //Respuesta final purchase order full
                    return Mono.zip(Mono.just(savedOrder), savedOrderDetail.collectList())
                            .map(tuple -> {
                                PurchaseOrderEntity purchaseOrderEntity = tuple.getT1();
                                List<PurchaseOrderDetailEntity> purchaseOrderDetailEntities = tuple.getT2();
                                return new PurchaseOrderFull(purchaseOrderEntity, purchaseOrderDetailEntities);
                            });
                })
                .onErrorResume(p-> {
                    log.info("Error creating purchase order reference {}", order.getPurchaseOrderEntity().getReference());
                    log.info("Error detail: {}", p.getMessage());
                    deleteByReference(order.getPurchaseOrderEntity().getReference()).subscribe();
                    return Mono.empty();
                });

    }

    //pendiente
    public Mono<PurchaseOrderEntity> update(PurchaseOrderEntity purchaseOrderEntity){
        return purchaseOrderRepository.save(purchaseOrderEntity)
                .doOnNext(p -> System.out.println("Persona actualizada, id: " + purchaseOrderEntity.getId()));
    }

    public Mono<ResponseEntity<String>> deleteByReference(String reference) {
        return purchaseOrderRepository.findByReference(reference)
                .flatMap(purchaseOrder -> purchaseOrderRepository.deleteByReference(reference)
                        .then(Mono.fromCallable(() -> {
                            log.info("Purchase order ID {} deleted", reference);
                            return ResponseEntity.ok("deleted ok");
                        }))
                )
                .doFinally(System.out::println)
                .timeout(Duration.ofSeconds(10))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Error deleting: purchase order ID {} not found", reference);
                    return Mono.just(
                            ResponseEntity
                                    .status(NOT_FOUND)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body("{\"Error\": \"refence "+ reference + " Not found\"}"));
                }))
                .doOnError(error -> log.error("Error deleting purchase order ID {}: {}", reference, error.getMessage()));
    }
}
