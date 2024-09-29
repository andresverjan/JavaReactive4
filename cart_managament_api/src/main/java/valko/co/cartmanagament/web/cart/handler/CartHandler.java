package valko.co.cartmanagament.web.cart.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import valko.co.cartmanagament.service.CartService;
import valko.co.cartmanagament.web.cart.dto.CartDTO;

import java.util.logging.Level;

@Log
@Component
@RequiredArgsConstructor
public class CartHandler {

    private final CartService cartService;
    public static final String USER_ID = "userId";
    private static final String MESSAGE_LOG = "Message: {0}";

    public Mono<ServerResponse> addProductToCart(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(CartDTO.class)
                .flatMap(cartService::addProductToCart)
                .flatMap(cart -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(cart))
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Product added to cart successfully"}));
    }

    public Mono<ServerResponse> removeProductFromCart(ServerRequest serverRequest) {
        Integer userId = Integer.parseInt(serverRequest.pathVariable(USER_ID));
        Integer productId = Integer.parseInt(serverRequest.pathVariable("productId"));
        return cartService.removeProductFromCart(userId, productId)
                .flatMap(cart -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(cart))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Product removed from cart successfully"}));
    }

    public Mono<ServerResponse> getCartContent(ServerRequest serverRequest) {
        Integer userId = Integer.parseInt(serverRequest.pathVariable(USER_ID));
        return cartService.getCartContent(userId)
                .flatMap(cart -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(cart))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Cart content retrieved successfully"}));
    }

    public Mono<ServerResponse> emptyCart(ServerRequest serverRequest) {
        Integer userId = Integer.parseInt(serverRequest.pathVariable(USER_ID));
        return cartService.emptyCart(userId)
                .then(ServerResponse.noContent().build())
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Cart emptied successfully"}));
    }

    public Mono<ServerResponse> calculateTotal(ServerRequest serverRequest) {
        Integer userId = Integer.parseInt(serverRequest.pathVariable(USER_ID));
        return cartService.calculateTotal(userId)
                .flatMap(total -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(total))
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Cart total calculated successfully"}));
    }

}
