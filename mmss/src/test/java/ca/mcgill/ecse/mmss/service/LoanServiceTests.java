package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.LoanRepository;
import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;


/** 
 * Tests for all Loan Service Methods
 */
@ExtendWith(MockitoExtension.class)
public class LoanServiceTests {

    // We mock repositories - the entities that access things from the database
    @Mock
    private LoanRepository loanRepository;

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private ArtefactRepository artefactRepository;

    @Mock
    private OpenDayRepository openDayRepository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private OpenDayService openDayService;

    // We inject the mocks in the loan service - the thing that calls on the
    // repositories
    @InjectMocks
    private LoanService loanService;

    // Four objects we will need in all our tests
    private Person person;
    private Visitor visitor;
    private Artefact artefact;
    private Loan loan;

    /**
     * Creates the obejcts needed by all test cases
     * 
     * @author Shidan Javaheri
     */
    @BeforeEach
    public void createObjects() {

        // create necessary objects for test
        // This will Mock the content of the database
        // Basically a fake database system
        this.person = new Person(0, "Henry", "Doppleganger");
        this.artefact = new Artefact(2, "Lightsaber", "From the death star", true, 0, 0);
        this.visitor = new Visitor("henry@doppleganger", "ILikeCheese", person);
        this.loan = new Loan();
        loan.setArtefact(artefact);
        loan.setVisitor(visitor);
        loan.setExchangeId(0);
        loan.setSubmittedDate(Date.valueOf("2022-10-10"));

    }

    /**
     * Deletes objects after each test
     * 
     * @author Shidan Javaheri
     */
    @AfterEach
    public void deleteObjects() {
        // delete the objects from the test
        this.person.delete();
        this.visitor.delete();
        this.artefact.delete();
        this.loan.delete();
    }

    /**
     * Tests retrieving a loan with a valid id
     * @author Shidan Javaheri
     */
    @Test 
    public void testGetLoanById () { 

        // setup mocks
        when(loanRepository.findLoanByExchangeId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> loan ); 

        // call service layer
        Loan retrievedLoan = loanService.getLoanById(0); 

        // assertions
        assertEquals (0, retrievedLoan.getExchangeId()); 
        assertEquals(visitor, retrievedLoan.getVisitor());
        assertEquals(artefact, retrievedLoan.getArtefact());

        // verify calls to repositories
        verify(loanRepository, times (1)).findLoanByExchangeId(0); 
    }

    /**
     * Tests getting a loan with an invalid Id
     * 
     * @author Shidan Javaheri
     */

    @Test
    public void testGetLoanByInvalidId() {
        final int invalidId = 99;
        // set up mock
        when(loanRepository.findLoanByExchangeId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> loanService.getLoanById(invalidId));

        // check the message contains the right message and status
        assertEquals("Loan not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

        // verify calls to repositories
        verify(loanRepository, times(1)).findLoanByExchangeId(invalidId);

    }

    /**
     * Tests creating a loan successfully
     * @author Shidan Javaheri
     */
    @Test
    public void testCreateLoan() { 

        // mock all three repositories in the create loan class

        // when a loan is saved, return that loan
        when(loanRepository.save(any(Loan.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0)); 

        // when an artefact is requested, return the artefact
        when(artefactRepository.findArtefactByArtefactId(any(int.class))).thenAnswer((InvocationOnMock invoation ) -> artefact);

        // when a visitor is requested, return the visitor
        when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> visitor); 

        // when a visitors loans are requested, return an empty list, since they have no loans
        when(loanRepository.findByVisitor(visitor)).thenAnswer((InvocationOnMock invocation) -> null); 

        // call the service to create a loan
        Loan loanCreated = loanService.createLoan(2, "henry@doppleganger"); 

        // assertions 
        assertEquals(0,loan.getExchangeId()); 
        assertEquals(artefact,loanCreated.getArtefact());
        assertEquals(visitor,loanCreated.getVisitor()); 
        // artefact that has been requested to be loaned can't be loaned further
        assertEquals(true, loanCreated.getArtefact().getCurrentlyOnLoan()); 
        assertEquals(ExchangeStatus.Pending, loanCreated.getExchangeStatus().Pending); 

        // check that each repository was called the right number of times, and with right arguments
        verify(loanRepository, times(1)).save(any(Loan.class)); 
        verify(artefactRepository, times(1)).findArtefactByArtefactId(2); 
        verify(loanRepository, times (1)).findByVisitor(visitor); 
        verify(visitorRepository, times(1)).findVisitorByUsername("henry@doppleganger"); 

    }

    // VISITOR ERRORS

    /**
     * Tests creating a loan with an invalid username
     * @author Shidan Javaheri
     */
    @Test
    public void testCreateLoanInvalidUsername () { 

        // set up mocks
        when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> null); 

        // call service layer, and get the exception

        MmssException ex = assertThrows(MmssException.class, () -> loanService.createLoan(0, "badUsername")); 

        // assert the exception is thrown with the right message and status

        assertEquals("The visitor with this username was not found", ex.getMessage()); 
        assertEquals (HttpStatus.NOT_FOUND, ex.getStatus()); 

        // verify the repository calls

        verify(visitorRepository, times(1)).findVisitorByUsername("badUsername"); 

    }

