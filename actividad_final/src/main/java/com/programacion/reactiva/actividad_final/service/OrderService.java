package com.programacion.reactiva.actividad_final.service;

import com.programacion.reactiva.actividad_final.model.Order;
import com.programacion.reactiva.actividad_final.model.OrderProduct;
import com.programacion.reactiva.actividad_final.repository.OrderProductRepository;
import com.programacion.reactiva.actividad_final.repository.OrderRepository;
import com.programacion.reactiva.actividad_final.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;



    public Mono<Order> updateOrder(int orderId, int productId, int quantity) {
        return orderRepository.findById((long)orderId)
                .flatMap(order -> {
                    return productRepository.findById((long)productId)
                            .flatMap(product -> {
                                return orderProductRepository.findByOrderIdAndProductId(orderId, productId)
                                        .flatMap(orderProduct -> {
                                            orderProduct.setQuantity(quantity);
                                            return orderProductRepository.save(orderProduct);
                                        })
                                        .switchIfEmpty(Mono.defer(() -> {

                                            OrderProduct newOrderProduct = new OrderProduct();
                                            newOrderProduct.setOrderId(orderId);
                                            newOrderProduct.setProductId(productId);
                                            newOrderProduct.setQuantity(quantity);
                                            newOrderProduct.setPrice(product.getPrice());
                                            return orderProductRepository.save(newOrderProduct);
                                        }))
                                        .then(Mono.defer(() -> {
                                            order.setStatus("UPDATED");
                                            return orderRepository.save(order);
                                        }))
                                        .then(orderRepository.findById((long)orderId));
                            });
                });
    }



    public Mono<Void> cancelOrder(long orderId) {
        return orderRepository.findById(orderId)
                .flatMap(order -> {
                    order.setStatus("CANCELED");
                    return orderRepository.save(order)
                            .then(orderProductRepository.deleteAllByOrderId(orderId));
                });
    }

    public Flux<Order> listOrders() {
        return orderRepository.findAll();
    }

    public Flux<OrderProduct> listOrderProducts(long orderId) {
        return orderProductRepository.findByOrderId(orderId);
    }
}
