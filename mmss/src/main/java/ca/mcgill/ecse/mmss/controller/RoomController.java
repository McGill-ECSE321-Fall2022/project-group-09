package ca.mcgill.ecse.mmss.controller;

import ca.mcgill.ecse.mmss.dto.RoomDto;
import ca.mcgill.ecse.mmss.model.Room;
import ca.mcgill.ecse.mmss.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * REST API for the Room class
 */

@RestController
@CrossOrigin
@RequestMapping ({"/room", "/room/"})
public class RoomController {

    @Autowired
    private RoomService roomService;

    /**
     * Get a room by its primary key
     *
     * @param id the room's primary key
     * @return a response entity with the {@link RoomDto} instance and the HttpStatus
     */
    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<RoomDto> getRoom(@PathVariable int id) {
        Room room = roomService.getRoomById(id);
        return new ResponseEntity<RoomDto>(new RoomDto(room), HttpStatus.OK);
    }

    /**
     * Get all the rooms of a given type
     *
     * @param type the room type: large, small, or storage
     * @return a response entity with an array list of {@link RoomDto} instances and the HttpStatus
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
     * Get all the rooms in the museum
     *
     * @return a response entity with an array list of {@link RoomDto} instances and the HttpStatus
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
     * Get the display current artefact count
     *
     * @return a response entity with the display current artefact count and the HttpStatus
     */
    @GetMapping({"/displayCapacity", "/displayCapacity/"})
    public ResponseEntity<Integer> getDisplayArtefactCount() {
        int displayCapacity = roomService.getDisplayArtefactCount();
        return new ResponseEntity<Integer>(displayCapacity, HttpStatus.OK);
    }
}
