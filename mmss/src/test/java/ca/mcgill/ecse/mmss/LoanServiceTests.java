package ca.mcgill.ecse.mmss;

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

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.LoanRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.service.LoanService;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTests {
    @Mock
    private LoanRepository loanRepository;

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private ArtefactRepository artefactRepository;

    @InjectMocks
    private LoanService loanService;

    // Four objects we will need in all our tests
    Person person;
    Visitor visitor;
    Artefact artefact;
    Loan loan;

    @BeforeEach
    public void createObjects() {
        // create necessary objects for test
        this.person = new Person(0, "Henry", "Doppleganger");
        this.artefact = new Artefact(2, "Lightsaber", "From the death star", false, 0, 0);
        this.visitor = new Visitor("henry@doppleganger", "ILikeCheese", person);
        this.loan = new Loan();
        loan.setArtefact(artefact);
        loan.setVisitor(visitor);
        loan.setExchangeId(0);
    }

    @AfterEach
    public void deleteObjects() {
        // delete the objects from the test
        this.person.delete();
        this.visitor.delete();
        this.artefact.delete();
        this.loan.delete();
    }

    @Test 
    public void testRetrieveLoanById () { 
        // setup mocks
        when(loanRepository.findLoanByExchangeId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> loan ); 

        // call service layer

        Loan retrievedLoan = loanService.retrieveLoanById(0); 

        // assertions
        assertEquals (0, retrievedLoan.getExchangeId()); 
        assertEquals(visitor, retrievedLoan.getVisitor());
        assertEquals(artefact, retrievedLoan.getArtefact());

        // verify calls to repositories
        verify(loanRepository, times (1)).findLoanByExchangeId(0); 
    }

    @Test
    public void testGetLoanByInvalidId() {
        final int invalidId = 99;
        // set up mock. By default, mock returns null
        when(loanRepository.findLoanByExchangeId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);

        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> loanService.retrieveLoanById(invalidId));

        // check the message contains the right message and status
        assertEquals("Loan not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());

    }

    @Test
    public void testCreateLoan() { 

        // mock all three repositories in the create loan class

        // when a loan is saved, return that loan
        when(loanRepository.save(any(Loan.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0)); 

        // when an artefact is requested, return the artefact we have here
        when(artefactRepository.findArtefactByArtefactId(any(int.class))).thenAnswer((InvocationOnMock invoation ) -> artefact);

        // when a visitor is requested, return the visitor we have here
        when(visitorRepository.findVisitorByUsername(any(String.class))).thenAnswer((InvocationOnMock invocation) -> visitor); 

        // need to change the loan service class to have this as its signature
        Loan loanCreated = loanService.createLoan(2, "henry@doppleganger"); 


        // assertions 
        assertEquals(0,loan.getExchangeId()); 
        assertEquals(artefact,loanCreated.getArtefact());
        assertEquals(visitor,loanCreated.getVisitor()); 

        // check that each repository was called the right number of times, and with right arguments
        verify(loanRepository, times(1)).save(any(Loan.class)); 
        verify(artefactRepository, times(1)).findArtefactByArtefactId(2); 
        verify(visitorRepository, times(1)).findVisitorByUsername("henry@doppleganger"); 



    }

}