    /**
     * Test creating a loan for a visitor who has too many loans
     * 
     * @author Shidan Javaheri
     */
    @Test
    public void testCreateLoanTooManyLoans() {

        // make the artefacts
        ArrayList<Loan> loans = makeListOfLoans();

        // set up mocks

        // when a visitor is requested, return the visitor
        when(visitorRepository.findVisitorByUsername(any(String.class)))
                .thenAnswer((InvocationOnMock invocation) -> visitor);

        // when a visitors loans are requested, the list of loans is returned
        when(loanRepository.findByVisitor(visitor)).thenAnswer((InvocationOnMock invocation) -> loans);

        // call service layer, and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> loanService.createLoan(0, "henry@doppleganger"));

        // assert the exception is thrown with the right message and status

        assertEquals("You cannot loan more than 5 items at a time", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());

        // check that each repository was called the right number of times, and with the
        // right arguments
        verify(loanRepository, times(1)).findByVisitor(visitor);
        verify(visitorRepository, times(1)).findVisitorByUsername("henry@doppleganger");

    }

    /**
     * Tests creating a loan when the visitor has a non zero balance
     * 
     * @author Shidan Javaheri
     */
    @Test
    public void testCreateLoanVisitorHasBalance() {

        // set non zero balance
        visitor.setBalance(100);

        // set up mocks

        // return the visitor
        when(visitorRepository.findVisitorByUsername(any(String.class)))
                .thenAnswer((InvocationOnMock invocation) -> visitor);

        // call service layer, and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> loanService.createLoan(0, "henry@doppleganger"));

        // assert the exception is thrown with the right message and status

        assertEquals("You cannot loan items when you have an outstanding balance", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());

        // verify the repository calls

        verify(visitorRepository, times(1)).findVisitorByUsername("henry@doppleganger");
    }

    /**
     * Tests when the visitor has an outstanding loan
     * 
     * @author Shidan Javaheri
     */
    @Test
    public void testCreateLoanVisitorHasOutstandingLoans() {

        // make the artefacts
        Artefact artefact1 = new Artefact(1, "MonaLisa1", "Super cool", true, 0, 0);
        Artefact artefact2 = new Artefact(2, "MonaLisa2", "Super cool", true, 0, 0);

        Loan loan1 = new Loan(1, Date.valueOf("2021-12-12"), artefact1, visitor);
        Loan loan2 = new Loan(2, Date.valueOf("2022-03-02"), artefact2, visitor);

        // set due date and status to approved
        loan1.setExchangeStatus(ExchangeStatus.Approved);

        OpenDay dueDate = new OpenDay(Date.valueOf("2024-12-12"));
        loan1.setDueDate(dueDate);

        // make the list of loans
        ArrayList<Loan> loans = new ArrayList<Loan>(10);
        loans.add(loan1);
        loans.add(loan2);

        // set up mocks

        // when a visitor is requested, return the visitor we have here
        when(visitorRepository.findVisitorByUsername(any(String.class)))
                .thenAnswer((InvocationOnMock invocation) -> visitor);

        // when a visitors loans are requested, the list of loans is returned
        when(loanRepository.findByVisitor(visitor)).thenAnswer((InvocationOnMock invocation) -> loans);

        // call service layer, and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> loanService.createLoan(0, "henry@doppleganger"));

        // assert the exception is thrown with the right message and status

        assertEquals("Please return outstanding loaned items before loaning a new one", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());

        // check that each repository was called the right number of times, and with
        // right arguments
        verify(loanRepository, times(1)).findByVisitor(visitor);
        verify(visitorRepository, times(1)).findVisitorByUsername("henry@doppleganger");

    }

