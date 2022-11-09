package ca.mcgill.ecse.mmss.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.LoanRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.dto.LoanDto;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;
import ca.mcgill.ecse.mmss.service.LoanService;

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
    private LoanService loanService;

    // Four objects we will need in all our tests
    private Person person;
    private Visitor visitor;
    private Artefact artefact;
    private Artefact artefactWithLoan;
    private Loan loan;

    /**
     * @author Shidan Javaheri
     * Creates the obejcts needed by all test cases
     * BeforeAll because these objects are only modified in the database, not themselves
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
        visitorRepository.save(visitor);

        // the loan
        loan = new Loan();
        loan.setArtefact(artefact);
        loan.setVisitor(visitor);
        loan.setExchangeId(0);
        loan.setSubmittedDate(Date.valueOf("2022-10-10"));
        loan.setExchangeStatus(ExchangeStatus.Pending); 
        loanRepository.save(loan);
    }

    /**
     * @author Shidan Javaheri
     * Deletes objects after each test
     */
    @AfterEach
    public void deleteObjects() {

        // delete the objects from the test
        this.person.delete();
        this.visitor.delete();
        this.artefact.delete();
        this.loan.delete();
        loanRepository.deleteAll();
        visitorRepository.deleteAll(); 
        artefactRepository.deleteAll();
        personRepository.deleteAll(); 

    }

    @Test
    public void testCreateAndGetLoan() {
        int loanId = testCreateLoan();
        testGetLoan(loanId); 
    }

    public int testCreateLoan() { 
        LoanDto request = new LoanDto(); 
        request.setVisitorId("mo.salah@gmail.com");
        request.setArtefactId(artefact.getArtefactId());
        
        // make the post
        ResponseEntity<LoanDto> response = client.postForEntity("/loan",request, LoanDto.class); 

        // make assertions on the post
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body");
        assertTrue(response.getBody().getExchangeId() > 0, "Response has a valid id"); 
        return response.getBody().getExchangeId();
        
    }

    public void testGetLoan(int id) { 
        // try the get
        ResponseEntity<LoanDto> response = client.getForEntity("/loan" + id, LoanDto.class); 

        // make assertions on the get

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has a body");
        assertEquals(response.getBody().getExchangeId(), id, "Response has correct id"); 

    } 

}
