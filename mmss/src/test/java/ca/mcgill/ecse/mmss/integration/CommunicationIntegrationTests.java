package ca.mcgill.ecse.mmss.integration;

import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.ManagerRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dto.CommunicationDto;
import ca.mcgill.ecse.mmss.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for the CommunicationController class
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommunicationIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private CommunicationRepository communicationRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ManagerRepository managerRepository;

    private Person person;

    private Manager manager;

    private Communication communication;

    /**
     * Create objects before each test
     */
    @BeforeEach
    public void createObjects() {
        // Create objects and associations
        communication = new Communication();
        person = new Person();
        person.setFirstName("Hello");
        person.setLastName("World");
        manager = new Manager("username", "password", person);
        manager.setCommunication(communication);
        // Persist to DB
        communicationRepository.save(communication);
        personRepository.save(person);
        managerRepository.save(manager);
    }

    /**
     * Delete objects after each test
     */
    @AfterEach
    public void deleteObjects() {
        communication.delete();
        person.delete();
        manager.delete();
        managerRepository.deleteAll();
        communicationRepository.deleteAll();
        personRepository.deleteAll();
    }

    /**
     * Get a communication by a username
     */
    @Test
    public void testGetCommunication() {
        // Try the get
        ResponseEntity<CommunicationDto> response = client.getForEntity("/communication/" + manager.getUsername(), CommunicationDto.class);
        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
