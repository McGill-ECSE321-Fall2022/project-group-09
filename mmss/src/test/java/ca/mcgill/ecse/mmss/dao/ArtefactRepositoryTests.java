package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse.mmss.model.Artefact;
//import ca.mcgill.ecse.mmss.model.Room;
//import ca.mcgill.ecse.mmss.model.Room.RoomType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtefactRepositoryTests {
  
  // the repository we are testing
  @Autowired
  private ArtefactRepository artefactRepository; 
  
  @AfterEach
  public void clearDatabase() {
    
      // delete the artefact after each test
      artefactRepository.deleteAll();
  }

  @Test 
  public void testPersistAndLoadArtefact() { 
	  
    // create the artefact and populate its fields          
    Artefact artefact = new Artefact() ;
    
    // set artefact attributes
    String artefactName = "lightSaber"; 
    String description = "Found in the remains of the Death Star";    
    artefact.setArtefactName(artefactName); 
    artefact.setDescription(description); 
    artefact.setCanLoan(false); 
    artefact.setInsuranceFee(200); 
    artefact.setLoanFee(20); 
    
    // save the Artefact
    artefactRepository.save(artefact); 
    
    // get its generated Id and save it to a variable
    int artefactId = artefact.getArtefactId();
    
    // set artefact to null    
    artefact = null;
    
    // get the artefact back from the database using the Id
    artefact = artefactRepository.findArtefactByArtefactId(artefactId); 
    
    // make sure artefact is not null
    assertNotNull(artefact);
    
    // make sure the database artefact's Id corresponds to the initial artefact Id
    assertEquals(artefactId, artefact.getArtefactId());
    
  }
}
