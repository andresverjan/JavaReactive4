package com.example.shopping_cart.service;

import com.example.shopping_cart.model.CartItem;
import com.example.shopping_cart.model.SalesOrder;
import com.example.shopping_cart.model.ShoppingCart;
import com.example.shopping_cart.model.TopProductReport;
import com.example.shopping_cart.repository.CartItemRepository;
import com.example.shopping_cart.repository.SalesOrderRespository;
import com.example.shopping_cart.repository.ShoppingcartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ShoppingCartService {
    private final CartItemRepository cartItemRepository;
    private final ShoppingcartRepository shoppingCartRepository;
    private final ProductService productService;
    private final SalesOrderRespository salesOrderRespository;

    public Mono<CartItem> createCartItem(CartItem cartItem,Integer idClient){
        //validaciones de data
        return validateShoppingCartClient(idClient,cartItem)
                .flatMap(shoppingCart -> {
                    cartItem.setCartId(shoppingCart.getId());
                    cartItem.setAddedAt(LocalDateTime.now());
                    cartItem.setTotalItemPrice(cartItem.getPriceUnit()*cartItem.getQuantity());
                    return cartItemRepository.save(cartItem);
                });


    }

    public Mono<ShoppingCart> validateShoppingCartClient(Integer idClient,CartItem cartItem){
        if (idClient==null){
            return Mono.empty();
        }

        return shoppingCartRepository.findByClientIdAndStatus(idClient, "PENDIENTE")
                .flatMap(shoppingCart -> {
                    //System.out.println(shoppingCart.toString());
                    return updatePendingCart(shoppingCart,cartItem);
                })
                .switchIfEmpty(
                        // Si no existe un carrito pendiente, crear uno nuevo
                        createNewPendingCart(idClient,cartItem)
                );

    }

    private Mono<ShoppingCart> createNewPendingCart(Integer idClient, CartItem cartItem) {
        System.out.println("nuevo carrito");
        ShoppingCart newCart = new ShoppingCart();
        newCart.setClientId(idClient);
        newCart.setStatus("PENDIENTE");
        newCart.setCreatedAt(LocalDateTime.now());
        newCart.setTotalPrice(cartItem.getPriceUnit()*cartItem.getQuantity());

        // Guardar el nuevo carrito y devolverlo
        return shoppingCartRepository.save(newCart);
    }

    private Mono<ShoppingCart> updatePendingCart(ShoppingCart cart, CartItem item){
        System.out.println("carrito existente");
        cart.setUpdateAt(LocalDateTime.now());
        cart.setTotalPrice(cart.getTotalPrice()+(item.getPriceUnit()*item.getQuantity()));

        return  shoppingCartRepository.save(cart);
    }

    //eliminar item del carrito
    public Mono<String> deleteCartItem(Integer idItem, Integer idClient){
        if (idItem==null||idClient==null){
            return Mono.error(new IllegalArgumentException("ID no puede ser null"));
        }

        return shoppingCartRepository.findByClientIdAndStatus(idClient,"PENDIENTE")
                .flatMap(shoppingCart -> {
                    System.out.println("carrito: "+shoppingCart.toString());
                    return cartItemRepository.findByProductIdAndCartId(idItem,shoppingCart.getId())
                            .flatMap(cartItem -> {
                                System.out.println("itemcarrito: "+cartItem.toString());
                                return cartItemRepository.deleteById(cartItem.getId())
                                        .then(updateCartTotal(shoppingCart,(shoppingCart.getTotalPrice()-cartItem.getTotalItemPrice())))
                                        .doOnNext(updatedCart -> System.out.println("Updated Cart: " + updatedCart))
                                        .then(Mono.just("Id Product delete from cart shoping: " + cartItem))
                                        .onErrorResume(e -> {
                                            System.out.println("Error during deletion: " + e.getMessage());
                                            return Mono.just("Error during deletion: " + e.getMessage());
                                        });
                            })
                            .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found with ID: " + idItem)))
                            .onErrorReturn(IllegalArgumentException.class, "Error: Product not found or invalid ID");

                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("SHoping cart active don exist: " + idItem)))
                .onErrorReturn(IllegalArgumentException.class, "Error: SHoping cart not found or status invalid");


    }

    //update cantidad de item en el carrito
    public Mono<CartItem> updateQuantityItem(Integer idItem, Integer quantity, Integer idClient){
        if (idItem==null||quantity==null||idClient==null){
            return Mono.error(new IllegalArgumentException("ninguno de los parametros puede ser null"));
        }
        if (quantity<=0){
            return Mono.error(new IllegalArgumentException("la cantidad debe ser mayor a 0"));
        }
        return shoppingCartRepository.findByClientIdAndStatus(idClient,"PENDIENTE")
                .flatMap(shoppingCart -> {
                    System.out.println("carrito: "+shoppingCart);
                    return cartItemRepository.findByProductIdAndCartId(idItem,shoppingCart.getId())
                            .flatMap(cartItem -> {
                                System.out.println("Item Carrito"+ cartItem.toString());
                                cartItem.setQuantity(quantity);
                                cartItem.setTotalItemPrice(cartItem.getPriceUnit()*quantity);
                                return cartItemRepository.save(cartItem)
                                        .flatMap(savedItem->updateCartTotal(shoppingCart,cartItem.getTotalItemPrice())
                                                .thenReturn(savedItem)
                                        )
                                        .doOnNext(updatedCart -> System.out.println("Updated Cart: " + updatedCart))
                                        .onErrorResume(e -> {
                                            System.out.println("Error during deletion: " + e.getMessage());
                                            //return Mono.just("Error during update: " + e.getMessage());
                                            return Mono.error(new IllegalArgumentException("Error during update: " + e.getMessage()));
                                        });
                            });
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("SHoping cart active don exist or Item Cart nt exist(id client, item id) " + idItem+", "+idClient)));

    }

    //funcion utilizada para actualizar el total del carrito de compras ya sea por eliminación de item o por modificacion de cantidad de item
    private Mono<ShoppingCart> updateCartTotal(ShoppingCart shoppingCart, Float newTotalCart){
        shoppingCart.setTotalPrice(newTotalCart);
        shoppingCart.setUpdateAt(LocalDateTime.now());
        return shoppingCartRepository.save(shoppingCart);

    }
    //listar items del carrito del cliente activo
    public Flux<CartItem> listItemsFromShoppingCart(Integer idClient){
        if (idClient==null){
            return Flux.error(new IllegalArgumentException("identificado de cliente no puede ser null"));
        }
        return shoppingCartRepository.findByClientIdAndStatus(idClient,"PENDIENTE")
                .flatMapMany(shoppingCart -> {
                    System.out.println("carrito: "+shoppingCart.toString());
                    return cartItemRepository.findAllByCartId(shoppingCart.getId());
                })
                .switchIfEmpty(Flux.error(new IllegalArgumentException("No existe un carrito activo para el cliente con ID: " + idClient)));
    }
    //limpiar el carrito

    public Mono<ShoppingCart> clearShoppingCart(Integer idClient){
        if (idClient==null){
            return Mono.error(new IllegalArgumentException("identificador no valido"));
        }
        return shoppingCartRepository.findByClientIdAndStatus(idClient,"PENDIENTE")
                .flatMap(shopingCart->{
                    System.out.println("carrito: "+shopingCart.toString());
                    return cartItemRepository.deleteAllByCartId(shopingCart.getId())
                            .then(updateCartTotal(shopingCart, 0.0F));
                })
                .switchIfEmpty(Mono.error(new IllegalArgumentException("No existe un carrito activo para el cliente con ID: " + idClient)));
    }

    //calcular total con impuesto y envio
    public Mono<SalesOrder> preSalesOrder(SalesOrder salesOrder){
        if (salesOrder==null){
            return Mono.error(new IllegalArgumentException("carrito de compras no valido"));
        }
        return shoppingCartRepository.findByClientIdAndStatus(salesOrder.getClientId(),"PENDIENTE")
                .flatMap(existShoppigCart->{
                    System.out.println("carrito de compras valido: "+existShoppigCart.toString());
                    salesOrder.setCreatedAt(LocalDateTime.now());
                    salesOrder.setStatus("PENDIENTE");
                    salesOrder.setTotal(existShoppigCart.getTotalPrice()+(existShoppigCart.getTotalPrice()*salesOrder.getTaxes()/100)+ salesOrder.getShippingCost());
                    return salesOrderRespository.save(salesOrder);
                });
    }
    //registrar orden de venta
    public Mono<Object> registerSalesOrder(Integer saleOrderId) {
        if (saleOrderId == null || saleOrderId <= 0) {
            return Mono.error(new IllegalArgumentException("id de orden de venta invalido"));
        }

        return salesOrderRespository.findByIdAndStatus(saleOrderId,"PENDIENTE")
                .flatMap(salesOrderValid -> {
                    return listItemsFromShoppingCart(salesOrderValid.getClientId())
                            // Validar el stock de cada producto en el carrito
                            .flatMap(item -> validationProductsSale(item)
                                    .thenReturn(item) // Continuar solo si la validación pasa
                            )
                            .collectList() // Esperar a que se validen todos los productos
                            .flatMap(validatedItems -> {
                                // Si todas las validaciones pasaron, se puede continuar con el registro
                                return finalizeSalesOrder(salesOrderValid, validatedItems);
                            })
                            .onErrorResume(e -> {
                                // Manejar el error si alguna validación falla
                                return Mono.error(new IllegalArgumentException("Error al validar productos en stock: " + e.getMessage()));
                            });
                });
    }


    private Mono<Void> validationProductsSale(CartItem cartItem) {
        // Aquí debes llamar a tu servicio de productos para obtener el stock disponible
        return productService.getProductById(cartItem.getProductId())
                .flatMap(product -> {
                    if (product.getStock() < cartItem.getQuantity()) {
                        return Mono.error(new IllegalArgumentException("El producto " + product.getName() + " no tiene suficiente stock"));
                    }
                    // Si hay suficiente stock, continuar con el flujo
                    return Mono.empty();
                });
    }

    private Mono<SalesOrder> finalizeSalesOrder(SalesOrder salesOrder, List<CartItem> items) {


        return Flux.fromIterable(items)
                .flatMap(item -> {
                    System.out.println("udapte stock: "+item.toString());
                    return productService.getProductById(item.getProductId())
                            .flatMap(itemStock->{
                                Integer newQuantity= itemStock.getStock()-item.getQuantity();
                                return productService.updateProductStock(item.getProductId(), newQuantity);
                            });

                })
                .flatMap(shoppingcart->{
                    System.out.println("carrito completado: "+shoppingcart.toString());
                    return shoppingCartRepository.findById(salesOrder.getShoppingCartId())
                            .flatMap(cart->{
                                cart.setStatus("COMPLETADO");
                                cart.setUpdateAt(LocalDateTime.now());
                                return shoppingCartRepository.save(cart);
                            });
                })
                .then(Mono.defer(() -> {
                    salesOrder.setStatus("COMPLETADO");  // Cambiamos el estado de la orden de venta a COMPLETADO
                    return salesOrderRespository.save(salesOrder);  // Guardamos la orden de venta actualizada
                })); 
    }
    //listar ordenes de venta por cliente

    public Flux<SalesOrder> listSalesOrderByClientId(Integer clientId){
        if (clientId==null||clientId<=0){
            return Flux.error(new IllegalArgumentException("identificador de cliente invalido"));
        }
        return salesOrderRespository.findAllByClientId(clientId);
    }

    //actualizar orden a cancelada

    public Mono<SalesOrder> updateSaleOrderCanceled(Integer idOrder){
        if (idOrder==null||idOrder<=0){
            return Mono.error(new IllegalArgumentException("id de orden invalido"));
        }

        return salesOrderRespository.findById(idOrder)
                .flatMap(order->{
                    order.setStatus("CANCELADA");
                    return salesOrderRespository.save(order);
                });
    }

    //top 5 productos
    public Flux<TopProductReport> generateTopProductReport(LocalDateTime startDate, LocalDateTime endDate) {
        System.out.println("inicio: "+startDate);
        System.out.println("fin: "+endDate);
        return cartItemRepository.findTopProductsByDateRange(startDate, endDate);
    }

}