    // ARTEFACT ERRORS

    /**
     * Tests create loan when the artefact doesn't exist
     * 
     * @author Shidan Javaheri
     */
    @Test
    public void testCreateLoanInvalidArtefactId() {

        final int invalidId = 99;

        // set up mocks

        // when you request the artefact with invalid Id, return null
        when(artefactRepository.findArtefactByArtefactId(any(int.class)))
                .thenAnswer((InvocationOnMock invocation) -> null);

        // when a visitor is requested, return the visitor we have here
        when(visitorRepository.findVisitorByUsername(any(String.class)))
                .thenAnswer((InvocationOnMock invocation) -> visitor);

        // when a visitors loans are requested, return an empty list, since they have no
        // loans
        when(loanRepository.findByVisitor(visitor)).thenAnswer((InvocationOnMock invocation) -> null);

        // call service layer, and get the exception
        MmssException ex = assertThrows(MmssException.class,
                () -> loanService.createLoan(invalidId, "henry@doppleganger"));

        // assert the exception is thrown with the right message and status

        assertEquals("The artefact with this Id was not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

        // check that each repository was called the right number of times, and with
        // right arguments
        verify(artefactRepository, times(1)).findArtefactByArtefactId(invalidId);
        verify(loanRepository, times(1)).findByVisitor(visitor);
        verify(visitorRepository, times(1)).findVisitorByUsername("henry@doppleganger");

    }

    /**
     * Test creating a loan when the artefact is unavailable for loan
     * @author Shidan Javaheri
     */

    @Test
    public void testCreateLoanUnavailableArtefact() { 

        // set up mocks

        // when you request an artefact, return hte artefact
        when(artefactRepository.findArtefactByArtefactId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> artefact); 

        // when a visitor is requested, return the visitor we have here
        when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> visitor); 

        // when a visitors loans are requested, return an empty list, since they have no loans
        when(loanRepository.findByVisitor(visitor)).thenAnswer((InvocationOnMock invocation) -> null); 

        // make artefact unavailable
        artefact.setCurrentlyOnLoan(true);

        // call service layer, and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> loanService.createLoan(0, "henry@doppleganger")); 

        // assert the exception is thrown with the right message and status

        assertEquals("This item is not available to be loaned", ex.getMessage()); 
        assertEquals (HttpStatus.BAD_REQUEST, ex.getStatus()); 

        // check that each repository was called the right number of times, and with right arguments
        verify(artefactRepository, times(1)).findArtefactByArtefactId(0); 
        verify(loanRepository, times (1)).findByVisitor(visitor); 
        verify(visitorRepository, times(1)).findVisitorByUsername("henry@doppleganger"); 

    }

    /**
     * Test successfully updating the status of a loan to Declined
     * @author Shidan Javaheri

     */

    @Test
    public void testUpdateStatusToDeclined() { 
        // set up the mocks

        // retrieve the loan
        when(loanRepository.findLoanByExchangeId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> loan ); 

        // call the service layer
        Loan updatedLoan = loanService.updateStatus(0, ExchangeStatus.Declined); 

        // assertions
        assertEquals(updatedLoan.getExchangeId(), 0);
        assertEquals(updatedLoan.getArtefact(), artefact);
        assertEquals(ExchangeStatus.Declined, updatedLoan.getExchangeStatus()); 

        // verify the calls to the repository are with the correct arguments
        // find loan call made by update and delete
        verify(loanRepository, times(2)).findLoanByExchangeId(0); 

        // verify that the delete method was called
        verify(loanRepository, times(1)).deleteById(0);

        String message = "Your loan request submitted on date" + updatedLoan.getSubmittedDate().toString()
                        + "with id: " + String.valueOf(updatedLoan.getExchangeId()) + "has been denied";

        // verify that a notification was created
        verify (notificationService, times(1)).createNotificationByUsername(updatedLoan.getVisitor().getUsername(),(message)); 

    }

