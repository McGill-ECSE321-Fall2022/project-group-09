package ca.mcgill.ecse.mmss.service;

import java.sql.Date;
import java.util.ArrayList;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.TourRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
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
	public Tour retrieveTourById (int id) {
		Tour tour = tourRepository.findTourByBookingId(id);
		if (tour == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "Tour not found.");
		}
		return tour;
	}
	
	/**
	 * Find all tours in the DB
	 * @return ArrayList of tours
	 * @author Shyam Desai
	 */
	@Transactional
	public ArrayList<Tour> getAllTours() {
		ArrayList<Tour> allTours = tourRepository.findAll();
		return allTours;
	}
	
	/**
	 * Create tours. Check if museum is open on day. Check number of participants is 0 or more than 20. 
	 * @param username
	 * @param date
	 * @param numberOfParticipants
	 * @param tourTime
	 * @return tour
	 * @author Shyam Desai
	 */
	@Transactional
	public Tour createTour (String username, Date date, int numberOfParticipants, ShiftTime tourTime) {
	
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
    	 
    	 //return ticket object
    	 return tour;
	}
	
	/**
	 * Deletes the tour of a given Id if it exists
	 * @param id
	 * @author Shyam Desai
	 */
	@Transactional
	public void deleteTour (int id) {
		Tour tour = tourRepository.findTourByBookingId(id);
		if(tour == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "The tour with this Id was not found.");
		}
		tourRepository.delete(tour);
	}
	
	/**
	 * Takes tour id, date, and number of participants.
	 * @param id
	 * @param date
	 * @param numberOfParticipants
	 * @return tour
	 * @author Shyam Desai
	 */
	@Transactional
	public Tour updateTour (int id, Date date, int numberOfParticipants) {
		Tour tour = tourRepository.findTourByBookingId(id);
		OpenDay openDay = openDayRepository.findOpenDayByDate(date);
		
		if(tour == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The tour with this Id was not found");
		} else {
			if(openDay == null) {
				throw new IllegalArgumentException("Cannot book tours on this day.");
			}
		}
		return tour;
	}
}