package ca.mcgill.ecse.mmss.controller;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse.mmss.dto.TicketDto;
import ca.mcgill.ecse.mmss.service.TicketService;

@RestController
@RequestMapping ("/ticket")
public class TicketController {
	
	@Autowired
	TicketService ticketService;
	
	@GetMapping("/{ticket}")
	
	public ResponseEntity<TicketDto> getTicket (@PathVariable int id) {
        return ticketService.retrieveTicketById(id).map(tour -> new ResponseEntity<TicketDto>(tour, HttpStatus.OK)).orElse(new ResponseEntity<TicketDto>(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping
    public ResponseEntity<TicketDto> createTour (@RequestParam String username, @RequestParam Date date, @RequestParam int numberOfTickets) {
		try { 
			TicketDto persistedTicket = ticketService.createTicket(username, date, numberOfTickets);
            return new ResponseEntity<TicketDto>(persistedTicket, HttpStatus.CREATED); // return it in the response entity
        } catch (DataIntegrityViolationException e) { 
            throw e;
        }
	}
}