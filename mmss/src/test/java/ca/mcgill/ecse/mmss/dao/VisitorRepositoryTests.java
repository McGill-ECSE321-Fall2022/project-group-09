package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Visitor;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class VisitorRepositoryTests {
	// repository we are testing
	  @Autowired
	  private VisitorRepository visitorRepository;
	  
	  //also need a person in order to have a visitor account
	  @Autowired  
	  private PersonRepository personRepository; 
	 
	  //also need a person in order to have a visitor account
	  @Autowired  
	  private CommunicationRepository communicationRepository; 
	  
	  @AfterEach
	  public void clearDatabase() {
		  
		  
		  // Delete all the visitors first
	      visitorRepository.deleteAll(); 
	      // delete communication and person repositories
		  communicationRepository.deleteAll();
		  personRepository.deleteAll();
	      
	  }

	  @Test 
	  public void testPersistAndLoadVisitor() { 
		  
		// create a Person for visitor
		String fName = "Scott";
		String lName = "Lang";
		Person person = new Person();
		person.setFirstName(fName);
		person.setLastName(lName);
		// save to personRepository
		personRepository.save(person);
		int personId = person.getPersonId();
		
		// create a communication for the visitor
		Communication communication = new Communication();
		communicationRepository.save(communication);
		int communicationId = communication.getCommunicationId();
		
	    
	    // create the Visitor and populate its field 
		String username = "John";
	    String password = "password";
	    int balance = 200; 
	    Visitor visitor = new Visitor();  
	    visitor.setPerson(person);
	    visitor.setCommunication(communication);
	    visitor.setUsername(username);
	    visitor.setPassword(password);
	    visitor.setBalance(balance); 
	    
	    // save the Visitor    
	    visitorRepository.save(visitor); 
	    
	    // get its username    
	    String uName = visitor.getUsername();   
	    
	    // set visitor to null    
	    visitor = null;
	    person = null;
	    communication = null;
	    
	    // get the visitor from the database using the username
	    visitor = visitorRepository.findVisitorByUsername(uName); 
	    
	    // run J-Unit tests
	    assertNotNull(visitor);
	    assertEquals(balance, visitor.getBalance());
	    assertEquals(password, visitor.getPassword());
		assertEquals(uName, visitor.getUsername());

		assertNotNull(visitor.getPerson());
		assertEquals(personId, visitor.getPerson().getPersonId());

		assertNotNull(visitor.getCommunication());
		assertEquals(communicationId, visitor.getCommunication().getCommunicationId());
	    
	  }

}
