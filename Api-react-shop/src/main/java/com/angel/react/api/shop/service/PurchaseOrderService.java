package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.PurchaseOrderDetailEntity;
import com.angel.react.api.shop.model.PurchaseOrderEntity;
import com.angel.react.api.shop.model.PurchaseOrderFull;
import com.angel.react.api.shop.repository.PurchaseOrderDetailRepository;
import com.angel.react.api.shop.repository.PurchaseOrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    public Flux<PurchaseOrderEntity> getAll(){
        return purchaseOrderRepository.findAll();
    }

    public Mono<PurchaseOrderEntity> getById(Long id){
        if(id == null){
            return Mono.empty();
        }

        return purchaseOrderRepository.findById(id)
                .doOnNext(p -> log.info("Purchase order getted, id: {}", id));
    }

    public Flux<PurchaseOrderEntity> getByDates(String dateInit, String dateEnd) throws ParseException {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateI = formater.parse(dateInit);
        Date dateE = formater.parse(dateEnd);
        return purchaseOrderRepository.findByDateBetween(dateI, dateE);
    }

    public Mono<PurchaseOrderFull> getDetailsByReference(String reference){
        if(reference == null){
            return Mono.empty();
        }

        Mono<PurchaseOrderEntity> purchaseOrderEntityMono =
                purchaseOrderRepository.findByReference(reference)
                        .doOnNext(p -> log.info("Purchase order getted, reference: {}", reference));

        Flux<PurchaseOrderDetailEntity> purchaseOrderDetailEntityFlux =
                purchaseOrderDetailRepository.findByReference(reference)
                        .doOnNext(p -> log.info("Purchase order getted, reference: {}", reference));

        return Mono.zip(purchaseOrderEntityMono, purchaseOrderDetailEntityFlux.collectList())
                .map(tuple -> {
                    PurchaseOrderEntity purchaseOrderEntity = tuple.getT1();
                    List<PurchaseOrderDetailEntity> purchaseOrderDetailEntities = tuple.getT2();

                    return new PurchaseOrderFull(purchaseOrderEntity, purchaseOrderDetailEntities);
                    });
    }

    public Mono<PurchaseOrderFull> create(PurchaseOrderFull order){
        Mono<PurchaseOrderEntity> purchaseOrderEntityMono =
                purchaseOrderRepository.save(order.getPurchaseOrderEntity())
                        .doOnNext(p -> log.info("Purchase order created reference {}", p.getReference()))
                        .onErrorResume(p-> {
                            log.info("Error creating purchase order reference {}", order.getPurchaseOrderEntity().getReference());
                            return Mono.empty();
                        });

        if(purchaseOrderEntityMono.equals(Mono.empty())){
            return Mono.empty();
        }
        else {
            Flux<PurchaseOrderDetailEntity> purchaseOrderDetailEntityFlux =
                    purchaseOrderDetailRepository.saveAll(order.getPurchaseOrderDetailEntity())
                            .doOnNext(p -> log.info("Item created, id: {} - order [{}]", p.getId(), p.getReference()))
                            .onErrorResume(p -> {
                                purchaseOrderRepository.deleteById(purchaseOrderEntityMono.map(PurchaseOrderEntity::getId));
                                return Flux.empty();
                            });

            return Mono.zip(purchaseOrderEntityMono, purchaseOrderDetailEntityFlux.collectList())
                    .map(tuple -> {
                        PurchaseOrderEntity purchaseOrderEntity = tuple.getT1();
                        List<PurchaseOrderDetailEntity> purchaseOrderDetailEntities = tuple.getT2();
                        return new PurchaseOrderFull(purchaseOrderEntity, purchaseOrderDetailEntities);
                    });
        }
    }

    public Mono<PurchaseOrderEntity> update(PurchaseOrderEntity person){
        return purchaseOrderRepository.save(person)
                .doOnNext(p -> System.out.println("Persona actualizada, id: " + person.getId()));
    }

    public Mono<ResponseEntity<String>> deleteById(Long id) {
        if(id == null){
            return Mono.empty();
        }
        return purchaseOrderRepository.findById(id)
                .flatMap(persona -> purchaseOrderRepository.deleteById(id)
                        .then(Mono.fromCallable(() -> {
                            log.info("Purchase order ID {} deleted", id);
                            return ResponseEntity.ok("deleted ok");
                        }))
                )
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Error deleting: purchase order ID {} not found", id);
                    return Mono.just(ResponseEntity.notFound().build());
                }))
                .doOnError(error -> log.error("Error deleting purchase order ID {}: {}", id, error.getMessage()));
    }
}
