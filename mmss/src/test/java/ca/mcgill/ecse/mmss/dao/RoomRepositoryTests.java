package ca.mcgill.ecse.mmss.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ca.mcgill.ecse.mmss.model.Room;
import ca.mcgill.ecse.mmss.model.Room.RoomType;

@ExtendWith(SpringExtension.class)
@SpringBootTest

public class RoomRepositoryTests {
  
  // repository we are testing
  @Autowired
  private RoomRepository roomRepository;
  
  @AfterEach
  public void clearDatabase() {
      
      // empty the room repository
      roomRepository.deleteAll();
  }

  @Test 
  public void testPersistAndLoadPerson() { 
    
	// create Room
	Room room = new Room();
	int artefactCount = 0;
	room.setArtefactCount(artefactCount);
	room.setRoomType(RoomType.Storage); 
	
	// Add room to repository
	roomRepository.save(room);
	
	// get room id
	int roomId = room.getRoomId();
    
    // set room to null
    room = null;
    		
    // get the room from the repository using the ID
    room = roomRepository.findRoomByRoomId(roomId);
    
    // check not null and primary key
    assertNotNull(room);
    assertEquals(roomId, room.getRoomId());
   
  }
}
