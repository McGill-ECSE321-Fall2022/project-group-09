package ca.mcgill.ecse.mmss.controller;

import ca.mcgill.ecse.mmss.dto.RoomDto;
import ca.mcgill.ecse.mmss.model.Room;
import ca.mcgill.ecse.mmss.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping ({"/room", "/room/"})
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     * Get a room by its primary key
     * @param id
     * @return a response entity with the room and ok status
     */
    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<RoomDto> getRoom(@PathVariable int id) {
        Room room = roomService.retrieveRoomById(id);
        return new ResponseEntity<RoomDto>(new RoomDto(room), HttpStatus.OK);
    }

    /**
     * Get all the rooms of a given type
     * @param type RoomType
     * @return an array list with the rooms as DTOs
     */
    @GetMapping({"/type", "/type/"})
    public ResponseEntity<ArrayList<RoomDto>> getAllRoomsByRoomType(@RequestParam Room.RoomType type) {
        ArrayList<Room> rooms = roomService.getAllRoomsByRoomType(type);
        // DTOs
        ArrayList<RoomDto> roomDTOs = new ArrayList<>();
        for (Room room : rooms) {
            roomDTOs.add(new RoomDto(room));
        }
        return new ResponseEntity<ArrayList<RoomDto>>(roomDTOs, HttpStatus.OK);
    }

    /**
     * Get all rooms
     * @return an array list of rooms as DTOs
     */
    @GetMapping
    public ResponseEntity<ArrayList<RoomDto>> getAllRooms() {
        ArrayList<Room> rooms = roomService.getAllRooms();
        // DTOs
        ArrayList<RoomDto> roomDTOs = new ArrayList<>();
        for (Room room : rooms) {
            roomDTOs.add(new RoomDto(room));
        }
        return new ResponseEntity<ArrayList<RoomDto>>(roomDTOs, HttpStatus.OK);
    }

    /**
     * Get the display capacity
     * @return the display capacity
     */
    @GetMapping({"/displayCapacity", "/displayCapacity/"})
    public ResponseEntity<Integer> getDisplayCapacity() {
        int displayCapacity = roomService.getDisplayCapacity();
        return new ResponseEntity<Integer>(displayCapacity, HttpStatus.OK);
    }
}
