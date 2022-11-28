package ca.mcgill.ecse.mmss.integration;

import ca.mcgill.ecse.mmss.dao.RoomRepository;
import ca.mcgill.ecse.mmss.dto.RoomDto;
import ca.mcgill.ecse.mmss.model.Room;
import ca.mcgill.ecse.mmss.service.RoomService;
import ca.mcgill.ecse.mmss.utils.Util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for the Room class
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoomIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomRepository roomRepository;

    private ArrayList<Room> rooms;

    /**
     * Clear the database before all tests
     * @author Shidan Javaheri
     */
    @BeforeAll
    public static void clearDatabase(@Autowired Util util) {
        util.clearDatabase();
    }
    /**
     * Create all the rooms in the museum before each test
     */
    @BeforeEach
    public void createRooms() {
        rooms = roomService.createRooms();
    }

    /**
     * Delete all the rooms in the museum after each test
     */
    @AfterEach
    public void deleteRooms() {
        rooms.clear();
        roomRepository.deleteAll();
    }

    /**
     * Get a room by its primary key
     */
    @Test
    public void testGetRoom() {
        int id = rooms.get(0).getRoomId();
        // try the get
        ResponseEntity<RoomDto> response = client.getForEntity("/room/" + id, RoomDto.class);
        // make assertions on the get
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getRoomId(), id);
    }

    /**
     * Get rooms by their room type
     */
    @Test
    public void testGetAllRoomsByRoomType() {
        // try the get
        ResponseEntity<ArrayList> requestSmall = client.getForEntity("/room/type/?type=" + Room.RoomType.Small.toString(), ArrayList.class);
        ResponseEntity<ArrayList> requestLarge = client.getForEntity("/room/type/?type=" + Room.RoomType.Large.toString(), ArrayList.class);
        ResponseEntity<ArrayList> requestStorage = client.getForEntity("/room/type/?type=" + Room.RoomType.Storage.toString(), ArrayList.class);
        // Process the responses
        ArrayList<RoomDto> smallRooms = requestSmall.getBody();
        ArrayList<RoomDto> largeRooms = requestLarge.getBody();
        ArrayList<RoomDto> storage = requestStorage.getBody();
        // Assertions
        assertNotNull(smallRooms);
        assertEquals(HttpStatus.OK, requestSmall.getStatusCode());
        assertEquals(5, smallRooms.size());
        assertNotNull(largeRooms);
        assertEquals(HttpStatus.OK, requestLarge.getStatusCode());
        assertEquals(5, largeRooms.size());
        assertNotNull(storage);
        assertEquals(HttpStatus.OK, requestStorage.getStatusCode());
        assertEquals(1, storage.size());
    }

    /**
     * Get all rooms in the museum
     */
    @Test
    public void testGetAllRooms() {
        // try the get
        ResponseEntity<ArrayList> request = client.getForEntity("/room/", ArrayList.class);
        // Process the responses
        ArrayList<RoomDto> rooms = request.getBody();
        // Assertions
        assertNotNull(rooms);
        assertEquals(HttpStatus.OK, request.getStatusCode());
        assertEquals(11, rooms.size());
    }

    /**
     * Get the display artefact count
     */
    @Test
    public void testGetDisplayArtefactCount() {
        // Update room count
        for (Room room : rooms) {
            room.setArtefactCount(50);
            roomRepository.save(room);
        }
        // try the get
        ResponseEntity<Integer> request = client.getForEntity("/room/displayCapacity/", Integer.class);
        // Process the responses
        Integer displayCapacity = request.getBody();
        // Assertions
        assertNotNull(displayCapacity);
        assertEquals(HttpStatus.OK, request.getStatusCode());
        assertEquals(500, displayCapacity);
    }
}
