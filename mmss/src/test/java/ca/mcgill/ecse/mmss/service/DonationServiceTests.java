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

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.DonationRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Donation;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;

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
    
    // We inject the mocks in the loan service - the thing that calls on the
    // repositories
    @InjectMocks
    private DonationService donationService;

    // Objects we will need in all our tests
    private Donation donation;
    private Person person;
    private Visitor visitor;

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
    }

    /**
     * Tests retrieving a donation with a valid id
     * 
     * @author Mohamed Elsamadouny
     */
    @Test 
    public void testRetrieveDonationById () { 

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

        // when a loan is saved, return that loan
        when(donationRepository.save(any(Donation.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0)); 

        // when a visitor is requested, return the visitor
        when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> visitor); 

        // call the service to create a loan
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
     * Tests creating a loan with an invalid username
     * @author Mohamed Elsamadouny
     */
    @Test
    public void testCreateLoanInvalidUsername () { 

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
    public void testDeleteLoan () { 

        // set up the mocks
        when(donationRepository.findDonationByExchangeId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> donation); 
        
        // delete donation
        donationService.deleteDonation(0);

        // try to get the donation by id and verify it was removed
        // catch the exception
        MmssException ex = assertThrows(MmssException.class,
                () -> donationService.getDonationById(0));

        // assert that the donation no longer exists
        assertEquals(ex.getMessage(), "Donation not found");

        // verify that the delete method was called
        verify(donationRepository, times(1)).deleteById(0);

    }











}
