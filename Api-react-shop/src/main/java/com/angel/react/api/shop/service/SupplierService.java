package com.angel.react.api.shop.service;

import com.angel.react.api.shop.model.SupplierEntity;
import com.angel.react.api.shop.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@AllArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public Flux<SupplierEntity> getAll() {
        return supplierRepository.findAll()
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("No suppliers found")))
                .doOnComplete(() -> log.info("Find all suppliers success"))
                .doOnError(error -> log.error("Error finding all suppliers: {}", error.getMessage()));
    }

    public Mono<SupplierEntity> getById(Long id) {
        return supplierRepository.findById(id)
                .switchIfEmpty(Mono.fromRunnable(() -> log.info("Supplier not found, id: {}", id)))
                .doOnNext(p -> log.info("Supplier found, id: {}", id))
                .doOnError(error -> log.error("Error finding the supplier ID {}: {}", id, error.getMessage()));
    }

    public Mono<SupplierEntity> create(SupplierEntity supplier) {
        return supplierRepository.save(supplier)
                .doOnNext(p -> log.info("Supplier created, id: {}", supplier.getId()))
                .onErrorResume(error -> {
                            log.error("Error creating the supplier NAME {}: {}", supplier.getName(), error.getMessage());
                            return Mono.empty();
                        }
                );
    }

    public Mono<SupplierEntity> update(SupplierEntity supplier) {
        return supplierRepository.save(supplier)
                .doOnNext(p -> log.info("Supplier update, id: {}", supplier.getId()))
                .doOnError(error -> log.error("Error updating the supplier ID {}: {}", supplier.getId(), error.getMessage()));
    }

    public Mono<ResponseEntity<String>> deleteById(Long id) {
        return supplierRepository.findById(id)
                .flatMap(item -> supplierRepository.deleteById(id)
                        .then(Mono.fromCallable(() -> {
                            log.info("Supplier [{}] deleted", item.getName());
                            return ResponseEntity.ok("deleted ok");
                        }))
                )
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Supplier ID {} not found, error deleting", id);
                    return Mono.just(ResponseEntity.notFound().build());
                }))
                .doOnError(error -> log.error("Error deleting supplier ID {}: {}", id, error.getMessage()));
    }
}
