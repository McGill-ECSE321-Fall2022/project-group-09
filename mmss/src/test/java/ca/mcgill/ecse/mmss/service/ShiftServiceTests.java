package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import ca.mcgill.ecse.mmss.dao.ShiftRepository;
import ca.mcgill.ecse.mmss.dao.EmployeeRepository;
import ca.mcgill.ecse.mmss.model.*;
import ca.mcgill.ecse.mmss.model.Shift.ShiftTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse.mmss.exception.MmssException;

@ExtendWith(MockitoExtension.class)
public class ShiftServiceTests {
	@Mock
    private ShiftRepository shiftRepository;
	@Mock
    private EmployeeRepository employeeRepository;
	@Mock
    private ScheduleService scheduleService;
	@Mock
    private EmployeeService employeeService;
    @InjectMocks
    private ShiftService shiftService;
    
    // Acts like the DB
    Schedule schedule;
    Person person;
    ArrayList<Shift> shifts;
    ArrayList<Employee> employees;
    Employee employee;
    Employee secEmployee;

    /**
     * @author: Athmane Benarous
     * Create the schedule, person, employee and the shifts needed by all the tests
     */
    @BeforeEach
    public void createObjects() {
        // Create schedule
        this.schedule = new Schedule(1);
        
        // Create shifts
        this.shifts = new ArrayList<>();
        
        Shift morningShift = new Shift(1, ShiftTime.Morning, schedule);
        shifts.add(morningShift);

        Shift afternoonShift = new Shift(2, ShiftTime.Afternoon, schedule);
        shifts.add(afternoonShift);

        Shift eveningShift = new Shift(3, ShiftTime.Evening, schedule);
        shifts.add(eveningShift);
        
        // Create person
        this.person = new Person(1, "Peter", "Griffin");
        
        // Create employees
        this.employees = new ArrayList<>();
        
        // Create employee 1
        this.employee = new Employee("peter.griffin@edu.ca", "verySecurePassword", this.person, "438-777-7777");
        this.employee.setShift(eveningShift);
        employees.add(employee);
        
        // Create employee 2
        this.secEmployee = new Employee("stewie.griffin@edu.ca", "wayTooSecurePassword", this.person, "438-111-1111");
        this.secEmployee.setShift(morningShift);
        employees.add(secEmployee);
    }

    /**
     * Delete the schedule, person, employee and shifts
     */
    @AfterEach
    public void deleteDelete(){
        schedule.delete();
        person.delete();
        shifts.clear();
        employee.delete();
        secEmployee.delete();
        employees.clear();
    }

    /**
     * Test retrieving an shift with a valid id
     */
    @Test
    public void testGetShiftById() {
        // setup mocks
        when(shiftRepository.findShiftByShiftId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> shifts.get(0));
        
        // call service layer
        Shift shift = shiftService.getShiftById(1);
        
        // assertion
        assertEquals(shifts.get(0), shift);
        
        // verify calls to repositories
        verify(shiftRepository, times (1)).findShiftByShiftId(any(int.class));
    }
    
    /**
     * Test retrieving a schedule with an invalid id
     */
    @Test
    public void testGetShiftByInvalidId() {
        final int invalidId = 99;
        // setup mocks
        when(shiftRepository.findShiftByShiftId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> shiftService.getShiftById(invalidId));
        // assertion
        assertEquals("Shift not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        // verify calls to repositories
        verify(shiftRepository, times (1)).findShiftByShiftId(invalidId);
    }
    
    /**
     * Test retrieving an shift with a valid shift time
     */
    @Test
    public void testGetShiftByShiftTime() {
        // setup mocks
        when(shiftRepository.findAllByShiftTime(any(ShiftTime.class))).thenAnswer((InvocationOnMock invocation) -> shifts);
        
        // call service layer
        Shift shift = shiftService.getShiftByShiftTime(ShiftTime.Morning);
        
        // assertion
        assertEquals(shifts.get(0), shift);
        
        // verify calls to repositories
        verify(shiftRepository, times (1)).findAllByShiftTime(any(ShiftTime.class));
    }
    
    /**
     * Test retrieving a schedule with an invalid shift time
     */
    @Test
    public void testGetShiftByInvalidShiftTime() {
        final ShiftTime invalidShiftTime = ShiftTime.Evening;
        // setup mocks
        when(shiftRepository.findAllByShiftTime(invalidShiftTime)).thenAnswer((InvocationOnMock invocation) -> null);
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> shiftService.getShiftByShiftTime(invalidShiftTime));
        // assertion
        assertEquals("Shift not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        // verify calls to repositories
        verify(shiftRepository, times (1)).findAllByShiftTime(invalidShiftTime);
    }
    
    /**
     * Test to get all 3 shifts
     */
    @Test
    public void testGetAllShifts() {
        // setup mocks
        when(shiftRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> shifts);
        // call service layer
        ArrayList<Shift> shiftList = shiftService.getAllShifts();
        // assertion
        assertEquals(shifts, shiftList);
        // Verify
        verify(shiftRepository, times(1)).findAll();
    }
    
    /**
     * Test to create the shifts
     */
    @Test
    public void testCreateShifts() {
        // setup mocks
        ArrayList<Shift> emptyList = new ArrayList<>();
    	for(int i=0; i<=2; i++) {
    		Shift shift = this.shifts.get(i);
            lenient().when(shiftRepository.findAllByShiftTime(shift.getShiftTime())).thenAnswer((InvocationOnMock invocation) -> emptyList);
            lenient().when(shiftRepository.save(shift)).thenAnswer((InvocationOnMock invocation) -> shift);
    	}
        lenient().when(shiftRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> shifts);
        // call service layer
        ArrayList<Shift> outputList = new ArrayList<>();
        outputList = shiftService.createShifts();
        // assertion
        for(int i=0; i<=2; i++) assertEquals(this.shifts.get(i).getShiftTime(), outputList.get(i).getShiftTime());
        // Verify
        verify(shiftRepository, times(3)).save(any(Shift.class));
    }
    
    /**
     * Test to create the shifts on top of pre-existing ones
     */
    @Test
    public void testInvalidCreateShifts() {
        // setup mocks
    	ArrayList<Shift> preExistingShifts = new ArrayList<>();
		Shift shift = this.shifts.get(0);
        preExistingShifts.add(shift);

        lenient().when(shiftRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> this.shifts);
        
        lenient().when(shiftRepository.findAllByShiftTime(shift.getShiftTime())).thenAnswer((InvocationOnMock invocation) -> preExistingShifts);
        lenient().when(shiftRepository.save(shift)).thenAnswer((InvocationOnMock invocation) -> shift);
        
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> shiftService.createShifts());
        
        // assertion
        assertEquals("The shift " + shift.getShiftTime().name() + " already exists.", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    	
        // Verify
        verify(shiftRepository, times(1)).findAllByShiftTime(shift.getShiftTime());
    }
    
    /**
     * Test to get an employee's work shift
     */
    @Test
    public void testGetShiftFromEmployee() {
        // setup mocks
        when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> this.employee);
        
        // call service layer
        Shift shift = shiftService.getShiftFromEmployee(this.employee.getUsername());
        // assertion
        assertEquals(shift, this.employee.getShift());
        // Verify
        verify(employeeRepository, times(1)).findEmployeeByUsername(any(String.class));
    }
    
