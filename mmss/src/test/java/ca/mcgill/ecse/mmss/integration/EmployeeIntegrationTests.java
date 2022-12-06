package ca.mcgill.ecse.mmss.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import ca.mcgill.ecse.mmss.dao.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse.mmss.dto.EmployeeDto;
import ca.mcgill.ecse.mmss.dto.EmployeeRequestDto;
import ca.mcgill.ecse.mmss.dto.VisitorDto;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Schedule;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Shift.ShiftTime;
import ca.mcgill.ecse.mmss.utils.Util;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeIntegrationTests {
	@Autowired
    private TestRestTemplate client;

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private ScheduleRepository scheduleRepository;
    
    @Autowired
    private ShiftRepository shiftRepository;
    
    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private VisitorRepository visitorRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    private Person person;
    private Communication communication;
    private Shift shift;
    private Schedule schedule;
    private Employee employee;


    /**
     * Clear the database before all tests
     * @author Shidan Javaheri
     */
    @BeforeAll
    public static void clearDatabase(@Autowired Util util) {
        util.clearDatabase();
    }
    /**
     * Creates the obejcts needed by all test cases
     * BeforeAll because these objects are only modified in the database,
     * not themselves
     * 
     * @author Saviru Perera
     */
    @BeforeEach
    public void createObjects() {
        // create necessary objects for test, and save them to the database
        person = new Person();
        person.setFirstName("Young");
        person.setLastName("Jeezy");
        person = personRepository.save(person);
        communication = communicationRepository.save(new Communication());
        schedule = scheduleRepository.save(new Schedule());
        shift = new Shift();
        shift.setShiftTime(ShiftTime.Morning);
        shift.setSchedule(schedule);
        shift = shiftRepository.save(shift);
        employee = new Employee("young.jeezy@gmail.com", "hardestOut2", person, "514-354-6789");
        employee.setCommunication(communication);
        employee.setShift(shift);
        employee = employeeRepository.save(employee);
    }
    
    /**
     * Deletes objects after each test
     * 
     * @author Saviru Perera
     */
    @AfterEach
    public void deleteObjects() {

        // delete the objects from the test
    	this.employee.delete();
        this.shift.delete();
        this.schedule.delete();
        this.person.delete();
        this.communication.delete();
        employeeRepository.deleteAll();
        visitorRepository.deleteAll();
        shiftRepository.deleteAll();
        scheduleRepository.deleteAll();
        personRepository.deleteAll();
        communicationRepository.deleteAll();
    }
    
    /**
     * Tests creating a employee, and returns its username
     * 
     * @author Saviru Perera
     */
    @Test
    public void testCreateEmployee() {
        EmployeeRequestDto request = new EmployeeRequestDto();
        request.setFirstName("Hello");
        request.setLastName("World");
        request.setUsername("sasha@gmail");
        request.setPhoneNumber("514-555-6787");
        request.setPassword("password");
        // make the post
        ResponseEntity<EmployeeDto> response = client.postForEntity("/employee/", request, EmployeeDto.class);
        // make assertions on the post
        assertNotNull(response, "The response is not null");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body");
        assertEquals(response.getBody().getUserName() , "sasha@gmail");
    }
    
    /**
     * Tests creating an additional visitor, and returns its username
     * 
     * @author Saviru Perera
     */
    @Test
    public void testCreateAdditionalVisitorForEmployee() {
        EmployeeRequestDto request = new EmployeeRequestDto();
        request.setUsername(employee.getUsername());
        request.setNewUsername("travis@scott");
        request.setNewPassword("Beanforever24");

        // make the post
        ResponseEntity<VisitorDto> response = client.postForEntity("/employee/addVisitorAccount", request, VisitorDto.class);

        // make assertions on the post
        assertNotNull(response, "The response is not null");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body");
        assertTrue(response.getBody().getUserName() != null, "Response has a valid username");

    }

    /**
     * Retrieves the employee that was created by its username
     * 
     * @author Saviru Perera
     */
    @Test
    public void testGetEmployee() {
        String username = employee.getUsername();
        // try the get
        ResponseEntity<EmployeeDto> response = client.getForEntity("/employee/" + username, EmployeeDto.class);

        // make assertions on the get

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body");
        assertEquals(response.getBody().getUserName(), username, "Response has correct username");

    }
    
    /**
     * Tests updating a employee password and phone number
     * 
     * @author Saviru Perera
     */
    @Test
    public void testUpdateEmployeePasswordAndPhone() {
        // make Dto for request
        EmployeeRequestDto employeeDto = new EmployeeRequestDto(employee,employee.getUsername(),"Ilovejava23","514-987-5786");

        // make an entity to send the request with 
        HttpEntity<EmployeeRequestDto> request= new HttpEntity<>(employeeDto); 

        // send the request
        ResponseEntity<EmployeeDto> response = client.exchange("/employee", HttpMethod.PUT ,request, EmployeeDto.class);

        // assertions on response
        assertNotNull(response); 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody()); 
        assertEquals(response.getBody().getPhoneNumber(), "514-987-5786"); 

        // get the updated employee from the database
        Employee updatedEmployee = employeeRepository.findEmployeeByUsername(employee.getUsername());

        // verify the update
        assertEquals(updatedEmployee.getPassword(), "Ilovejava23");

    }

    
    /**
     * Tests deleting an employee
     * 
     * @author Saviru Perera
     */
    
    @Test
    public void testDeleteEmployee() {

        // make Dto for request
        EmployeeDto request = new EmployeeDto(employee);
        String username = request.getUserName();

        
        ResponseEntity<String> response = client.exchange("/employee/" + username, HttpMethod.DELETE,null, String.class);

        // assert on the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Employee successfully deleted", response.getBody());

        // get the updated employee from the database
        Employee updatedEmployee = employeeRepository.findEmployeeByUsername(username);

        // verify the employee has been deleted
        assertNull(updatedEmployee, "Employee successfully deleted");

    }

    /**
     * Tests getting all employees
     * 
     * @author Saviru Perera
     */

    @Test
    public void testGetAllEmployees() {

        // make request
        var response = client.getForEntity("/employee", ArrayList.class);

        // assertions on the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody()); 
        // get array list of employees
        ArrayList<EmployeeDto> extractedEmployees = response.getBody();

        // assertions
        assertNotNull(extractedEmployees);
        assertEquals(1, extractedEmployees.size());

    };
    
    /**
     * Tests getting all employees by their shift
     * 
     * @author Saviru Perera
     */

    @Test
    public void testGetAllEmployeesByShift() {
        // make request
        var response = client.getForEntity("/employee/byShift?id=" + shift.getShiftId(), ArrayList.class);

        // assertions on the responses
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody()); 

       
        // get array list of employees
        ArrayList<EmployeeDto> extractedEmployees = response.getBody();

        // assertions
        assertNotNull(extractedEmployees);
        assertEquals(1, extractedEmployees.size());
    }

}
