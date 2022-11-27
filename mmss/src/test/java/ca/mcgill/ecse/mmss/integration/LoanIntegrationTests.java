package ca.mcgill.ecse.mmss.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

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
import ca.mcgill.ecse.mmss.dao.LoanRepository;
import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.dto.LoanDto;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;

// import clear database util 

import ca.mcgill.ecse.mmss.utils.Util; 


/** 
 * Tests the functionality of all services exposed through 
 * the URL "/loan"
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoanIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private ArtefactRepository artefactRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private OpenDayRepository openDayRepository;

    @Autowired
    private CommunicationRepository communicationRepository;

    @Autowired
    private Util util;

    // Four objects we will need in all our tests
    private Person person;
    private Visitor visitor;
    private Artefact artefact;
    private Artefact artefactWithLoan;
    private Loan loan;

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
     * @author Shidan Javaheri
     */
    @BeforeEach
    public void createObjects() {
        // create necessary objects for test, and save them to the database

        // person for visitor
        person = new Person();
        person.setFirstName("Mo");
        person.setLastName("Salah");
        personRepository.save(person);

        // artefact that will not be on loan
        artefact = new Artefact();
        artefact.setArtefactName("Lightsaber");
        artefact.setDescription("Best for peeling potatoes");
        artefact.setCanLoan(true);
        artefact.setInsuranceFee(45);
        artefact.setLoanFee(17.2);
        artefactRepository.save(artefact);

        // artefact that will not be on loan
        artefactWithLoan = new Artefact();
        artefactWithLoan.setArtefactName("Potato Peeler");
        artefactWithLoan.setDescription("Best for making lightsabers");
        artefactWithLoan.setCanLoan(true);
        artefactWithLoan.setInsuranceFee(45);
        artefactWithLoan.setLoanFee(17.2);
        artefactRepository.save(artefactWithLoan);

        // the visitor
        this.visitor = new Visitor("mo.salah@gmail.com", "IScoreGoalz", person);

        // a visitor is always created with a communication
        Communication communication = new Communication();
        communicationRepository.save(communication);
        visitor.setCommunication(communication);
        visitorRepository.save(visitor);

        // the loan
        loan = new Loan();
        loan.setArtefact(artefactWithLoan);
        loan.setVisitor(visitor);
        loan.setExchangeId(0);
        loan.setSubmittedDate(Date.valueOf("2022-10-10"));
        loan.setExchangeStatus(ExchangeStatus.Pending);
        loanRepository.save(loan);
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
        util.clearDatabase(); 
        
    }

    /**
     * Tests creating and retrieving a loan
     *
     * @author Shidan Javaheri
     */
    @Test
    public void testCreateAndGetLoan() {
        int loanId = testCreateLoan();
        testGetLoan(loanId);
    }

    /**
     * Tests creating a loan, and returns its id
     * 
     * @author Shidan Javaheri
     * @return the id of the loan
     */
    public int testCreateLoan() {
        LoanDto request = new LoanDto();
        request.setVisitorId("mo.salah@gmail.com");
        request.setArtefactId(artefact.getArtefactId());

        // make the post
        ResponseEntity<LoanDto> response = client.postForEntity("/loan", request, LoanDto.class);

        // make assertions on the post
        assertNotNull(response, "The response is not null");
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body");
        assertTrue(response.getBody().getExchangeId() > 0, "Response has a valid id");
        return response.getBody().getExchangeId();

    }

    /**
     * Retrieves the loan that was created by its id
     * 
     * @author Shidan Javaheri
     */

    public void testGetLoan(int id) {
        // try the get
        ResponseEntity<LoanDto> response = client.getForEntity("/loan/" + id, LoanDto.class);

        // make assertions on the get

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body");
        assertEquals(response.getBody().getExchangeId(), id, "Response has correct id");

    }

    /**
     * Tests updating a loan status to approved
     * 
     * @author Shidan Javaheri
     */
    @Test
    public void testUpdateLoantoApproved() {

        // adds openDays to the database
        createAndSaveOpenDays();

        // make Dto for request
        LoanDto loanDto = new LoanDto(loan);
        loanDto.setExchangeStatus(ExchangeStatus.Approved);

        // make an entity to send the request with
        HttpEntity<LoanDto> request = new HttpEntity<>(loanDto);

        // send the request
        ResponseEntity<LoanDto> response = client.exchange("/loan", HttpMethod.PUT, request, LoanDto.class);

        // assertions on response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getExchangeStatus(), ExchangeStatus.Approved);

        // get the updated Loan from the database
        Loan updatedLoan = loanRepository.findLoanByExchangeId(loan.getExchangeId());

        // verify the update of the status
        assertEquals(updatedLoan.getExchangeStatus(), ExchangeStatus.Approved);
    
        // get the correct due date
        Date correctDueDate = addDays(new Date(System.currentTimeMillis()),7); 

        // verify the dueDate is set to 7 days from the current date
        assertEquals(correctDueDate.toString(), updatedLoan.getDueDate().getDate().toString()); 

    }

    /**
     * Tests declining a loan status
     * This automatically tests the delete service
     * 
     * @author Shidan Javaheri
     */
    @Test
    public void testUpdateLoantoDeclined() {
        // make Dto for request
        LoanDto loanDto = new LoanDto(loan);
        loanDto.setExchangeStatus(ExchangeStatus.Declined);

        // make an entity to send the request with
        HttpEntity<LoanDto> request = new HttpEntity<>(loanDto);

        // send the request
        ResponseEntity<LoanDto> response = client.exchange("/loan", HttpMethod.PUT, request, LoanDto.class);

        // assertions on response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getExchangeStatus(), ExchangeStatus.Declined);

        // get the updated Loan from the database
        Loan updatedLoan = loanRepository.findLoanByExchangeId(loan.getExchangeId());

        // verify the loan doesn't exist anymore
        assertNull(updatedLoan);

    }

    /**
     * Tests deleting a loan
     * 
     * @author Shidan Javaheri
     */

    @Test
    public void testDeleteLoan() {

        // make Dto for request
        LoanDto request = new LoanDto(loan);
        int id = request.getExchangeId();

        ResponseEntity<String> response = client.exchange("/loan/" + id, HttpMethod.DELETE, null, String.class);

        // assert on the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Loan successfully deleted", response.getBody());

        // get the updated Loan from the database
        Loan updatedLoan = loanRepository.findLoanByExchangeId(id);

        // verify the loan has been deleted
        assertNull(updatedLoan, "Loan successfully delted");

    }

    /**
     * Tests getting all loans
     * 
     * @author Shidan Javaheri
     */

    @Test
    public void testGetAllLoans() {

        // make request
        var response = client.getForEntity("/loan", ArrayList.class);

        // assertions on the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        // get array list of loans
        ArrayList<LoanDto> extractedLoans = response.getBody();

        // assertions
        assertNotNull(extractedLoans);
        assertEquals(1, extractedLoans.size());

    }

    /**
     * Tests getting all loans by their status
     * 
     * @author Shidan Javaheri
     */

    @Test
    public void testGetAllLoansByStatus() {
        // make request
        var response = client.getForEntity("/loan/status?status=Pending", ArrayList.class);
        var responseEmpty = client.getForEntity("/loan/status?status=Declined", ArrayList.class);

        // assertions on the responses
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertNotNull(responseEmpty);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseEmpty.getBody());

        // get array list of loans
        ArrayList<LoanDto> extractedLoans = response.getBody();
        ArrayList<LoanDto> empty = responseEmpty.getBody();

        // assertions
        assertNotNull(extractedLoans);
        assertEquals(0, empty.size());
        assertEquals(1, extractedLoans.size());
    }

    /**
     * Tests getting all loans by their Submitted date
     * 
     * @author Shidan Javaheri
     */

    @Test
    public void testGetAllLoansBySubmittedDate() {
        // make request
        var response = client.getForEntity("/loan/submittedDate?date=2022-10-10", ArrayList.class);
        var responseEmpty = client.getForEntity("/loan/submittedDate?date=2022-09-09", ArrayList.class);

        // assertions on the responses
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertNotNull(responseEmpty);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(responseEmpty.getBody());

        // get array list of loans
        ArrayList<LoanDto> extractedLoans = response.getBody();
        ArrayList<LoanDto> empty = responseEmpty.getBody();

        // assertions
        assertNotNull(extractedLoans);
        assertEquals(0, empty.size());
        assertEquals(1, extractedLoans.size());
    }

    /**
     * Tests getting all loans by their due date
     * 
     * @author Shidan Javaheri
     */

    @Test
    public void testGetAllLoansByDueDate() {

        // make the loan have a due date
        OpenDay dueDate = new OpenDay(Date.valueOf("2022-10-17"));
        openDayRepository.save(dueDate);

        loan.setDueDate(dueDate);
        loanRepository.save(loan);

        // make request
        var response = client.getForEntity("/loan/dueDate?date=2022-10-17", ArrayList.class);

        // assertions on the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // get array list of loans
        ArrayList<LoanDto> extractedLoans = response.getBody();

        // assertions
        assertNotNull(extractedLoans);
        assertEquals(1, extractedLoans.size());
    }

    /**
     * Tests getting all loans by a visitor
     * 
     * @author Shidan Javaheri
     */

    @Test
    public void testGetAllLoansByVisitor() {
        // make request
        var response = client.getForEntity("/loan/visitor?username=mo.salah@gmail.com", ArrayList.class);

        // assertions on the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // get array list of loans
        ArrayList<LoanDto> extractedLoans = response.getBody();

        // assertions
        assertNotNull(extractedLoans);
        assertEquals(1, extractedLoans.size());

    }


    // HELPER METHODS

    /**
     * Helper method to increase a date by 1 day. 
     * Inspired by this Source: https://stackoverflow.com/questions/15802010/how-to-add-days-to-java-sql-date
     * @param date the date to add
     * @param days the integer number of days to add to the date
     * @return the date increased by the given number of days
     */
    public Date addDays(Date date, int days) {
        // adds one day to the input date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * Makes 10 open days that to be used for testing, and saves them to 
     * the database. All of these 10 days are after the current date
     * 
     * @return and ArrayList of OpenDays
     */
    public void createAndSaveOpenDays() {
        // get the current date
        Date currentDate = new Date(System.currentTimeMillis());
        // add the current date and 9 future days to the openDay list
        for (int i = 0; i < 10; i++) {
            OpenDay openDay = new OpenDay(currentDate);
            openDayRepository.save(openDay); 
            currentDate = addDays(currentDate, 1);
        }

    }

}
