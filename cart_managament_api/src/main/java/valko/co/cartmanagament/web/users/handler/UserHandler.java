package valko.co.cartmanagament.web.users.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import valko.co.cartmanagament.model.users.User;
import valko.co.cartmanagament.service.UserService;

import java.util.logging.Level;

@Log
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;
    public static final String MESSAGE_LOG = "Message: {0}";

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(User.class)
                .flatMap(userService::createUser)
                .flatMap(savedUser -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedUser))
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"User created successfully"}));
    }

    public Mono<ServerResponse> findUserById(ServerRequest serverRequest) {
        Integer id = Integer.parseInt(serverRequest.pathVariable("id"));
        return userService.findUserById(id)
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"User retrieved successfully"}));
    }

    public Mono<ServerResponse> findUserByEmail(ServerRequest serverRequest) {
        String email = serverRequest.queryParam("email").orElse("");
        return userService.findUserByEmail(email)
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"User retrieved successfully by email"}));
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        Integer id = Integer.parseInt(serverRequest.pathVariable("id"));
        return serverRequest.bodyToMono(User.class)
                .flatMap(user -> userService.updateUser(id, user))
                .flatMap(updatedUser -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedUser))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"User updated successfully"}));
    }

    public Mono<ServerResponse> deleteUserById(ServerRequest serverRequest) {
        Integer id = Integer.parseInt(serverRequest.pathVariable("id"));
        return userService.deleteUserById(id)
                .then(ServerResponse.noContent().build())
                .doOnSuccess(response -> log.log(Level.INFO,
                        MESSAGE_LOG,
                        new String[]{"User deleted successfully"}));
    }

}
