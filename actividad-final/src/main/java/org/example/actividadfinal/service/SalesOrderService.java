package org.example.actividadfinal.service;

import lombok.AllArgsConstructor;
import org.example.actividadfinal.exceptions.OrderException;
import org.example.actividadfinal.exceptions.ShopException;
import org.example.actividadfinal.model.SalesOrder;
import org.example.actividadfinal.model.ResponseData;
import org.example.actividadfinal.repository.ProductRepository;
import org.example.actividadfinal.repository.SalesOrderRepository;
import org.example.actividadfinal.repository.ShopRepository;
import org.example.actividadfinal.util.CalculateTotal;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    public Mono<ResponseData> createOrEditOrder(SalesOrder salesOrder, Boolean isEdit){
        salesOrder.setDate(LocalDateTime.now());
        return shopRepository.findByIdShop(salesOrder.getFkIdShop())
            .switchIfEmpty(Mono.defer(() -> Mono.error(new ShopException("No se encontraron productos asociados a la venta"))))
            .flatMap(s -> productRepository.findById(s.getFkIdProduct())
                    .flatMap(p -> {
                        p.setStock(p.getStock() - s.getAmount());
                        return productRepository.save(p);
                    })
                    .map(p1 -> s.getAmount() * p1.getPrice())
            )
            .collectList()
            .flatMap(CalculateTotal::getTotalOfShop)
            .flatMap(total -> {
                salesOrder.setTotal(total);
                return salesOrderRepository.save(salesOrder);
            })
            .flatMap(p -> Mono.just(ResponseData.builder()
                    .data(p)
                    .message(Boolean.TRUE.equals(isEdit) ? "Orden de compra editada exitosamente" :
                            "Orden de compra creada exitosamente"  )
                    .build())
            );
    }

    public Mono<ResponseData> getOrders(){
        return salesOrderRepository.findAll()
            .switchIfEmpty(Mono.defer(() -> Mono.error(new OrderException("No encontrado ordenes de venta"))))
            .collectList()
            .flatMap(p -> Mono.just(ResponseData.builder()
                    .data(p)
                    .build()));
    }

    public Mono<ResponseData> updateState(Map<String, String> mapRequest){
        return salesOrderRepository.findById(Long.parseLong(mapRequest.get("id")))
            .switchIfEmpty(Mono.defer(() -> Mono.error(new OrderException("Orden de venta no encontrada"))))
            .flatMap(order -> {
                order.setState(mapRequest.get("state"));
                return salesOrderRepository.save(order);
            })
            .map(result -> ResponseData.builder()
                    .message("El estado de la orden de venta fue actualizado exitosamente!")
                    .data(result)
                    .build()
            );
    }


    public Mono<ResponseData> updateOrder(Long id){
        return salesOrderRepository.findById(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new OrderException("Orden de compra no encontrada"))))
            .flatMap(order -> this.createOrEditOrder(order, true));
    }

    public Mono<ResponseData> getProductsByOrder(Long id) {
        return salesOrderRepository.findById(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new OrderException("Orden de compra no encontrada"))))
            .flatMap(order -> shopRepository.findByIdShop(order.getFkIdShop())
                .flatMap(shop -> productRepository.findById(shop.getFkIdProduct()))
                .collectList())
            .map(result -> ResponseData.builder()
                .message("Productos asociados a la orden " + id)
                .data(result)
                .build());
    }

}
