package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse.mmss.model.Manager;
import ca.mcgill.ecse.mmss.model.Person;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class ManagerRepositoryTests {
  
  // repository we are testing
  @Autowired
  private ManagerRepository managerRepository; 
  
  // also need person database to store the manager
  private PersonRepository personRepository;
  
  @AfterEach
  public void clearDatabase() {
	  
      // empty manager repository
      managerRepository.deleteAll(); 
      
      // empty the person repository
      personRepository.deleteAll();
  }

  @Test 
  public void testPersistAndLoadManager() { 
    
	// create Manager Person
	Person person = new Person();
	person.setFirstName("Big");
	person.setLastName("Boss");
	
	// save the person to repository
	personRepository.save(person);
	
	
    // create the manager with its fields         
	String username = "manager"; 
	String password = "bigboss"; 
	Manager manager = new Manager(username, password, person);
    
    // save the manager  
    managerRepository.save(manager); 
    
    // set manager to null
    manager = null;
    		
    // get the manager from the database using the username
    manager = managerRepository.findManagerByUsername(username); 
    
    // run J-Unit tests
    assertNotNull(manager);
    assertEquals(username, manager.getUsername());
    assertEquals(password, manager.getPassword()); 
   
  }
}
