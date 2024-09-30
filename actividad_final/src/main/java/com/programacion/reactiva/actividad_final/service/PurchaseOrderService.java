package com.programacion.reactiva.actividad_final.service;

import com.programacion.reactiva.actividad_final.model.PurchaseOrder;
import com.programacion.reactiva.actividad_final.model.PurchaseOrderProduct;
import com.programacion.reactiva.actividad_final.repository.PurchaseOrderProductRepository;
import com.programacion.reactiva.actividad_final.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderProductRepository purchaseOrderProductRepository;

    public Mono<PurchaseOrder> createPurchaseOrder(List<PurchaseOrderProduct> products) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setStatus("CREATED");

        return purchaseOrderRepository.save(purchaseOrder)
                .flatMap(savedOrder -> {

                    List<Mono<PurchaseOrderProduct>> orderProductMonos = products.stream()
                            .map(product -> {
                                product.setOrderId(savedOrder.getId());
                                return purchaseOrderProductRepository.save(product);
                            })
                            .collect(Collectors.toList());


                    return Mono.zip(orderProductMonos, objects -> savedOrder)
                            .flatMap(savedOrderAfterProducts ->

                                    purchaseOrderRepository.findById((long)savedOrderAfterProducts.getId())
                            );
                });
    }


    public Mono<PurchaseOrder> updatePurchaseOrder(Long orderId, List<PurchaseOrderProduct> products) {
        return purchaseOrderRepository.findById(orderId)
                .flatMap(existingOrder -> {
                    existingOrder.setStatus("UPDATED");
                    return purchaseOrderRepository.save(existingOrder)
                            .then(purchaseOrderProductRepository.findAllByOrderId(orderId)
                                    .flatMap(orderProduct ->
                                            purchaseOrderProductRepository.delete(orderProduct)
                                                    .then(Mono.just(orderProduct))
                                    )
                                    .then(Mono.defer(() -> {
                                        List<Mono<PurchaseOrderProduct>> orderProductMonos = products.stream()
                                                .map(product -> {
                                                    product.setOrderId(existingOrder.getId());
                                                    return purchaseOrderProductRepository.save(product);
                                                })
                                                .collect(Collectors.toList());
                                        return Mono.zip(orderProductMonos, objects -> existingOrder);
                                    }))
                            )
                            .flatMap(updatedOrder ->
                                    purchaseOrderRepository.findById((long)updatedOrder.getId())
                            );
                });
    }




    public Flux<PurchaseOrder> listPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public Mono<Void> cancelPurchaseOrder(Long orderId) {
        return purchaseOrderRepository.findById(orderId)
                .flatMap(order -> {
                    order.setStatus("CANCELLED");
                    return purchaseOrderRepository.save(order)
                            .then(purchaseOrderProductRepository.deleteAllByOrderId(orderId));
                });
    }
}
