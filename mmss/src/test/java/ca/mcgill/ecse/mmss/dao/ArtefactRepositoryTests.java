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
  
  // tested repository
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
    // save the Artefact    
    
    artefact.setRoom(room); 
    
    artefactRepository.save(artefact); 
    
    
    
    // get its id ( that was set automatically by spring )    
    int artefactId = artefact.getArtefactId();   
    int roomId = room.getRoomId();
    
    // set artefact to null    
    artefact = null;
    room = null;
    
    // get the artefact from the database using the Id
    artefact = artefactRepository.findArtefactByArtefactId(artefactId); 
    
    // run J-Unit tests
    assertNotNull(artefact);
    assertNotNull(artefact.getRoom());
    
    assertEquals(artefactId, artefact.getArtefactId());
    assertEquals(roomId, artefact.getRoom().getRoomId());
    
    
    
    
    
    
    
    
    
    
    
  }
}
