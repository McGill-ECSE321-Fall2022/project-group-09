package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
import ca.mcgill.ecse.mmss.utils.Util;
import ca.mcgill.ecse.mmss.model.Schedule;

/**
 * Employee Repository testing class which initiates an employee, person, communication, shift, and weekly schedule repository, executes the tests, then clears each instance from the database.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeRepositoryTests {
  
  // repository we are testing
  @Autowired
  private EmployeeRepository employeeRepository; 
  
  // also need a person in order to add an employee
  @Autowired  
  private PersonRepository personRepository; 
  
  // adding the communication repository for the employee
  @Autowired  
  private CommunicationRepository communicationRepository; 
  
  // adding the shift repository for the employee
  @Autowired  
  private ShiftRepository shiftRepository; 
  
  // also need a  schedule repository for the shift
  @Autowired  
  private ScheduleRepository ScheduleRepository; 
      /**
     * Clear the database before all tests
     * @author Shidan Javaheri
     */
    @BeforeAll
    public static void clearDatabase(@Autowired Util util) {
        util.clearDatabase();
    }
  
  @AfterEach
  public void clearDatabase() {
    
      // make sure employee is deleted first, because employees cannot exist without a person
      employeeRepository.deleteAll();
      
      // delete all the persons after each execution
      personRepository.deleteAll(); 
      
      // delete all the communications after each execution
      communicationRepository.deleteAll(); 
      
      // delete all the shifts before the  schedule, because shifts cannot exist without a  schedule
      shiftRepository.deleteAll(); 
      
      // delete all the  schedules after each execution
      ScheduleRepository.deleteAll(); 
  }

  /**
 * Employee testing method which creates, populates the attributes, sets associations, and saves each employee, person, communication, shift, and weekly schedule object and identifier.
 * It can then test to make sure each object reached from the employee found in the repository is not null and that each initially saved Id corresponds to the one
 * reached from the repository.
 */
  @Test 
  public void testPersistAndLoadEmployee() { 
	  
// MANDATORY CLASS TESTS
	  
    // create the person for the employee
    Person person = new Person();
    person.setFirstName("Joe");
    person.setLastName("Swanson");
    
    // save the person
    personRepository.save(person);
    
    // create the Employee and populate its fields          
    Employee employee = new Employee() ;
    employee.setPhoneNumber("5147777777");
    String employeeName = "joe.swanson@mmss.qc.ca"; 
    employee.setUsername(employeeName); 
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
    
    
// OPTIONAL CLASS TESTS
    
    // create the  schedule for the shift
    Schedule Schedule = new Schedule();
    
    // save the  schedule
    ScheduleRepository.save(Schedule);

    // create the shift for the employee
    Shift shift = new Shift();
    shift.setShiftTime(ShiftTime.Morning);
    
    // set  schedule to shift then save shift
    shift.setSchedule(Schedule);
    shiftRepository.save(shift);
    
    // create the communication for the person
    Communication communication = new Communication();
    
    // save the  schedule
    communicationRepository.save(communication);

    // set shift and communication to employee then save employee
    employee.setShift(shift);
    employee.setCommunication(communication);
    employeeRepository.save(employee);
    
    // get shiftId and communicationId then save them to variables
    int communicationId = communication.getCommunicationId();  
    int shiftId = shift.getShiftId();
    
    // set the used employee, shift and communication to null
    employee = null;
    shift = null;
    communication = null;
    
    // get the employee back from the database using the Id
    employee = employeeRepository.findEmployeeByUsername(username); 
    
    // make sure employee, person, shift, and communication are not null
    assertNotNull(employee);
    assertNotNull(employee.getPerson());
    assertNotNull(employee.getShift());
    assertNotNull(employee.getCommunication());
    
    // make sure the created username, personId, shiftId, and communicationId match those in the database
    // check an attribute is stored properly
    assertEquals(username, employee.getUsername());
    assertEquals(personId, employee.getPerson().getPersonId());
    assertEquals(shiftId, employee.getShift().getShiftId());
    assertEquals(communicationId, employee.getCommunication().getCommunicationId());
  }
}