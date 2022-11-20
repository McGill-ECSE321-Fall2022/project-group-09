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
import ca.mcgill.ecse.mmss.dao.EmployeeRepository;
import ca.mcgill.ecse.mmss.dao.ManagerRepository;
import ca.mcgill.ecse.mmss.model.AccountType;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Manager;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Person;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTests {
	
	 @Mock
	 private VisitorRepository visitorRepository;
	 
	 @Mock
	 private EmployeeRepository employeeRepository;
	 
	 @Mock
	 private ManagerRepository managerRepository;
	 
	 @InjectMocks
	 private LoginService loginService;
	 
	 private Person personOne;
	 private Person personTwo;
	 private Person personThree;
	 private Visitor visitor;
	 private Employee employee;
	 private Manager manager;
	 
	 @BeforeEach
	 public void createObjects() {

		 // create necessary objects for test
		 // This will Mock the content of your database
		 // Basically a fake database system
		 this.personOne = new Person(0, "John", "Coder");
		 this.personTwo = new Person(1, "Babbu", "Beard");
		 this.personThree = new Person(0, "Java", "Docs");
		 this.visitor = new Visitor("John@Coder", "ILikeSoftwareTests1", personOne);
		 this.employee = new Employee("Jon@Jones","Fngannou270", personTwo, "514-878-9789");
		 this.manager = new Manager("Lebron@James","leCapper81", personThree);
	 }
	 
	 @AfterEach
	 public void deleteObjects() {
		 // delete the objects from the test
		 
	     this.visitor.delete();
	     this.employee.delete();
	     this.manager.delete();
	     this.personOne.delete();
	     this.personTwo.delete();
	     this.personThree.delete();
	     
	 }
	 
	 @Test
	 public void testGetLoginServiceVisitor() {
		 
		 when(visitorRepository.findVisitorByUsername(visitor.getUsername())).thenAnswer((InvocationOnMock invocation) -> visitor); 
		 
		 when(employeeRepository.findEmployeeByUsername(visitor.getUsername())).thenAnswer((InvocationOnMock invocation) -> null); 
		 
		 when(managerRepository.findManagerByUsername(visitor.getUsername())).thenAnswer((InvocationOnMock invocation) -> null); 
		 // call the service to create a loan
	     AccountType visitorCreated = loginService.getAccountByUsername(visitor.getUsername()); 
	     
	     assertEquals(visitor.getUsername(), visitorCreated.getUsername());
	     
	     verify(visitorRepository, times(1)).findVisitorByUsername(visitor.getUsername());
	     verify(employeeRepository, times(1)).findEmployeeByUsername(visitor.getUsername());
	     verify(managerRepository, times(1)).findManagerByUsername(visitor.getUsername());
	 }
	 
	 @Test
	 public void testGetLoginServiceEmployee() {
		 
		 when(visitorRepository.findVisitorByUsername(employee.getUsername())).thenAnswer((InvocationOnMock invocation) -> null); 
		 
		 when(employeeRepository.findEmployeeByUsername(employee.getUsername())).thenAnswer((InvocationOnMock invocation) -> employee); 
		 
		 when(managerRepository.findManagerByUsername(employee.getUsername())).thenAnswer((InvocationOnMock invocation) -> null); 
		 // call the service to create a loan
	     AccountType employeeCreated = loginService.getAccountByUsername(employee.getUsername()); 
	     
	     assertEquals(employee.getUsername(), employeeCreated.getUsername());
	     
	     verify(visitorRepository, times(1)).findVisitorByUsername(employee.getUsername());
	     verify(employeeRepository, times(1)).findEmployeeByUsername(employee.getUsername());
	     verify(managerRepository, times(1)).findManagerByUsername(employee.getUsername());
	 }
	 
	 @Test
	 public void testGetLoginServiceManager() {
		 
		 when(visitorRepository.findVisitorByUsername(manager.getUsername())).thenAnswer((InvocationOnMock invocation) -> null); 
		 
		 when(employeeRepository.findEmployeeByUsername(manager.getUsername())).thenAnswer((InvocationOnMock invocation) -> null); 
		 
		 when(managerRepository.findManagerByUsername(manager.getUsername())).thenAnswer((InvocationOnMock invocation) -> manager); 
		 // call the service to create a loan
	     AccountType managerCreated = loginService.getAccountByUsername(manager.getUsername()); 
	     
	     assertEquals(manager.getUsername(), managerCreated.getUsername());
	     
	     verify(visitorRepository, times(1)).findVisitorByUsername(manager.getUsername());
	     verify(employeeRepository, times(1)).findEmployeeByUsername(manager.getUsername());
	     verify(managerRepository, times(1)).findManagerByUsername(manager.getUsername());
	 }
	 
	 @Test
	 public void testGetLoginServiceInvalid() {
		 
		 when(visitorRepository.findVisitorByUsername(manager.getUsername())).thenAnswer((InvocationOnMock invocation) -> null); 
		 
		 when(employeeRepository.findEmployeeByUsername(manager.getUsername())).thenAnswer((InvocationOnMock invocation) -> null); 
		 
		 when(managerRepository.findManagerByUsername(manager.getUsername())).thenAnswer((InvocationOnMock invocation) -> manager); 
		 // call the service to create a loan
	     AccountType managerCreated = loginService.getAccountByUsername(manager.getUsername()); 
	     
	     assertEquals(manager.getUsername(), managerCreated.getUsername());
	     
	     verify(visitorRepository, times(1)).findVisitorByUsername(manager.getUsername());
	     verify(employeeRepository, times(1)).findEmployeeByUsername(manager.getUsername());
	     verify(managerRepository, times(1)).findManagerByUsername(manager.getUsername());
	 }
	 

}
