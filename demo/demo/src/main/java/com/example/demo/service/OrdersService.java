package com.example.demo.service;
import com.example.demo.models.*;
import com.example.demo.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShoppingCartRepository;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    ProductRepository productRepository;
    private final ObjectMapper objectMapper;
    public OrdersService(){
        objectMapper= new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
    public Mono<Orders> ssaveOrder(Long cartId) {
        return shoppingCartRepository.findByCartId(Math.toIntExact(cartId))
                .collectList()
                .flatMap(products ->{
                    try {
                        List<ProductOrder> productOrders= new ArrayList<>();
                        int client_id=0;
                        double price=0.0;
                            for (ShoppingCart shoppingCart : products){
                                ProductOrder productOrder1= new ProductOrder();
                                productOrder1.setAmount(shoppingCart.getAmount());
                                productOrder1.setProduct_id(shoppingCart.getProductId());
                                productOrder1.setPrice(shoppingCart.getPrice());
                                productOrders.add(productOrder1);
                                client_id=shoppingCart.getClientId();
                                price+=shoppingCart.getPrice();
                            }

                        String productsJson = objectMapper.writeValueAsString(productOrders);
                        Orders order = new Orders();
                        order.setProducts(productsJson);
                        order.setClientId(client_id);
                        order.setPrice(price);
                        order.setCreatedAt(LocalDateTime.now());
                        order.setUpdateAt(LocalDateTime.now());

                        return orderRepository.save(order);
                    }
                    catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException("Error al convertir a JSON", e));
                    }
                });


    }
    public Mono<Void> deleteOrder(Long id) {

            return orderRepository.findById(id)
                    .flatMap(order -> {
                        // Captura datos del JSON
                        return processJsonAndUpdateStock(order.getProducts(),"delete")
                                .then(orderRepository.delete(order)); // Eliminar el registro
                    });

    }
    private Flux<Product> processJsonAndUpdateStock(String jsonData,String type) {
        try {
            ProductOrder[] products = objectMapper.readValue(jsonData, ProductOrder[].class);

            return Flux.fromArray(products)
                    .flatMap(productDTO -> {
                        return productRepository.findById(Long.valueOf(productDTO.getProduct_id()))
                                .flatMap(product -> {
                                    if (type.equals("delete")){
                                        product.setStock(product.getStock() + productDTO.getAmount());

                                    }
                                    else if (type.equals("update")){
                                    }
                                    return productRepository.save(product);
                                });
                    });
        } catch (Exception e) {
            return Flux.error(new RuntimeException("Error al procesar el JSON", e));
        }
    }
    public Flux<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
    public Mono<OrderReport> getReport(Dates dates) {
        return orderRepository.findByCreatedAtBetween(dates.getInitialDate(),dates.getFinalDate()).collectList().flatMap(orders -> {
            double totalPrice=orders.stream().mapToDouble(Orders::getPrice).sum();
            OrderReport orderReport=new OrderReport();
            orderReport.setOrderNumber(orders.size());
            orderReport.setProductOrderList(orders);
            orderReport.setTotal(totalPrice);
            return Mono.just(orderReport);
        });
    }
    public Mono<Orders> updateOrder(Long id, Orders orders) {
        return orderRepository.findById(id)
                .flatMap(order -> {
                    order.setProducts(orders.getProducts());
                    order.setClientId(orders.getClientId());
                    return processJsonAndUpdateStock(orders.getProducts(),"update").then(orderRepository.save(order));

                });

    }
    public Flux<ProductOrder> getProductOrder(Long id) {
        return orderRepository.findById(id)
                .flatMapMany(order -> {
                    try {
                        ProductOrder[] products = objectMapper.readValue(order.getProducts(), ProductOrder[].class);
                        return Flux.fromArray(products);
                    } catch (JsonProcessingException e) {
                        return Flux.error(new RuntimeException("Error al procesar el JSON", e));
                    }
                });
    }





}
