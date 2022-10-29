package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse.mmss.model.Person;

/**
 * Person Repository testing class which initiates a person repository, executes the tests, then clears each instance from the database.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PersonRepositoryTests {
  
  // repository we are testing
  @Autowired
  private PersonRepository personRepository;
  
  @AfterEach
  public void clearDatabase() {
      
      // empty the person repository
      personRepository.deleteAll();
  }

  /**
 * Person testing method which creates, populates the attributes, sets associations, and saves each person object and identifier.
 * It can then test to make sure each object reached from the person found in the repository is not null and that each initially saved Id corresponds to the one
 * reached from the repository.
 */
  @Test 
  public void testPersistAndLoadPerson() { 
    
	// create Person
	Person person = new Person();
	String firstName = "Test";
	String lastName = "Person";
	person.setFirstName(firstName);
	person.setLastName(lastName);
	
	// save person to repository
	personRepository.save(person);
	
	// get person id
	int personId = person.getPersonId(); 
    
    // set person to null
    person = null;
    		
    // get the Person from the repository using the ID
    person = personRepository.findPersonByPersonId(personId);
    
    // check not null and primary key
    assertNotNull(person);
    assertEquals(personId, person.getPersonId());
    
    // check an attribute is stored properly
    assertEquals(firstName, person.getFirstName()); 
  }
}
