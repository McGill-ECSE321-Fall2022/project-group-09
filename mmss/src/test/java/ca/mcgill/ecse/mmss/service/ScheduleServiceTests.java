package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.ArrayList;

import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.ScheduleRepository;
import ca.mcgill.ecse.mmss.model.*;

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
public class ScheduleServiceTests {
	@Mock
    private ScheduleRepository scheduleRepository;
	@Mock
    private OpenDayRepository openDayRepository;
    @InjectMocks
    private ScheduleService scheduleService;
    
    // Acts like the DB
    Schedule schedule;
    ArrayList<OpenDay> days;
    OpenDay day1;
    OpenDay day2;

    /**
     * @author: Athmane Benarous
     * Create the schedule, person, openDay and the schedules needed by all the tests
     */
    @BeforeEach
    public void createObjects() {
        // Create schedule
        this.schedule = new Schedule(1);
        
        // Create days
        this.days = new ArrayList<>();
        
        // Create day 1
        this.day1 = new OpenDay(Date.valueOf("2022-10-10"));
        days.add(day1);
        
        // Create day 2
        this.day2 = new OpenDay(Date.valueOf("2022-11-11"));
        days.add(day2);
    }

    /**
     * Delete the schedule, person, openDay and schedules
     */
    @AfterEach
    public void deleteDelete(){
        schedule.delete();
        day1.delete();
        day2.delete();
        days.clear();
    }

    /**
     * Test retrieving an schedule with a valid id
     */
    @Test
    public void testGetSchedule() {
    	ArrayList<Schedule> mockScheduleList = new ArrayList<>();
    	mockScheduleList.add(schedule);
        // setup mocks
        when(scheduleRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> mockScheduleList);
        
        // call service layer
        Schedule grabSchedule = scheduleService.getSchedule();
        
        // assertion
        assertEquals(this.schedule, grabSchedule);
        
        // verify calls to repositories
        verify(scheduleRepository, times (2)).findAll();
    }
    
    /**
     * Test retrieving a schedule with an invalid id
     */
    @Test
    public void testGetInvalidSchedule() {
    	ArrayList<Schedule> mockScheduleList = new ArrayList<>();
        // setup mocks
        when(scheduleRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> mockScheduleList);
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> scheduleService.getSchedule());
        // assertion
        assertEquals("Schedule not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        // verify calls to repositories
        verify(scheduleRepository, times (1)).findAll();
    }
    
    /**
     * Test to create a schedule
     */
    @Test
    public void testCreateSchedule() {
        // setup mocks
        lenient().when(scheduleRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> new ArrayList());
        lenient().when(scheduleRepository.save(any(Schedule.class))).thenAnswer((InvocationOnMock invocation) -> schedule);
            
        // call service layer
        Schedule grabSchedule = scheduleService.createSchedule();
        // assertion
        assertNotNull(grabSchedule);
        // Verify
        verify(scheduleRepository, times(1)).save(any(Schedule.class));
        verify(scheduleRepository, times(1)).findAll();
    }
    
    /**
     * Test to create a schedule on top of a pre-existing one
     */
    @Test
    public void testInvalidCreateSchedule() {
        // setup mocks
    	ArrayList<Schedule> mockScheduleList = new ArrayList<>();
    	mockScheduleList.add(schedule);
		
        lenient().when(scheduleRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> mockScheduleList);
        
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> scheduleService.createSchedule());
        
        // assertion
        assertEquals("A schedule already exists.", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    	
        // Verify
        verify(scheduleRepository, times(1)).findAll();
    }
    
    /**
     * Test to set an openDay's work schedule
     */
    @Test
    public void testAssignScheduleToOpenDay() {
        // setup mocks
    	ArrayList<Schedule> mockScheduleList = new ArrayList<>();
    	mockScheduleList.add(schedule);
		
        lenient().when(scheduleRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> mockScheduleList);
        when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> this.day1);
        when(openDayRepository.save(any(OpenDay.class))).thenAnswer((InvocationOnMock invocation) -> this.day1);

        // call service layer
        scheduleService.assignScheduleToOpenDay(this.day1.getDate());

        // assertion
        assertEquals(this.day1.getSchedule(), this.schedule);
        // Verify
        verify(openDayRepository, times(1)).save(any(OpenDay.class));
        verify(openDayRepository, times(1)).findOpenDayByDate(any(Date.class));
        verify(scheduleRepository, times(2)).findAll();
    }
    
    /**
     * Test to set an invalid openDay's work schedule
     */
    @Test
    public void testAssignScheduleToInvalidOpenDay() {
        // setup mocks
        when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> null);
        
        // call service layer
        MmssException ex = assertThrows(MmssException.class, () -> scheduleService.assignScheduleToOpenDay(this.day1.getDate()));
        
        // assertion
        assertEquals("Open Day not found" , ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        
        // Verify
        verify(openDayRepository, times(1)).findOpenDayByDate(any(Date.class));
    }
    
    /**
     * Test to set multiple employees' work shift
     */
    @Test
    public void testAssignShiftToEmployees() {
        // setup mocks
    	ArrayList<Schedule> mockScheduleList = new ArrayList<>();
    	mockScheduleList.add(schedule);
		
        lenient().when(scheduleRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> mockScheduleList);
        when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> this.day2);
        when(openDayRepository.save(any(OpenDay.class))).thenAnswer((InvocationOnMock invocation) -> this.day2);

        // call service layer
        scheduleService.assignScheduleToOpenDays(this.days);

        // assertion
        assertEquals(this.day2.getSchedule(), this.schedule);
        // Verify
        verify(openDayRepository, times(2)).save(any(OpenDay.class));
        verify(openDayRepository, times(2)).findOpenDayByDate(any(Date.class));
        verify(scheduleRepository, times(4)).findAll();
    }
}
