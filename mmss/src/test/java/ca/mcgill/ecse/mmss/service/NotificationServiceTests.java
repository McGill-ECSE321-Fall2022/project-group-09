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

/**
 * Tests for the NotificationService class
 */
@ExtendWith(MockitoExtension.class)
public class NotificationServiceTests {

    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private CommunicationService communicationService;

    @InjectMocks
    private NotificationService notificationService;

    AccountType account;
    Communication communication;
    ArrayList<Notification> notifications;

    /**
     * Create objects before each test
     */
    @BeforeEach
    public void createObjects(){
        account = new Manager("user@mail", "password", new Person());
        communication = new Communication(0);
        account.setCommunication(communication);
        notifications = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            notifications.add(new Notification(i, Integer.toString(i), new Date(System.currentTimeMillis()), communication));
        }
    }

    /**
     * Delete objects after each test
     */
    @AfterEach
    public void deleteObjects() {
        notifications.clear();
        communication.delete();
        account.delete();
    }

    /**
     * Test get a notification with a valid id
     */
    @Test
    public void testGetNotificationById () {
        Notification notification = notifications.get(0);
        // setup mocks
        when(notificationRepository.findNotificationByNotificationId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> notification );
        // call service layer
        Notification notif = notificationService.getNotificationById(0);
        // assertions
        assertEquals (notification, notif);
        // verify calls to repositories
        verify(notificationRepository, times (1)).findNotificationByNotificationId(any(int.class));
    }

    /**
     * Test get a notification with an invalid id
     */
    @Test
    public void testGetNotificationByInvalidId () {
        final int invalidId = 99;
        // setup mocks
        when(notificationRepository.findNotificationByNotificationId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null );
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> notificationService.getNotificationById(invalidId));
        // check the message contains the right message and status
        assertEquals("Notification not found.", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        // verify calls to repositories
        verify(notificationRepository, times (1)).findNotificationByNotificationId(invalidId);
    }

    /**
     * Test to get all notifications with a valid username
     */
    @Test
    public void testGetAllNotificationsByUsername () {
        // setup mocks
        when(communicationService.getCommunicationByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> communication );
        when(notificationRepository.findAllBySentNotification(any(Communication.class))).thenAnswer((InvocationOnMock invocation) -> notifications );
        // call service layer
        ArrayList<Notification> notifs = notificationService.getAllNotificationsByUsername("user@mail");
        // assertions
        assertEquals (notifications, notifs);
        // verify calls to repositories
        verify(notificationRepository, times (1)).findAllBySentNotification(any(Communication.class));
        verify(communicationService, times (1)).getCommunicationByUsername(any(String.class));
    }

    /**
     * Test create a notification with an invalid message
     */
    @Test
    public void testCreateNotificationWithInvalidMessage () {
        // setup mocks
        when(communicationService.getCommunicationByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> communication );
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> notificationService.createNotificationByUsername(account.getUsername(), " "));
        // check the message contains the right message and status
        assertEquals("The notification’s message cannot be empty or longer than 300 characters.", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        // verify calls to repositories
        verify(communicationService, times (1)).getCommunicationByUsername(any(String.class));
    }

    /**
     * Test create a notification
     */
    @Test
    public void testCreateNotification () {
        // setup mocks
        when(communicationService.getCommunicationByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> communication );
        when(notificationRepository.save(any(Notification.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        // call service layer
        Notification notif = notificationService.createNotificationByUsername(account.getUsername(), "Hello");
        // assertion
        assertEquals("Hello", notif.getMessage());
        assertEquals(communication, notif.getSentNotification());
        // verify calls to repositories
        verify(communicationService, times (1)).getCommunicationByUsername(any(String.class));
        verify(notificationRepository, times (1)).save(any(Notification.class));
    }

    /**
     * Test delete a notification
     */
    @Test
    public void testDeleteNotification () {
        Notification notification = notifications.get(0);
        // setup mocks
        when(notificationRepository.findNotificationByNotificationId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> notification );
        // call service layer
        notificationService.deleteNotification(0);
        // verify calls to repositories
        verify(notificationRepository, times (1)).findNotificationByNotificationId(any(int.class));
    }
}
