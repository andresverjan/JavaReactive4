package valko.co.ejercicio5.controllers.router;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import valko.co.ejercicio5.controllers.handler.PersonHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class PersonRouterRest {

    private static final String BASE_PATH = "/api/v1/persons";

    private final PersonHandler personHandler;

    @Bean
    public RouterFunction<ServerResponse> routerPerson() {
        return route()
                .GET(BASE_PATH, personHandler::retrieveAllPersons)
                .GET(BASE_PATH.concat("/{id}"), personHandler::retrievePersonById)
                .POST(BASE_PATH, personHandler::savePerson)
                .PUT(BASE_PATH.concat("/{id}"), personHandler::updatePerson)
                .DELETE(BASE_PATH.concat("/{id}"), personHandler::deletePerson)
                .build();
    }
}
