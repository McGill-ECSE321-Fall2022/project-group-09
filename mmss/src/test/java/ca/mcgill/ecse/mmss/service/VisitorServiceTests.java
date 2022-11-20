package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.EmployeeRepository;
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
	 private EmployeeRepository employeeRepository;
	 
	 @Mock
	 private CommunicationRepository communicationRepository;
	 
	 @InjectMocks
	 private VisitorService visitorService;

	 // Four objects we will need in all our tests
	 private Person person;
	 private Visitor visitor;
	 private Visitor visitorTwo;
	 private Communication communication;
	 private String newName;
	 
	 @BeforeEach
	 public void createObjects() {

		 // create necessary objects for test
		 // This will Mock the content of your database
		 // Basically a fake database system
		 this.person = new Person(0, "John", "Coder");
		 this.communication = new Communication(2);
		 this.visitor = new Visitor("John@Coder", "ILikeSoftwareTests1", person);
		 this.visitor.setCommunication(communication);
		 this.visitor.setBalance(0); // setting it for test purposes
		 this.visitorTwo = new Visitor("Jon@Jones","Fngannou270", person);
		 this.newName = "travis@scott";
	 }
	 
	 @AfterEach
	 public void deleteObjects() {
		 // delete the objects from the test
	     
	     this.communication.delete();
	     this.visitor.delete();
	     this.visitorTwo.delete();
	     this.person.delete();
	 }
	 
	 @Test
	 public void testCreateVisitor() {
		 
		 when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null); 
		 
		 when(personRepository.save(any(Person.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0)); 
		 
		 when(communicationRepository.save(any(Communication.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		 
		 when(visitorRepository.save(any(Visitor.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		 
		 // call the service to create a loan
	     Visitor visitorCreated = visitorService.createVisitor("John", "Coder", "John@Coder", "ILikeSoftwareTests1"); 
	     
	     assertEquals(person.getFirstName(), visitorCreated.getPerson().getFirstName());
	     assertEquals(person.getLastName(), visitorCreated.getPerson().getLastName());
	     assertEquals(visitor.getUsername(), visitorCreated.getUsername());
	     assertEquals(visitor.getPassword(), visitorCreated.getPassword());
	     
	     verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername());
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
	 public void testCreateInvalidVisitorUsernameTaken() {
		 when(visitorRepository.findVisitorByUsername(visitor.getUsername())).thenAnswer((InvocationOnMock invocation) -> visitor); 
		 MmssException ex = assertThrows(MmssException.class, () -> visitorService.createVisitor("John", "Coder", visitor.getUsername(), "ILikeSoftwareTests1")); 

	     // assert the exception is thrown with the right message and status

	     assertEquals("The username entered is taken. Please enter another username.", ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_ACCEPTABLE, ex.getStatus()); 
	     verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername());
	 }
	 
	 @Test
	 public void testCreateInvalidVisitorPassword() {
		 
		 MmssException ex = assertThrows(MmssException.class, () -> visitorService.createVisitor("John", "Coder", "John@Coder", "password")); 

	     // assert the exception is thrown with the right message and status

	     assertEquals("The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.", ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_ACCEPTABLE, ex.getStatus()); 

	 }
	 
	 // a series of tests for create additional visitor
	 
	 @Test
	 public void testCreateAdditionalVisitor() {
		 when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer(new Answer() {
			 private int count = 0;
			 public Object answer (InvocationOnMock invocation) {
				 if (++count==1) {
					 return visitor;
				 }
				 return null;
			 }
		 }
			 
		 ); 

		 when(visitorRepository.save(any(Visitor.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		 
		 // call the service to create a loan
	     Visitor visitorCreated = visitorService.createAdditionalVisitor(visitor.getUsername(),"Jon@Jones", "Fngannou270"); 
	     
	     assertEquals(person.getFirstName(), visitorCreated.getPerson().getFirstName());
	     assertEquals(person.getLastName(), visitorCreated.getPerson().getLastName());
	     assertEquals(visitorTwo.getUsername(), visitorCreated.getUsername());
	     assertEquals(visitorTwo.getPassword(), visitorCreated.getPassword());
	     
	     verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername()); 
	     verify(visitorRepository, times(1)).save(any(Visitor.class)); 
	 }
	 
	 @Test
	 public void testCreateAdditionalInvalidVisitor() {
		 when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null); 
		 
		 MmssException ex = assertThrows(MmssException.class, () -> visitorService.createAdditionalVisitor("badUsername","Jon@Jones", "Fngannou270")); 

	     // assert the exception is thrown with the right message and status

	     assertEquals("There is no visitor with this username.", ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_FOUND, ex.getStatus()); 

	     // verify the repository calls

	    verify(visitorRepository, times(1)).findVisitorByUsername("badUsername");
	 }
	 
	 @Test
	 public void testCreateAdditionalInvalidVisitorBadNew() {
		 
		 when(visitorRepository.findVisitorByUsername(visitor.getUsername())).thenAnswer((InvocationOnMock invocation) -> visitor); 
		 MmssException ex = assertThrows(MmssException.class, () -> visitorService.createAdditionalVisitor(visitor.getUsername(),"invalid", "Fngannou270")); 

	     // assert the exception is thrown with the right message and status

	     assertEquals("The username entered is an invalid email address. Please enter another username.", ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_ACCEPTABLE, ex.getStatus()); 

	     // verify the repository calls

	    verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername());
	 }
	 
	 @Test
	 public void testCreateAdditionalInvalidVisitorExisting() {
		 
		 when(visitorRepository.findVisitorByUsername(visitor.getUsername())).thenAnswer((InvocationOnMock invocation) -> visitor); 
		 when(visitorRepository.findVisitorByUsername(visitorTwo.getUsername())).thenAnswer((InvocationOnMock invocation) -> visitorTwo);
		 MmssException ex = assertThrows(MmssException.class, () -> visitorService.createAdditionalVisitor(visitor.getUsername(),visitorTwo.getUsername(), "Fngannou270")); 

	     // assert the exception is thrown with the right message and status

	     assertEquals("The username entered is taken. Please enter another username.",ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_ACCEPTABLE, ex.getStatus()); 

	     // verify the repository calls

	    verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername());
	    verify(visitorRepository, times(1)).findVisitorByUsername(visitorTwo.getUsername());
	 }
	 
	 @Test
	 public void testCreateAdditionalInvalidVisitorBadPw() {
		 
		 when(visitorRepository.findVisitorByUsername(visitor.getUsername())).thenAnswer((InvocationOnMock invocation) -> visitor); 
		 when(visitorRepository.findVisitorByUsername("John@Jones")).thenAnswer((InvocationOnMock invocation) -> null); 
		 MmssException ex = assertThrows(MmssException.class, () -> visitorService.createAdditionalVisitor(visitor.getUsername(),"John@Jones", "travgoat")); 

	     // assert the exception is thrown with the right message and status

	     assertEquals("The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.", ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_ACCEPTABLE, ex.getStatus()); 

	     // verify the repository calls

	    verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername());
	    verify(visitorRepository, times(1)).findVisitorByUsername("John@Jones");
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
	public void testGetVisitorByInvalidUsername() {
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
	
	@Test
	public void testUpdateVisitorUsername() {
		when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer(new Answer() {
			 private int count = 0;
			 public Object answer (InvocationOnMock invocation) {
				 if (++count==1) {
					 return visitor;
				 }
				 return null;
			 }
		 }); 

		when(visitorRepository.save(any(Visitor.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		
		Visitor visitorUpdated = visitorService.updateVisitorUsername(visitor.getUsername(), newName);
		
		assertEquals(newName, visitorUpdated.getUsername());
		
		verify(visitorRepository, times (1)).findVisitorByUsername(visitor.getUsername()); 
		verify(visitorRepository, times(1)).save(any(Visitor.class)); 
	}
	
	@Test
	public void testUpdateVisitorInvalidUsernameNotExisting() {
		final String invalidUsername = "bobcode";
        
		when(visitorRepository.findVisitorByUsername(invalidUsername)).thenAnswer((InvocationOnMock invocation) -> null); 
		
		MmssException ex = assertThrows(MmssException.class, () -> visitorService.updateVisitorUsername(invalidUsername,"Skeletons@travis"));
		
		// check the message contains the right message and status
        assertEquals("There is no such visitor account with this username.", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        
        verify(visitorRepository, times(1)).findVisitorByUsername(invalidUsername);
        
	}
	
	@Test
	public void testUpdateVisitorInvalidUsername() {
		final String invalidUsername = "bobcode";
        
		when(visitorRepository.findVisitorByUsername(visitor.getUsername())).thenAnswer((InvocationOnMock invocation) -> visitor); 
		
		MmssException ex = assertThrows(MmssException.class, () -> visitorService.updateVisitorUsername(visitor.getUsername(),invalidUsername));
		
		// check the message contains the right message and status
        assertEquals("The username entered is an invalid email address. Please enter another username.", ex.getMessage());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        
        verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername());
        
	}
	
	@Test
	public void testUpdateVisitorInvalidExistingUsername() {
        
		when(visitorRepository.findVisitorByUsername(visitor.getUsername())).thenAnswer((InvocationOnMock invocation) -> visitor); 
		when(visitorRepository.findVisitorByUsername(visitorTwo.getUsername())).thenAnswer((InvocationOnMock invocation) -> visitorTwo); 
		MmssException ex = assertThrows(MmssException.class, () -> visitorService.updateVisitorUsername(visitor.getUsername(),visitorTwo.getUsername()));
		
		// check the message contains the right message and status
        assertEquals("The username entered is taken. Please enter another username.", ex.getMessage());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        
        verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername());
        verify(visitorRepository, times(1)).findVisitorByUsername(visitorTwo.getUsername());
	}
	
	@Test
	public void testUpdateVisitorPassword() {
		when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> visitor); 

		when(visitorRepository.save(any(Visitor.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		
		Visitor visitorUpdated = visitorService.updateVisitorPassword(visitor.getUsername(),visitor.getPassword(),"Ilovecontrollers2");
		
		assertEquals("Ilovecontrollers2", visitorUpdated.getPassword());
		
		verify(visitorRepository, times (1)).findVisitorByUsername(visitor.getUsername());
		verify(visitorRepository, times(1)).save(any(Visitor.class)); 
	}
	
	@Test
	public void testUpdateVisitorInvalidPasswordNoSpecial() {
		final String invalidPassword = "sdpisabeauty";
        
		when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> visitor); 
		
		MmssException ex = assertThrows(MmssException.class, () -> visitorService.updateVisitorPassword(visitor.getUsername(),visitor.getPassword(),invalidPassword));
		
		// check the message contains the right message and status
        assertEquals("The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.", ex.getMessage());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        
        verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername());
	}
	
	@Test
	public void testUpdateVisitorInvalidPasswordBadPw() {
		final String invalidPassword = "IlikeJava1";
        
		when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> visitor); 
		
		MmssException ex = assertThrows(MmssException.class, () -> visitorService.updateVisitorPassword(visitor.getUsername(),"savCodes3",invalidPassword));
		
		// check the message contains the right message and status
        assertEquals("The password entered is not correct. Please enter correct password.", ex.getMessage());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        
        verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername());
	}
	
	@Test
	public void testUpdateVisitorInvalidPasswordInvalidUser() {
		final String validPassword = "IlikeJava1";
        
		when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null); 
		
		MmssException ex = assertThrows(MmssException.class, () -> visitorService.updateVisitorPassword("ksi@deji","savCodes3",validPassword));
		
		// check the message contains the right message and status
        assertEquals("There is no such visitor account with this username.", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        
        verify(visitorRepository, times(1)).findVisitorByUsername("ksi@deji");
	}
	
	@Test
	public void testUpdateVisitorBalance() {
		when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> visitor);
		when(visitorRepository.save(any(Visitor.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		
		Visitor visitorUpdated = visitorService.updateVisitorBalance(visitor.getUsername(), 25);
		assertEquals(visitorUpdated.getBalance(), 25);
		
		verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername());
		verify(visitorRepository, times(1)).save(any(Visitor.class));
	}
	
	@Test
	public void testUpdateVisitorBalanceInvalidUser() {
		final String invalidUsername = "bobcode";
        
		when(visitorRepository.findVisitorByUsername(invalidUsername)).thenAnswer((InvocationOnMock invocation) -> null); 
		
		MmssException ex = assertThrows(MmssException.class, () -> visitorService.updateVisitorBalance(invalidUsername,25));
		
		// check the message contains the right message and status
        assertEquals("There is no such visitor account with this username.", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        
        verify(visitorRepository, times(1)).findVisitorByUsername(invalidUsername);
	}
	
	// needs modification
	@Test
	public void testDeleteVisitor() {
		when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> visitor); 
		
        visitorService.deleteVisitor(visitor.getUsername());
   
        verify(visitorRepository, times(1)).deleteById(visitor.getUsername());
	}
	
	@Test
	public void testDeleteVisitorInvalidUsername() {
		when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null); 

        MmssException ex = assertThrows(MmssException.class,() -> visitorService.deleteVisitor(visitor.getUsername()));

        // assert the exception is as expected
        assertEquals("The visitor with this username was not found", ex.getMessage()); 
        assertEquals (HttpStatus.NOT_FOUND, ex.getStatus());
	}
	
	@Test
	public void testGetAllVisitorsInvalidPerson() {
		when(personRepository.findPersonByPersonId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> null);
		
		MmssException ex = assertThrows(MmssException.class,() -> visitorService.getAllVisitorsByPerson(-22));

        // assert the exception is as expected
        assertEquals("The person with this id was not found", ex.getMessage()); 
        assertEquals (HttpStatus.NOT_FOUND, ex.getStatus());
	}
	
	@Test
	public void testCheckValidUser() {
		
		boolean isValidUser = visitorService.checkValidUser("John@Jones");
		assertEquals(true, isValidUser);
	
	}
	
	@Test
	public void testCheckInvalidUserBadUser() {
		
		boolean isValidUser = visitorService.checkValidUser("travis");
		assertEquals(false, isValidUser);
		
	}	
	
	@Test
	public void testCheckValidPassword() {
		
		boolean isValidPass = visitorService.checkValidPassword("IlikeCoding1");
		assertEquals(true, isValidPass);
	
	}
	
	@Test
	public void testCheckInValidPasswordShort() {
		
		boolean isValidPass = visitorService.checkValidPassword("hi");
		assertEquals(false, isValidPass);
	
	}
	
	@Test
	public void testCheckInValidPasswordNoDigit() {
		
		boolean isValidPass = visitorService.checkValidPassword("IlikeCoding");
		assertEquals(false, isValidPass);
	
	}
	
	@Test
	public void testCheckInValidPasswordNoCapital() {
		
		boolean isValidPass = visitorService.checkValidPassword("ilikecoding2");
		assertEquals(false, isValidPass);
	
	}
}
