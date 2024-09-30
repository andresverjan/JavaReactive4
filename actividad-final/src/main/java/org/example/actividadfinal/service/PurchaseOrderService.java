package org.example.actividadfinal.service;

import lombok.AllArgsConstructor;
import org.example.actividadfinal.exceptions.OrderException;
import org.example.actividadfinal.model.PurchaseOrder;
import org.example.actividadfinal.model.ResponseData;
import org.example.actividadfinal.model.Shop;
import org.example.actividadfinal.repository.ProductRepository;
import org.example.actividadfinal.repository.PurchaseOrderRepository;
import org.example.actividadfinal.repository.ShopRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;

    public Mono<ResponseData> getOrders(){
        return purchaseOrderRepository.findAll()
            .switchIfEmpty(Mono.defer(() -> Mono.error(new OrderException("No encontrado ordenes de compra"))))
            .collectList()
            .map(p -> ResponseData.builder()
                    .data(p)
                    .build());
    }

    public Mono<ResponseData> createPurchaseOrder(PurchaseOrder purchaseOrder, Boolean isEdit) {
        return shopRepository.findByIdShop(purchaseOrder.getFkIdShop())
            .flatMap(shop -> productRepository.findById(shop.getFkIdProduct())
                .flatMap(product -> {
                    product.setStock(product.getStock() +  shop.getAmount());
                    return productRepository.save(product);
                })
            ).collectList()
            .then(purchaseOrderRepository.save(purchaseOrder))
            .map(p -> ResponseData.builder()
                .data(p)
                .message(Boolean.TRUE.equals(isEdit) ? "Orden de compra editada exitosamente" :
                        "Orden de compra creada exitosamente")
                .build());

    }

    public Mono<ResponseData> updatePurchaseOrder(Long id) {
        return purchaseOrderRepository.findById(id)
            .flatMap(purchase -> this.createPurchaseOrder(purchase, true));

    }

    public Mono<ResponseData> cancelPurchaseOrder(Long id) {
        return purchaseOrderRepository.findById(id)
            .flatMap(purchase -> {
                purchase.setState("CANCELADO");
                return shopRepository.findByIdShop(purchase.getFkIdShop())
                    .flatMap(shop -> productRepository.findById(shop.getFkIdProduct())
                            .flatMap(product -> {
                                product.setStock(product.getStock() -  shop.getAmount());
                                return productRepository.save(product);
                            })
                    ).collectList()
                    .then(purchaseOrderRepository.save(purchase))
                    .map(p -> ResponseData.builder()
                            .data(p)
                            .message("Orden de compra cancelada exitosamente")
                            .build());
                }
            );

    }
}
