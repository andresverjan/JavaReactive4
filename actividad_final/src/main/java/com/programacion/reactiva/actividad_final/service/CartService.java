package com.programacion.reactiva.actividad_final.service;
import com.programacion.reactiva.actividad_final.model.*;
import com.programacion.reactiva.actividad_final.model.dto.CartTotalDTO;
import com.programacion.reactiva.actividad_final.model.dto.ItemsDTO;
import com.programacion.reactiva.actividad_final.model.dto.ProductDTO;
import com.programacion.reactiva.actividad_final.model.dto.ShoppingCartDTO;
import com.programacion.reactiva.actividad_final.repository.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    public CartService(CartRepository cartRepository, CartProductRepository cartProductRepository, ProductRepository productRepository, OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
        this.cartRepository = cartRepository;
        this.cartProductRepository = cartProductRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }


    public Mono<ShoppingCartDTO> addItemToCart(int userId, int productId, int quantity) {
        return cartRepository.findByUserId(userId)
                .switchIfEmpty(createCartForUser(userId))
                .flatMap(cart -> productRepository.findById((long)productId)
                        .flatMap(product -> {
                            double subtotal = product.getPrice() * quantity;
                            CartProduct cartProduct = new CartProduct();
                            cartProduct.setCartId(cart.getId());
                            cartProduct.setProductId(productId);
                            cartProduct.setQuantity(quantity);
                            cartProduct.setSubtotal(subtotal);
                            return cartProductRepository.save(cartProduct).then(getCartContents(cart.getId()));
                        })
                );
    }


    private Mono<Cart> createCartForUser(int userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setTotal(0.0);
        return cartRepository.save(cart);
    }


    public Mono<ShoppingCartDTO> removeItemFromCart(int cartId, int productId) {
        return cartProductRepository.deleteByCartIdAndProductId(cartId, productId)
                .then(getCartContents(cartId));
    }


    public Mono<ShoppingCartDTO> updateItemQuantity(int cartId, int productId, int newQuantity) {
        return cartProductRepository.findAllByCartId(cartId)
                .filter(item -> item.getProductId() == productId)
                .singleOrEmpty()
                .flatMap(item -> {
                    item.setQuantity(newQuantity);
                    return cartProductRepository.save(item).then(getCartContents(cartId));
                });
    }


    public Mono<ShoppingCartDTO> getCartContents(int cartId) {
        return cartRepository.findById((long)cartId)
                .flatMap(cart -> cartProductRepository.findAllByCartId(cartId)
                        .flatMap(cartProduct -> productRepository.findById((long) cartProduct.getProductId())
                                .map(product -> {
                                    ItemsDTO itemDTO = new ItemsDTO();
                                    itemDTO.setQuantity(cartProduct.getQuantity());
                                    itemDTO.setProduct(mapToProductDTO(product));
                                    itemDTO.setSubtotal(cartProduct.getSubtotal());
                                    return itemDTO;
                                })
                        )
                        .collectList()
                        .map(items -> mapToShoppingCartDTO(cart, items))
                );
    }


    public Mono<Void> emptyCart(int cartId) {
        return cartProductRepository.deleteByCartId(cartId);
    }

    public Mono<CartTotalDTO> calculateCartTotal(long cartId) {
        return cartRepository.findById(cartId)
                .flatMap(cart -> {
                    double subtotal = cart.getTotal();
                    double tax = subtotal * 0.19;
                    double shippingCost = 10;
                    double total = subtotal + tax + shippingCost;

                    CartTotalDTO cartTotalDTO = new CartTotalDTO();
                    cartTotalDTO.setSubtotal(subtotal);
                    cartTotalDTO.setTax(tax);
                    cartTotalDTO.setShippingCost(shippingCost);
                    cartTotalDTO.setTotal(total);

                    return Mono.just(cartTotalDTO);
                });
    }

    public Mono<Order> createOrder(int cartId) {
        return cartRepository.findById((long) cartId)
                .flatMap(cart -> {
                    Order order = new Order();
                    order.setUserId(cart.getUserId());
                    order.setStatus("CREATED");


                    Mono<Order> orderMono = orderRepository.save(order)
                            .flatMap(savedOrder -> cartProductRepository.findAllByCartId(cartId)
                                    .flatMap(cartProduct ->

                                            productRepository.findById((long)cartProduct.getProductId())
                                                    .flatMap(product -> {
                                                        OrderProduct orderProduct = new OrderProduct();
                                                        orderProduct.setOrderId(savedOrder.getId());
                                                        orderProduct.setProductId(product.getId());
                                                        orderProduct.setQuantity(cartProduct.getQuantity());
                                                        orderProduct.setPrice(product.getPrice());
                                                        return orderProductRepository.save(orderProduct);
                                                    })
                                    )
                                    .collectList()
                                    .then(Mono.just(savedOrder))
                            );
                    return orderMono.flatMap(currentOrder -> {
                        return orderRepository.findById((long)currentOrder.getId());
                    });
                });
    }


    private ShoppingCartDTO mapToShoppingCartDTO(Cart cart, List<ItemsDTO> items) {
        ShoppingCartDTO dto = new ShoppingCartDTO();
        dto.setCartId(cart.getId());
        dto.setUserId(cart.getUserId());
        dto.setItems(items);
        dto.setTotal(items.stream().mapToDouble(ItemsDTO::getSubtotal).sum());
        return dto;
    }

    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        return dto;
    }
}

