package ca.mcgill.ecse.mmss.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.RoomRepository;
import ca.mcgill.ecse.mmss.dto.ArtefactDto;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Room;
import ca.mcgill.ecse.mmss.utils.Util;



/**
 * Tests for the ArtefactController class
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArtefactIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private ArtefactRepository artefactRepository;
    @Autowired
    private RoomRepository roomRepository;

    
    
    ArrayList<Artefact> artefacts;
    Room smallRoom;
    Room largeRoom;
    Room storage;
    /**
     * Clear the database before all tests
     * @author Shidan Javaheri
     */
    @BeforeAll
    public static void clearDatabase(@Autowired Util util) {
        util.clearDatabase();
    }
    
    /**
     * @author: Sasha Denouvilliez-Pech
     * Create the rooms and the artefacts needed by all the tests
     */
    @BeforeEach
    public void createObjects() {
        // Create rooms
        smallRoom = new Room();
        smallRoom.setRoomType(Room.RoomType.Small);
        largeRoom = new Room();
        largeRoom.setRoomType(Room.RoomType.Large);
        storage = new Room();
        storage.setRoomType(Room.RoomType.Storage);
        smallRoom = roomRepository.save(smallRoom);
        largeRoom = roomRepository.save(largeRoom);
        storage = roomRepository.save(storage);
        // Create artefacts and save to DB
        artefacts = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Artefact artefact = new Artefact();
            artefact.setArtefactName(String.valueOf(i));
            artefact.setDescription(String.valueOf(i));
            if (i % 3 == 0) {
                artefact.setRoom(smallRoom);
                artefact.setCanLoan(true);
                smallRoom.setArtefactCount(smallRoom.getArtefactCount() + 1);
            }
            else if (i % 3 == 1) {
                artefact.setCanLoan(false);
                artefact.setRoom(largeRoom);
                largeRoom.setArtefactCount(largeRoom.getArtefactCount() + 1);
            }
            else {
                artefact.setRoom(storage);
                storage.setArtefactCount(storage.getArtefactCount() + 1);
            }
            // Store artefacts in DB
            artefact = artefactRepository.save(artefact);
            artefacts.add(artefact);
        }
        smallRoom = roomRepository.save(smallRoom);
        largeRoom = roomRepository.save(largeRoom);
        storage = roomRepository.save(storage);
    }

    /**
     * Delete the rooms and the artefacts
     */
    @AfterEach
    public void deleteDelete(){
        smallRoom.delete();
        largeRoom.delete();
        storage.delete();
        artefacts.clear();
        artefactRepository.deleteAll();
        roomRepository.deleteAll();
    }

    /**
     * Get an artefact by its primary key
     */
    @Test
    public void testGetArtefact() {
        Artefact artefact = artefacts.get(0);
        // Try the get
        ResponseEntity<ArtefactDto> response = client.getForEntity("/artefact/" + artefact.getArtefactId(), ArtefactDto.class);
        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(artefact.getArtefactId(), response.getBody().getArtefactId());
        assertEquals(artefact.getArtefactName(), response.getBody().getArtefactName());
    }

    /**
     * Get all artefacts
     */
    @Test
    public void testGetAllArtefacts() {
        // make request
        ResponseEntity<ArrayList> request = client.getForEntity("/artefact", ArrayList.class);
        // get array list of loans
        ArrayList<ArtefactDto> extractedArtefacts = request.getBody();
        // assertions
        assertNotNull(extractedArtefacts);
        assertEquals(HttpStatus.OK, request.getStatusCode());
        assertEquals(artefacts.size(), extractedArtefacts.size());
    }

    /**
     * Get all artefacts with canLoan value set to true
     */
    @Test
    public void testGetAllArtefactsByCanLoan() {
        // Try the get
        ResponseEntity<ArrayList> response = client.getForEntity("/artefact/canLoan?canLoan=" + true, ArrayList.class);
        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }

    /**
     * Get all the artefacts associated with the storage
     */
    @Test
    public void testGetAllArtefactsByRoom() {
        int roomId = storage.getRoomId();
        // Try the get
        ResponseEntity<ArrayList> response = client.getForEntity("/artefact/room?roomId=" + roomId, ArrayList.class);
        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }

    /**
     * Get all the artefacts associated with the large room and canLoan set to false
     */
    @Test
    public void testGetAllArtefactsByRoomAndByCanLoan() {
        int roomId = largeRoom.getRoomId();
        // Try the get
        ResponseEntity<ArrayList> response = client.getForEntity("/artefact/roomAndCanLoan?roomId=" + roomId + "&canLoan=" + false, ArrayList.class);
        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }

    /**
     * Get all artefacts in display
     */
    @Test
    public void testGetAllArtefactsInDisplay() {
        // Try the get
        ResponseEntity<ArrayList> response = client.getForEntity("/artefact/display", ArrayList.class);
        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(6, response.getBody().size());
    }

    /**
     * Get all artefacts in display with canLoan value set to true
     */
    @Test
    public void testGetAllArtefactsInDisplayByCanLoan() {
        // Try the get
        ResponseEntity<ArrayList> response = client.getForEntity("/artefact/display/canLoan?canLoan=" + true, ArrayList.class);
        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
    }

    /**
     * Create a new artefact based on an input request and add it to the small room
     */
    @Test
    public void testCreateArtefact() {
        ArtefactDto request = new ArtefactDto();
        request.setArtefactName("Hello");
        request.setDescription("World");
        request.setCanLoan(true);
        request.setInsuranceFee(1);
        request.setLoanFee(1);
        request.setRoomId(smallRoom.getRoomId());
        request.setImageUrl("Hello.jpg");
        // make the post
        ResponseEntity<ArtefactDto> response = client.postForEntity("/artefact", request, ArtefactDto.class);
        // make assertions on the post
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Hello", response.getBody().getArtefactName());
        // Check for updated room count
        assertEquals(4, roomRepository.findAllByRoomType(Room.RoomType.Small).get(0).getArtefactCount());
    }

    /**
     * Update an artefact based on an input request
     */
    @Test
    public void testUpdateArtefact() {
        ArtefactDto artefactDto = new ArtefactDto(artefacts.get(0));
        artefactDto.setDescription("World");
        artefactDto.setCanLoan(false);
        artefactDto.setInsuranceFee(0);
        artefactDto.setLoanFee(0);
        artefactDto.setImageUrl("Hello.jpg");
        // make an entity to send the request with
        HttpEntity<ArtefactDto> request = new HttpEntity<>(artefactDto);
        // make the post
        ResponseEntity<ArtefactDto> response = client.exchange("/artefact", HttpMethod.PUT, request, ArtefactDto.class);
        // make assertions on the post
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("1", response.getBody().getArtefactName());
        assertEquals("World", response.getBody().getDescription());
        assertEquals(false, response.getBody().getCanLoan());
    }

    /**
     * Move an artefact from large room to small room
     */
    @Test
    public void testMoveArtefactToRoom() {
        int artefactId = artefacts.get(0).getArtefactId();
        int roomId = smallRoom.getRoomId();
        // make the post
        ResponseEntity<String> response = client.exchange("/artefact/move?artefactId=" + artefactId + "&roomId=" + roomId, HttpMethod.PUT, null, String.class);
        // make assertions on the post
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Artefact successfully moved.", response.getBody());
        // Check for updated room count
        assertEquals(2, roomRepository.findAllByRoomType(Room.RoomType.Large).get(0).getArtefactCount());
        assertEquals(4, roomRepository.findAllByRoomType(Room.RoomType.Small).get(0).getArtefactCount());
    }

    /**
     * Delete an artefact given its primary key
     */
    @Test
    public void testDeleteArtefact() {
        // make DTO for request
        int id = artefacts.get(0).getArtefactId();
        ResponseEntity<String> response = client.exchange("/artefact/"+ id, HttpMethod.DELETE,null, String.class);
        // make assertions on the post
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Artefact successfully deleted", response.getBody());
        assertEquals(2, roomRepository.findAllByRoomType(Room.RoomType.Large).get(0).getArtefactCount());
    }
}
