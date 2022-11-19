package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;

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

@ExtendWith(MockitoExtension.class)
public class OpenDayServiceTests {

    @Mock
    private OpenDayRepository openDayRepository;

    // We inject the mocks in the OpenDay service - the thing that calls on the
    // repositories
    @InjectMocks
    private OpenDayService openDayService;

    // Objects we will need in the tests
    OpenDay openDay;
    Date testDate = Date.valueOf("2022-10-10");

    /**
     * Creates the obejcts needed by all test cases
     * 
     * @author Mohamed Elsamadouny
     */
    @BeforeEach
    public void createObjects() {
        // create necessary objects for test
        this.openDay = new OpenDay(testDate);
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
    }

    /**
     * Tests retreiving an OpenDay by valid Date
     * 
     * @author Mohamed Elsamadouny
     */
    @Test 
    public void testRetrieveOpenDayByDate () { 

        // setup mocks
        when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> openDay ); 

        // call service layer
        OpenDay retrievedOpenDay = openDayService.retrieveOpenDayByDate(testDate); 

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
    public void testRetrieveOpenDayByInvalidDate() { 

        // setup mocks
        when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> null); 

        // call service layer
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> openDayService.retrieveOpenDayByDate(testDate));

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
    public void testCreateDonation() { 

        // mock the repositories in the create Donation class

        // when a loan is saved, return that loan
        when(openDayRepository.save(any(OpenDay.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0)); 

        // call the service
        OpenDay openDayCreated = openDayService.createOpenDay(testDate); 

        verify(openDayRepository, times(1)).save(openDayCreated); 
    }

    @Test
    public void testDeleteDonation () { 

        // set up the mocks
        when(openDayRepository.findOpenDayByDate(testDate)).thenAnswer((InvocationOnMock invocation) -> openDay); 
        
        // delete the openday
        openDayService.deleteOpenDay(testDate);

        // verify that the delete method was called
        verify(openDayRepository, times(1)).deleteById(testDate);

    }








    



    
}