    /**
     * Test successfully updating the status of a loan to Approved
     * @author Shidan Javaheri
     */
    @Test
    public void testUpdateStatusToApproved() {
         // set up the mocks

        // return the loan that is saved
        when(loanRepository.save(any(Loan.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        // retrieve the loan
        when(loanRepository.findLoanByExchangeId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> loan ); 

        // return the artefact when it is saved
        when(artefactRepository.save((any(Artefact.class)))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0)); 

        // when the openDay service is called
        when(openDayService.calculateLoanDueDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> new OpenDay(Date.valueOf("2023-12-12"))); 

        // call the service layer
        Loan updatedLoan = loanService.updateStatus(0, ExchangeStatus.Approved); 

        // assertions
        assertEquals(updatedLoan.getExchangeId(), 0);
        assertEquals(updatedLoan.getArtefact(), artefact);
        assertEquals(updatedLoan.getExchangeStatus(), ExchangeStatus.Approved);
    

        // add assertion that its due date is set 

        // verify the calls to the repository are with the correct arguments
        verify(loanRepository, times(1)).findLoanByExchangeId(0); 
        verify(loanRepository, times(1)).save(any(Loan.class)); 
        verify(artefactRepository, times(1)).save((any(Artefact.class))); 


        String message = "Your loan request submitted on date" + loan.getSubmittedDate().toString()
                + "with id: " + String.valueOf(loan.getExchangeId())
                + "has been approved! Please follow this link to process payment, and pass by the Museum to pick up the related artefact. http://payhere.com";

        verify(notificationService,times(1)).createNotificationByUsername(updatedLoan.getVisitor().getUsername(), message); 

        // verify that the due date was calculated
        verify(openDayService, times(1)).calculateLoanDueDate(any(Date.class)); 

    }

    /**
     * Tests when a loan's status is updated to pending
     * @author Shidan Javaheri
     */
    @Test
    public void testUpdateStatusToPending() { 
        // set up the mocks
        // retrieve the loan
        when(loanRepository.findLoanByExchangeId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> loan ); 

        // catch the exception         
        MmssException ex = assertThrows(MmssException.class, () ->loanService.updateStatus(0, ExchangeStatus.Pending)); 

        // assert the exception is as expected
        assertEquals("Cannot set the status of a loan to pending", ex.getMessage()); 
        assertEquals (HttpStatus.BAD_REQUEST, ex.getStatus());

        // verify the right repositories were called
        verify(loanRepository, times(1)).findLoanByExchangeId(0); 

    }

    /**
     * Tests update status with an invalid id
     * 
     * @author Shidan Javaheri
     */
    @Test
    public void testUpdateStatusWithInvalidId() {
        // set up the mocks
        // retrieve the loan
        final int invalidId = 99;
        when(loanRepository.findLoanByExchangeId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> null);

        // catch the exception
        MmssException ex = assertThrows(MmssException.class,
                () -> loanService.updateStatus(invalidId, ExchangeStatus.Pending));

        // assert the exception is as expected
        assertEquals("The loan with this Id was not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

        // verify the right repositories were called
        verify(loanRepository, times(1)).findLoanByExchangeId(invalidId);
    }

    /**
     * Tests successfully deleting a loan
     * @author Shidan Javaheri
     */

    @Test
    public void testDeleteLoan () { 
        // set up the mocks
        when(loanRepository.findLoanByExchangeId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> loan); 

        loanService.deleteLoan(loan.getExchangeId());

        // assert the artefact is no longer on loan
        assertEquals(artefact.getCurrentlyOnLoan(), false);

        // verify that the delete method was called
        verify(loanRepository, times(1)).deleteById(0);

    }

    /**
     * Tests deleting a loan with invalid Id
     * @author Shidan Javaheri
     */

    @Test
    public void testDeleteLoanByInvalid() { 
        // set up the mocks
        when(loanRepository.findLoanByExchangeId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> null); 

        // call service layer, and get the exception
        MmssException ex = assertThrows(MmssException.class,
                () -> loanService.deleteLoan(0));

        // assert the exception is as expected
        assertEquals("The loan with this Id was not found", ex.getMessage()); 
        assertEquals (HttpStatus.NOT_FOUND, ex.getStatus());

    }

    // TESTS FOR GET ALL METHODS ARE PERFORMED IN INTEGRATION TESTS. ONLY NEED TO
    // CHECK INVALID TESTS

    /**
     * Tests getting all loans with an invalid username
     * 
     * @author Shidan Javaheri
     */
    @Test
    public void testGetAllLoansByVisitorInvalidUsername() {
        final String invalidUsername = "badUsername";

        // setup mocks
        when(visitorRepository.findVisitorByUsername(any(String.class)))
                .thenAnswer((InvocationOnMock invocation) -> null);

        // call service
        MmssException ex = assertThrows(MmssException.class, () -> loanService.getAllLoansByVisitor(invalidUsername));

        // assertions on error
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("The visitor with this username was not found", ex.getMessage());

        // verfications
        verify(visitorRepository, times(1)).findVisitorByUsername(invalidUsername);

    }

    /**
     * Tests getting all loans with invalid open day
     * 
     * @author Shidan Javaheri
     */
    @Test
    public void testGetAllLoansByDueDateInvalidOpenDay() {
        final Date invalidDate = Date.valueOf("2022-01-01");

        // setup mocks
        when(openDayRepository.findOpenDayByDate(any(Date.class))).thenAnswer((InvocationOnMock invocation) -> null);

        // call service
        MmssException ex = assertThrows(MmssException.class, () -> loanService.getAllLoansByDueDate(invalidDate));

        // assertions on error
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        assertEquals("There is no open day with this date", ex.getMessage());

        // verfications
        verify(openDayRepository, times(1)).findOpenDayByDate(invalidDate);

    }

    // Helper Methods

    /**
     * Makes a list of loans useful for tests
     * 
     * @return an ArrayList<Loan> of loans
     * 
     * @author Shidan Javaheri
     */
    public ArrayList<Loan> makeListOfLoans() {
        // make the artefacts
        Artefact artefact1 = new Artefact(1, "MonaLisa1", "Super cool", true, 0, 0);
        Artefact artefact2 = new Artefact(2, "MonaLisa2", "Super cool", true, 0, 0);
        Artefact artefact3 = new Artefact(3, "MonaLisa3", "Super cool", true, 0, 0);
        Artefact artefact4 = new Artefact(4, "MonaLisa4", "Super cool", true, 0, 0);
        Artefact artefact5 = new Artefact(5, "MonaLisa5", "Super cool", true, 0, 0);
        Artefact artefact6 = new Artefact(6, "MonaLisa6", "Super cool", true, 0, 0);

        // make the loans
        Loan loan1 = new Loan(1, Date.valueOf("2022-03-02"), artefact1, visitor);
        Loan loan2 = new Loan(2, Date.valueOf("2022-03-02"), artefact2, visitor);
        Loan loan3 = new Loan(3, Date.valueOf("2022-03-02"), artefact3, visitor);
        Loan loan4 = new Loan(4, Date.valueOf("2022-03-02"), artefact4, visitor);
        Loan loan5 = new Loan(5, Date.valueOf("2022-03-02"), artefact5, visitor);
        Loan loan6 = new Loan(6, Date.valueOf("2022-03-02"), artefact6, visitor);

        OpenDay dueDate = new OpenDay(Date.valueOf("2022-03-09"));

        loan1.setExchangeStatus(ExchangeStatus.Approved);
        loan2.setDueDate(dueDate);

        // make the list of loans
        ArrayList<Loan> loans = new ArrayList<Loan>(10);
        loans.add(loan1);
        loans.add(loan2);
        loans.add(loan3);
        loans.add(loan4);
        loans.add(loan5);
        loans.add(loan6);

        return loans;

    }

}
