package ca.mcgill.ecse.mmss.controller;

import java.util.ArrayList;

import ca.mcgill.ecse.mmss.dto.EmployeeDto;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse.mmss.dto.VisitorDto;
import ca.mcgill.ecse.mmss.dto.VisitorRequestDto;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.service.VisitorService;

/**
 * REST API for the Visitor class
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping ({"/visitor","/visitor/"})
public class VisitorController {
	
	@Autowired
	VisitorService visitorService;

    /**
     * Create a visitor
     *
     * @param request a {@link VisitorRequestDto} instance
     * @return a response entity with the {@link VisitorDto} instance and the HttpStatus
     */
	@PostMapping
    public ResponseEntity<VisitorDto> createVisitor(@RequestBody VisitorRequestDto request) {
        // get parameters
		String firstname = request.getFirstName();
		String lastname = request.getLastName();
        String username = request.getUsername();
		String password = request.getPassword();
        // create the object with the service
        Visitor persistedVisitor = visitorService.createVisitor(firstname,lastname,username,password);
        // return it in the response entity
        return new ResponseEntity<VisitorDto>(new VisitorDto(persistedVisitor), HttpStatus.CREATED);
    }

    /**
     * Create an additional account for a visitor
     *
     * @param request a {@link VisitorRequestDto} instance
     * @return a response entity with the {@link VisitorDto} instance and the HttpStatus
     */
	@PostMapping ({"/addAccount", "/addAccount/"})
    public ResponseEntity<VisitorDto> createAdditionalVisitor(@RequestBody VisitorRequestDto request) {
        // get parameters
        String username = request.getUsername();
        String newUsername = request.getNewUserName();
		String newPassword = request.getNewPassword();
        // create the object with the service
        Visitor persistedVisitor = visitorService.createAdditionalVisitor(username,newUsername,newPassword);
        // return it in the response entity
        return new ResponseEntity<VisitorDto>(new VisitorDto(persistedVisitor), HttpStatus.CREATED);
    }

    /**
     * Get a visitor by its primary key
     *
     * @param username the visitor's username
     * @return a response entity with the {@link VisitorDto} instance and the HttpStatus
     */
	@GetMapping({"/{username}", "/{username}/"})
	public ResponseEntity<VisitorDto> getVisitor(@PathVariable String username) {
        // call service
        Visitor retrievedVisitor = visitorService.getVisitorByUsername(username);
        // return response entity with Dto
        return new ResponseEntity<VisitorDto>(new VisitorDto(retrievedVisitor), HttpStatus.OK);
    }

    /**
     * Update an visitor's password
     *
     * @param request a {@link VisitorRequestDto} instance
     * @return a response entity with the {@link VisitorDto} instance and the HttpStatus
     */
	@PutMapping
    public ResponseEntity<VisitorDto> updateVisitorPassword(@RequestBody VisitorRequestDto request) {
        // get parameters
        String username = request.getUsername();
        String oldPassword = request.getPassword();
        String newPassword = request.getNewPassword();
        // call service layer
        Visitor updatedVisitor = visitorService.updateVisitorPassword(username, oldPassword, newPassword);
        // return updated Visitor as Dto
        return new ResponseEntity<VisitorDto>(new VisitorDto(updatedVisitor), HttpStatus.OK);
    }

    /**
     * Delete a visitor by its primary key
     *
     * @param username the visitor's username
     * @return a response entity with a message instance and the HttpStatus
     */
	@DeleteMapping({"/{username}", "/{username}/"})
    public ResponseEntity<String> deleteVisitor(@PathVariable String username) {
        // call service layer
        visitorService.deleteVisitor(username);
        // return updated Visitor as Dto
        return new ResponseEntity<String>("Visitor successfully deleted", HttpStatus.OK);
    }

    /**
     * Get all visitors in the system
     *
     * @return a response entity with an array list of {@link VisitorDto} instances and the HttpStatus
     */
	@GetMapping
    public ResponseEntity<ArrayList<VisitorDto>> getAllVisitors() {
        // get all visitors
        ArrayList<Visitor> retrievedVisitors = (ArrayList<Visitor>) visitorService.getAllVisitors();
        // make Dtos
        ArrayList<VisitorDto> allVisitorsDto = new ArrayList<>();
        for (Visitor visitor : retrievedVisitors) {
            allVisitorsDto.add(new VisitorDto(visitor));
        }
        // return the Dtos
        return new ResponseEntity<ArrayList<VisitorDto>>(allVisitorsDto, HttpStatus.OK);
    }

    /**
     * Get all employees by person
     * @param id the person's primary key
     * @return a response entity with an array list of {@link EmployeeDto} instances and the HttpStatus
     */
	@GetMapping ({"/byPerson", "/byPerson/"})
    public ResponseEntity<ArrayList<VisitorDto>> getAllVisitorsByPerson(@RequestParam int id) {
        // get all visitors
        ArrayList<Visitor> retrievedVisitors = (ArrayList<Visitor>) visitorService.getAllVisitorsByPerson(id);
        // make Dtos
        ArrayList<VisitorDto> allVisitorsDto = new ArrayList<>();
        for (Visitor visitor : retrievedVisitors)
            allVisitorsDto.add(new VisitorDto(visitor));
        // return the Dtos
        return new ResponseEntity<ArrayList<VisitorDto>>(allVisitorsDto, HttpStatus.OK);
    }

    /**
     * Get all visitors by person
     * @param username a request parameter, the username of the visitor
     * @param amount a double, the value of the visitors new balance
     * @return a response entity with a {@link VisitorDto} and the HttpStatus
     */
    @PutMapping({"/{username}", "/{username}/"})
    public ResponseEntity<VisitorDto> updateBalance(@PathVariable String username, @RequestParam double amount) { 
        Visitor visitor = visitorService.updateVisitorBalance(username, amount);
        return new ResponseEntity<VisitorDto>(new VisitorDto(visitor), HttpStatus.OK);
    } 
}
