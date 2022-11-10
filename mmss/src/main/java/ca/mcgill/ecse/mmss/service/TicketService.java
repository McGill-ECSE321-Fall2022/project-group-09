package ca.mcgill.ecse.mmss.service;

import java.sql.Date;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.TicketRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Ticket;
import ca.mcgill.ecse.mmss.model.Visitor;

@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private VisitorRepository visitorRepository;

	@Autowired
	private OpenDayRepository openDayRepository;

	/**
	 * Find ticket by its Id
	 * 
	 * @param id
	 * @return ticket, or an exception if not found
	 * @author Shyam Desai
	 */
	@Transactional
	public Ticket retrieveTicketById(int id) {
		Ticket ticket = ticketRepository.findTicketByBookingId(id);
		if (ticket == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "Ticket not found.");
		}
		return ticket;
	}

	/**
	 * Find all tickets in DB
	 * 
	 * @return ArrayList of Tickets
	 * @author Shyam Desai
	 */
	public ArrayList<Ticket> getAllTickets() {
		ArrayList<Ticket> allTickets = ticketRepository.findAll();
		return allTickets;
	}

	/**
	 * Creates a ticket. Checks if museum open on the day. Checks if 0 tickets are
	 * booked.
	 * 
	 * @param username
	 * @param date
	 * @param numberOfTickets
	 * @return ticket
	 * @author Shyam Desai
	 */
	@Transactional
	public Ticket createTicket(String username, Date date, int numberOfTickets) {
		Visitor visitor = visitorRepository.findVisitorByUsername(username);
		OpenDay openDay = openDayRepository.findOpenDayByDate(date);

		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "The visitor with this Id was not found.");
		} else  {
			if (openDay == null) {
				throw new MmssException(HttpStatus.NOT_FOUND, "Cannot book tickets on this day.");
			}

			if (numberOfTickets == 0) {
				throw new MmssException(HttpStatus.NOT_FOUND, "Cannot book 0 tickets.");
			}
		}

		// if all checks pass, then create ticket
		Ticket ticket = new Ticket();
		ticket.setVisitor(visitor);
		ticket.setDate(openDay);
		ticket.setPricePerPerson(20);

		// save the ticket object
		ticketRepository.save(ticket);
		
		// return ticket
		return ticket;
	}

	/**
	 * Updates ticket booking Takes id and date of a ticket to modify booking date
	 * 
	 * @param id
	 * @param date
	 * @return ticket
	 * @author Shyam Desai
	 */
	@Transactional
	public Ticket updateTicket(int id, Date date) {

		Ticket ticket = ticketRepository.findTicketByBookingId(id);
		OpenDay openDay = openDayRepository.findOpenDayByDate(date);

		if (ticket == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "The ticket with this Id was not found.");
		} else {
			if (openDay == null) {
				throw new MmssException(HttpStatus.NOT_FOUND, "Cannot update tickets to this day.");
			} else {
				ticket.setDate(openDay);

//    			String message = "Your ticket date change request originally on " + ticket.getDate().toString()
//                        + "with id: " + String.valueOf(ticket.getBookingId())
//                        + "has been approved! The following email from the Museum will have attached your updated tickets.";

				// use create notification method from Sasha
			}
		}

		return ticket;
	}

	/**
	 * Delete ticket of given Id if it exists
	 * 
	 * @param id
	 * @author Shyam Desai
	 */
	@Transactional
	public void deleteTicket(int id) {
		Ticket ticket = ticketRepository.findTicketByBookingId(id);

		if (ticket == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "The ticket with this Id was not found.");
		}

		ticketRepository.delete(ticket);
	}
}