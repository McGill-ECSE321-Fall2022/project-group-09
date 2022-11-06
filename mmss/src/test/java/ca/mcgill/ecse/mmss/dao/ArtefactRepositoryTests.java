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

/**
 * Artefact Repository testing class which initiates an artefact and a room repository, executes the tests, then clears each instance from the database.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArtefactRepositoryTests {
  
  // the repository we are testing
  @Autowired
  private ArtefactRepository artefactRepository; 

  // also need a room in order to add an artefact
  @Autowired  
  private RoomRepository roomRepository; 
  
  @AfterEach
  public void clearDatabase() {
    
      // make sure the artefact is deleted first, because artefacts cannot exist without a room
      artefactRepository.deleteAll();
      
      // delete the room after each test
      roomRepository.deleteAll();
  }

  /**
 * Artefact testing method which creates, populates the attributes, sets associations, and saves each artefact and room object and identifier.
 * It can then test to make sure each object reached from the artefact found in the repository is not null and that each initially saved Id corresponds to the one
 * reached from the repository.
 */
@Test 
  public void testPersistAndLoadArtefact() { 
	  
// MANDATORY CLASS TESTS

	// create the artefact and set its attributes        
	Artefact artefact = new Artefact() ;
	String artefactName = "lightSaber"; 
	String description = "Found in the remains of the Death Star";    
	artefact.setArtefactName(artefactName); 
	artefact.setDescription(description); 
	artefact.setCanLoan(false); 
	artefact.setInsuranceFee(200); 
	artefact.setLoanFee(20); 
  artefact.setCurrentlyOnLoan(false);
    // save the artefact
    artefactRepository.save(artefact); 
    
    // get the artefactId then save it to a variable
    int artefactId = artefact.getArtefactId();
    
    // set artefact variable to null    
    artefact = null;
    
    // get the artefact back from the database using the Id
    artefact = artefactRepository.findArtefactByArtefactId(artefactId); 
    
    // make sure artefact is not null
    assertNotNull(artefact);

    // make sure the database artefact's Id corresponds to the initial artefact Id
    assertEquals(artefactId, artefact.getArtefactId());
    
    
// OPTIONAL CLASS TESTS
    
	// create a room for the artefact and set its attributes
    Room room = new Room();
    room.setRoomType(RoomType.Storage);

    // save the room
    roomRepository.save(room); 
    
    // set the room to the artefact and save the artefact
    artefact.setRoom(room);
    artefactRepository.save(artefact);

    // get the roomId then save it to a variable
    int roomId = room.getRoomId();

    // set artefact and room variables to null
    artefact = null;
    room = null;

    // get the artefact back from the database using the Id
    artefact = artefactRepository.findArtefactByArtefactId(artefactId); 
    
    // make sure artefact and room accessed from artefact are not null
    assertNotNull(artefact);
    assertNotNull(artefact.getRoom());
    
    // make sure the database artefact's Id and room's Id correspond to the initial artefact Id and room Id
    assertEquals(artefactId, artefact.getArtefactId());
    assertEquals(roomId, artefact.getRoom().getRoomId());
    assertEquals(artefactName, artefact.getArtefactName());  
  }
}
