package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.sql.Date;
import java.util.ArrayList;

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.DonationRepository;
import ca.mcgill.ecse.mmss.dao.RoomRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Donation;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Room;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;
import ca.mcgill.ecse.mmss.model.Room.RoomType;

/**
 * Tests for all business Logic for Donation
 */

@ExtendWith(MockitoExtension.class)
public class DonationServiceTests {

    // We mock repositories - the entities that access things from the database
    @Mock
    private DonationRepository donationRepository;

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private ArtefactRepository artefactRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private ArtefactService artefactService;

    @Mock
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    // We inject the mocks in the doantion service - the thing that calls on the
    // repositories
    @InjectMocks
    private DonationService donationService;



    // Objects we will need in all our tests
    private Donation donation;
    private Person person;
    private Visitor visitor;
    private Artefact artefact;
    private Room storageRoom;

    /**
     * Creates the obejcts needed by all test cases
     * 
     * @author Mohamed Elsamadouny
     */
    @BeforeEach
    public void createObjects() {

        // create necessary objects for test
        // This will Mock the content of your database
        // Basically a fake database system
        this.person = new Person(0, "Henry", "Doppleganger");
        this.visitor = new Visitor("henry@doppleganger", "ILikeCheese", person);
        this.donation = new Donation();
        this.artefact = new Artefact();
        this.storageRoom = new Room(0, RoomType.Storage);
        artefact.setArtefactName("Lightsaber");
        artefact.setDescription("From the death star");
        artefact.setArtefactId(0);
        artefact.setCanLoan(false);
        artefact.setInsuranceFee(1.0);
        artefact.setLoanFee(0.5);
        artefact.setImageUrl("hello.jpg");
        artefact.setRoom(storageRoom);
        donation.setItemName("Lightsaber");
        donation.setDescription("From the death star");
        donation.setVisitor(visitor);
        donation.setExchangeId(0);
        donation.setSubmittedDate(Date.valueOf("2022-10-10"));

    }

    /**
     * Deletes objects after each test
     * 
     * @author Mohamed Elsamadouny
     */
    @AfterEach
    public void deleteObjects() {
        // delete the objects from the test
        this.visitor.delete();
        this.person.delete();
        this.donation.delete();
        this.artefact.delete();
        this.storageRoom.delete();
    }

    /**
     * Tests retrieving a donation with a valid id
     * 
     * @author Mohamed Elsamadouny
     */
    @Test 
    public void testGetDonationById () { 

        // setup mocks
        when(donationRepository.findDonationByExchangeId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> donation ); 

        // call service layer
        Donation retrievedDonation = donationService.getDonationById(0); 

        // assertions
        assertEquals(0, retrievedDonation.getExchangeId()); 
        assertEquals(visitor, retrievedDonation.getVisitor());
        assertEquals("Lightsaber", retrievedDonation.getItemName());

        // verify calls to repositories
        verify(donationRepository, times (1)).findDonationByExchangeId(0); 
    }

    /**
     * Tests retrieving all donations by a visiot
     * 
     * @author Mohamed Elsamadouny
     */
    @Test 
    public void testGetAllDonationsByVisitor () { 

        // setup mocks
        when(visitorRepository.findVisitorByUsername(any(String.class)))
        .thenAnswer((InvocationOnMock invocation) -> visitor);
        ArrayList<Donation> list = new ArrayList<Donation>();
        list.add(donation);
        when(donationRepository.findByVisitor(visitor)).thenAnswer((InvocationOnMock invocation) -> list);

        // call service layer
        ArrayList<Donation> retrievedDonations = donationService.getAllDonationsByVisitor(visitor.getUsername()); 

        // assertions
        assertEquals(1, retrievedDonations.size());
        assertEquals(visitor.getUsername(), retrievedDonations.get(0).getVisitor().getUsername());  

        // verify calls to repositories
        verify(visitorRepository, times (1)).findVisitorByUsername(visitor.getUsername()); 
        verify(donationRepository, times (1)).findByVisitor(visitor); 
    }

    /**
     * Tests getting all donations with an invalid username
     * 
     * @author Mohamed Elsamadouny
     */
    @Test
    public void testGetAllDonationsByVisitorInvalidUsername() {
        final String invalidUsername = "badUsername";

        // setup mocks
        when(visitorRepository.findVisitorByUsername(any(String.class)))
                .thenAnswer((InvocationOnMock invocation) -> null);

        // call service
        MmssException ex = assertThrows(MmssException.class,
                () -> donationService.getAllDonationsByVisitor(invalidUsername));

        // assertions on error
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("The visitor with this Id was not found", ex.getMessage());

        // verfications
        verify(visitorRepository, times(1)).findVisitorByUsername(invalidUsername);

    }

    /**
     * Tests getting a donation with an invalid Id
     * 
     * @author Mohamed Elsamadouny
     */

