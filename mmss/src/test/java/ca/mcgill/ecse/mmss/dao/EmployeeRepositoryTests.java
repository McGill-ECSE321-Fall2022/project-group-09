package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Person;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeRepositoryTests {
  
  // repository we are testing
  @Autowired
  private EmployeeRepository employeeRepository; 
  
  // also need a person in order to add an employee
  @Autowired  
  private PersonRepository personRepository; 
  
  @AfterEach
  public void clearDatabase() {
    
      // make sure employee is deleted first, because employees cannot exist without a person
      employeeRepository.deleteAll();
      
      // then you can delete all the persons after each execution
      personRepository.deleteAll(); 
  }

  @Test 
  public void testPersistAndLoadEmployee() { 
    
    // create the person for the employee
    Person person = new Person();
    person.setFirstName("Joe");
    person.setLastName("Swanson");
    
    // save the person
    personRepository.save(person);
    
    // create the Employee and populate its fields          
    Employee employee = new Employee() ;
    employee.setPhoneNumber("5147777777");
    employee.setUsername("joe.swanson@mmss.qc.ca"); 
    employee.setPassword("VerySecurePassword");
    
    // set person to employee then save employee
    employee.setPerson(person);
    employeeRepository.save(employee);
    
    // get the username and the person Id and save them to variables
    String username = employee.getUsername();  
    int personId = person.getPersonId();
    
    // set the used employee and person to null
    employee = null;
    person = null;
    
    // get the employee back from the database using the Id
    employee = employeeRepository.findEmployeeByUsername(username); 
    
    // make sure employee and person are not null
    assertNotNull(employee);
    assertNotNull(employee.getPerson());
    
    // make sure the created username and personId match those in the database
    assertEquals(username, employee.getUsername());
    assertEquals(personId, employee.getPerson().getPersonId());
    
  }
}