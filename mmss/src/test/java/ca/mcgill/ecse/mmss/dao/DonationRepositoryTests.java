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

import ca.mcgill.ecse.mmss.model.Donation;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Visitor;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DonationRepositoryTests {
  
  // repository we are testing
  @Autowired
  private DonationRepository donationRepository; 
  
  // also need a visitor in order to add a donation
  @Autowired  
  private VisitorRepository visitorRepository; 
  
  // need a person for visitor
  @Autowired  
  private PersonRepository personRepository; 
  
  @AfterEach
  public void clearDatabase() {
    
      // make sure the donation is deleted first, because donations cannot exist without a visitor
      donationRepository.deleteAll();
      
      // then you can delete all the visitors
      visitorRepository.deleteAll(); 
      
      // then you can delete all the persons after each execution
      personRepository.deleteAll(); 
  }

  @Test 
  public void testPersistAndLoadDonation() { 
	  
// MANDATORY CLASS TESTS
	  
	// create a person for the visitor and set its attributes
    Person person = new Person();
    person.setFirstName("Peter");
    person.setLastName("Griffin");
    
    // save the person
    personRepository.save(person); 
	  
    // create the visitor for the donation and set its attributes
    Visitor visitor = new Visitor();
    visitor.setUsername("peter.griffin@mmss.qc.ca"); 
    visitor.setPassword("ExtremelySecurePassword");
    visitor.setBalance(1000);
    
    // set person to visitor and save visitor
    visitor.setPerson(person);
    visitorRepository.save(visitor); 
    
    // create the Donation and set its attributes   
    Donation donation = new Donation() ;
    donation.setItemName("Mona Lisa"); 
    donation.setDescription("Leonardo DaVinci's most famous artwork!"); 
    donation.setSubmittedDate(Date.valueOf("2022-10-06")); 
    donation.setExchangeStatus(ExchangeStatus.Pending); 

    // set visitor to donation and save donation
    donation.setVisitor(visitor); 
    donationRepository.save(donation); 

    // get the exchange Id, the visitor's username, and the person Id then save them to variables
    String username = visitor.getUsername();
    int exchangeId = donation.getExchangeId();
    int personId = person.getPersonId();
    
    // set used donation, visitor, and person to null    
    donation = null;
    visitor = null;
    person = null;
    
    // get the donation back from the database using the Id
    donation = donationRepository.findDonationByExchangeId(exchangeId); 
    
    // make sure donation, visitor, and person are not null
    assertNotNull(donation);
    assertNotNull(donation.getVisitor());
    assertNotNull(donation.getVisitor().getPerson());
    
    // make sure the created exchangeId, username and personId match those in the database
    assertEquals(exchangeId, donation.getExchangeId());
    assertEquals(username, donation.getVisitor().getUsername());
    assertEquals(personId, donation.getVisitor().getPerson().getPersonId());
    
  }
}