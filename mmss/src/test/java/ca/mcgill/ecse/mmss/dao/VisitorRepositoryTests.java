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
	    
	    // CASE 1: create the Visitor with Communication and populate its field 
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
	    String username1 = visitor.getUsername();   
	    
	    // set visitor to null    
	    visitor = null;
	    person = null;
	    communication = null;
	    
	    visitor = visitorRepository.findVisitorByUsername(username1); 
	    
	    // CASE 2: create the Visitor without Communication and populate its field 
		String fName2 = "Emily";
		String lName2 = "Oliver";
		Person person2 = new Person();
		
		person2.setFirstName(fName2);
		person2.setLastName(lName2);
		
		personRepository.save(person2);
		int personId2 = person2.getPersonId();
		
	    String username2 = "Emily96";
	    String password2 = "notAPassword";
	    
	    int balance2 = 600;
	    
	    Visitor visitor2 = new Visitor();  
	    visitor2.setPerson(person2);
	    visitor2.setUsername(username2);
	    visitor2.setPassword(password2);
	    visitor2.setBalance(balance2); 
	    
	    // save the Visitor    
	    visitorRepository.save(visitor2); 
	    
	    // get its username    
	    String userName2 = visitor2.getUsername();   
	    
	    // set visitor to null    
	    visitor2 = null;
	    person2 = null;
	    
	    // get the visitor from the database using the username
	    visitor2 = visitorRepository.findVisitorByUsername(userName2); 
	    
	    // run J-Unit tests for CASE 1: VISITOR with COMMUNICATION
	    assertNotNull(visitor);
	    assertNotNull(visitor.getPerson());
		assertNotNull(visitor.getCommunication());
	    
        // check an attribute is stored properly
		assertEquals(username1, visitor.getUsername());
		assertEquals(personId, visitor.getPerson().getPersonId());
		assertEquals(communicationId, visitor.getCommunication().getCommunicationId());
		
		 // run J-Unit tests for Case 2: VISITOR without COMMUNICATION
	    assertNotNull(visitor2);
	    assertNotNull(visitor2.getPerson());
	    
        // check an attribute is stored properly
		assertEquals(userName2, visitor2.getUsername());
		assertEquals(personId2, visitor2.getPerson().getPersonId());
		


	  }
}
