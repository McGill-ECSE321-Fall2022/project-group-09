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
    
      // clear communication instances
      communicationRepository.deleteAll();
      
  }

  @Test 
  public void testPersistAndLoadCommunication() { 
    
    // create the communication instance        
	Communication communication = new Communication() ;
    
    // save the Communication    
    communicationRepository.save(communication); 
    
    // get its id  
    int communicationId = communication.getCommunicationId();   
    
    // set artefact to null    
    communication = null;
    
    // get the Communication from the database using the Id
    communication = communicationRepository.findCommunicationByCommunicationId(communicationId); 
    
    // run J-Unit tests
    assertNotNull(communication);
    assertEquals(communicationId, communication.getCommunicationId());
  }
}
