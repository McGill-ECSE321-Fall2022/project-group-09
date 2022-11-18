package ca.mcgill.ecse.mmss.controller;
import ca.mcgill.ecse.mmss.dto.CommunicationDto;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.service.CommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/communication", "/communication/"})
public class CommunicationController {

    @Autowired
    private CommunicationService communicationService;

    /**
     * Get a communication by a username
     *
     * @param username
     * @return a response entity with the communication and ok status
     */
    @GetMapping({"/{username}", "/{username}/"})
    public ResponseEntity<CommunicationDto> getCommunication(@PathVariable String username) {
        Communication communication = communicationService.getCommunicationByUsername(username);
        return new ResponseEntity<CommunicationDto>(new CommunicationDto(communication), HttpStatus.OK);
    }
    
}
