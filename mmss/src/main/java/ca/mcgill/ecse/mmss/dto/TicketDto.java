package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Ticket;

public class TicketDto {
    private int bookingId; 
    private double pricePerPerson; 
    private VisitorDto visitorDto;
    
    /**
     * @author Shidan Javaheri
     * Constructor that takes in a ticket as an argument
     * @param ticket
     */
    public TicketDto (Ticket ticket) { 
        this.bookingId = ticket.getBookingId();
        this.pricePerPerson = ticket.getPricePerPerson();
        this.visitorDto = new VisitorDto(ticket.getVisitor());
    }

    /**
     * @author Shidan Javaheri
     * Constructor that takes in seperate arguments
     * @param bookingId
     * @param pricePerPerson
     * @param visitorDto
     */
    public TicketDto(int bookingId, double pricePerPerson, VisitorDto visitorDto) {
        this.bookingId = bookingId;
        this.pricePerPerson = pricePerPerson;
        this.visitorDto = visitorDto;
    }
    public int getBookingId() {
        return bookingId;
    }
    public double getPricePerPerson() {
        return pricePerPerson;
    }
    public VisitorDto getVisitorDto() {
        return visitorDto;
    } 

    

    
}
