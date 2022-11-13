package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;

import ca.mcgill.ecse.mmss.dao.*;
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
public class CommunicationServiceTests {

    @Mock
    private CommunicationRepository communicationRepository;
    @Mock
    private LoginService loginService;
    @InjectMocks
    private CommunicationService communicationService;

    private Communication communication;
    private AccountType account;

    /**
     * Create objects before each test
     */
    @BeforeEach
    public void createObjects() {
        communication = new Communication(0);
        account = new Manager("username", "password", new Person());
        account.setCommunication(communication);
    }

    /**
     * Delete objects after each test
     */
    @AfterEach
    public void deleteObjects() {
        communication.delete();
        account.delete();
    }

    /**
     * Test get a communication with a valid username
     */
    @Test
    public void testGetCommunicationByUsername () {
        // setup mocks
        when(loginService.getAccountByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> account );
        // call service layer
        Communication com = communicationService.getCommunicationByUsername("username");
        // assertions
        assertEquals (communication, com);
        // verify calls to repositories
        verify(loginService, times (1)).getAccountByUsername(any(String.class));
    }

    /**
     * Test get a communication with a valid username with no prior communication
     */

    @Test
    public void testGetCommunicationInvalid() {
        // setup mocks
        when(loginService.getAccountByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> account );
        // Set no communication
        account.setCommunication(null);
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> communicationService.getCommunicationByUsername("username"));
        // check the message contains the right message and status
        assertEquals("Communication not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        // verify calls to repositories
        verify(loginService, times (1)).getAccountByUsername(any(String.class));
    }

}
