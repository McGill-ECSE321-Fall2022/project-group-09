package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import ca.mcgill.ecse.mmss.dao.RoomRepository;
import ca.mcgill.ecse.mmss.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse.mmss.exception.MmssException;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTests {

    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private RoomService roomService;

    // Need all the rooms for each tests
    ArrayList<Room> museumRooms;

    /**
     * @author: Sasha Denouvilliez-Pech
     * Create the rooms needed by all the tests
     */
    @BeforeEach
    public void createRooms() {
        museumRooms = new ArrayList<>();
        // Create small rooms
        for (int i = 0; i < 5; i++)
            museumRooms.add(new Room(i, Room.RoomType.Small));
        // Create large rooms
        for (int i = 5; i < 10; i++)
            museumRooms.add(new Room(i, Room.RoomType.Large));
        // Create storage room
        museumRooms.add(new Room(10, Room.RoomType.Storage));
    }

    /**
     * Delete the rooms
     */
    @AfterEach
    public void deleteRooms(){
        museumRooms.clear();
    }

    /**
     * Test retrieving a room with a valid id
     */
    @Test
    public void testGetRoomById() {
        // setup mocks
        when(roomRepository.findRoomByRoomId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> museumRooms.get(0));
        // call service layer
        Room room = roomService.getRoomById(0);
        // assertion
        assertEquals(museumRooms.get(0).getRoomId(), room.getRoomId());
        assertEquals(museumRooms.get(0).getRoomType(), room.getRoomType());
        // verify calls to repositories
        verify(roomRepository, times (1)).findRoomByRoomId(0);
    }

    /**
     * Test retrieving a room with an invalid id
     */
    @Test
    public void testRetrieveRoomByInvalidId() {
        final int invalidId = 99;
        // setup mocks
        when(roomRepository.findRoomByRoomId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> roomService.getRoomById(invalidId));
        // check the message contains the right message and status
        assertEquals("Room not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        // Verify
        verify(roomRepository, times(1)).findRoomByRoomId(invalidId);
    }

    /**
     * Test to get all room of type small
     */
    @Test
    public void testGetAllSmallRooms() {
        Room.RoomType type = Room.RoomType.Small;
        ArrayList<Room> smallRoomsFromTest = getRoomsByType(museumRooms, Room.RoomType.Small);
        // setup mocks
        when(roomRepository.findAllByRoomType(any(Room.RoomType.class))).thenAnswer((InvocationOnMock invocation) -> smallRoomsFromTest);
        // call service layer
        ArrayList<Room> smallRooms = roomService.getAllRoomsByRoomType(type);
        // assertion
        assertEquals(smallRoomsFromTest, smallRooms);
        // Verify
        verify(roomRepository, times(1)).findAllByRoomType(type);
    }

    /**
     * Test to all rooms in the museum
     */
    @Test
    public void testGetAllRooms() {
        // setup mocks
        when(roomRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> museumRooms);
        // call service layer
        ArrayList<Room> rooms = roomService.getAllRooms();
        // assertion
        assertEquals(museumRooms, rooms);
        // Verify
        verify(roomRepository, times(1)).findAll();
    }

    /**
     * Test for successful display artefact count
     */
    @Test
    public void testGetDisplayArtefactCount() {
        // Set current capacity to 100 for all rooms in the museum
        for (Room room : museumRooms)
            room.setArtefactCount(100);
        // setup mocks
        ArrayList<Room> smallRooms = getRoomsByType(museumRooms, Room.RoomType.Small);
        ArrayList<Room> largeRooms = getRoomsByType(museumRooms, Room.RoomType.Large);
        when(roomRepository.findAllByRoomType(Room.RoomType.Small)).thenAnswer((InvocationOnMock invocation) -> smallRooms);
        when(roomRepository.findAllByRoomType(Room.RoomType.Large)).thenAnswer((InvocationOnMock invocation) -> largeRooms);
        // call service layer
        int displayCapacity = roomService.getDisplayArtefactCount();
        // assertion
        assertEquals(1000, displayCapacity);
        // Verify
        verify(roomRepository, times(1)).findAllByRoomType(Room.RoomType.Small);
        verify(roomRepository, times(1)).findAllByRoomType(Room.RoomType.Large);
    }

    /**
     * Test for successful room creation
     */
    @Test
    public void testCreateRooms() {
        // setup mocks
        for (int i = 0; i < 11; i++) {
            Room room = museumRooms.get(i);
            lenient().when(roomRepository.save(room)).thenAnswer((InvocationOnMock invocation) -> room);
        }
        // call service layer
        ArrayList<Room> rooms = roomService.createRooms();
        // assertion
        assertEquals(museumRooms.size(), rooms.size());
        assertEquals(getRoomsByType(museumRooms, Room.RoomType.Small).size(), getRoomsByType(rooms, Room.RoomType.Small).size());
        assertEquals(getRoomsByType(museumRooms, Room.RoomType.Large).size(), getRoomsByType(rooms, Room.RoomType.Large).size());
        assertEquals(getRoomsByType(museumRooms, Room.RoomType.Storage).size(), getRoomsByType(rooms, Room.RoomType.Storage).size());
        // Verify
        verify(roomRepository, times(11)).save(any(Room.class));
    }

    /**
     * Test for a full small room
     */
    @Test
    public void testIsRoomFullWithFullSmallRoom() {
        // Small room with 200 artefacts
        Room fullSmallRoom = getRoomsByType(museumRooms, Room.RoomType.Small).get(0);
        fullSmallRoom.setArtefactCount(200);
        // setup mocks
        when(roomRepository.findRoomByRoomId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> fullSmallRoom);
        // call service layer
        boolean full = roomService.isRoomFull(fullSmallRoom.getRoomId());
        // assertion
        assertEquals(true, full);
        // Verify
        verify(roomRepository, times(1)).findRoomByRoomId(any(int.class));
    }

    /**
     * Test for a not full large room
     */
    @Test
    public void testIsRoomFullWithNotFullLargeRoom() {
        // Large room with 200 artefacts
        Room largeRoom = getRoomsByType(museumRooms, Room.RoomType.Large).get(0);
        largeRoom.setArtefactCount(200);
        // setup mocks
        when(roomRepository.findRoomByRoomId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> largeRoom);
        // call service layer
        boolean notFull = roomService.isRoomFull(largeRoom.getRoomId());
        // assertion
        assertEquals(false, notFull);
        // Verify
        verify(roomRepository, times(1)).findRoomByRoomId(any(int.class));
    }

    /**
     * Test for storage capacity
     */
    @Test
    public void testIsRoomFullWithStorage() {
        // Large room with 200 artefacts
        Room storage = getRoomsByType(museumRooms, Room.RoomType.Storage).get(0);
        storage.setArtefactCount(1000000);
        // setup mocks
        when(roomRepository.findRoomByRoomId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> storage);
        // call service layer
        boolean notFull = roomService.isRoomFull(storage.getRoomId());
        // assertion
        assertEquals(false, notFull);
        // Verify
        verify(roomRepository, times(1)).findRoomByRoomId(any(int.class));
    }

    /**
     * Helper method to retrieve rooms by their RoomType
     * @param roomList
     * @param type
     * @return an array list of rooms with the same RoomType
     */
    private ArrayList<Room> getRoomsByType(ArrayList<Room> roomList, Room.RoomType type) {
        ArrayList<Room> rooms = new ArrayList<>();
        for (Room room : roomList) {
            if (room.getRoomType() == type)
                rooms.add(room);
        }
        return rooms;
    }

}
