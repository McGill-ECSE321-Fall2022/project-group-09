package ca.mcgill.ecse.mmss.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse.mmss.dao.ManagerRepository;
import ca.mcgill.ecse.mmss.dto.ManagerDto;
import ca.mcgill.ecse.mmss.model.Manager;
import ca.mcgill.ecse.mmss.service.ManagerService;

/**
 * Tests the functionality of all services exposed through the URL "/manager"
 * @author Shidan Javaheri
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ManagerIntegrationTests {

    @Autowired 
    private TestRestTemplate client; 

    @Autowired
    private ManagerRepository managerRepository; 


    @Autowired ManagerService managerService; 

   /**
     * Create objects needed for tests. 
     * Uses the service method that is not exposed. 
     * This service method was already tested. 
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
     * @author Shidan Javaheri
     */
    @Test
    public void testGetManager() { 
        
        // make the get request
        
        ResponseEntity<ManagerDto> response = client.getForEntity("/manager/", ManagerDto.class); 

        // assertions 
        assertNotNull(response); 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody().getUserName(), "marwan.kanaan@mcgill.ca"); 
         

    }

    /**
     * Tests updating the manager's password
     * @author Shidan Javaheri
     */

    @Test    
    public void testUpdateManger() { 

        // make the put request
        client.put("/manager?oldPassword=aVerySecurePassword&newPassword=aNewVerySecurePassword", ManagerDto.class);

        // find out if the manager is updated
        Manager manager = managerRepository.findManagerByUsername("marwan.kanaan@mcgill.ca"); 

        // assertions that the update worked
        assertEquals(manager.getUsername(), "marwan.kanaan@mcgill.ca"); 
        assertEquals(manager.getPassword(), "aNewVerySecurePassword");
                    
    }
    

    
}
