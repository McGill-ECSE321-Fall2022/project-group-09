package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Shift.ShiftTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeRepositoryTests {
  
  // repository we are testing
  @Autowired
  private EmployeeRepository employeeRepository; 
  
  // also need a shift in order to add a employee
  @Autowired  
  private ShiftRepository shiftRepository; 
  @Autowired  
  private CommunicationRepository communicationRepository; 
  @Autowired  
  private PersonRepository personRepository; 
  
  @AfterEach
  public void clearDatabase() {
    
      // make sure the employee is deleted first, because employees cannot exist without a shift
      employeeRepository.deleteAll();
      
      // then you can delete all the shifts
      shiftRepository.deleteAll(); 
      communicationRepository.deleteAll(); 
      personRepository.deleteAll(); 
  }

  @Test 
  public void testPersistAndLoadEmployee() { 
    
    // create the shift for the employee
    Shift shift = new Shift();
    shift.setShiftTime(ShiftTime.Morning);
    shiftRepository.save(shift);
    
    // create the person for the employee
    Person person = new Person();
    person.setFirstName("Joe");
    person.setLastName("Swanson");
    personRepository.save(person);
    
    // create the communication for the employee
    Communication communication = new Communication();
    communicationRepository.save(communication);
    
    // create the Employee and populate its fields          
    Employee employee = new Employee() ;
    employee.setPhoneNumber("5147777777");
    employee.setUsername("joe.swanson@mmss.qc.ca"); 
    employee.setPassword("VerySecurePassword");
    employeeRepository.save(employee);
    
    employee.setShift(shift);
    employee.setCommunication(communication);
    employee.setPerson(person);
    // save the Employee    
    employeeRepository.save(employee);
    
    String username = employee.getUsername();  
    int shiftId = shift.getShiftId();
    int personId = person.getPersonId();
    int communicationId = communication.getCommunicationId();
    
    // set employee to null    
    employee = null;
    shift = null;
    person = null;
    communication = null;
    
    // get the employee from the database using the Id
    employee = employeeRepository.findEmployeeByUsername(username); 
    
    // run J-Unit tests
    assertNotNull(employee);
    assertNotNull(employee.getShift());
    assertNotNull(employee.getCommunication());
    assertNotNull(employee.getPerson());
    
    assertEquals(username, employee.getUsername());
    assertEquals(shiftId, employee.getShift().getShiftId());
    assertEquals(personId, employee.getPerson().getPersonId());
    assertEquals(communicationId, employee.getCommunication().getCommunicationId());
    
  }
}
