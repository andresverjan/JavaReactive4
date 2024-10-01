package com.bancolombia.shoppingcart.service;

import com.bancolombia.shoppingcart.dto.CartDTO;
import com.bancolombia.shoppingcart.dto.CartDetailAmountDTO;
import com.bancolombia.shoppingcart.dto.CartDetailDTO;
import com.bancolombia.shoppingcart.dto.CartTotalPurchaseDTO;
import com.bancolombia.shoppingcart.entity.Cart;
import com.bancolombia.shoppingcart.entity.CartDetail;
import com.bancolombia.shoppingcart.entity.SalesOrder;
import com.bancolombia.shoppingcart.entity.SalesOrderDetail;
import com.bancolombia.shoppingcart.repository.CartDetailRepository;
import com.bancolombia.shoppingcart.repository.CartRepository;
import com.bancolombia.shoppingcart.repository.SalesOrderDetailRepository;
import com.bancolombia.shoppingcart.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private SalesOrderRepository salesOrderRepository;
    @Autowired
    private SalesOrderDetailRepository salesOrderDetailRepository;

    private Mono<CartDTO> getCartDetailsForCart(Cart cart) {

        return Mono.just(cart)
                .zipWith(cartDetailRepository.findByCartId(cart.getId()).collectList())
                //.as(transactionalOperator::transactional)       
                .map(result ->  CartDTO.builder()
                                .id(result.getT1().getId())
                                .subtotalorder(result.getT1().getSubtotalorder())
                                .totaldiscount(result.getT1().getTotaldiscount())
                                .totaltax(result.getT1().getTotaltax())
                                .createdAt(result.getT1().getCreatedAt())
                                .updatedAt(result.getT1().getUpdatedAt())
                                .userid(result.getT1().getUserid())
                                .cartDetailList(result.getT2())
                                .build());
    }

    private Mono<Boolean> cartExists(Long id){
        return cartRepository.existsById(id).handle(
                (exists, sink) -> {
                    if (Boolean.FALSE.equals(exists)){
                        sink.error(new IllegalArgumentException());
                    }
                    else{
                        sink.next(exists);
                    }
                }
        );
    }

    private Mono<Boolean> cartDetailExists(Long id){
        return cartDetailRepository.existsById(id).handle(
                (exists, sink) -> {
                    if (Boolean.FALSE.equals(exists)){
                        sink.error(new IllegalArgumentException());
                    }
                    else{
                        sink.next(exists);
                    }
                }
        );
    }

    public Flux<CartDTO> findAll(){
        return cartRepository.findAll().flatMap(this::getCartDetailsForCart);
    }

    public Mono<CartDTO> findById(Long id){
        return cartRepository.findById(id)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Carrito no existe")))
                .flatMap(this::getCartDetailsForCart);
    }

    public Mono<CartDTO> createCart(Cart cart){
        if(cart.getId() != null){
            return Mono.error(new IllegalArgumentException());
        }
        return cartRepository.save(cart)
                .flatMap(newCart -> this.findById(newCart.getId()));
                /*.map(
                newCart -> CartDTO.builder()
                        .subtotalorder(newCart.getSubtotalorder())
                        .totaldiscount(newCart.getTotaldiscount())
                        .totaltax(newCart.getTotaltax())
                        .createdAt(newCart.getCreatedAt())
                        .updatedAt(newCart.getUpdatedAt())
                        .userid(newCart.getUserid())
                        .cartDetailList(newCart.getCartDetailList())
                        .build()
                );*/
    }

    public Mono<CartDTO> updateCart(Cart cart){
        return cartExists(cart.getId())
                .then(cartRepository.save(cart))
                .flatMap(updatedCart -> this.findById(updatedCart.getId()))
                /*.map(updatedCart -> CartDTO.builder()
                        .subtotalorder(updatedCart.getSubtotalorder())
                        .totaldiscount(updatedCart.getTotaldiscount())
                        .totaltax(updatedCart.getTotaltax())
                        .createdAt(updatedCart.getCreatedAt())
                        .updatedAt(updatedCart.getUpdatedAt())
                        .userid(updatedCart.getUserid())
                        .cartDetailList(updatedCart.getCartDetailList())
                        .build())*/;
    }

    public Mono<Void> createCartDetailForCart(CartDetail cartDetail) {
        if (cartDetail.getId() != null) {
            return Mono.error(new IllegalArgumentException());
        }

        return cartExists(cartDetail.getCartid())
                .then(cartDetailRepository.save(cartDetail)).then()
                /*.as(transactionalOperator::transactional)*/;

    }

    public Mono<Void> deleteCartDetail(Long id) {
        return cartDetailRepository.deleteById(id);
    }

    public Mono<Void> deleteCart(Long id) {
        return  cartDetailRepository.deleteAllByCartId(id).then(cartRepository.deleteById(id));
    }

    public Mono<Void> deleteAllCartDetail(Long cartId) {
        return cartDetailRepository.deleteAllByCartId(cartId).then();
    }

    public Mono<CartDTO> updateCartDetailAmount(CartDetailAmountDTO cartDetailAmountDTO){

        return cartDetailRepository.findById(cartDetailAmountDTO.getId())
                                   .flatMap(existingCartDetail -> {
                                       if(existingCartDetail.getAmount() <= 0){
                                           return Mono.error(new IllegalArgumentException());
                                       }

                                       existingCartDetail.setAmount(existingCartDetail.getAmount()+cartDetailAmountDTO.getAmount());
                                        return cartDetailRepository.save(existingCartDetail).flatMap(cart -> this.findById(cart.getCartid()));
                                   });
    }

    public Mono<CartTotalPurchaseDTO> calcTotalCart(Long cartId){
        return cartRepository.findById(cartId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Carrito no existe")))
                .flatMap(this::getCartDetailsForCart)
                .map(cart -> {
                   double total = cart.getCartDetailList().stream().mapToDouble(x -> (x.getPrice() * x.getAmount()) - x.getAmountdiscount() + x.getAmounttax()).sum();

                    CartTotalPurchaseDTO cartTotalPurchaseDTO = CartTotalPurchaseDTO.builder()
                            .id(cartId)
                            .totalPurchase(total)
                            .build();

                   return cartTotalPurchaseDTO;
               });
    }

    public Mono<Void> registerSalesOrderOfCart(Long cartId){
        return cartRepository.findById(cartId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("Carrito no existe")))
                .flatMap(this::getCartDetailsForCart)
                .map(cart -> {
                    List<SalesOrderDetail> salesOrderDetail = new ArrayList<>();
                    cart.getCartDetailList().forEach(cartDetail -> salesOrderDetail.add(
                            SalesOrderDetail.builder()
                                    .amount(cartDetail.getAmount())
                                    .salesPrice(cartDetail.getPrice())
                                    .amountdiscount(cartDetail.getAmountdiscount())
                                    .amounttax(cartDetail.getAmounttax())
                                    .productid(cartDetail.getProductid())
                                    .build()
                    ));
                    SalesOrder salesOrder = SalesOrder.builder()
                            .userid(cart.getUserid())
                            .iscancelled(false)
                            .subtotalorder(cart.getSubtotalorder())
                            .totaldiscount(cart.getTotaldiscount())
                            .totaltax(cart.getTotaltax())
                            .salesOrderDetails(salesOrderDetail)
                            .build();

                    System.out.println(salesOrder);
                    return salesOrderRepository.save(salesOrder);/*.flatMap(newSalesOrder -> {
                        System.out.println(newSalesOrder);
                        newSalesOrder.getSalesOrderDetails().forEach(
                                sodetail -> {
                                    sodetail.setOrderid(newSalesOrder.getId());
                                    salesOrderDetailRepository.save(sodetail);
                                }
                        );
                        return Mono.just(newSalesOrder);
                    });*/
                }).then();
    }
}
