package ca.mcgill.ecse.mmss.dto;

import java.sql.Date;
import ca.mcgill.ecse.mmss.model.Ticket;

public class TicketDto {
	private int bookingId;
	private String visitorUsername;
	private Date date;

	/**
	 * Null constructor
	 * 
	 * @author Shyam Desai
	 */
	public TicketDto() {
	}

	/**
	 * Constructor that takes in a ticket as an argument
	 * 
	 * @author Shyam Desai, Shidan Javaheri
	 * @param ticket
	 */
	public TicketDto(Ticket ticket) {
		this.bookingId = ticket.getBookingId();
		this.visitorUsername = ticket.getVisitor().getUsername();
		this.date = ticket.getDate().getDate();
	}

	/**
	 * Constructor that takes in separate arguments
	 * 
	 * @author Shyam Desai, Shidan Javaheri
	 * @param bookingId
	 * @param pricePerPerson
	 * @param visitorUsername
	 */
	public TicketDto(int bookingId, String visitorUsername, Date date) {
		this.bookingId = bookingId;
		this.visitorUsername = visitorUsername;
		this.date = date;
	}

	public int getBookingId() {
		return bookingId;
	}


	public String getVisitorUsername() {
		return visitorUsername;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setVisitorUsername(String visitorUsername) {
		this.visitorUsername = visitorUsername;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

}