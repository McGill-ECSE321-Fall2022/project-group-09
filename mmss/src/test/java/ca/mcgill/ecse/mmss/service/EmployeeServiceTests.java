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
import ca.mcgill.ecse.mmss.dao.EmployeeRepository;
import ca.mcgill.ecse.mmss.dao.ShiftRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Shift;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
	
	 @Mock
	 private PersonRepository personRepository;

	 @Mock
	 private VisitorRepository visitorRepository;
	 
	 @Mock
	 private EmployeeRepository employeeRepository;
	 
	 @Mock
	 private ShiftRepository shiftRepository;
	 
	 @Mock
	 private CommunicationRepository communicationRepository;
	 
	 @InjectMocks
	 private EmployeeService employeeService;

	 // Four objects we will need in all our tests
	 private Person person;
	 private Person personTwo;
	 private Visitor visitor;
	 private Employee employee;
	 private Employee employeeTwo;
	 private Communication communication;
	 private String newName;
	 
	 @BeforeEach
	 public void createObjects() {

		 // create necessary objects for test
		 // This will Mock the content of your database
		 // Basically a fake database system
		 this.person = new Person(0, "John", "Coder");
		 this.personTwo = new Person(1,"Tenoch", "Huerta");
		 this.communication = new Communication(2);
		 this.employee = new Employee();
		 employee.setUsername("John@Coder");
		 employee.setPassword("testPassword1");
		 employee.setPhoneNumber("514-396-2314");
		 employee.setPerson(person);
		 employee.setCommunication(communication);
		 this.employeeTwo = new Employee();
		 employeeTwo.setUsername("Tenoch@Huerta");
		 employeeTwo.setPhoneNumber("514-789-9878");
		 employeeTwo.setPerson(personTwo);
		 this.visitor = new Visitor("Jon@Jones","Fngannou270", personTwo);
		 this.newName = "travis@scott";
	 }
	 
	 @AfterEach
	 public void deleteObjects() {
		 // delete the objects from the test
	     
	     this.personTwo.delete();
	     this.communication.delete();
	     this.visitor.delete();
	     this.employee.delete();
	     this.employeeTwo.delete();
	     this.person.delete();
	 }
	 
	 @Test
	 public void testCreateEmployee() {
		 
		 when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null); 
		 
		 when(personRepository.save(any(Person.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0)); 
		 
		 when(communicationRepository.save(any(Communication.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		 
		 when(employeeRepository.save(any(Employee.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		 // call the service to create a loan
	     Employee employeeCreated = employeeService.createEmployee("John", "Coder", "John@Coder", "password", "514-396-2314");
	     
	     assertEquals(person.getFirstName(), employeeCreated.getPerson().getFirstName());
	     assertEquals(person.getLastName(), employeeCreated.getPerson().getLastName());
	     assertEquals(employee.getUsername(), employeeCreated.getUsername());
	     assertEquals(employee.getPhoneNumber(), employeeCreated.getPhoneNumber());
	     
	     verify(employeeRepository, times(1)).findEmployeeByUsername(employee.getUsername());
	     verify(personRepository, times(1)).save(any(Person.class)); 
	     verify(employeeRepository, times(1)).save(any(Employee.class)); 
	     
	 }
	 
	 @Test
	 public void testCreateInvalidEmployeeUsername() {
		 MmssException ex = assertThrows(MmssException.class, () -> employeeService.createEmployee("John", "Coder", "badUsernameNoSign", "password", "514-396-2314"));

	     // assert the exception is thrown with the right message and status

	     assertEquals("The username entered is an invalid email address. Please enter another username.", ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_ACCEPTABLE, ex.getStatus()); 
	 }
	 
	 @Test
	 public void testCreateInvalidEmployeeUsernameTaken() {
		 when(employeeRepository.findEmployeeByUsername(employee.getUsername())).thenAnswer((InvocationOnMock invocation) -> employee); 
		 MmssException ex = assertThrows(MmssException.class, () -> employeeService.createEmployee("John", "Coder", employee.getUsername(), "password", "514-396-2314"));

	     // assert the exception is thrown with the right message and status

	     assertEquals("The username entered is taken. Please enter another username.", ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_ACCEPTABLE, ex.getStatus()); 
	     verify(employeeRepository, times(1)).findEmployeeByUsername(employee.getUsername());
	 }
	 
	 @Test
	 public void testCreateInvalidEmployeePhoneNumber() {
		 
		 MmssException ex = assertThrows(MmssException.class, () -> employeeService.createEmployee("John", "Coder", visitor.getUsername(), "password", "514-396-2314--"));

	     // assert the exception is thrown with the right message and status

	     assertEquals("Please enter a valid phone number in the format xxx-xxx-xxxx.", ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_ACCEPTABLE, ex.getStatus()); 

	 }
	 
	 @Test
	 public void testCreateAdditionalVisitor() {
		 when(employeeRepository.findEmployeeByUsername(employee.getUsername())).thenAnswer((InvocationOnMock invocation) -> employee); 
		 when(visitorRepository.findVisitorByUsername(visitor.getUsername())).thenAnswer((InvocationOnMock invocation) -> null); 
		 when(visitorRepository.save(any(Visitor.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		 
		 // call the service to create a loan
	     Visitor visitorCreated = employeeService.createVisitorForEmployee(employee.getUsername(),visitor.getUsername(), visitor.getPassword()); 
	     
	     assertEquals(person.getFirstName(), visitorCreated.getPerson().getFirstName());
	     assertEquals(person.getLastName(), visitorCreated.getPerson().getLastName());
	     assertEquals(visitor.getUsername(), visitorCreated.getUsername());
	     assertEquals(visitor.getPassword(), visitorCreated.getPassword());
	     
	     verify(employeeRepository, times(1)).findEmployeeByUsername(employee.getUsername()); 
	     verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername()); 
	     verify(visitorRepository, times(1)).save(any(Visitor.class)); 
	 }
	 
	 @Test
	 public void testCreateAdditionalInvalidEmployee() {
		 when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null); 
		 
		 MmssException ex = assertThrows(MmssException.class, () -> employeeService.createVisitorForEmployee("badUsername",visitor.getUsername(), visitor.getPassword())); 

	     // assert the exception is thrown with the right message and status

	     assertEquals("There is no employee account with that username.", ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_FOUND, ex.getStatus()); 

	     // verify the repository calls

	    verify(employeeRepository, times(1)).findEmployeeByUsername("badUsername");
	 }
	 
	 @Test
	 public void testCreateAdditionalVisitorInvalidNewUsername() {
		 
		 when(employeeRepository.findEmployeeByUsername(employee.getUsername())).thenAnswer((InvocationOnMock invocation) -> employee); 
		 MmssException ex = assertThrows(MmssException.class, () -> employeeService.createVisitorForEmployee(employee.getUsername(),"kulkulkan", visitor.getPassword())); 

	     // assert the exception is thrown with the right message and status

	     assertEquals("The username entered is an invalid email address. Please enter another username.", ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_ACCEPTABLE, ex.getStatus()); 

	     // verify the repository calls

	    verify(employeeRepository, times(1)).findEmployeeByUsername(employee.getUsername());
	 }
	 
	 @Test
	 public void testCreateAdditionalInvalidVisitorExisting() {
		 
		 when(employeeRepository.findEmployeeByUsername(employee.getUsername())).thenAnswer((InvocationOnMock invocation) -> employee); 
		 when(visitorRepository.findVisitorByUsername(visitor.getUsername())).thenAnswer((InvocationOnMock invocation) -> visitor);
		 MmssException ex = assertThrows(MmssException.class, () -> employeeService.createVisitorForEmployee(employee.getUsername(),visitor.getUsername(), visitor.getPassword())); 

	     // assert the exception is thrown with the right message and status

	     assertEquals("The username entered is taken. Please enter another username.",ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_ACCEPTABLE, ex.getStatus()); 

	     // verify the repository calls

	    verify(employeeRepository, times(1)).findEmployeeByUsername(employee.getUsername());
	    verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername());
	 }
	 
	 @Test
	 public void testCreateAdditionalInvalidVisitorBadPw() {
		 
		 when(employeeRepository.findEmployeeByUsername(employee.getUsername())).thenAnswer((InvocationOnMock invocation) -> employee); 
		 when(visitorRepository.findVisitorByUsername(visitor.getUsername())).thenAnswer((InvocationOnMock invocation) -> null); 
		 MmssException ex = assertThrows(MmssException.class, () -> employeeService.createVisitorForEmployee(employee.getUsername(),visitor.getUsername(), "kulkulan")); 

	     // assert the exception is thrown with the right message and status

	     assertEquals("The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.", ex.getMessage()); 
	     assertEquals (HttpStatus.NOT_ACCEPTABLE, ex.getStatus()); 

	     // verify the repository calls
	    verify(employeeRepository, times(1)).findEmployeeByUsername(employee.getUsername());
	    verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername());
	 }
	 
	 @Test 
	 public void testGetEmployeeByUsername() { 

		 // setup mocks
	     when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> employee ); 

	     // call service layer
	     Employee retrievedEmployee = employeeService.getEmployeeByUsername(employee.getUsername()); 

	     // assertions
	     assertEquals (employee.getUsername(), retrievedEmployee.getUsername()); 
	     assertEquals(person.getFirstName(), retrievedEmployee.getPerson().getFirstName());
	     assertEquals(person.getLastName(), retrievedEmployee.getPerson().getLastName());

	     // verify calls to repositories
	     verify(employeeRepository, times (1)).findEmployeeByUsername(employee.getUsername()); 
	     
	 }
	 
	 @Test
     public void testGetEmployeeByInvalidUsername() {
		final String invalidUsername = "bobcode";
	    // set up mock
	    when(employeeRepository.findEmployeeByUsername(invalidUsername)).thenAnswer((InvocationOnMock invocation) -> null);

	    // call service layer and get the exception
	    MmssException ex = assertThrows(MmssException.class, () -> employeeService.getEmployeeByUsername(invalidUsername));

	    // check the message contains the right message and status
	    assertEquals("There is no such employee with this username.", ex.getMessage());
	    assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

	    // verify calls to repositories
	    verify(employeeRepository, times(1)).findEmployeeByUsername(invalidUsername);
	 }
	 
	 @Test
	 public void testUpdateEmployeeUsername() {
		when(employeeRepository.findEmployeeByUsername(employee.getUsername())).thenAnswer((InvocationOnMock invocation) -> employee); 
		when(employeeRepository.findEmployeeByUsername(newName)).thenAnswer((InvocationOnMock invocation) -> null); 
		when(employeeRepository.save(any(Employee.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
			
		Employee employeeUpdated = employeeService.updateEmployeeUsername(employee.getUsername(), newName);
			
		assertEquals(newName, employeeUpdated.getUsername());
			
		verify(employeeRepository, times (1)).findEmployeeByUsername(employee.getUsername()); 
		verify(employeeRepository, times (1)).findEmployeeByUsername(newName); 
		verify(employeeRepository, times(1)).save(any(Employee.class)); 
	}
	 
	 @Test
	 public void testUpdateEmployeeInvalidUsernameNotExisting() {
		final String invalidUsername = "bobcode";
	        
		when(employeeRepository.findEmployeeByUsername(invalidUsername)).thenAnswer((InvocationOnMock invocation) -> null); 
			
		MmssException ex = assertThrows(MmssException.class, () -> employeeService.updateEmployeeUsername(invalidUsername,"Skeletons@travis"));
			
		// check the message contains the right message and status
	    assertEquals("There is no such employee account with this username.", ex.getMessage());
	    assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
	        
	    verify(employeeRepository, times(1)).findEmployeeByUsername(invalidUsername);
	        
	}
	 
	 @Test
	 public void testUpdateEmployeeInvalidUsername() {
		final String invalidUsername = "bobcode";
	        
		when(employeeRepository.findEmployeeByUsername(employee.getUsername())).thenAnswer((InvocationOnMock invocation) -> employee); 
			
		MmssException ex = assertThrows(MmssException.class, () -> employeeService.updateEmployeeUsername(employee.getUsername(),invalidUsername));
			
		// check the message contains the right message and status
	    assertEquals("The username entered is an invalid email address. Please enter another username.", ex.getMessage());
	    assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
	        
	    verify(employeeRepository, times(1)).findEmployeeByUsername(employee.getUsername());
	        
	}
	 
	@Test
	public void testUpdateEmployeeInvalidExistingUsername() {
	        
		when(employeeRepository.findEmployeeByUsername(employee.getUsername())).thenAnswer((InvocationOnMock invocation) -> employee); 
		when(employeeRepository.findEmployeeByUsername(employeeTwo.getUsername())).thenAnswer((InvocationOnMock invocation) -> employeeTwo); 
		MmssException ex = assertThrows(MmssException.class, () -> employeeService.updateEmployeeUsername(employee.getUsername(),employeeTwo.getUsername()));
			
		// check the message contains the right message and status
	    assertEquals("The username entered is taken. Please enter another username.", ex.getMessage());
	    assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
	        
	    verify(employeeRepository, times(1)).findEmployeeByUsername(employee.getUsername());
	    verify(employeeRepository, times(1)).findEmployeeByUsername(employeeTwo.getUsername());
	}
	
	@Test
	public void testUpdateEmployeePasswordandPhone() {
		when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> employee); 

		when(employeeRepository.save(any(Employee.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
		
		Employee employeeUpdated = employeeService.updateEmployeePasswordAndPhone(employee.getUsername(), employee.getPassword(),"Ilovecontrollers2","514-456-7896");
		
		assertEquals("Ilovecontrollers2", employeeUpdated.getPassword());
		assertEquals("514-456-7896", employeeUpdated.getPhoneNumber());
		
		verify(employeeRepository, times (1)).findEmployeeByUsername(employee.getUsername());
		verify(employeeRepository, times(1)).save(any(Employee.class)); 
	}
	
	@Test
	public void testUpdateEmployeeInvalidPasswordNoSpecial() {
		final String invalidPassword = "sdpisabeauty";
        
		when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> employee); 
		
		MmssException ex = assertThrows(MmssException.class, () -> employeeService.updateEmployeePasswordAndPhone (employee.getUsername(), employee.getPassword(), invalidPassword, employee.getPhoneNumber()));
		
		// check the message contains the right message and status
        assertEquals("The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.", ex.getMessage());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        
        verify(employeeRepository, times(1)).findEmployeeByUsername(employee.getUsername());
	}
	
	@Test
	public void testUpdateEmployeeInvalidPasswordBadPw() {
		final String invalidPassword = "IlikeJava1";
        
		when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> employee); 
		
		MmssException ex = assertThrows(MmssException.class, () -> employeeService.updateEmployeePasswordAndPhone(employee.getUsername(),"savCodes3",invalidPassword, employee.getPhoneNumber()));
		
		// check the message contains the right message and status
        assertEquals("The password entered is not correct. Please enter correct password.", ex.getMessage());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        
        verify(employeeRepository, times(1)).findEmployeeByUsername(employee.getUsername());
	}
	
	@Test
	public void testUpdateEmployeeInvalidPasswordInvalidUser() {
		final String validPassword = "IlikeJava1";
        
		when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null); 
		
		MmssException ex = assertThrows(MmssException.class, () -> employeeService.updateEmployeePasswordAndPhone("ksi@deji","savCodes3",validPassword,employee.getPhoneNumber()));
		
		// check the message contains the right message and status
        assertEquals("There is no such employee account with this password.", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        
        verify(employeeRepository, times(1)).findEmployeeByUsername("ksi@deji");
	}
	
	@Test
	public void testUpdateEmployeePhoneNumberInvalidPhoneNumber() {
        
		when(employeeRepository.findEmployeeByUsername(employee.getUsername())).thenAnswer((InvocationOnMock invocation) -> employee); 
		
		MmssException ex = assertThrows(MmssException.class, () -> employeeService.updateEmployeePasswordAndPhone(employee.getUsername(),employee.getPassword(),"Ilikecontrollers3","5143335555"));
		
		// check the message contains the right message and status
        assertEquals("Enter a valid phone number in the format xxx-xxx-xxxx.", ex.getMessage());
        assertEquals(HttpStatus.NOT_ACCEPTABLE, ex.getStatus());
        
        verify(employeeRepository, times(1)).findEmployeeByUsername(employee.getUsername());
	}
	
	// needs modification
	@Test
	public void testDeleteEmployee() {
		when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> employee); 

	    employeeService.deleteEmployee(employee.getUsername());

	    verify(employeeRepository, times(1)).deleteById(employee.getUsername());
	}
		
	@Test
	public void testDeleteEmployeeInvalid() {
		when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null); 

	    MmssException ex = assertThrows(MmssException.class,() -> employeeService.deleteEmployee(employee.getUsername()));

	    // assert the exception is as expected
	    assertEquals("The employee with this username was not found", ex.getMessage()); 
	    assertEquals (HttpStatus.NOT_FOUND, ex.getStatus());
	}
	
	@Test
	public void testGetAllEmployeesInvalidShift() {
		when(shiftRepository.findShiftByShiftId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> null);
		
		MmssException ex = assertThrows(MmssException.class,() -> employeeService.getAllEmployeesByShift(-27));

        // assert the exception is as expected
        assertEquals("The shift with this id was not found", ex.getMessage()); 
        assertEquals (HttpStatus.NOT_FOUND, ex.getStatus());
	}
	
	@Test
	public void testCheckValidUser() {
		
		boolean isValidUser = employeeService.checkValidUser("John@Jones");
		assertEquals(true, isValidUser);
	
	}
	
	@Test
	public void testCheckInvalidUserBadUser() {
		
		boolean isValidUser = employeeService.checkValidUser("travis");
		assertEquals(false, isValidUser);
		
	}	
	
	@Test
	public void testCheckValidPassword() {
		
		boolean isValidPass = employeeService.checkValidPassword("IlikeCoding1");
		assertEquals(true, isValidPass);
	
	}
	
	@Test
	public void testCheckInValidPasswordShort() {
		
		boolean isValidPass = employeeService.checkValidPassword("hi");
		assertEquals(false, isValidPass);
	
	}
	
	@Test
	public void testCheckInValidPasswordNoDigit() {
		
		boolean isValidPass = employeeService.checkValidPassword("IlikeCoding");
		assertEquals(false, isValidPass);
	
	}
	
	@Test
	public void testCheckInValidPasswordNoCapital() {
		
		boolean isValidPass = employeeService.checkValidPassword("ilikecoding2");
		assertEquals(false, isValidPass);
	
	}

}
