package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Tour;
import ca.mcgill.ecse.mmss.model.Tour.ShiftTime;

public class TourDto {
    private int bookingId; 
    private double pricePerPerson; 
    private int numberOfParticipants; 
    private ShiftTime shiftTime; 
    private VisitorDto visitorDto;

    /**
     * @author Shidan Javaheri
     * Constructor that takes in a tour as an arugment
     * @param tour
     */
    public TourDto (Tour tour) { 
        this.bookingId = tour.getBookingId();
        this.pricePerPerson = tour.getPricePerPerson();
        this.numberOfParticipants = tour.getNumberOfParticipants();
        this.shiftTime = tour.getTourTime();
        this.visitorDto = new VisitorDto (tour.getVisitor());
    }
   
    /**
     * @author Shidan Javaheri
     * Constructor that takes in seperate arguemnts
     * @param bookingId
     * @param pricePerPerson
     * @param numberOfParticipants
     * @param shiftTime
     * @param visitorDto
     */
    public TourDto(int bookingId, double pricePerPerson, int numberOfParticipants, ShiftTime shiftTime,
        VisitorDto visitorDto) {
    this.bookingId = bookingId;
    this.pricePerPerson = pricePerPerson;
    this.numberOfParticipants = numberOfParticipants;
    this.shiftTime = shiftTime;
    this.visitorDto = visitorDto;
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
    public VisitorDto getVisitorDto() {
        return visitorDto;
    } 

   
   

}
