package ca.mcgill.ecse.mmss.service;

import ca.mcgill.ecse.mmss.dao.RoomRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    /**
     * Get a room by its primary key
     *
     * @author Sasha Denouvilliez-Pech
     * @param id
     * @return the room or an exception
     */
    @Transactional
    public Room retrieveRoomById(int id) {
        Room room = roomRepository.findRoomByRoomId(id);
        if (room == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Room not found");
        }
        return room;
    }

    /**
     * Get rooms by their room type
     *
     * @param type
     * @return an array list of rooms
     */
    @Transactional
    public ArrayList<Room> getAllRoomsByRoomType(Room.RoomType type) {
        return roomRepository.findAllByRoomType(type);
    }

    /**
     * Get all the rooms
     * @return a array list of rooms
     */
    @Transactional
    public ArrayList<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    /**
     * Get the display capacity
     *
     * @return the display capacity
     */
    @Transactional
    public int getDisplayCapacity() {
        int displayCapacity = 0;
        // Get current capacity for small rooms
        for (Room smallRoom : getAllRoomsByRoomType(Room.RoomType.Small)) {
            displayCapacity += smallRoom.getArtefactCount();
        }
        // Get current capacity for large rooms
        for (Room largeRoom : getAllRoomsByRoomType(Room.RoomType.Large)) {
            displayCapacity += largeRoom.getArtefactCount();
        }
        return displayCapacity;
    }

    /**
     * Create the necessary rooms for the museum
     * This method should be called only ONCE when booting the app.
     */
    @Transactional
    public void createRooms() {
        // Create small rooms
        for (int i = 0; i < 5; i++) {
            Room smallRoom = new Room();
            smallRoom.setRoomType(Room.RoomType.Small);
            roomRepository.save(smallRoom);
        }
        // Create large rooms
        for (int i = 0; i < 5; i++) {
            Room largeRoom = new Room();
            largeRoom.setRoomType(Room.RoomType.Large);
            roomRepository.save(largeRoom);
        }
        // Create storage room
        Room storage = new Room();
        storage.setRoomType(Room.RoomType.Storage);
        roomRepository.save(storage);
    }

    /**
     * Checks whether the room is full
     *
     * @param roomId
     * @return whether the room is full
     */
    @Transactional
    public boolean isRoomFull(int roomId) {
        Room room = retrieveRoomById(roomId);
        if (room == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "Room not found");
        Room.RoomType type = room.getRoomType();
        int artefactCount = room.getArtefactCount();
        if (type == Room.RoomType.Small)
            return artefactCount >= 200;
        else if (type == Room.RoomType.Large)
            return artefactCount >= 300;
            // Unlimited capacity for storage
        else return false;
    }
}
