package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import ca.mcgill.ecse.mmss.dao.ShiftRepository;
import ca.mcgill.ecse.mmss.dao.EmployeeRepository;
import ca.mcgill.ecse.mmss.dao.ScheduleRepository;
import ca.mcgill.ecse.mmss.dao.ShiftRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
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
    @InjectMocks
    private ShiftService shiftService;
    
    // Acts like the DB
    ArrayList<Shift> shifts;
    Schedule schedule;
    Person person;
    Employee employee;

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
        
        // Create employee
        employee = new Employee("peter.griffin@edu.ca", "verySecurePassword", person, "438-777-7777");
        
    }

    /**
     * Delete the schedule, person, employee and shifts
     */
    @AfterEach
    public void deleteDelete(){
        schedule.delete();
        person.delete();
        employee.delete();
        shifts.clear();
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
    public void testRetrieveShiftByInvalidId() {
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
    public void testRetrieveShiftByInvalidShiftTime() {
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
        ArrayList<Shift> shiftsTest = shiftService.getAllShifts();
        // assertion
        assertEquals(shifts, shiftsTest);
        // Verify
        verify(shiftRepository, times(1)).findAll();
    }
}
