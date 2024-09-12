package valko.co.ejercicio5.controllers.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import valko.co.ejercicio5.model.Person;
import valko.co.ejercicio5.service.PersonService;

@Component
@RequiredArgsConstructor
public class PersonHandler {

    private final PersonService personService;

    public Mono<ServerResponse> retrieveAllPersons(ServerRequest serverRequest) {

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(personService.listAllPersons(), Person.class)
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> System.out.println("Listado con exito: " + response));
    }

    public Mono<ServerResponse> retrievePersonById(ServerRequest serverRequest) {

        int personId = Integer.parseInt(serverRequest.pathVariable("id"));

        return personService.findPersonById(personId)
                .flatMap(person -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(person))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> System.out.println("Encontrado con exito: " + response));
    }

    public Mono<ServerResponse> savePerson(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(Person.class)
                .flatMap(personService::savePerson)
                .flatMap(person -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(person))
                .doOnSuccess(response -> System.out.println("Salvado con exito: " + response));
    }


    public Mono<ServerResponse> updatePerson(ServerRequest serverRequest) {

        int personId = Integer.parseInt(serverRequest.pathVariable("id"));

        return serverRequest.bodyToMono(Person.class)
                .flatMap(person -> personService.updatePerson(personId, person))
                .flatMap(updatedPerson -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedPerson))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnSuccess(response -> System.out.println("Actualizado con exito: " + response));
    }


    public Mono<ServerResponse> deletePerson(ServerRequest serverRequest) {

        int personId = Integer.parseInt(serverRequest.pathVariable("id"));

        return personService.deletePersonById(personId)
                .then(ServerResponse.noContent().build())
                .doOnSuccess(response -> System.out.println("Eliminado con exito: " + response));
    }
}
