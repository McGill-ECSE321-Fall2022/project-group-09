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
  
  @AfterEach
  public void clearDatabase() {
    
      // make sure the donation is deleted first, because donations cannot exist without a visitor
      donationRepository.deleteAll();
      
      // then you can delete all the visitors
      visitorRepository.deleteAll(); 
  }

  @Test 
  public void testPersistAndLoadDonation() { 
    
    // create the visitor for the donation
	String username = "peter.griffin@mmss.qc.ca";
    Visitor visitor = new Visitor();
    visitor.setUsername(username); 
    visitor.setPassword("ExtremelySecurePassword");
    visitor.setBalance(1000);
    visitorRepository.save(visitor); 
    
    // create the Donation and populate its fields          
    Donation donation = new Donation() ;
    
    donation.setItemName("Mona Lisa"); 
    donation.setDescription("Leonardo DaVinci's most famous artwork!"); 
    donation.setSubmittedDate(Date.valueOf("2022-10-06")); 
    donation.setExchangeStatus(ExchangeStatus.Pending); 

    donation.setVisitor(visitor); 
    
    donationRepository.save(donation); 

    
    // save the Donation Id
    int exchangeId = donation.getExchangeId();
    
    
    // set donation to null    
    donation = null;
    visitor = null;
    
    // get the donation from the database using the Id
    donation = donationRepository.findDonationByExchangeId(exchangeId); 
    
    // run J-Unit tests
    assertNotNull(donation);
    assertNotNull(donation.getVisitor());
    
    assertEquals(username, donation.getVisitor().getUsername());
    assertEquals(exchangeId, donation.getExchangeId());
    
  }
}
