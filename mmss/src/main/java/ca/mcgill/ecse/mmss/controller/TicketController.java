package ca.mcgill.ecse.mmss.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import ca.mcgill.ecse.mmss.dto.TicketDto;
import ca.mcgill.ecse.mmss.model.Ticket;
import ca.mcgill.ecse.mmss.service.TicketService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping({ "/ticket", "/ticket/" })
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
	@GetMapping({ "/{id}", "/{id}/" })
	public ResponseEntity<TicketDto> getTicket(@PathVariable int id) {
		Ticket retrievedTicket = ticketService.getTicketById(id);
		return new ResponseEntity<TicketDto>(new TicketDto(retrievedTicket), HttpStatus.OK);
	}

	/**
	 * Create ticket based on input request
	 * 
	 * @param request {@link TicketDto}
	 * @return created Ticket as Dto in response entity with created status
	 * @author Shyam Desai
	 */
	@PostMapping
	public ResponseEntity<TicketDto> createTicket(@RequestBody TicketDto request) {
		String visitorUsername = request.getVisitorUsername();
		Date date = request.getDate();

		Ticket persistedTicket = ticketService.createTicket(visitorUsername, date);

		return new ResponseEntity<TicketDto>(new TicketDto(persistedTicket), HttpStatus.CREATED);
	}

	/**
	 * Update ticket date given its Id
	 * 
	 * @param request {@link TicketDto}
	 * @return updated Ticket Dto in response entity with ok status
	 * @author Shyam Desai
	 */
	@PutMapping
	public ResponseEntity<TicketDto> updateTicket(@RequestBody TicketDto request) {
		int ticketId = request.getBookingId();
		Date date = request.getDate();

		Ticket updatedTicket = ticketService.updateTicket(ticketId, date);

		return new ResponseEntity<TicketDto>(new TicketDto(updatedTicket), HttpStatus.OK);
	}

	/**
	 * Delete ticket given its Id
	 * 
	 * @param request
	 * @return message indicated ticket deleted
	 * @author Shyam Desai
	 */
	@DeleteMapping({ "/{id}", "/{id}/" })
	public ResponseEntity<String> deleteTicket(@PathVariable int id) {

		ticketService.deleteTicket(id);

		return new ResponseEntity<String>("Ticket successfully deleted.", HttpStatus.OK);
	}

	// MAPPING OF OTHER GET METHODS.

	/**
	 * Get all tickets
	 * 
	 * @return ArrayList of all tickets as Dto
	 * @author Shyam Desai
	 */
	@GetMapping
	public ResponseEntity<ArrayList<TicketDto>> getAllTickets() {

		ArrayList<Ticket> retrievedTickets = ticketService.getAllTickets();

		ArrayList<TicketDto> allTicketsDto = new ArrayList<>();
		for (Ticket ticket : retrievedTickets) {
			allTicketsDto.add(new TicketDto(ticket));
		}

		return new ResponseEntity<ArrayList<TicketDto>>(allTicketsDto, HttpStatus.OK);
	}

	/**
	 * Get all tickets given a date
	 * 
	 * @param date
	 * @return ArrayList of all tickets as DTOs
	 * @author Shyam Desai
	 */
	@GetMapping({ "/date", "/date/" })
	public ResponseEntity<ArrayList<TicketDto>> getAllTicketsWithDate(
			@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
		ArrayList<Ticket> retrievedTickets = ticketService.getAllTicketsByDate(Date.valueOf(date));

		ArrayList<TicketDto> allTicketsDto = new ArrayList<>();
		for (Ticket ticket : retrievedTickets) {
			allTicketsDto.add(new TicketDto(ticket));
		}

		return new ResponseEntity<ArrayList<TicketDto>>(allTicketsDto, HttpStatus.OK);
	}

	/**
	 * Get all tickets given a visitor
	 * 
	 * @param username
	 * @return ArrayList of all tickets as DTOs
	 * @author Shyam Desai
	 */
	@GetMapping({ "/visitor", "/visitor/" })
	public ResponseEntity<ArrayList<TicketDto>> getAllTicketsWithUsername(@RequestParam String username) {
		ArrayList<Ticket> retrievedTickets = ticketService.getAllTicketsByVisitor(username);

		ArrayList<TicketDto> allTicketsDto = new ArrayList<>();
		for (Ticket ticket : retrievedTickets) {
			allTicketsDto.add(new TicketDto(ticket));
		}

		return new ResponseEntity<ArrayList<TicketDto>>(allTicketsDto, HttpStatus.OK);
	}
}