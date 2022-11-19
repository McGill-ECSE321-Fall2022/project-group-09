package ca.mcgill.ecse.mmss.integration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
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
import ca.mcgill.ecse.mmss.dto.LoanDto;
import ca.mcgill.ecse.mmss.dto.VisitorDto;
import ca.mcgill.ecse.mmss.dto.VisitorRequestDto;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Visitor;

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
     * Tests creating and retrieving a visitor
     *
     * @author Shidan Javaheri
     */
    @Test
    public void testCreateAndGetVisitor() {
        String username = testCreateVisitor();
        testGetVisitor(username);
    }
    
    /**
     * Tests creating a visitor, and returns its username
     * 
     * @author Saviru Perera
     * @return the username of the person
     */
    
    public String testCreateVisitor() {
        VisitorRequestDto request = new VisitorRequestDto();
        request.setFirstName(person.getFirstName());
        request.setLastName(person.getLastName());
        request.setUsername("young.jeezy@gmail.com");
        request.setPassword("hardestOut2");

        // make the post
        ResponseEntity<VisitorDto> response = client.postForEntity("/visitor", request, VisitorDto.class);

        // make assertions on the post
        assertNotNull(response, "The response is not null");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body");
        assertTrue(response.getBody().getUsername() != null, "Response has a valid username");
        return response.getBody().getUsername();

    }
    
    /**
     * Tests creating an additional visitor, and returns its username
     * 
     * @author Saviru Perera
     * @return the username of the person
     */
    
    public String testCreateAdditionalVisitor() {
        VisitorRequestDto request = new VisitorRequestDto();
        request.setUsername(visitor.getUsername());
        request.setNewUsername("travis@scott");
        request.setNewPassword("Beanforever24");

        // make the post
        ResponseEntity<VisitorDto> response = client.postForEntity("/visitor", request, VisitorDto.class);

        // make assertions on the post
        assertNotNull(response, "The response is not null");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body");
        assertTrue(response.getBody().getUsername() != null, "Response has a valid username");
        return response.getBody().getUsername();

    }

    
    /**
     * Retrieves the visitor that was created by its username
     * 
     * @author Saviru Perera
     */
    
    public void testGetVisitor(String username) {
        // try the get
        ResponseEntity<VisitorDto> response = client.getForEntity("/visitor/" + username, VisitorDto.class);

        // make assertions on the get

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body");
        assertEquals(response.getBody().getUsername(), username, "Response has correct username");

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
        String username = request.getUsername();

        
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

    //@Test
//    public void testGetAllLoansByStatus() {
//        // make request
//        var response = client.getForEntity("/loan/status?status=Pending", ArrayList.class);
//        var responseEmpty = client.getForEntity("/loan/status?status=Declined", ArrayList.class);
//
//        // assertions on the responses
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody()); 
//
//        assertNotNull(responseEmpty);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(responseEmpty.getBody()); 
//
//        // get array list of loans
//        ArrayList<LoanDto> extractedLoans = response.getBody();
//        ArrayList<LoanDto> empty = responseEmpty.getBody();
//
//        // assertions
//        assertNotNull(extractedLoans);
//        assertEquals(0, empty.size());
//        assertEquals(1, extractedLoans.size());
//    }
    
    
    
    
    
    


}
