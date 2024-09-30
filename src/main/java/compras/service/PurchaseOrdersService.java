package compras.service;

import compras.model.CartItem;
import compras.model.OrdersEntity;
import compras.model.PurchaseOrdersEntity;
import compras.model.ShoppingCartEntity;
import compras.repository.OrdersRepository;
import compras.repository.ProductRepository;
import compras.repository.PurchaseOrdersRepository;
import compras.repository.ShoppingCartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PurchaseOrdersService {
    private final PurchaseOrdersRepository purchaseOrdersRepository;
    private final ProductRepository productRepository;

    // Registrar una nueva orden de compra y actualizar el stock del producto
    public Mono<PurchaseOrdersEntity> createPurchaseOrder(PurchaseOrdersEntity purchaseOrder) {
        return productRepository.findById(Integer.parseInt(purchaseOrder.getProduct()))
                .flatMap(product -> {
                    // Incrementar el stock del producto con la cantidad comprada
                    product.setStock(product.getStock() + purchaseOrder.getTotalAmount().intValue());

                    // Guardar el producto actualizado
                    return productRepository.save(product)
                            .then(Mono.defer(() -> {
                                // Establecer valores adicionales en la orden de compra
                                purchaseOrder.setStatus("Creacion");
                                purchaseOrder.setCreatedAt(LocalDateTime.now());
                                purchaseOrder.setUpdatedAt(LocalDateTime.now());

                                // Guardar la orden de compra en la base de datos
                                return purchaseOrdersRepository.save(purchaseOrder);
                            }));
                });
    }

    // Editar una orden de compra existente
    public Mono<PurchaseOrdersEntity> updatePurchaseOrder(Integer orderId, PurchaseOrdersEntity purchaseOrder) {
        return purchaseOrdersRepository.findById(orderId)
                .flatMap(existingOrder -> {
                    existingOrder.setProduct(purchaseOrder.getProduct());
                    existingOrder.setTotalAmount(purchaseOrder.getTotalAmount());
                    existingOrder.setStatus("Editada");
                    existingOrder.setUpdatedAt(LocalDateTime.now());

                    return purchaseOrdersRepository.save(existingOrder);
                });
    }

    // Cancelar una orden de compra existente
    public Mono<Void> cancelPurchaseOrder(Integer orderId) {
        return purchaseOrdersRepository.findById(orderId)
                .flatMap(order -> {
                    order.setStatus("Cancelada");
                    return purchaseOrdersRepository.save(order).then();
                });
    }

    // Listar todas las órdenes de compra
    public Flux<PurchaseOrdersEntity> listAllPurchaseOrders() {
        return purchaseOrdersRepository.findAll();
    }

    // Obtener una orden de compra específica por su ID
    public Mono<PurchaseOrdersEntity> getPurchaseOrderById(Integer orderId) {
        return purchaseOrdersRepository.findById(orderId);
    }
}
