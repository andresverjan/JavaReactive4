package reactor.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.model.PersonEntity;

@Repository
public interface PersonRepository extends ReactiveCrudRepository<PersonEntity, Integer> {
}
