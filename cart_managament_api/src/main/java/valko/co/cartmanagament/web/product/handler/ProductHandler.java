package valko.co.cartmanagament.web.product.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import valko.co.cartmanagament.model.products.Product;
import valko.co.cartmanagament.service.ProductService;

import java.util.logging.Level;

@Log
@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductService productService;
    private static final String MESSAGE_LOG = "Message: {0}";

    public Mono<ServerResponse> saveProduct(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Product.class)
                .flatMap(productService::saveProduct)
                .flatMap(product -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(product))
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Product saved successfully"}
                ));
    }

    public Mono<ServerResponse> updateProduct(ServerRequest serverRequest) {
        int id = Integer.parseInt(serverRequest.pathVariable("id"));
        return serverRequest.bodyToMono(Product.class)
                .flatMap(product -> productService.updateProduct(id, product))
                .flatMap(product -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(product))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Product updated successfully"}));
    }

    public Mono<ServerResponse> listAllProducts(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.listAllProducts(), Product.class)
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Products retrieved successfully"}));
    }

    public Mono<ServerResponse> findProductById(ServerRequest serverRequest) {
        int id = Integer.parseInt(serverRequest.pathVariable("id"));
        return productService.findProductById(id)
                .flatMap(product -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(product))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Product retrieved successfully"}));
    }

    public Mono<ServerResponse> findProductByName(ServerRequest serverRequest) {
        String name = serverRequest.queryParam("name").orElse("");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.findProductsByName(name), Product.class)
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Products retrieved successfully by name"}));
    }

    public Mono<ServerResponse> updateProductStock(ServerRequest serverRequest) {
        int id = Integer.parseInt(serverRequest.pathVariable("id"));
        return serverRequest.bodyToMono(Integer.class) // Se espera que en el cuerpo solo venga el nuevo stock
                .flatMap(newStock -> productService.updateProductStock(id, newStock))
                .flatMap(product -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(product))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Product stock updated successfully"}));
    }

    public Mono<ServerResponse> deleteProductById(ServerRequest serverRequest) {
        int id = Integer.parseInt(serverRequest.pathVariable("id"));
        return productService.deleteProductById(id)
                .then(Mono.defer(() -> ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"Product deleted successfully"}));
    }

}
