package ca.mcgill.ecse.mmss.service;

import java.sql.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.TicketRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.dto.TicketDto;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Ticket;
import ca.mcgill.ecse.mmss.model.Visitor;

@Service
public class TicketService {
	
	// make the loan repository private
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private VisitorRepository visitorRepository;
    
    @Autowired
    private OpenDayRepository openDayRepository;
    
    
    @Transactional
    public Optional<TicketDto> retrieveTicketById (int id) { 
        Ticket ticket = ticketRepository.findTicketByBookingId(id); 
        if (ticket == null) {//do something
        }
        var ticketDto = new TicketDto (ticket);
        return Optional.of(ticketDto); 
    }

    public TicketDto createTicket (String username, Date date, int numberOfTickets) {
    	Visitor visitor = visitorRepository.findVisitorByUsername(username); 
    	OpenDay openDay = openDayRepository.findOpenDayByDate(date);
    	
    	if(openDay == null) {
    		throw new IllegalArgumentException("Cannot book tickets on this day.");
    	}
    	
		if (numberOfTickets == 0) {
    		throw new IllegalArgumentException("Cannot book 0 tickets.");
    	}    		
    		
    	if (visitor == null) {          
    		throw new IllegalArgumentException("Name can't be empty.");
    	}
    	 	 
    	 //if all checks pass, then create ticket
    	 Ticket ticket = new Ticket();
    	 ticket.setVisitor(visitor);
    	 ticket.setDate(openDay);
    	 ticket.setPricePerPerson(20);
   
    	 
    	 //save the ticket object
    	 ticketRepository.save(ticket);
    	 
    	 //return Dto of ticket object
    	 return (new TicketDto(ticket));
    }
}