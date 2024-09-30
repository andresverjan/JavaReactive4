package compras.service;

import compras.model.OrdersEntity;
import compras.model.ProductEntity;
import compras.repository.OrdersRepository;
import compras.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;

    // Crear una nueva orden con múltiples productos
    public Mono<OrdersEntity> createOrder(Integer cartId, List<ProductEntity> products) {
        Integer totalAmount = products.stream()
                .mapToInt(ProductEntity::getPrice)
                .sum();

        // Actualizar stock y crear la orden
        return Flux.fromIterable(products)
                .flatMap(product -> productRepository.findById(product.getId())
                        .flatMap(existingProduct -> {
                            if (existingProduct.getStock() < 1) {
                                return Mono.error(new RuntimeException("No hay suficiente stock para el producto: " + existingProduct.getName()));
                            }
                            existingProduct.setStock(existingProduct.getStock() - 1);
                            return productRepository.save(existingProduct);
                        }))
                .then(Mono.defer(() -> {
                    OrdersEntity order = new OrdersEntity();
                    order.setCartId(cartId);
                    order.setTotalAmount(totalAmount);
                    order.setStatus("Creacion");
                    order.setCreatedAt(LocalDateTime.now());
                    order.setUpdatedAt(LocalDateTime.now());
                    return ordersRepository.save(order);
                }));
    }

    // Editar una orden existente
    public Mono<OrdersEntity> editOrder(Integer orderId, List<ProductEntity> updatedProducts) {
        return ordersRepository.findById(orderId)
                .switchIfEmpty(Mono.error(new RuntimeException("La orden no existe")))
                .flatMap(existingOrder -> {
                    Integer totalAmount = updatedProducts.stream()
                            .mapToInt(ProductEntity::getPrice)
                            .sum();
                    existingOrder.setTotalAmount(totalAmount);
                    existingOrder.setUpdatedAt(LocalDateTime.now());
                    return ordersRepository.save(existingOrder);
                });
    }

    // Cancelar una orden existente
    public Mono<Void> cancelOrder(Integer orderId) {
        return ordersRepository.findById(orderId)
                .switchIfEmpty(Mono.error(new RuntimeException("La orden no existe")))
                .flatMap(existingOrder -> {
                    existingOrder.setStatus("Cancelada");
                    existingOrder.setUpdatedAt(LocalDateTime.now());
                    return ordersRepository.save(existingOrder);
                }).then();
    }

    // Listar todas las órdenes
    public Flux<OrdersEntity> listAllOrders() {
        return ordersRepository.findAll();
    }

    // Listar todas las órdenes por producto
    public Flux<OrdersEntity> listOrdersByProduct(String productName) {
        return ordersRepository.findAllByProduct(productName);
    }
}
