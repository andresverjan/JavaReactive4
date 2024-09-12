package valko.co.ejercicio5.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import valko.co.ejercicio5.model.Person;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<Person, Integer> {
}
