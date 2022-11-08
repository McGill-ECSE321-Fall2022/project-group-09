import org.checkerframework.checker.lock.qual.MayReleaseLocks;
import org.checkerframework.dataflow.qual.TerminatesExecution;

import ca.mcgill.ecse.mmss.dao.LoanRepository;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.service.LoanService;
import ca.mcgill.ecse.mmss.service.PersonService;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTests {
    @Mock
    private LoanRepository loanRepository; 

    @InjectMocks
    private LoanService loanService; 

    @Test
    public void testCreateLoan() { 
        when(loanRepository.save(any(Loan.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0)); 

        Artefact arefact = new Artefact(2, "Lightsaber", "From the death star", false, 0, 0); 
        Person person = new Person(0, "Henry", "Doppleganger"); 
        Visitor visitor = new Visitor("henry@doppleganger", "ILikeCheese", person); 
        Loan loan = new Loan(); 
        loan.setArtefact(artefact); 
        loan.setVisitor((visitor)); 
        loan.setExchangeId(0); 

        // need to change the loan servic class to have this as its signature
        Loan loanCreated = loanService.createLoan(loan); 

        // assertions 


        // check repo was called
        verify(loanRepository, times(1)).save(loan); 



    }

    @Test
    public void testGetLoanByInvalidId() { 
        // set up mock. By default, mock returns null
        when(loanRepository.findLoanByExchangeId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> null); 


        // call service layer
        try { 
            loanService.retrieveLoanById(0); 

        } catch (Exception e) { 

        }
    }
}
