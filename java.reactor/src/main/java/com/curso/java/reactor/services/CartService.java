package com.curso.java.reactor.services;

import com.curso.java.reactor.exceptions.BusinessException;
import com.curso.java.reactor.models.*;
import com.curso.java.reactor.models.dto.*;
import com.curso.java.reactor.models.dto.CartProductDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import com.curso.java.reactor.repository.CartProductRepository;
import com.curso.java.reactor.repository.CartRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);
    private final CartProductRepository cartProductRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final SaleOrderService saleOrderService;

    public CartService(CartProductRepository cartProductRepository, CartRepository cartRepository, ProductService productService, SaleOrderService saleOrderService) {
        this.cartProductRepository = cartProductRepository;
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.saleOrderService = saleOrderService;
    }

    public Mono<CartProductDTO> addProductsToCart(CartProduct cartProduct) {
        return validatePurchaseCart((long) cartProduct.getCartId()).flatMap(addCart -> {
            cartProduct.setCartId(addCart.getId());
            return productService.findProductStock((long) cartProduct.getProductId(), cartProduct.getQuantity()).flatMap(product -> cartProductRepository.findByCartIdAndProductId((long) cartProduct.getCartId(), (long) cartProduct.getProductId()).flatMap(existsCartProduct -> {
                existsCartProduct.setQuantity(existsCartProduct.getQuantity() + cartProduct.getQuantity());
                return saveProductOnCart(existsCartProduct, product, cartProduct.getQuantity());
            }).switchIfEmpty(saveProductOnCart(cartProduct, product, cartProduct.getQuantity())));
        });
    }

    public Mono<Cart> validatePurchaseCart(Long cartId) {
        return (cartId == 0 ? Mono.defer(() -> cartRepository.save(new Cart())) : cartRepository.findById(cartId))
                .switchIfEmpty(Mono.error(new BusinessException(404, "Cart not found")));
    }

    public Mono<CartProductDTO> saveProductOnCart(CartProduct cartProduct, Product product, int quantity) {
        return cartProductRepository.save(cartProduct).flatMap(productSaved -> {
            logger.info("Product saved on cart: {}", productSaved);
            return productService.updateStock((long) product.getId(), product.getStock() - quantity)
                    .then(Mono.just(CartProductDTO.builder().cartId((long) cartProduct.getCartId())
                            .product(ProductDTO.builder().id(product.getId()).name(product.getName())
                                    .price(product.getPrice()).description(product.getDescription())
                                    .imageUrl(product.getImageUrl())
                                    .build())
                            .quantity(productSaved.getQuantity())
                            .build()));
        });
    }

    public Flux<PurchaseCartDTO> getCart(Long id) {
        return cartProductRepository.findByCartId(id).collectList().flatMapMany(validateCart -> {
            if (validateCart.isEmpty()) {
                return Flux.error(new BusinessException(400, "There aren't products on cart"));
            }
            List<ProductQuantityDTO> products = validateCart.stream().map(cartProduct -> ProductQuantityDTO.builder()
                            .productId(cartProduct.getProductId()).quantity(cartProduct.getQuantity()).build())
                    .collect(Collectors.toList());
            return productService.mapperSaleItems(products).flux();
        });
    }


    public Mono<CartProductDTO> updateProductQuantity(Long carritoId, Long productoId, int quantity) {
        return cartProductRepository.findByCartIdAndProductId(carritoId, productoId).flatMap(updateQuantity -> {
            if (quantity < updateQuantity.getQuantity()) {
                logger.info("Decreasing stock: {}", updateQuantity);
                return productService.findProductStock(productoId, -quantity).flatMap(decreaseStock -> {
                    updateQuantity.setQuantity(updateQuantity.getQuantity() - quantity);
                    return saveProductOnCart(updateQuantity, decreaseStock, -quantity);
                });
            } else {
                logger.info("Increasing stock: {}", updateQuantity);
                return productService.findProductStock(productoId, quantity).flatMap(increaseStock -> {
                    updateQuantity.setQuantity(updateQuantity.getQuantity() + quantity);
                    return saveProductOnCart(updateQuantity, increaseStock, quantity);
                });
            }
        });
    }


    public Mono<Void> deleteProductInCart(Long cartId, Long productId) {
        return cartProductRepository.deleteByCartIdAndProductId(cartId, productId);
    }

    public Mono<Void> deleteCart(Long cartId) {
        return cartProductRepository.deleteByCartId(cartId).then(cartRepository.deleteById(cartId));
    }

    public Mono<totalAmountDTO> getTotalOfCart(Long cartId, Double shipment) {
        return cartProductRepository.findByCartId(cartId).collectList().flatMap(cartProducts -> {
            if (cartProducts.isEmpty()) {
                return Mono.error(new BusinessException(400, "There are no products in the cart, the total cannot be calculated"));
            }
            List<ProductQuantityDTO> products = cartProducts.stream().map(cartProduct ->
                    ProductQuantityDTO.builder().productId(cartProduct.getProductId())
                            .quantity(cartProduct.getQuantity()).build()).toList();
            return productService.getTotal(products, shipment);
        });
    }


    public Mono<SaleDTO> saveASale(Long cartId, Double shipment) {
        return cartProductRepository.findByCartId(cartId).collectList().flatMap(cartProducts -> {
            if (cartProducts.isEmpty()) {
                return Mono.error(new BusinessException(404, "There are no products in the cart, purchase cannot be made"));
            }
            List<ProductQuantityDTO> products = cartProducts.stream().map(cartProduct ->
                    ProductQuantityDTO.builder().productId(cartProduct.getProductId())
                            .quantity(cartProduct.getQuantity()).build()).toList();
            return productService.getTotal(products, shipment)
                    .flatMap(totalAmount -> saleOrderService
                            .createSaleOrder(totalAmount.getTotal())
                            .flatMap(saleOrderSaved -> saleOrderService
                                    .createSaleOrderProduct(products, saleOrderSaved.getId())
                                    .collectList()
                                    .flatMap(items -> productService
                                            .mapperSaleItems(products))
                                    .flatMap(purchaseCartDTO -> deleteCart(cartId)
                                            .thenReturn(saleOrderService
                                                    .saleResponse(saleOrderSaved, purchaseCartDTO, totalAmount)))));
        });
    }
}
