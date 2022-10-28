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
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;
import ca.mcgill.ecse.mmss.model.Artefact;

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
  
  @AfterEach
  public void clearDatabase() {
    
      // make sure the loan is deleted first, because loans cannot exist without a visitor and an artefact
      loanRepository.deleteAll();
      
      // then you can delete all the visitors and artefacts (delete visitor before person)
      artefactRepository.deleteAll();
      visitorRepository.deleteAll();
      
      // finally delete persons
      personRepository.deleteAll();
  }

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
    
    // create the artefact and set its attributes   
    Artefact artefact = new Artefact();
    artefact.setArtefactName("The MilkMaid");
    artefact.setDescription("One of Vermeer's most iconic artworks!");
    artefact.setCanLoan(true);
    artefact.setInsuranceFee(100);
    artefact.setLoanFee(900);
    
    // save the artefact
    artefactRepository.save(artefact);
    
    // create the loan and set its attributes   
    Loan loan = new Loan() ;
    loan.setSubmittedDate(Date.valueOf("2022-10-20")); 
    loan.setExchangeStatus(ExchangeStatus.Pending);
    
    // set visitor and artefact to loan then save the loan
    loan.setVisitor(visitor); 
    loan.setArtefact(artefact);
    loanRepository.save(loan);

    // get the the person Id, the visitor's username, the artefact Id, and the exchange Id then save them to variables
    int personId = person.getPersonId();
    String username = visitor.getUsername();
    int artefactId = artefact.getArtefactId();
    int exchangeId = loan.getExchangeId();  
    
    // set used person, visitor, artefact, and loan to null    
    person = null;
    visitor = null;
    artefact = null;
    loan = null;
    
    // get the loan back from the database using the Id
    loan = loanRepository.findLoanByExchangeId(exchangeId); 
    
    // make sure loan, artefact, visitor, and person are not null
    assertNotNull(loan);
    assertNotNull(loan.getArtefact());
    assertNotNull(loan.getVisitor());
    assertNotNull(loan.getVisitor().getPerson());
    
    // make sure the created exchangeId, artefactId, username, and personId match those in the database
    assertEquals(exchangeId, loan.getExchangeId());
    assertEquals(artefactId, loan.getArtefact().getArtefactId());
    assertEquals(username, loan.getVisitor().getUsername());
    assertEquals(personId, loan.getVisitor().getPerson().getPersonId());
    
  }
}