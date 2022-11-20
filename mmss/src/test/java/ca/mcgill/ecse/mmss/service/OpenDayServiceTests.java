package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Schedule;

@ExtendWith(MockitoExtension.class)
public class OpenDayServiceTests {

    @Mock
    private OpenDayRepository openDayRepository;

    // We inject the mocks in the OpenDay service - the thing that calls on the
    // repositories
    @InjectMocks
    private OpenDayService openDayService;

    @Mock
    private ScheduleService scheduleService;

    // Objects we will need in the tests
    OpenDay openDay;
    Schedule schedule;
    Date testDate = Date.valueOf("2022-10-10");

    /**
     * Creates the obejcts needed by all test cases
     * 
     * @author Mohamed Elsamadouny
     */
    @BeforeEach
    public void createObjects() {
        // create necessary objects for test
        openDay = new OpenDay(testDate);
        schedule = new Schedule();
        openDay.setSchedule(schedule);

    }

    /**
     * Deletes objects after each test
     * 
     * @author Mohamed Elsamadouny
     */
    @AfterEach
    public void deleteObjects() {
        // delete the objects from the test
        this.openDay.delete();
        schedule.delete();
    }

    /**
     * Tests get all openDay
     * 
     * @author Mohamed Elsamadouny
     */
    @Test 
    public void testGetAllOpenDay () { 

        // setup mocks
        ArrayList<OpenDay> list = new ArrayList<OpenDay>();
        list.add(openDay);

        when(openDayRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> list);

        // call service layer
        ArrayList<OpenDay> retrievedOpenDays = openDayService.getAllOpenDays(); 

        // assertions
        assertEquals(1, retrievedOpenDays.size()); 

        // verify calls to repositories
        verify(openDayRepository, times (1)).findAll(); 
    }

    /**
     * Tests retreiving an OpenDay by valid Date
     * 
     * @author Mohamed Elsamadouny
     */
    @Test 
    public void testGetOpenDayByDate () { 

        // setup mocks
        when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> openDay ); 

        // call service layer
        OpenDay retrievedOpenDay = openDayService.getOpenDayByDate(testDate); 

        // assertions
        assertEquals (Date.valueOf("2022-10-10"), retrievedOpenDay.getDate()); 

        // verify calls to repositories
        verify(openDayRepository, times (1)).findOpenDayByDate(testDate); 
    }

    /**
     * Tests retreiving an OpenDay by valid Date
     * 
     * @author Mohamed Elsamadouny
     */
    @Test 
    public void testGetOpenDayByInvalidDate() { 

        // setup mocks
        when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> null); 

        // call service layer
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> openDayService.getOpenDayByDate(testDate));

        // assertions
        assertEquals ("OpenDay not found", ex.getMessage()); 

        // verify calls to repositories
        verify(openDayRepository, times (1)).findOpenDayByDate(testDate); 
    }

    /**
     * Tests creating an OpenDay successfully
     * @author Mohamed Elsamadouny
     */

    @Test
    public void testCreateOpenDay() { 

        // mock the repositories in the create Donation class

        // when a loan is saved, return that loan
        when(openDayRepository.save(any(OpenDay.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        when(scheduleService.getSchedule()).thenAnswer((InvocationOnMock invocation) -> schedule); 

        // call the service
        OpenDay openDayCreated = openDayService.createOpenDay(testDate); 

        assertEquals(openDay.getDate(), openDayCreated.getDate());
        assertEquals(openDay.getSchedule(), openDayCreated.getSchedule());
        verify(openDayRepository, times(1)).save(openDayCreated); 
        verify(scheduleService, times(1)).getSchedule();
    }

    /**
     * Tests calculating due date
     * @author Mohamed Elsamadouny
     */
    @Test
    public void testCalculateLoanDueDate(){
        when(openDayRepository.findByDateGreaterThan(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> generateOpenDays());

        OpenDay generatedDueDate = openDayService.calculateLoanDueDate(testDate);

        assertEquals(Date.valueOf("2022-10-18"), generatedDueDate.getDate());

        verify(openDayRepository, times(1)).findByDateGreaterThan(testDate);

    }

    /**
     * Tests calculating due date when there are not enough due dates
     * @author Mohamed Elsamadouny
     */
    @Test
    public void testInvalidCalculateLoanDueDate(){
        ArrayList<OpenDay> list = new ArrayList<OpenDay>();
        list.add(new OpenDay(Date.valueOf("2022-10-10")));
        when(openDayRepository.findByDateGreaterThan(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> list);

        // catch exception
        MmssException ex = assertThrows(MmssException.class, () -> openDayService.calculateLoanDueDate(testDate));

        // assertions
        assertEquals ("Not enough OpenDays, please contact the manager", ex.getMessage()); 

        verify(openDayRepository, times(1)).findByDateGreaterThan(testDate);

    }

    private ArrayList<OpenDay> generateOpenDays() {

        ArrayList<OpenDay> list = new ArrayList<OpenDay>();
        list.add(new OpenDay(Date.valueOf("2022-10-10")));
        list.add(new OpenDay(Date.valueOf("2022-10-11")));
        list.add(new OpenDay(Date.valueOf("2022-10-14")));
        list.add(new OpenDay(Date.valueOf("2022-10-15")));
        list.add(new OpenDay(Date.valueOf("2022-10-16")));
        list.add(new OpenDay(Date.valueOf("2022-10-17")));
        list.add(new OpenDay(Date.valueOf("2022-10-18")));

        return list;
    }
    
}
