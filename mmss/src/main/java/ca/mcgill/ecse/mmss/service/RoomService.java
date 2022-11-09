package ca.mcgill.ecse.mmss.service;

import ca.mcgill.ecse.mmss.dao.RoomRepository;
import ca.mcgill.ecse.mmss.dto.RoomDto;
import ca.mcgill.ecse.mmss.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

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
}
