package org.example.actividadfinal.service;

import lombok.AllArgsConstructor;
import org.example.actividadfinal.exceptions.ShopException;
import org.example.actividadfinal.model.PurchaseOrder;
import org.example.actividadfinal.model.ResponseData;
import org.example.actividadfinal.repository.ProductRepository;
import org.example.actividadfinal.repository.PurchaseOrderRepository;
import org.example.actividadfinal.repository.ShopRepository;
import org.example.actividadfinal.util.CalculateTotal;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    public Mono<ResponseData> createOrder(PurchaseOrder purchaseOrder){
        purchaseOrder.setDate(LocalDateTime.now());
        return  shopRepository.findByIdShop(purchaseOrder.getFkIdShop())
            .switchIfEmpty(Mono.defer(() -> Mono.error(new ShopException("No se encontraron productos asociados a la compra"))))
            .flatMap(s -> productRepository.findById(s.getFkIdProduct())
                    .map(p -> p.getPrice() * s.getAmount().doubleValue())
            )
            .collectList()
            .flatMap(CalculateTotal::getTotalOfShop)
            .flatMap(total -> {
                purchaseOrder.setTotal(total);
                return purchaseOrderRepository.save(purchaseOrder);
            })
            .flatMap(p -> Mono.just(ResponseData.builder()
                    .data(p)
                    .message("Orden de compra creada exitosamente")
                    .build())
            );
    }

}
