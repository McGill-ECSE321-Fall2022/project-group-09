package ca.mcgill.ecse.mmss.service;

import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.ManagerRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Manager;
import ca.mcgill.ecse.mmss.model.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.aopalliance.intercept.Invocation;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTests {

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private PersonRepository personRepository;

    @Mock 
    private CommunicationRepository communicationRepository; 

    @InjectMocks
    private ManagerService managerService;

    // objects needed for tests
    private Person person;
    private Manager manager;

    /**
     * Create objects needed for tests
     * Mocks the database
     * 
     * @author Shidan Javaheri
     */
    @BeforeEach
    public void createObjects() {
        // create person
        person = new Person();
        person.setFirstName("Marwan");
        person.setLastName("kanaan");
        // create manager
        manager = new Manager("marwan.kanaan@mcgill.ca", "aVerySecurePassword", person);

    }

    /**
     * Delete all objects
     * 
     * @autor Shidan Javaheri
     */
    @AfterEach
    public void deleteObjects() {
        // delete all objects
        manager.delete();
        person.delete();
    }

    /**
     * Tests getting the manager
     * @author Shidan Javaheri
     */
    @Test
    public void testGetManager() { 
        // set up mocks
        when(managerRepository.findManagerByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation)-> manager);

        // call service
        Manager retrievedManager = managerService.getManager(); 

        // assertions

        assertEquals(manager.getUsername(), retrievedManager.getUsername());

        // verifications
        verify(managerRepository, times(1)).findManagerByUsername("marwan.kanaan@mcgill.ca"); 


    }

    /**
     * Tests creating a manager. Can never fail, can never be called 
     * @author Shidan Javaheri
     */
    @Test
    public void testCreateManager() { 
        // setup mocks 
        when(managerRepository.save(any(Manager.class))).thenAnswer((InvocationOnMock invocation)-> invocation.getArgument(0));

        when(personRepository.save(any(Person.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0)); 

        when(communicationRepository.save(any(Communication.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0)); 


        // call to service layer
        Manager manager = managerService.createManager();

        // assertions
        assertEquals(manager.getUsername(), "marwan.kanaan@mcgill.ca");
        assertEquals(manager.getPerson().getFirstName(), person.getFirstName()); 
        assertNotNull(manager.getCommunication(), "The communication is not null"); 

        // verifications
        verify(managerRepository, times(1)).save(any(Manager.class)); 
        verify(personRepository, times(1)).save(any(Person.class)); 
        verify(communicationRepository, times(1)).save((any(Communication.class))); 

           
    }

    /**
     * Tests sucessfully updating a password
     * @author Shidan Javaheri
     */
    @Test
    public void testUpdatePassword() { 

        // set up mocks
        when(managerRepository.findManagerByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> manager); 
        when(managerRepository.save(any(Manager.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0)); 

        // call to service layer
        Manager updatedManager = managerService.updateMangagerPassword("aVerySecurePassword", "aNewVerySecurePassword"); 

        // assertions 
        assertEquals(updatedManager.getUsername(), manager.getUsername());
        assertEquals(updatedManager.getPassword(), "aNewVerySecurePassword"); 

        // verifications
        verify(managerRepository, times(1)).findManagerByUsername("marwan.kanaan@mcgill.ca"); 
        verify(managerRepository, times (1)).save(any(Manager.class)); 
    }

    /**
     * Tests updating the managers password with incorrect current password
     */
    @Test
    public void testUpdatePasswordWithWrongPassword() {
        final String incorrectPassword = "noGoodPassword";

        // set up mocks
        when(managerRepository.findManagerByUsername(any(String.class)))
                .thenAnswer((InvocationOnMock invocation) -> manager);

        // call which catches the exception
        MmssException ex = assertThrows(MmssException.class,
                () -> managerService.updateMangagerPassword(incorrectPassword, "attemptedNewPassword"));

        // assertions
        assertEquals(ex.getStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(ex.getMessage(), "Incorrect password");

        // verifications
        verify(managerRepository, times(1)).findManagerByUsername("marwan.kanaan@mcgill.ca");
    }

}
