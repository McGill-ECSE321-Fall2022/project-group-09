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
}
