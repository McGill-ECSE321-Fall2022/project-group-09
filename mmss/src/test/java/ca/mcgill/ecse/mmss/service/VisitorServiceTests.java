package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Communication;

@ExtendWith(MockitoExtension.class)
public class VisitorServiceTests {
	
	 @Mock
	 private PersonRepository personRepository;

	 @Mock
	 private VisitorRepository visitorRepository;
	 
	 @Mock
	 private CommunicationRepository communicationRepository;
	 
	 @InjectMocks
	 private VisitorService visitorService;

	 // Four objects we will need in all our tests
	 private Person person;
	 private Visitor visitor;
	 private Visitor visitorTwo;
	 private Communication communication;
	 
	 @BeforeEach
	 public void createObjects() {

		 // create necessary objects for test
		 // This will Mock the content of your database
		 // Basically a fake database system
		 this.person = new Person(0, "John", "Coder");
		 this.communication = new Communication(2);
		 this.visitor = new Visitor("John@Coder", "ILikeSoftwareTests1", person);
		 visitor.setPerson(person);
		 visitor.setCommunication(communication);
		 this.visitorTwo = new Visitor("Jon@Jones","Fngannou270", person);
	 }
	 
	 @AfterEach
	 public void deleteObjects() {
		 // delete the objects from the test
	     this.person.delete();
	     this.communication.delete();
	     this.visitor.delete();
	 }
	 
	 @Test
	 public void testCreateVisitor() {
		 
		 when(personRepository.save(any(Person.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0)); 
		 
		 when(communicationRepository.save(any(Communication.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		 
		 when(visitorRepository.save(any(Visitor.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		 
		 // call the service to create a loan
	     Visitor visitorCreated = visitorService.createVisitor("John", "Coder", "John@Coder", "ILikeSoftwareTests1"); 
	     
	     assertEquals(person.getFirstName(), visitorCreated.getPerson().getFirstName());
	     assertEquals(person.getLastName(), visitorCreated.getPerson().getLastName());
	     assertEquals(visitor.getUsername(), visitorCreated.getUsername());
	     assertEquals(visitor.getPassword(), visitorCreated.getPassword());
	     
	     verify(personRepository, times(1)).save(any(Person.class)); 
	     verify(visitorRepository, times(1)).save(any(Visitor.class)); 
	     
	 }
	 
	 @Test
	 public void testCreateInvalidVisitorUsername() {
		 
		 MmssException ex = assertThrows(MmssException.class, () -> visitorService.createVisitor("John", "Coder", "badUsernameNoSign", "ILikeSoftwareTests1")); 

	     // assert the exception is thrown with the right message and status

	     assertEquals("The username entered is an invalid email address. Please enter another username.", ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_ACCEPTABLE, ex.getStatus()); 

	 }
	 
	 @Test
	 public void testCreateInvalidVisitorPassword() {
		 
		 MmssException ex = assertThrows(MmssException.class, () -> visitorService.createVisitor("John", "Coder", "John@Coder", "password")); 

	     // assert the exception is thrown with the right message and status

	     assertEquals("The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.", ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_ACCEPTABLE, ex.getStatus()); 

	 }
	 
	 @Test
	 public void testCreateAdditionalVisitor() {
		 
		 when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> visitor); 
		 
		 when(visitorRepository.save(any(Visitor.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		 
		 // call the service to create a loan
	     Visitor visitorCreated = visitorService.createAdditionalVisitor("John@Coder","Jon@Jones", "Fngannou270"); 
	     
	     assertEquals(person.getFirstName(), visitorCreated.getPerson().getFirstName());
	     assertEquals(person.getLastName(), visitorCreated.getPerson().getLastName());
	     assertEquals(visitorTwo.getUsername(), visitorCreated.getUsername());
	     assertEquals(visitorTwo.getPassword(), visitorCreated.getPassword());
	     
	     verify(visitorRepository, times(1)).findVisitorByUsername("John@Coder"); 
	     verify(visitorRepository, times(1)).save(any(Visitor.class)); 
	 }
	 
	 @Test
	 public void testCreateAdditionalInvalidVisitor() {
		 when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null); 
		 
		 MmssException ex = assertThrows(MmssException.class, () -> visitorService.createAdditionalVisitor("badUsername","Jon@Jones", "Fngannou270")); 

	     // assert the exception is thrown with the right message and status

	     assertEquals("The visitor with this username was not found", ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_FOUND, ex.getStatus()); 

	     // verify the repository calls

	    verify(visitorRepository, times(1)).findVisitorByUsername("badUsername");
	 }
	 
	 @Test 
	 public void testGetVisitorByUsername() { 

		 // setup mocks
	     when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> visitor ); 

	     // call service layer
	     Visitor retrievedVisitor = visitorService.getVisitorByUsername("John@Coder"); 

	     // assertions
	     assertEquals ("John@Coder", retrievedVisitor.getUsername()); 
	     assertEquals(person.getFirstName(), retrievedVisitor.getPerson().getFirstName());
	     assertEquals(person.getLastName(), retrievedVisitor.getPerson().getLastName());

	     // verify calls to repositories
	     verify(visitorRepository, times (1)).findVisitorByUsername("John@Coder"); 
	     
	}
	 
	@Test
	public void testGetVisitorByInvalidUsername () {
		final String invalidUsername = "bobcode";
        // set up mock
        when(visitorRepository.findVisitorByUsername(invalidUsername)).thenAnswer((InvocationOnMock invocation) -> null);

        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> visitorService.getVisitorByUsername(invalidUsername));

        // check the message contains the right message and status
        assertEquals("There is no such visitor account with this username.", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

        // verify calls to repositories
        verify(visitorRepository, times(1)).findVisitorByUsername(invalidUsername);
	}
	
	
	 

}
