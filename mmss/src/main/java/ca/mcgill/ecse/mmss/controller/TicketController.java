package ca.mcgill.ecse.mmss.controller;

import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse.mmss.dto.TicketDto;
import ca.mcgill.ecse.mmss.model.Ticket;
import ca.mcgill.ecse.mmss.service.TicketService;

@RestController
@RequestMapping ("/ticket")
public class TicketController {
	
	@Autowired
	TicketService ticketService;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @author Shyam Desai
	 */
	@GetMapping("/{tour}")
	public ResponseEntity<TicketDto> getTicket (@PathVariable int id) {
		Ticket retrievedTicket = ticketService.retrieveTicketById(id);
        return new ResponseEntity<TicketDto>(new TicketDto(retrievedTicket), HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param username
	 * @param date
	 * @param numberOfTickets
	 * @return
	 * @author Shyam Desai
	 */
	@PostMapping
    public ResponseEntity<TicketDto> createTicket (@RequestBody String username, @RequestBody Date date, @RequestBody int numberOfTickets) {
		Ticket persistedTicket = ticketService.createTicket(username, date, numberOfTickets);
		return new ResponseEntity<TicketDto>(new TicketDto(persistedTicket), HttpStatus.CREATED);
	}
}