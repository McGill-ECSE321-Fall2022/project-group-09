package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse.mmss.model.Communication;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CommunicationRepositoryTests {
  
  // instance of tested repository
  @Autowired
  private CommunicationRepository communicationRepository;
  
  @AfterEach
  public void clearDatabase() {
    
      // clear communications after each execution
      communicationRepository.deleteAll();
      
  }

  @Test 
  public void testPersistAndLoadCommunication() { 
    
    // create the communication instance        
	Communication communication = new Communication() ;
    
    // save the communication    
    communicationRepository.save(communication); 
    
    // get its id and save it to a variable 
    int communicationId = communication.getCommunicationId();   
    
    // set communication to null   
    communication = null;
    
    // get the communication back from the database using the Id
    communication = communicationRepository.findCommunicationByCommunicationId(communicationId); 
    
    // make sure the communication is not null
    assertNotNull(communication);
    
    // make sure the created communication's Id matches the one in the database
    assertEquals(communicationId, communication.getCommunicationId());
  }
}