    @Test
    public void testGetDonationByInvalidId() {
        final int invalidId = 99;
        // set up mock
        when(donationRepository.findDonationByExchangeId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> donationService.getDonationById(invalidId));

        // check the message contains the right message and status
        assertEquals("Donation not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

        // verify calls to repositories
        verify(donationRepository, times(1)).findDonationByExchangeId(invalidId);
    }

    /**
     * Tests creating a donation successfully
     * @author Mohamed Elsamadouny
     */
    @Test
    public void testCreateDonation() { 

        // mock the repositories in the create Donation class

        // when a donation is saved, return that donation
        when(donationRepository.save(any(Donation.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0)); 

        // when a visitor is requested, return the visitor
        when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> visitor); 

        // call the service to create a donation
        Donation donationCreated = donationService.createDonation("henry@doppleganger", "LightSaber", "From the death star"); 

        // assertions 
        assertEquals(0,donation.getExchangeId());
        assertEquals(visitor, donationCreated.getVisitor()); 
        // donation that has been requested should have status pending
        assertEquals(ExchangeStatus.Pending, donationCreated.getExchangeStatus()); 

        // check that each repository was called the right number of times, and with right arguments
        verify(donationRepository, times(1)).save(any(Donation.class)); 
        verify(visitorRepository, times(1)).findVisitorByUsername("henry@doppleganger"); 
    }

    /**
     * Tests creating a donation successfully
     * @author Mohamed Elsamadouny
     */
    @Test
    public void testCreateDonationInvalidName() { 

        // mock the repositories in the create Donation cl
        // when a visitor is requested, return the visitor
        when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> visitor); 

        // call the service to create a donation
        MmssException ex = assertThrows(MmssException.class, () -> donationService.createDonation("henry@doppleganger", "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz",
         "From the death star")); 

        // assertions 
        assertEquals("The donation name should not exceed 50 characters", ex.getMessage());

        // check that each repository was called the right number of times, and with right arguments
        verify(visitorRepository, times(1)).findVisitorByUsername("henry@doppleganger"); 
    }

    @Test
    public void testCreateDonationInvalidDescription() { 

        // mock the repositories in the create Donation cl
        // when a visitor is requested, return the visitor
        when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> visitor); 

        // call the service to create a donation
        MmssException ex = assertThrows(MmssException.class, () -> donationService.createDonation("henry@doppleganger", "Lightsaber",
         "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz")); 

        // assertions 
        assertEquals("The donation description should not exceed 300 characters", ex.getMessage());

        // check that each repository was called the right number of times, and with right arguments
        verify(visitorRepository, times(1)).findVisitorByUsername("henry@doppleganger"); 
    }

    /**
     * Tests creating a donation with an invalid username
     * @author Mohamed Elsamadouny
     */
    @Test
    public void testCreateDonationInvalidUsername () { 

        // set up mocks
        when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null); 

        // call service layer, and get the exception

        MmssException ex = assertThrows(MmssException.class, () -> donationService.createDonation("badUsername", "name", "description")); 

        // assert the exception is thrown with the right message and status

        assertEquals("The visitor with this username was not found", ex.getMessage()); 
        assertEquals (HttpStatus.NOT_FOUND, ex.getStatus()); 

        // verify the repository calls

        verify(visitorRepository, times(1)).findVisitorByUsername("badUsername"); 

    }

    /**
     * Tests successfully deleting a donation
     * @author Mohamed Elsamadouny
     */

    @Test
    public void testDeleteDonation () { 

        // set up the mocks
        when(donationRepository.findDonationByExchangeId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> donation); 
        
        // delete donation
        donationService.deleteDonation(0);

        //add assertion

        // verify that the delete method was called
        verify(donationRepository, times(1)).deleteById(0);

    }

    /**
     * Tests deleting a donation with invalid Id
     * @author Mohamed Elsamadouny
     */

    @Test
    public void testDeleteDonationByInvalid() { 
        // set up the mocks
        when(donationRepository.findDonationByExchangeId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> null); 

        // call service layer, and get the exception
        MmssException ex = assertThrows(MmssException.class,
                () -> donationService.deleteDonation(0));

        // assert the exception is as expected
        // add verify
        assertEquals("The donation with this Id was not found", ex.getMessage()); 
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    /**
     * Test successfully approving a donation
     * @author Mohamed Elsamadouny
     */
    @Test
    public void testUpdateStatusToApproved() {
         // set up the mocks

        // retrieve the donation
        when(donationRepository.findDonationByExchangeId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> donation); 

        // create the artefact
        when(artefactService.createArtefact(any(String.class), any(String.class), any(boolean.class), any(double.class), any(double.class), any(String.class))).thenAnswer((InvocationOnMock invocation) -> artefact);

        // get the id of the storage room
        ArrayList<Room> rooms = new ArrayList<Room>();
        rooms.add(storageRoom);
        when(roomService.getAllRoomsByRoomType(RoomType.Storage)).thenAnswer((InvocationOnMock invocation) -> rooms);
        
        // call the service layer
        Artefact createdArtefact = donationService.updateStatus(0, ExchangeStatus.Approved, false, 1.0, 0.5, "Hello world"); 

        // assertions
        assertEquals(createdArtefact.getArtefactId(), artefact.getArtefactId());
        assertEquals(donation.getItemName(), createdArtefact.getArtefactName());
        
        // verify the calls to the repository are with the correct arguments
        verify(donationRepository, times(1)).findDonationByExchangeId(0); 
        verify(artefactService, times(1)).createArtefact(any(String.class), any(String.class), any(boolean.class), any(double.class), any(double.class), any(String.class));
        verify(artefactService, times(1)).moveArtefactToRoom(0, 0);

        String message = "Your donation request submitted on date " + donation.getSubmittedDate().toString()
                + " with name: " + String.valueOf(donation.getItemName())
                + " has been approved! Thank you very much for your donation!";

        // verify notification was created
        verify(notificationService, times (1)).createNotificationByUsername(donation.getVisitor().getUsername(), message);

        // verify donation was deleted
        verify(donationRepository, times(1)).delete(donation);

    }

}
