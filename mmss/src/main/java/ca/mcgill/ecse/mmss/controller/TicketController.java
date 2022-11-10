package ca.mcgill.ecse.mmss.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse.mmss.dto.TicketDto;
import ca.mcgill.ecse.mmss.model.Ticket;
import ca.mcgill.ecse.mmss.service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	TicketService ticketService;

	/**
	 * Get ticket by its Id
	 * 
	 * @param id
	 * @return response entity with the ticket and ok status
	 * @author Shyam Desai
	 */
	@GetMapping("/{ticket}")
	public ResponseEntity<TicketDto> getTicket(@PathVariable int id) {
		Ticket retrievedTicket = ticketService.retrieveTicketById(id);
		return new ResponseEntity<TicketDto>(new TicketDto(retrievedTicket), HttpStatus.OK);
	}

	/**
	 * Create ticket based on input request
	 * 
	 * @param request
	 * @return created Ticket as Dto in response entity with ok status
	 * @author Shyam Desai
	 */
	@PostMapping
	public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto request) {
		String visitorUsername = request.getVisitorUsername();
//		Date date = request.get
		int numberOfTickets = request.get

		Ticket persistedTicket = ticketService.createTicket(visitorUsername, null, 0);

		return new ResponseEntity<TicketDto>(new TicketDto(persistedTicket), HttpStatus.CREATED);
	}

	/**
	 * Update ticket date given its Id
	 * 
	 * @param request
	 * @return updated Ticket Dto in response entity with ok status
	 * @author Shyam Desai
	 */
	@PutMapping
	public ResponseEntity<TicketDto> updateTicketStatus(@RequestBody TicketDto request) {
		int ticketId = request.getBookingId();

		Ticket updatedTicket = ticketService.updateTicket(ticketId, null);

		return new ResponseEntity<TicketDto>(new TicketDto(updatedTicket), HttpStatus.OK);
	}

	/**
	 * Delete ticket given its Id
	 * 
	 * @param request
	 * @return message indicated ticket deleted
	 * @author Shyam Desai
	 */
	@DeleteMapping
	public ResponseEntity<String> deleteTicket(@RequestBody TicketDto request) {

		int ticketId = request.getBookingId();

		ticketService.deleteTicket(ticketId);

		return new ResponseEntity<String>("Ticket succesfully deleted", HttpStatus.OK);
	}

	// MAPPING OF OTHER GET METHODS

	/**
	 * Get all tickets
	 * 
	 * @return ArrayList of all tickets as Dto
	 * @author Shyam Desai
	 */
	@GetMapping("/getall")
	public ResponseEntity<ArrayList<TicketDto>> getAllTickets() {

		ArrayList<Ticket> retrievedTickets = ticketService.getAllTickets();

		ArrayList<TicketDto> allTicketsDto = new ArrayList<>();
		for (Ticket ticket : retrievedTickets) {
			allTicketsDto.add(new TicketDto(ticket));
		}

		return new ResponseEntity<ArrayList<TicketDto>>(allTicketsDto, HttpStatus.OK);
	}
}