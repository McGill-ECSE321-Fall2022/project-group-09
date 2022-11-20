package ca.mcgill.ecse.mmss.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
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

import ca.mcgill.ecse.mmss.dao.ShiftRepository;
import ca.mcgill.ecse.mmss.dao.EmployeeRepository;
import ca.mcgill.ecse.mmss.dao.LoanRepository;
import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.ScheduleRepository;
import ca.mcgill.ecse.mmss.dao.ShiftRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.dto.LoanDto;
import ca.mcgill.ecse.mmss.dto.ShiftDto;
import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Schedule;
import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;
import ca.mcgill.ecse.mmss.model.Shift.ShiftTime;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ShiftIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PersonRepository personRepository;

    Schedule schedule;
    ArrayList<Shift> shifts;
    Person person1;
    Person person2;
    ArrayList<Employee> employees;
    
    /**
     * @author: Athmane Benarous
     * Create the schedule and the shifts needed by all the tests
     */
    @BeforeEach
    public void createObjects() {
        
        // Create schedule
        schedule = new Schedule();
        scheduleRepository.save(schedule);
        
        // Create shifts and save to DB
        shifts = new ArrayList<>();
        Shift morningShift;
        Shift afternoonShift;
        Shift eveningShift;

        morningShift = new Shift();
        morningShift.setSchedule(schedule);
        morningShift.setShiftTime(ShiftTime.Morning);
        shiftRepository.save(morningShift);
        shifts.add(morningShift);

        afternoonShift = new Shift();
        afternoonShift.setSchedule(schedule);
        afternoonShift.setShiftTime(ShiftTime.Afternoon);
        shiftRepository.save(afternoonShift);
        shifts.add(afternoonShift);

        eveningShift = new Shift();
        eveningShift.setSchedule(schedule);
        eveningShift.setShiftTime(ShiftTime.Evening);
        shiftRepository.save(eveningShift);
        shifts.add(eveningShift);

        // Create and save persons
        person1 = new Person();
        person1.setFirstName("Peter");
        person1.setLastName("Griffin");
        personRepository.save(person1);

        person2 = new Person();
        person2.setFirstName("Stewie");
        person2.setLastName("Griffin");
        personRepository.save(person2);
        
        // Create and save employees
        this.employees = new ArrayList<>();

        Employee employee1;
        Employee employee2;
        
        // Create and save employee 1
        employee1 = new Employee();
        employee1.setUsername("peter.griffin@edu.ca");
        employee1.setPassword("verySecurePassword");
        employee1.setPhoneNumber("438-777-7777");
        employee1.setPerson(person1);
        employee1.setShift(shifts.get(0));
        
        employee1 = employeeRepository.save(employee1);
        employees.add(employee1);
        
        // Create and save employee 2
        employee2 = new Employee();
        employee2.setUsername("stewie.griffin@edu.ca");
        employee2.setPassword("wayTooSecurePassword");
        employee2.setPhoneNumber("438-111-1111");
        employee2.setPerson(person2);
        employee2.setShift(shifts.get(0));

        employee2 = employeeRepository.save(employee2);
        employees.add(employee2);
    }

    /**
     * Delete the schedules and the shifts
     */
    @AfterEach
    public void deleteDelete(){
        employees.get(0).delete();
        employees.get(1).delete();
        employees.clear();
        employeeRepository.deleteAll();
        person1.delete();
        person2.delete();
        personRepository.deleteAll();
    	shifts.get(0).delete();
    	shifts.get(1).delete();
    	shifts.get(2).delete();
        shifts.clear();
        shiftRepository.deleteAll();
    	schedule.delete();
        scheduleRepository.deleteAll();
    }

    /**
     * Get a shift by its shift time
     */
    @Test
    public void testGetShiftByShiftTime() {
        Shift shift = shifts.get(0);
        // Try the get
        ResponseEntity<ShiftDto> response = client.getForEntity("/shift/" + shift.getShiftTime(), ShiftDto.class);
        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(shift.getShiftTime(), response.getBody().getShiftTime());
    }

    /**
     * Get all shifts
     */
    @Test
    public void testGetAllShifts() {
        // make request
        ResponseEntity<ArrayList> request = client.getForEntity("/shift", ArrayList.class);
        // get array list of shifts
        ArrayList<ShiftDto> extractedShifts = request.getBody();
        // assertions
        assertNotNull(extractedShifts);
        assertEquals(HttpStatus.OK, request.getStatusCode());
        assertEquals(shifts.size(), extractedShifts.size());
    }

    /**
     * Get shift of an employee
     */
    @Test
    public void testGetShiftFromEmployee() {
        // Try the get
        ResponseEntity<ShiftDto> response = client.getForEntity("/shift/username?username=" + employees.get(0).getUsername(), ShiftDto.class);
        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(employees.get(0).getShift().getShiftTime(), response.getBody().getShiftTime());
    }

    /**
     * Assign a shift to an employee
     */
    @Test
    public void testAssignShiftToEmployee() {
        int shiftId = shifts.get(0).getShiftId();
        String username = employees.get(0).getUsername();
        // make the post
        ResponseEntity<String> response = client.exchange("/shift/assign?shiftId=" + shiftId + "&username=" + username, HttpMethod.PUT, null, String.class);
        // make assertions on the post
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Shift successfully assigned.", response.getBody());
    }

    /**
     * Assign a shift to an employee
     */
    @Test
    public void testAssignShiftToEmployees() {
        int shiftId = shifts.get(0).getShiftId();
        // make the post
        ResponseEntity<String> response = client.exchange("/shift/assignToAll?shiftId=" + shiftId + "&employeeList=" + this.employees, HttpMethod.PUT, null, String.class);
        // make assertions on the post
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Shifts successfully assigned.", response.getBody());
    }


    /**
     * Delete an shift given its primary key
     */
    @Test
    public void testRemoveShiftFromEmployee() {
        // make DTO for request
        ResponseEntity<String> response = client.exchange("/shift/"+ employees.get(0).getUsername(), HttpMethod.DELETE,null, String.class);
        // make assertions on the post
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Shift successfully removed", response.getBody());
    }
}
