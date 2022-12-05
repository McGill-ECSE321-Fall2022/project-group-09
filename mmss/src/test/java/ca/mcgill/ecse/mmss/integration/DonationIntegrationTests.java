package ca.mcgill.ecse.mmss.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
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

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.DonationRepository;
import ca.mcgill.ecse.mmss.dao.NotificationRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.RoomRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.dto.ArtefactDto;
import ca.mcgill.ecse.mmss.dto.DonationDto;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Donation;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;
import ca.mcgill.ecse.mmss.model.Room.RoomType;
import ca.mcgill.ecse.mmss.utils.Util;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Room;
import ca.mcgill.ecse.mmss.model.Visitor;



/**
 * Integration tests for Donation
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DonationIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private ArtefactRepository artefactRepository;

    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired 
    private NotificationRepository notificationRepository;

    @Autowired
    private RoomRepository roomRepository; 

    // Objects we will need in all our tests
    private Donation donation;
    private Person person;
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
     * Creates the obejcts needed by all test cases. 
     * This is a BeforeEach because objects that are manipulated by certain calls should be reset for each test
     * 
     * @author Mohamed Elsamadouny
     */
    @BeforeEach
    public void createObjects() {
        // create necessary objects for test, and save them to the database

        // person for visitor
        person = new Person();
        person.setFirstName("Mo");
        person.setLastName("Salah");
        personRepository.save(person);

        // the visitor
        this.visitor = new Visitor("mo.salah@gmail.com", "IScoreGoalz", person);

        // a visitor is always created with a communication
        Communication communication = new Communication();
        communicationRepository.save(communication);
        visitor.setCommunication(communication);
        visitorRepository.save(visitor);
        
        // the donation
        donation = new Donation();
        donation.setVisitor(visitor);
        donation.setExchangeId(0);
        donation.setSubmittedDate(Date.valueOf("2022-10-10"));
        donation.setItemName("Trophy");
        donation.setDescription("Won from World Cup 2022");
        donation.setExchangeStatus(ExchangeStatus.Pending);
        donationRepository.save(donation);

        // create a storate room for the artefact to be moved to
        Room storageRoom = new Room();
        storageRoom.setRoomType(RoomType.Storage);
        roomRepository.save(storageRoom);

    }

    /**
     * Deletes objects after each test
     * 
     * @author Mohamed Elsamadouny
     */
    @AfterEach
    public void deleteObjects() {

        // delete the objects from the test
        this.person.delete();
        this.visitor.delete();
        this.donation.delete();

        artefactRepository.deleteAll(); 
        roomRepository.deleteAll();
        donationRepository.deleteAll();
        visitorRepository.deleteAll();
        notificationRepository.deleteAll(); 
        communicationRepository.deleteAll();
        personRepository.deleteAll();
        
    }


    /**
     * Tests creating and retrieving a donation
     *
     * @author Mohamed Elsamadouny
     */
    @Test
    public void testCreateAndGetDonation() {
        // create donation dto
        DonationDto request = new DonationDto(donation);

        // make the post
        ResponseEntity<DonationDto> response1 = client.postForEntity("/donation", request, DonationDto.class);

        // make assertions on the post
        assertNotNull(response1, "The response is not null");
        assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        assertNotNull(response1.getBody(), "Response has a body");
        assertTrue(response1.getBody().getExchangeId() > 0, "Response has a valid id");
        int id = response1.getBody().getExchangeId();

        // try the get
        ResponseEntity<DonationDto> response2 = client.getForEntity("/donation/" + id, DonationDto.class);

        // make assertions on the get
        assertNotNull(response2);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody(), "Response has a body");
        assertEquals(response2.getBody().getExchangeId(), id, "Response has correct id");
    }

    /**
     * Tests updating approving a donation
     * 
     * @author Mohamed Elsamadouny
     */
    @Test
    public void testUpdateDonationtoApproved() {

        // get the donation id
        int donationId = donation.getExchangeId();

        // create an artefact Dto
        ArtefactDto artefactDto = new ArtefactDto();
        artefactDto.setCanLoan(false);
        artefactDto.setInsuranceFee(1.0);
        artefactDto.setLoanFee(0.5);
        artefactDto.setImageUrl("nullable.jpg");

        // make an entity to send the request with
        HttpEntity<ArtefactDto> request = new HttpEntity<>(artefactDto);

        // send the request
        ResponseEntity<ArtefactDto> response = client.exchange("/donation/" + donationId, HttpMethod.PUT, request, ArtefactDto.class);

        // assertions on response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // asssert artefact was created
        ArrayList<Artefact> artefacts = artefactRepository.findAll();
        assertEquals(1, artefacts.size());

        // get the updated Donation from the database
        Donation updatedDonation = donationRepository.findDonationByExchangeId(donation.getExchangeId());

        // verify the doantion doesn't exist anymore
        assertNull(updatedDonation);
    }

    /**
     * Tests declining a loan status
     * This automatically tests the delete service
     * 
     * @author Mohamed Elsamadouny
     */
    @Test
    public void testDeclinedDonation() {
         // make Dto for request
         DonationDto donationDto = new DonationDto(donation);
         donationDto.setExchangeStatus(ExchangeStatus.Declined);
         int donationId = donationDto.getExchangeId();

        // send the request
        ResponseEntity<String> response = client.exchange("/donation?id=" + donationId, HttpMethod.PUT, null, String.class);

        // assertions on response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody(), "Donation successfully Declined");

        // get the updated Donation from the database
        Donation updatedDonation = donationRepository.findDonationByExchangeId(donation.getExchangeId());

        // verify the doantion doesn't exist anymore
        assertNull(updatedDonation);

    }

    /**
     * Tests deleting a donation
     * 
     * @author Mohamed Elsamadouny
     */

    @Test
    public void testDeleteDonation() {

        // make Dto for request
        DonationDto request = new DonationDto(donation);
        int id = request.getExchangeId();

        ResponseEntity<String> response = client.exchange("/donation/" + id, HttpMethod.DELETE, null, String.class);

        // assert on the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Donation successfully deleted", response.getBody());

        // get the updated donation from the database
        Donation updatedDonation = donationRepository.findDonationByExchangeId(id);

        // verify the donation has been deleted
        assertNull(updatedDonation);

    }

    /**
     * Tests getting all donations
     * 
     * @author Mohamed Elsamadouny
     */

    @Test
    public void testGetAllDonations() {

        // make request
        var response = client.getForEntity("/donation", ArrayList.class);

        // assertions on the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // get array list of loans
        ArrayList<DonationDto> extractedDonations = response.getBody();

        // assertions
        assertNotNull(extractedDonations);
        assertEquals(1, extractedDonations.size());

    };

    /**
     * Tests getting all donations by their Submitted date
     * 
     * @author Mohamed Elsamadouny
     */

    @Test
    public void testGetAllLoansBySubmittedDate() {
        // make request
        var response = client.getForEntity("/donation/submittedDate?date=2022-10-10", ArrayList.class);

        // assertions on the responses
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // get array list of 
        ArrayList<DonationDto> extractedDonations = response.getBody();

        // assertions
        assertNotNull(extractedDonations);
        assertEquals(1, extractedDonations.size());
    }

    /**
     * Tests getting all donations by a visitor
     * 
     * @author Mohamed Elsamadouny
     */

    @Test
    public void testGetAllDonationsByVisitor() {
        // make request
        var response = client.getForEntity("/donation/visitor?username=mo.salah@gmail.com", ArrayList.class);

        // assertions on the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // get array list of loans
        ArrayList<DonationDto> extractedDonations = response.getBody();

        // assertions
        assertNotNull(extractedDonations);
        assertEquals(1, extractedDonations.size());

    }

    
}
