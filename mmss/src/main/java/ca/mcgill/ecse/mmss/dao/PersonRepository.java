package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
package ca.mcgill.ecse.mmss.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    Person findPersonByPersonId(int personId);
}