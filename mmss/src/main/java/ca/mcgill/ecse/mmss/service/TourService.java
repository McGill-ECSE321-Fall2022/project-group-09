package ca.mcgill.ecse.mmss.service;

import java.sql.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.TourRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.dto.TourDto;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Tour;
import ca.mcgill.ecse.mmss.model.Tour.ShiftTime;
import ca.mcgill.ecse.mmss.model.Visitor;

@Service
public class TourService {
    
	@Autowired
	private TourRepository tourRepository;
	
    @Autowired
    private VisitorRepository visitorRepository;
    
    @Autowired
    private OpenDayRepository openDayRepository;

	@Transactional
	public Optional<TourDto> retrieveTourById (int id) {
		Tour tour = tourRepository.findTourByBookingId(id);
		if (tour == null) {
			//do something
		}
		var TourDto = new TourDto(tour);
		return Optional.of(TourDto);
	}
	
	@Transactional
	public TourDto createTour (String username, Date date, int numberOfParticipants, ShiftTime tourTime) {
		//process inputs, make sure valid
		//tests related to the tour
	
		Visitor visitor = visitorRepository.findVisitorByUsername(username); 
		OpenDay openDay = openDayRepository.findOpenDayByDate(date);
	    	
	    	if(openDay == null) {
	    		throw new IllegalArgumentException("Cannot book tours on this day.");
	    	}
	    	
			if (numberOfParticipants == 0) {
	    		throw new IllegalArgumentException("Cannot book a tour for 0 visitors.");
	    	}    		
	    	
			if (numberOfParticipants > 20) {
	    		throw new IllegalArgumentException("Cannot book a tour for more than 20 visitors in this time slot.");
	    	}

	    	if (visitor == null) {          
	    		throw new IllegalArgumentException("Name can't be empty.");
	    	}
	    	 	 
	    	 //if all checks pass, then create tour
	    	 Tour tour = new Tour();
	    	 tour.setVisitor(visitor);
	    	 tour.setDate(openDay);
	    	 tour.setPricePerPerson(25);
	    	 tour.setTourTime(tourTime);
	    	 tour.setNumberOfParticipants(numberOfParticipants);
	   
	    	 //save the ticket object
	    	 tourRepository.save(tour);
	    	 
	    	 //return Dto of ticket object
	    	 return (new TourDto(tour));
	}
}