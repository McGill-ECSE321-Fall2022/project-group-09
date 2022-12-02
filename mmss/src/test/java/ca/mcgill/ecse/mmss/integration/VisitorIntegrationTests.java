package ca.mcgill.ecse.mmss.integration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.dto.VisitorDto;
import ca.mcgill.ecse.mmss.dto.VisitorRequestDto;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.utils.Util;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class VisitorIntegrationTests {
	
	@Autowired
    private TestRestTemplate client;

    @Autowired
    private PersonRepository personRepository;
    
    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private VisitorRepository visitorRepository;
    
    private Person person;
    private Communication communication;
    private Visitor visitor;
    /**
     * Clear the database before all tests
     * @author Shidan Javaheri
     */
    @BeforeAll
    public static void clearDatabase(@Autowired Util util) {
        util.clearDatabase();
    }

    /**
     * Creates the obejcts needed by all test cases
     * BeforeAll because these objects are only modified in the database,
     * not themselves
     * 
     * @author Saviru Perera
     */

    @BeforeEach
    public void createObjects() {
        // create necessary objects for test, and save them to the database

        // person for visitor
        person = new Person();
        person.setFirstName("Young");
        person.setLastName("Jeezy");
        personRepository.save(person);
        
        communication = new Communication();
        communication.setCommunicationId(0);
        communicationRepository.save(communication);

        // the visitor
        this.visitor = new Visitor("young.jeezy@gmail.com", "hardestOut2", person);
        visitor.setCommunication(communication);
        visitorRepository.save(visitor);
    }
    
    /**
     * Deletes objects after each test
     * 
     * @author Saviru Perera
     */
    @AfterEach
    public void deleteObjects() {

        // delete the objects from the test
    	this.visitor.delete();
        this.person.delete();
        this.communication.delete();
        visitorRepository.deleteAll();
        personRepository.deleteAll();
        communicationRepository.deleteAll();

    }
    
    
    
    /**
     * Tests creating a visitor, and returns its username
     * 
     * @author Saviru Perera
     * @return the username of the person
     */
    @Test
    public void testCreateVisitor() {
        VisitorRequestDto request = new VisitorRequestDto();
        request.setFirstName("Chandler");
        request.setLastName("Jeezy");
        request.setUsername("chandler.jeezy@gmail.com");
        request.setPassword("hardestOut2");

        // make the post
        ResponseEntity<VisitorDto> response = client.postForEntity("/visitor", request, VisitorDto.class);

        // make assertions on the post
        assertNotNull(response, "The response is not null");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body");
        assertTrue(response.getBody().getUserName() != null, "Response has a valid username");

    }
    
    /**
     * Tests creating an additional visitor, and returns its username
     * 
     * @author Saviru Perera
     */
    @Test
    public void testCreateAdditionalVisitor() {
        VisitorRequestDto request = new VisitorRequestDto();
        request.setUsername(visitor.getUsername());
        request.setNewUsername("travis@scott");
        request.setNewPassword("Beanforever24");

        // make the post
        ResponseEntity<VisitorDto> response = client.postForEntity("/visitor/addAccount", request, VisitorDto.class);

        // make assertions on the post
        assertNotNull(response, "The response is not null");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body");
        assertTrue(response.getBody().getUserName() != null, "Response has a valid username");

    }

    
    /**
     * Retrieves the visitor that was created by its username
     * 
     * @author Saviru Perera
     */
    @Test
    public void testGetVisitor() {
        String username = visitor.getUsername();
        // try the get
        ResponseEntity<VisitorDto> response = client.getForEntity("/visitor/" + username, VisitorDto.class);

        // make assertions on the get

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body");
        assertEquals(response.getBody().getUserName(), username, "Response has correct username");

    }
    
    /**
     * Tests updating a visitor password
     * 
     * @author Saviru Perera
     */
    @Test
    public void testUpdateVisitorPassword() {
        // make Dto for request
        VisitorRequestDto visitorDto = new VisitorRequestDto(visitor,visitor.getUsername(),"Ilovejava23");

        // make an entity to send the request with 
        HttpEntity<VisitorRequestDto> request= new HttpEntity<>(visitorDto); 

        // send the request
        ResponseEntity<VisitorDto> response = client.exchange("/visitor", HttpMethod.PUT ,request, VisitorDto.class);

        // assertions on response
        assertNotNull(response); 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody()); 
        assertEquals(response.getBody().getPassword(), "Ilovejava23"); 

        // get the updated visitor from the database
        Visitor updatedVisitor = visitorRepository.findVisitorByUsername(visitor.getUsername());

        // verify the update
        assertEquals(updatedVisitor.getPassword(), "Ilovejava23");

    }

    
    /**
     * Tests deleting a visitor
     * 
     * @author Saviru Perera
     */
    
    @Test
    public void testDeleteVisitor() {

        // make Dto for request
        VisitorDto request = new VisitorDto(visitor);
        String username = request.getUserName();

        
        ResponseEntity<String> response = client.exchange("/visitor/" + username, HttpMethod.DELETE,null, String.class);

        // assert on the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Visitor successfully deleted", response.getBody());

        // get the updated visitor from the database
        Visitor updatedVisitor = visitorRepository.findVisitorByUsername(username);

        // verify the visitor has been deleted
        assertNull(updatedVisitor, "Visitor successfully deleted");

    }

    /**
     * Tests getting all visitors
     * 
     * @author Saviru Perera
     */

    @Test
    public void testGetAllVisitors() {

        // make request
        var response = client.getForEntity("/visitor", ArrayList.class);

        // assertions on the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody()); 
        // get array list of visitors
        ArrayList<VisitorDto> extractedVisitors = response.getBody();

        // assertions
        assertNotNull(extractedVisitors);
        assertEquals(1, extractedVisitors.size());

    };
    
    /**
     * Tests getting all visitors by their person
     * 
     * @author Saviru Perera
     */

    @Test
    public void testGetAllVisitorsByPerson() {
        // make request
        var response = client.getForEntity("/visitor/byPerson?id=" + person.getPersonId(), ArrayList.class);

        // assertions on the responses
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody()); 

       
        // get array list of employees
        ArrayList<VisitorDto> extractedVisitors = response.getBody();

        // assertions
        assertNotNull(extractedVisitors);
        assertEquals(1, extractedVisitors.size());
    }

}
