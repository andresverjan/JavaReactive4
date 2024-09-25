package com.example.shopping_cart.service;

import com.example.shopping_cart.model.PurchaseItem;
import com.example.shopping_cart.model.PurchaseOrder;
import com.example.shopping_cart.repository.PurchaseItemRepository;
import com.example.shopping_cart.repository.PurchaseOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class PurchaseService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final ProductService productService;

    public Mono<PurchaseOrder> createPurchaseOrder(PurchaseOrder purchaseOrder){
        //validación de data
        if (purchaseOrder.getTotal()==null||purchaseOrder.getTotal()<0){
            return Mono.error(new IllegalArgumentException("parametros no validos para orden de compra"));
        }
        purchaseOrder.setCreatedAt(LocalDateTime.now());
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public Flux<PurchaseItem> createPurchaseItem(List<PurchaseItem> purchaseItems){

        return Flux.fromIterable(purchaseItems)
                .concatMap(purchaseItem -> {
                    if (purchaseItem.getPriceUnit()==null||purchaseItem.getPriceUnit()<=0||purchaseItem.getQuantity()==null||purchaseItem.getQuantity()<=0){
                        return Mono.error(new IllegalArgumentException("parametros no validos para orden de compra"));
                    }
                    return purchaseOrderRepository.findById(purchaseItem.getPurchaseOrderId())
                            .flatMap(purchaseOrder -> {
                                purchaseItem.setAddedAt(LocalDateTime.now());
                                purchaseItem.setTotalItemPrice(purchaseItem.getPriceUnit()*purchaseItem.getQuantity());
                                return purchaseItemRepository.findByProductIdAndPurchaseOrderId(purchaseItem.getProductId(), purchaseItem.getPurchaseOrderId())
                                        //valida si existe ya un item en la tabla purchase item que cumpla con las condiciones y se hace update de los datos nada mas
                                        .flatMap(itemFound->{
                                            purchaseItem.setId(itemFound.getId());
                                            purchaseItem.setUpdateAt(LocalDateTime.now());
                                            return purchaseItemRepository.save(purchaseItem)
                                                    .flatMap(saveItem->{
                                                        Float newTotal= purchaseOrder.getTotal()+purchaseItem.getTotalItemPrice()-itemFound.getTotalItemPrice();
                                                        purchaseOrder.setUpdatedAt(LocalDateTime.now());
                                                        purchaseOrder.setTotal(newTotal);
                                                        return purchaseOrderRepository.save(purchaseOrder)
                                                                .thenReturn(saveItem);
                                                    });
                                        })
                                        //si el item no existe se cre uno nuevo en la tabla de purchase item
                                        .switchIfEmpty(
                                                purchaseItemRepository.save(purchaseItem)
                                                        .flatMap(saveItem->{
                                                            Float newTotal= purchaseOrder.getTotal()+purchaseItem.getTotalItemPrice();
                                                            purchaseOrder.setUpdatedAt(LocalDateTime.now());
                                                            purchaseOrder.setTotal(newTotal);
                                                            return purchaseOrderRepository.save(purchaseOrder)
                                                                    .thenReturn(saveItem);
                                                        })
                                        );


                            });
                });
    }

    public Flux<PurchaseOrder> getPurchaseOrders(){
        return purchaseOrderRepository.findAll();
    }

    public Mono<PurchaseOrder> completePurchaseOrder(Integer idPurchase){
        if (idPurchase==null||idPurchase<=0){
            return Mono.error(new IllegalArgumentException("identificador de orden no valido"));
        }
        return purchaseOrderRepository.findById(idPurchase)
                .flatMap(existOrder->{
                    if (existOrder.getStatus().equals("COMPLETADA")){
                        return Mono.error(new Exception("la orden ya ha sido completada"));
                    }

                    return purchaseItemRepository.findAllByPurchaseOrderId(existOrder.getId())
                            .flatMap(item->{
                                return productService.getProductById(item.getProductId())
                                        .flatMap(itemStock->{
                                            Integer newQuantity= itemStock.getStock()+item.getQuantity();
                                            return productService.updateProductStock(item.getProductId(), newQuantity)
                                                    .onErrorResume(e->{
                                                        System.out.println("no se pudo actualizar el stock del producto "+e.getMessage());
                                                        return Mono.empty();
                                                    });
                                        });
                            })
                            .collectList()
                            .then(Mono.defer(()->{
                                existOrder.setUpdatedAt(LocalDateTime.now());
                                existOrder.setStatus("COMPLETADA");
                                return purchaseOrderRepository.save(existOrder);
                            }));
                })
                .onErrorResume(e->{
                    System.out.println("error a la hora de consultar Orden, no existe "+e.getMessage());
                    return Mono.empty();
                });
    }

    public Mono<PurchaseOrder> canceledPurchaseOrder(Integer idPurchase){
        if (idPurchase==null||idPurchase<=0){
            return Mono.error(new IllegalArgumentException("id de orden de compra invalido"));
        }
        return purchaseOrderRepository.findById(idPurchase)
                .flatMap(order->{
                    if (order.getStatus().equals("COMPLETADA")){
                        return Mono.error(new Exception("la orden esta en estado completada no se puede cancelar"));
                    }
                    System.out.println("orden encontrada: "+order.toString());
                    order.setStatus("CANCELADA");
                    order.setUpdatedAt(LocalDateTime.now());
                    return purchaseOrderRepository.save(order);
                })
                .onErrorResume(e->{
                    System.out.println("error al buscar la orden: "+e.getMessage());
                    return Mono.empty();
                });
    }

    public Mono<PurchaseOrder> updatePurchaseOrder(PurchaseOrder purchaseOrder){
        if (purchaseOrder.getId()==null||purchaseOrder.getId()<=0){
            return Mono.error(new IllegalArgumentException("identificador de orden invalido"));
        }
        return purchaseOrderRepository.findById(purchaseOrder.getId())
                .flatMap(order->{
                    order.setUpdatedAt(LocalDateTime.now());
                    order.setStatus(purchaseOrder.getStatus());
                    order.setSeller(purchaseOrder.getSeller());
                    order.setTransportCost(purchaseOrder.getTransportCost());
                    return purchaseOrderRepository.save(order);
                })
                .onErrorResume(e->{
                    System.out.println("error al buscar orden por id: "+purchaseOrder.getId());
                    return Mono.error(new Exception("error Id"));
                });
    }

    public Flux<PurchaseOrder>reportPurchaseOrder(LocalDateTime start, LocalDateTime end){
        return purchaseOrderRepository.findAllByCreatedAtBetween(start,end);
    }
}
