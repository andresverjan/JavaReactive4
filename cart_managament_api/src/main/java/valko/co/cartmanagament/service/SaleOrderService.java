package valko.co.cartmanagament.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import valko.co.cartmanagament.exception.NotFoundException;
import valko.co.cartmanagament.model.sale.SaleOrder;
import valko.co.cartmanagament.model.sale.SaleOrderDetail;
import valko.co.cartmanagament.repository.cart.CartItemRepository;
import valko.co.cartmanagament.repository.cart.CartRepository;
import valko.co.cartmanagament.repository.product.ProductRepository;
import valko.co.cartmanagament.repository.sales.SaleOrderDetailRepository;
import valko.co.cartmanagament.repository.sales.SaleOrderRepository;
import valko.co.cartmanagament.web.sales.dto.ProductDTO;
import valko.co.cartmanagament.web.sales.dto.SaleOrderDTO;

import java.time.LocalDateTime;

import static valko.co.cartmanagament.service.CartService.CART_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class SaleOrderService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final SaleOrderRepository saleOrderRepository;
    private final SaleOrderDetailRepository saleOrderDetailRepository;

    public Mono<SaleOrderDTO> createOrder(Integer userId) {
        return cartRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new NotFoundException("Cart not found for userId: " + userId)))
                .flatMap(cart -> calculateTotal(userId)
                        .flatMap(total -> {

                            SaleOrder saleOrder = SaleOrder.builder()
                                    .userId(userId)
                                    .creationDate(LocalDateTime.now())
                                    .updatedDate(LocalDateTime.now())
                                    .total(total)
                                    .build();

                            return saleOrderRepository.save(saleOrder)
                                    .flatMap(savedOrder -> cartItemRepository.findCartItemByCartId(cart.id())
                                            .flatMap(cartItem -> productRepository.findById(cartItem.product())
                                                    .flatMap(product -> {

                                                        SaleOrderDetail saleOrderDetail = SaleOrderDetail.builder()
                                                                .saleOrderId(savedOrder.id())
                                                                .productId(product.id())
                                                                .amount(cartItem.amount())
                                                                .build();

                                                        return saleOrderDetailRepository.save(saleOrderDetail)
                                                                .thenReturn(ProductDTO.builder()
                                                                        .name(product.name())
                                                                        .price(product.price())
                                                                        .amount(cartItem.amount())
                                                                        .build());
                                                    }))
                                            .collectList()
                                            .map(productDTOList -> SaleOrderDTO.builder()
                                                    .userId(userId)
                                                    .cartId(cart.id())
                                                    .products(productDTOList)
                                                    .total(total)
                                                    .date(savedOrder.creationDate())
                                                    .build())
                                    );
                        }));
    }

    public Mono<Void> deleteOrder(int id) {
        return saleOrderRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Order with ID " + id + " not found")))
                .flatMap(saleOrderRepository::delete);
    }

    public Flux<SaleOrder> listAllOrders() {
        return saleOrderRepository.findAll();
    }

    public Flux<SaleOrderDetail> listOrdersByProductId(int productId) {
        return saleOrderDetailRepository.findOrderDetailByProductId(productId);
    }

    public Mono<Double> calculateTotal(Integer userId) {
        return cartRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new NotFoundException(CART_NOT_FOUND.concat(String.valueOf(userId)))))
                .flatMapMany(cart -> cartItemRepository.findCartItemByCartId(cart.id()))
                .flatMap(cartItem -> productRepository.findById(cartItem.product())
                        .map(product -> product.price() * cartItem.amount()))
                .reduce(0.0, Double::sum);
    }

}
