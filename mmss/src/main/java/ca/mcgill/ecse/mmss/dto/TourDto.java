package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Tour;
import ca.mcgill.ecse.mmss.model.Tour.ShiftTime;

public class TourDto {
	private int bookingId;
	private double pricePerPerson;
	private int numberOfParticipants;
	private ShiftTime shiftTime;
	private String visitorUsername;

	/**
	 * Null constructor
	 * @author Shyam Desai
	 */
	public TourDto() {}
	
	/**
	 * Constructor that takes in a tour as an argument
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param tour
	 */
	public TourDto(Tour tour) {
		this.bookingId = tour.getBookingId();
		this.pricePerPerson = tour.getPricePerPerson();
		this.numberOfParticipants = tour.getNumberOfParticipants();
		this.shiftTime = tour.getTourTime();
		this.visitorUsername = tour.getVisitor().getUsername();
	}

	/**
	 * Constructor that takes in separate arguments
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param bookingId
	 * @param pricePerPerson
	 * @param numberOfParticipants
	 * @param shiftTime
	 * @param visitorUsername
	 */
	public TourDto(int bookingId, double pricePerPerson, int numberOfParticipants, ShiftTime shiftTime,
			String visitorUsername) {
		this.bookingId = bookingId;
		this.pricePerPerson = pricePerPerson;
		this.numberOfParticipants = numberOfParticipants;
		this.shiftTime = shiftTime;
		this.visitorUsername = visitorUsername;
	}

	public int getBookingId() {
		return bookingId;
	}

	public double getPricePerPerson() {
		return pricePerPerson;
	}

	public int getNumberOfParticipants() {
		return numberOfParticipants;
	}

	public ShiftTime getShiftTime() {
		return shiftTime;
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

	public void setNumberOfParticipants(int numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}

	public void setShiftTime(ShiftTime shiftTime) {
		this.shiftTime = shiftTime;
	}
}
