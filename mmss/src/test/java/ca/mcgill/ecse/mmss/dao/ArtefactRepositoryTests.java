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
import ca.mcgill.ecse.mmss.model.Room;
import ca.mcgill.ecse.mmss.model.Room.RoomType;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtefactRepositoryTests {
  
  // repository we are testing
  @Autowired
  private ArtefactRepository artefactRepository; 
  
  // also need a room in order to add an artefact
  @Autowired  
  private RoomRepository roomRepository; 
  
  @AfterEach
  public void clearDatabase() {
    
      // make sure the artefact is deleted first, because artefacts cannot exist without a room
      artefactRepository.deleteAll();
      
      // then you can delete all the rooms
      roomRepository.deleteAll(); 
  }

  @Test 
  public void testPersistAndLoadArtefact() { 
    
    // create the room for the artefact
    Room room = new Room();
    room.setRoomType(RoomType.Storage); 
    roomRepository.save(room); 
    
    // create the Artefact and populate its feilds          
    Artefact artefact = new Artefact() ;
    String artefactName = "lightSaber"; 
    String description = "Found in the remains of the Death Star";      
    artefact.setArtefactName(artefactName); 
    artefact.setDescription(description); 
    artefact.setCanLoan(false); 
    artefact.setInsuranceFee(200); 
    artefact.setLoanFee(20); 
    artefact.setRoom(room); 
    
    // save the Artefact    
    artefactRepository.save(artefact); 
    
    // get its id ( that was set automatically by spring )    
    int artefactId = artefact.getArtefactId();   
    
    // set artefact to null    
    artefact = null;
    
    // get the artefact from the database using the Id
    artefact = artefactRepository.findArtefactByArtefactId(artefactId); 
    
    // run J-Unit tests
    assertNotNull(artefact);
    assertEquals(artefactId, artefact.getArtefactId());
    assertEquals(artefactName, artefact.getArtefactName()); 
    
    
    
    
    
    
    
    
    
    
    
  }
}
