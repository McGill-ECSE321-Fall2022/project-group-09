package ca.mcgill.ecse.mmss.integration;

import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.ManagerRepository;
import ca.mcgill.ecse.mmss.dao.NotificationRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dto.NotificationDto;
import ca.mcgill.ecse.mmss.dto.RequestNotificationDto;
import ca.mcgill.ecse.mmss.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the NotificationController class
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotificationIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private CommunicationRepository communicationRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ManagerRepository managerRepository;

    Person person;
    Manager manager;
    Communication communication;
    ArrayList<Notification> notifications;

    /**
     * Create objects before each test
     */
    @BeforeEach
    public void createObjects() {
        // Create objects and associations
        // Store the saved instance back in the field
        person = new Person();
        person.setFirstName("Hello");
        person.setLastName("World");
        person = personRepository.save(person); // DB
        manager = new Manager("user@mail", "password", person);
        communication = communicationRepository.save(new Communication()); // DB
        manager.setCommunication(communication);
        manager = managerRepository.save(manager); //DB
        notifications = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Notification notification = new Notification();
            notification.setMessage("The message is " + i);
            notification.setDate(new Date(System.currentTimeMillis()));
            notification.setSentNotification(communication);
            notifications.add(notificationRepository.save(notification)); // DB
        }
    }

    /**
     * Delete objects after each test
     */
    @AfterEach
    public void deleteObjects() {
        person.delete();
        manager.delete();
        communication.delete();
        notifications.clear();
        managerRepository.deleteAll();
        notificationRepository.deleteAll();
        communicationRepository.deleteAll();
        personRepository.deleteAll();
    }

    /**
     * Get a notification by its primary key
     */
    @Test
    public void testGetCommunication() {
        // Try the get
        ResponseEntity<NotificationDto> response = client.getForEntity("/notification/" + notifications.get(0).getNotificationId(), NotificationDto.class);
        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(notifications.get(0).getNotificationId(), response.getBody().getNotificationId());
    }

    /**
     * Get all the notification associated with a username
     */
    @Test
    public void testGetAllNotificationsByUsername() {
        // Try the get
        ResponseEntity<ArrayList> response = client.getForEntity("/notification/username/?username=" + manager.getUsername(), ArrayList.class);
        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(notifications.size(), response.getBody().size());
    }

    /**
     * Create a notification based on an input request
     */
    @Test
    public void testCreateNotification() {
        RequestNotificationDto request = new RequestNotificationDto(manager.getUsername(), "Hello world");
        // make the post
        ResponseEntity<String> response = client.postForEntity("/notification", request, String.class);
        // make assertions on the post
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Notification successfully created.", response.getBody());
    }

    /**
     * Delete a notification given its primary key
     */
    @Test
    public void testDeleteNotification() {
        // make DTO for request
        int id = notifications.get(0).getNotificationId();
        ResponseEntity<String> response = client.exchange("/notification/"+ id, HttpMethod.DELETE,null, String.class);
        // make assertions on the post
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Notification successfully deleted.", response.getBody());
        assertNull(notificationRepository.findNotificationByNotificationId(id));
        assertEquals(notifications.size() - 1, notificationRepository.count());
    }
}
