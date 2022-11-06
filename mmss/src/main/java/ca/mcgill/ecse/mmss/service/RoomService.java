package ca.mcgill.ecse.mmss.service;

import ca.mcgill.ecse.mmss.dao.RoomRepository;
import ca.mcgill.ecse.mmss.dto.RoomDto;
import ca.mcgill.ecse.mmss.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    /**
     * @author Sasha Denouvilliez-Pech
     * Get a room by its primary key
     * @param roomId
     * @return a RoomDTO
     */
    @Transactional
    public Optional<RoomDto> getRoomById(int roomId) {
        Room room = roomRepository.findRoomByRoomId(roomId);
        if (room == null) {
            // look for invalid room Id
        }
        return Optional.of(new RoomDto(room));
    }

    /**
     * Get all the rooms
     * @return a list of RoomDTOs
     */
    @Transactional
    public ArrayList<RoomDto> getAllRooms() {
        ArrayList<Room> rooms = roomRepository.findAll();
        ArrayList<RoomDto>  roomDtos = new ArrayList<>();
        for (Room room: rooms) {
            if (room == null) {
                // look for invalid room Id
            }
            roomDtos.add(new RoomDto(room));
        }
        return roomDtos;
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
