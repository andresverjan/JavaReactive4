package reactor.controller;

import com.reactorproject.bancolombiacurso.model.PersonEntity;
import com.reactorproject.bancolombiacurso.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/personas")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public Flux<PersonEntity> getPersons(){
        return personService.getPersons();
    }
    @GetMapping("/{id}")
    public Mono<PersonEntity> getPersonById(@PathVariable Integer id){
        return personService.getPersonById(id);
    }
    @PostMapping
    public Mono<PersonEntity> save(@RequestBody PersonEntity person){
        return personService.save(person);
    }
    @PutMapping
    public Mono<String> update(@RequestBody PersonEntity person){
        return personService.update(person);
    }
    @DeleteMapping("/{id}")
    public Mono<Void> deletePersonById(@PathVariable Integer id){
        return personService.deletePersonById(id);
    }
}
