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

	@Autowired
	private NotificationService notificationService;

	/**
	 * Find ticket by its Id
	 * 
	 * @param id
	 * @throws exception - ticket not found
	 * @return ticket, or an exception if not found
	 * @author Shyam Desai
	 */
	@Transactional
	public Ticket getTicketById(int id) {
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
	@Transactional
	public ArrayList<Ticket> getAllTickets() {
		ArrayList<Ticket> allTickets = ticketRepository.findAll();
		return allTickets;
	}

	/**
	 * Find all tickets given a date
	 * 
	 * @param date
	 * @throws exception - no open day
	 * @return ArrayList of all tickets
	 * @author Shyam Desai
	 */
	@Transactional
	public ArrayList<Ticket> getAllTicketsByDate(Date date) {
		OpenDay openDateToFind = openDayRepository.findOpenDayByDate(date);

		if (openDateToFind == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no open day on this date.");
		}

		ArrayList<Ticket> allTickets = ticketRepository.findByDate(openDateToFind);

		return allTickets;
	}

	/**
	 * Get all tickets given a visitor
	 * 
	 * @param username
	 * @throws exception - no visitor with username
	 * @return ArrayList of all tickets
	 * @author Shyam Desai
	 */
	@Transactional
	public ArrayList<Ticket> getAllTicketsByVisitor(String username) {
		Visitor visitor = visitorRepository.findVisitorByUsername(username);

		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "The visitor with this username was not found.");
		}

		ArrayList<Ticket> allTickets = ticketRepository.findByVisitor(visitor);

		return allTickets;
	}

	/**
	 * Creates a ticket. Checks if museum open on the day. Checks if ticket exists.
	 * 
	 * @param username
	 * @param date
	 * @throws exception - no visitor with username
	 * @throws exception - no open day to book tickets
	 * @return ticket
	 * @author Shyam Desai
	 */
	@Transactional
	public Ticket createTicket(String username, Date date) {
		Visitor visitor = visitorRepository.findVisitorByUsername(username);
		OpenDay openDay = openDayRepository.findOpenDayByDate(date);

		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "The visitor with that username was not found.");
		} else {
			if (openDay == null) {
				throw new MmssException(HttpStatus.BAD_REQUEST, "Cannot book tickets on this day.");
			}
		}

		Ticket ticket = new Ticket();
		ticket.setVisitor(visitor);
		ticket.setDate(openDay);
		ticket.setPricePerPerson(20);

		ticketRepository.save(ticket);

		return ticket;
	}

	/**
	 * Updates ticket booking. Takes id and date of a ticket to modify booking date
	 * 
	 * @param id
	 * @param date
	 * @throws exception - id doesn't exist
	 * @throws exception - no open day to update ticket
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
				throw new MmssException(HttpStatus.BAD_REQUEST, "Cannot update tickets to this day.");
			}
		}

		ticket.setDate(openDay);
		Ticket updatedTicket = ticketRepository.save(ticket);

		String message = "Your ticket date change request to " + ticket.getDate().toString() + "with id: "
				+ String.valueOf(ticket.getBookingId())
				+ "has been processed! The following email from the Museum will have your updated tickets.";

		notificationService.createNotificationByUsername(ticket.getVisitor().getUsername(), message);

		return updatedTicket;
	}

	/**
	 * Delete ticket of given Id if it exists
	 * 
	 * @param id
	 * @throws exception - id doesn't exist
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