    /**
     * Test to get an invalid employee's work shift
     */
    @Test
    public void testGetShiftFromInvalidEmployee() {
        // setup mocks
        when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null);
        
        // call service layer
        MmssException ex = assertThrows(MmssException.class, () -> shiftService.getShiftFromEmployee(this.employee.getUsername()));
        
        // assertion
        assertEquals("Employee not found" , ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        // Verify
        verify(employeeRepository, times(1)).findEmployeeByUsername(any(String.class));
    }
    
    /**
     * Test to set an employee's work shift
     */
    @Test
    public void testAssignShiftToEmployee() {
        // setup mocks
        when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> this.employee);
        when(shiftRepository.findShiftByShiftId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> this.shifts.get(0));
        
        // call service layer
        shiftService.assignShiftToEmployee(1, this.employee.getUsername());
        // assertion
        assertEquals(1, this.employee.getShift().getShiftId());
        // Verify
        verify(employeeRepository, times(1)).findEmployeeByUsername(any(String.class));
        verify(shiftRepository, times(2)).findShiftByShiftId(any(int.class));
    }
    
    /**
     * Test to set an employee's invalid work shift
     */
    @Test
    public void testAssignInvalidShiftToEmployee() {
        // setup mocks
        when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> this.employee);
        when(shiftRepository.findShiftByShiftId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> null);
        
        // call service layer
        MmssException ex = assertThrows(MmssException.class, () -> shiftService.assignShiftToEmployee(1, this.employee.getUsername()));
        
        // assertion
        assertEquals("Shift not found" , ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        // Verify
        verify(employeeRepository, times(1)).findEmployeeByUsername(any(String.class));
        verify(shiftRepository, times(1)).findShiftByShiftId(any(int.class));
    }
    
    /**
     * Test to set an invalid employee's work shift
     */
    @Test
    public void testAssignShiftToInvalidEmployee() {
        // setup mocks
        when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null);
        
        // call service layer
        MmssException ex = assertThrows(MmssException.class, () -> shiftService.assignShiftToEmployee(1, this.employee.getUsername()));
        
        // assertion
        assertEquals("Employee not found" , ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        
        // Verify
        verify(employeeRepository, times(1)).findEmployeeByUsername(any(String.class));
    }
    
    /**
     * Test to set multiple employees' work shift
     */
    @Test
    public void testAssignShiftToEmployees() {
        // setup mocks
        when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> this.employee);
        when(shiftRepository.findShiftByShiftId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> this.shifts.get(0));
        
        // call service layer
        shiftService.assignShiftToEmployees(1, this.employees);
        // assertion
        assertEquals(1, this.employee.getShift().getShiftId());
        assertEquals(1, this.secEmployee.getShift().getShiftId());
        // Verify
        verify(employeeRepository, times(2)).findEmployeeByUsername(any(String.class));
        verify(shiftRepository, times(4)).findShiftByShiftId(any(int.class));
    }
    
    /**
     * Test to remove an employee's work shift
     */
    @Test
    public void testRemoveShiftFromEmployee() {
        // setup mocks
        when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> this.employee);
        
        // call service layer
        shiftService.removeShiftFromEmployee(this.employee.getUsername());
        // assertion
        assertEquals(null, this.employee.getShift());
        // Verify
        verify(employeeRepository, times(1)).findEmployeeByUsername(any(String.class));
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
    
    /**
     * Test to remove an invalid employee's invalid work shift
     */
    @Test
    public void testRemoveShiftFromInvalidEmployee() {
        // setup mocks
        when(employeeRepository.findEmployeeByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null);
        
        // call service layer
        MmssException ex = assertThrows(MmssException.class, () -> shiftService.removeShiftFromEmployee(this.employee.getUsername()));
        
        // assertion
        assertEquals("Employee not found" , ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        // Verify
        verify(employeeRepository, times(1)).findEmployeeByUsername(any(String.class));
    }
}
