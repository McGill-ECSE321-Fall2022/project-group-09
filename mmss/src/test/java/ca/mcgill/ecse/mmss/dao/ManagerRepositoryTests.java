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
  @Autowired
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
	Manager manager = new Manager(); 
	manager.setUsername(username); 
	manager.setPerson(person); 	
	manager.setPassword(password); 
	
    
    // save the manager  
    managerRepository.save(manager); 

    
    // set manager to null
    manager = null;

    		
    // get the manager from the database using the username
    manager = managerRepository.findManagerByUsername(username); 
    
    // make sure that the manager and its person are not null
    assertNotNull(manager);
    assertNotNull(manager.getPerson());
    
    // check primary key and foreign key constraints
    // check an attribute is stored properly
    assertEquals(username, manager.getUsername());
    assertEquals(person.getPersonId(), manager.getPerson().getPersonId()); 
   
  }
}
