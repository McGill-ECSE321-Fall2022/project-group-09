package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;
import ca.mcgill.ecse.mmss.model.Artefact;

/**
 * Loan Repository testing class which initiates a loan, visitor, person, and artefact repository, executes the tests, then clears each instance from the database.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanRepositoryTests {
  
  // repository we are testing
  @Autowired
  private LoanRepository loanRepository; 
  
  // need a visitor to add a loan
  @Autowired  
  private VisitorRepository visitorRepository; 
  
  // need a person to add a visitor
  @Autowired  
  private PersonRepository personRepository; 
  
  // also need an artefact in order to add a loan
  @Autowired  
  private ArtefactRepository artefactRepository;
  
  // a loan may optionally have a due date
  @Autowired 
  private OpenDayRepository openDayRepository; 
  
  @AfterEach
  public void clearDatabase() {
    
      // make sure the loan is deleted first, because loans cannot exist without a visitor and an artefact
      loanRepository.deleteAll();
      
      // then you can delete all the visitors and artefacts (delete visitor before person)
      artefactRepository.deleteAll();
      visitorRepository.deleteAll();
      openDayRepository.deleteAll(); 
      
      // finally delete persons
      personRepository.deleteAll();   
  }

  /**
 * Loan testing method which creates, populates the attributes, sets associations, and saves each loan, visitor, person, and artefact object and identifier.
 * It can then test to make sure each object reached from the loan found in the repository is not null and that each initially saved Id corresponds to the one
 * reached from the repository.
 */
  @Test 
  public void testPersistAndLoadLoan() { 
    
	// create a person for the visitor and set its attributes
    Person person = new Person();
    person.setFirstName("Stewie");
    person.setLastName("Griffin");
    
    // save the person
    personRepository.save(person);
    
    // create the visitor for the donation and set its attributes
    Visitor visitor = new Visitor();
    visitor.setBalance(8000);
    visitor.setUsername("stewie.griffin@mmss.qc.ca");
    visitor.setPassword("ImpressivelySecurePassword");
    
    // set person to visitor and save visitor
    visitor.setPerson(person);
    visitorRepository.save(visitor);
    
    // create artifacts for both loans and set its attributes   
    Artefact artefact1 = new Artefact();
    artefact1.setArtefactName("The MilkMaid");
    artefact1.setDescription("One of Vermeer's most iconic artworks!");
    artefact1.setCanLoan(true);
    artefact1.setInsuranceFee(100);
    artefact1.setLoanFee(900);
    
    // create artifacts for both loans and set its attributes   
    Artefact artefact2 = new Artefact();
    artefact2.setArtefactName("The Cucumber Sandwhich");
    artefact2.setDescription("One of the best snacks to regain your energy!");
    artefact2.setCanLoan(true);
    artefact2.setInsuranceFee(200);
    artefact2.setLoanFee(1000);
    
    // save the artefacts
    artefactRepository.save(artefact1);
    artefactRepository.save(artefact2); 
    
    // CASE 1: A LOAN WITHOUT AN DUE DATE
    // create the loan and set its attributes   
    Loan loan1 = new Loan() ;
    loan1.setSubmittedDate(Date.valueOf("2022-10-20")); 
    loan1.setExchangeStatus(ExchangeStatus.Pending);
    
    // set visitor and artefact to loan then save the loan
    loan1.setVisitor(visitor); 
    loan1.setArtefact(artefact1);
    loanRepository.save(loan1);

    // get the the person Id, the visitor's username, the artefact Ids, and the exchange Ids then save them to variables
    int personId = person.getPersonId();
    String username = visitor.getUsername();
    int artefactId1 = artefact1.getArtefactId();
    int artefactId2 = artefact2.getArtefactId(); 
    int exchangeId1 = loan1.getExchangeId();  
    
    // CASE 2: A LOAN WITH A DUE DATE
    // create the loan and set its attributes   
    Loan loan2 = new Loan() ;
    Date submitted = Date.valueOf("2022-10-20"); 
    loan2.setSubmittedDate(submitted); 
    loan2.setExchangeStatus(ExchangeStatus.Pending);
    
    // create an open day to be the due date of the loan
    OpenDay dueDay = new OpenDay(); 
    Date dueDate = Date.valueOf("2022-10-28"); 
    dueDay.setDate(dueDate);
    openDayRepository.save(dueDay); 
    
    // set visitor artefact to loan then save the loan
    loan2.setVisitor(visitor); 
    loan2.setArtefact(artefact2);
    loan2.setDueDate(dueDay);
    
    loanRepository.save(loan2);

    // get the id of the loan
    int exchangeId2 = loan2.getExchangeId();  
    
    // set loans to null
    loan1 = null;
    loan2 = null; 
    
    // get the loans back from the database using the Id
    loan1 = loanRepository.findLoanByExchangeId(exchangeId1); 
    loan2 = loanRepository.findLoanByExchangeId(exchangeId2); 
    
    // CASE 1: Loan without a due date
    // make sure loan, artefact, visitor, and person are not null
    assertNotNull(loan1);
    assertNotNull(loan1.getArtefact());
    assertNotNull(loan1.getVisitor());
    assertNotNull(loan1.getVisitor().getPerson());
    
    // make sure the created exchangeId, artefactId, username, and personId match those in the database
    assertEquals(exchangeId1, loan1.getExchangeId());
    assertEquals(artefactId1, loan1.getArtefact().getArtefactId());
    assertEquals(username, loan1.getVisitor().getUsername());
    assertEquals(personId, loan1.getVisitor().getPerson().getPersonId());
    
    // CASE 2: Loan with a due date
    // make sure loan, artefact, visitor, and person are not null
    assertNotNull(loan2);
    assertNotNull(loan2.getArtefact());
    assertNotNull(loan2.getVisitor());
    assertNotNull(loan2.getVisitor().getPerson());
    assertNotNull(loan2.getDueDate()); 
    
    // make sure the created exchangeId, artefactId, username, and personId match those in the database
    assertEquals(exchangeId2, loan2.getExchangeId());
    assertEquals(artefactId2, loan2.getArtefact().getArtefactId());
    assertEquals(username, loan2.getVisitor().getUsername());
    assertEquals(personId, loan2.getVisitor().getPerson().getPersonId());
    assertEquals(dueDate, loan2.getDueDate().getDate());
    
    // check an attribute is stored properly
    assertEquals(submitted, loan2.getSubmittedDate());  
  }
}