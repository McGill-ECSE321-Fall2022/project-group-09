package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Ticket;

public class TicketDto {
	private int bookingId;
	private double pricePerPerson;
	private String visitorUsername;

	/**
	 * Null constructor
	 * @author Shyam Desai
	 */
	public TicketDto() {}
	
	/**
	 * Constructor that takes in a ticket as an argument
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param ticket
	 */
	public TicketDto(Ticket ticket) {
		this.bookingId = ticket.getBookingId();
		this.pricePerPerson = ticket.getPricePerPerson();
		this.visitorUsername = ticket.getVisitor().getUsername();
	}

	/**
	 * Constructor that takes in separate arguments
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param bookingId
	 * @param pricePerPerson
	 * @param visitorUsername
	 */
	public TicketDto(int bookingId, double pricePerPerson, String visitorUsername) {
		this.bookingId = bookingId;
		this.pricePerPerson = pricePerPerson;
		this.visitorUsername = visitorUsername;
	}

	public int getBookingId() {
		return bookingId;
	}

	public double getPricePerPerson() {
		return pricePerPerson;
	}

	public String getVisitorUsername() {
		return visitorUsername;
	}

	public void setVisitorUsername(String visitorUsername) {
		this.visitorUsername = visitorUsername;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public void setPricePerPerson(double pricePerPerson) {
		this.pricePerPerson = pricePerPerson;
	}
}
