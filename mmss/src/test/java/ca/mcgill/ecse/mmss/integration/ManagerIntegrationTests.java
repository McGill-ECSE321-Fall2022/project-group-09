package ca.mcgill.ecse.mmss.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse.mmss.dao.ManagerRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dto.ManagerDto;
import ca.mcgill.ecse.mmss.model.Manager;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.service.ManagerService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ManagerIntegrationTests {

    @Autowired 
    private TestRestTemplate client; 

    @Autowired
    private ManagerRepository managerRepository; 

    @Autowired
    private PersonRepository personRepository; 

    @Autowired ManagerService managerService; 

    // two objects will be needed in our tests
    private Person person; 
    private Manager manager; 

   /**
     * Create objects needed for tests
     * Uses the service method that is not exposed
     * This service method was already tested
     * @author Shidan Javaheri
     */
    @BeforeEach
    public void createObjects() {
        // create the manager
        managerService.createManager(); 

    }

    /**
     * Delete all objects
     * 
     * @autor Shidan Javaheri
     */
    @AfterEach
    public void deleteObjects() {
        // delete all objects
        managerRepository.deleteAll();
    }

    /**
     * Tests getting the manager from the database
     */
    @Test
    public void testGetManager() { 
        
        // make the get request
        
        ResponseEntity<ManagerDto> response = client.getForEntity("/manager/", ManagerDto.class); 

        // assertions 
        assertNotNull(response); 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getUserName(), "marwan.kanan@mcgill.ca"); 
         

    }

    /**
     * Tests updating the manager's password
     */

    @Test    
    public void testUpdateManger() { 

        // make the put request
        client.put("/manager?oldPassword=aVerySecurePassword&newPassword=aNewVerySecurePassword", ManagerDto.class);

        // find out if the manager is updated
        Manager manager = managerRepository.findManagerByUsername("marwan.kanan@mcgill.ca"); 

        // assertions that the update worked
        assertEquals(manager.getUsername(), "marwan.kanan@mcgill.ca"); 
        assertEquals(manager.getPassword(), "aNewVerySecurePassword");
                    
    }
    

    
}
