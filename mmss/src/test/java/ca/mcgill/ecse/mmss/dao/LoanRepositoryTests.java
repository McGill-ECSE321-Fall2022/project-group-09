package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;
import ca.mcgill.ecse.mmss.model.Artefact;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanRepositoryTests {
  
  // repository we are testing
  @Autowired
  private LoanRepository loanRepository; 
  
  // also need a artefact in order to add a loan
  @Autowired  
  private ArtefactRepository artefactRepository; 
  @Autowired  
  private OpenDayRepository openDayRepository; 
  @Autowired  
  private VisitorRepository visitorRepository; 
  
  @AfterEach
  public void clearDatabase() {
    
      // make sure the loan is deleted first, because loans cannot exist without a artefact
      loanRepository.deleteAll();
      
      // then you can delete all the artefacts
      artefactRepository.deleteAll(); 
      openDayRepository.deleteAll(); 
      visitorRepository.deleteAll(); 
  }

  @Test 
  public void testPersistAndLoadLoan() { 
    
    // create the artefact for the loan
    Artefact artefact = new Artefact();
    artefact.setArtefactName("The MilkMaid");
    artefact.setDescription("One of Vermeer's most iconic artworks!");
    artefact.setCanLoan(true);
    artefact.setInsuranceFee(100);
    artefact.setLoanFee(900);
    artefactRepository.save(artefact);
    
    // create the open day for the loan
    OpenDay openDay = new OpenDay();
    openDay.setDate(Date.valueOf("2022-10-25"));
    openDayRepository.save(openDay);
    
    // create the visitor for the loan
    Visitor visitor = new Visitor();
    visitor.setBalance(8000);
    visitor.setUsername("stewin.griffin@mmss.qc.ca");
    visitor.setPassword("ImpressivelySecurePassword");
    visitorRepository.save(visitor);
    
    // create the Loan and populate its fields          
    Loan loan = new Loan() ;
    loan.setSubmittedDate(Date.valueOf("2022-10-20")); 
    loan.setExchangeStatus(ExchangeStatus.Pending);
    
    loan.setVisitor(visitor); 
    loan.setArtefact(artefact);
    loan.setDueDate(openDay);
    
    // save the Loan    
    loanRepository.save(loan);
    
    
    
    int exchangeId = loan.getExchangeId();  
    int artefactId = artefact.getArtefactId();
    Date date = openDay.getDate();
    String username = visitor.getUsername();
    
    // set loan to null    
    loan = null;
    artefact = null;
    openDay = null;
    visitor = null;
    
    // get the loan from the database using the Id
    loan = loanRepository.findLoanByExchangeId(exchangeId); 
    
    // run J-Unit tests
    assertNotNull(loan);
    assertNotNull(loan.getArtefact());
    assertNotNull(loan.getVisitor());
    assertNotNull(loan.getDueDate());
    
    assertEquals(exchangeId, loan.getExchangeId());
    assertEquals(artefactId, loan.getArtefact().getArtefactId());
    assertEquals(date, loan.getDueDate().getDate());
    assertEquals(username, loan.getVisitor().getUsername());
    
  }
}