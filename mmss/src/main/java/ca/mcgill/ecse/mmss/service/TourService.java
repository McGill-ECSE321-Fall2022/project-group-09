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

	@Autowired
	private NotificationService notificationService;

	/**
	 * Find tour by its Id
	 * 
	 * @param id
	 * @throws exception - id doesn't exist
	 * @return tour
	 * @author Shyam Desai
	 */
	@Transactional
	public Tour getTourById(int id) {
		Tour tour = tourRepository.findTourByBookingId(id);

		if (tour == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "Tour not found.");
		}

		return tour;
	}

	/**
	 * Find all tours
	 * 
	 * @return ArrayList of Tours
	 * @author Shyam Desai
	 */
	@Transactional
	public ArrayList<Tour> getAllTours() {
		ArrayList<Tour> allTours = tourRepository.findAll();
		return allTours;
	}

	/**
	 * Get all tours given date
	 * 
	 * @param date
	 * @throws exception - no open day
	 * @return ArrayList of all tours
	 * @author Shyam Desai
	 */
	@Transactional
	public ArrayList<Tour> getAllToursByDate(Date date) {
		OpenDay openDateToFind = openDayRepository.findOpenDayByDate(date);

		if (openDateToFind == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no open day with this date.");
		}

		ArrayList<Tour> allTours = tourRepository.findByDate(openDateToFind);

		return allTours;
	}

	/**
	 * Get all tours given a visitor
	 * 
	 * @param username
	 * @throws exception - visitor's username not found
	 * @return ArrayList of all tours
	 * @author Shyam Desai
	 */
	@Transactional
	public ArrayList<Tour> getAllToursByVisitor(String username) {
		Visitor visitor = visitorRepository.findVisitorByUsername(username);

		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "The visitor with this username was not found.");
		}

		ArrayList<Tour> allTours = tourRepository.findByVisitor(visitor);

		return allTours;
	}

	/**
	 * Get all tours given a number of participants
	 * 
	 * @param numberOfParticipants
	 * @return ArrayList of all tours
	 * @author Shyam Desai
	 */
	@Transactional
	public ArrayList<Tour> getAllToursByNumberOfParticipantsLessThan(int numberOfParticipants) {
		return tourRepository.findByNumberOfParticipantsLessThan(numberOfParticipants);
	}

	/**
	 * Get all tours given shift time
	 * 
	 * @param tourTime
	 * @return ArrayList of all tours
	 * @author Shyam Desai
	 */
	@Transactional
	public ArrayList<Tour> getAllToursByTourTime(ShiftTime tourTime) {
		return tourRepository.findByTourTime(tourTime);
	}

	/**
	 * Get all tours given a date and shift time
	 * 
	 * @param date
	 * @param tourTime
	 * @return ArrayList of all tours
	 * @author Shyam Desai
	 */
	@Transactional
	public ArrayList<Tour> getAllToursByDateAndShiftTime(OpenDay date, ShiftTime tourTime) {
		return tourRepository.findByDateAndTourTime(date, tourTime);
	}

	/**
	 * Get all tours given a number of participants less than
	 * 
	 * @param numberOfParticipants
	 * @return ArrayList of all tours
	 * @author Shyam Desai
	 */
	@Transactional
	public ArrayList<Tour> getAllToursByTourTimeAndDateAndNumberOfParticipantsLessThan(ShiftTime tourTime, OpenDay date,
			int numberOfParticipants) {
		return tourRepository.findByTourTimeAndDateAndNumberOfParticipantsLessThan(tourTime, date,
				numberOfParticipants);
	}

	/**
	 * Create tours, check if museum is open on day, and check number of
	 * participants is 0 or more than 20
	 * 
	 * @param username
	 * @param date
	 * @param numberOfParticipants
	 * @param tourTime
	 * @throws exception - visitor's username not found
	 * @throws exception - no open day
	 * @throws exception - 0 participants
	 * @throws exception - more than 20 participants
	 * @return tour
	 * @author Shyam Desai
	 */
	@Transactional
	public Tour createTour(String username, Date date, int numberOfParticipants, ShiftTime tourTime) {

		Visitor visitor = visitorRepository.findVisitorByUsername(username);
		OpenDay openDay = openDayRepository.findOpenDayByDate(date);

		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "The visitor with that username was not found.");
		} else if (openDay == null) {
			throw new MmssException(HttpStatus.BAD_REQUEST, "Cannot book tours on this day.");
		} else if (numberOfParticipants == 0) {
			throw new MmssException(HttpStatus.BAD_REQUEST, "Cannot book a tour for 0 visitors.");
		} else if (numberOfParticipants > 20) {
			throw new MmssException(HttpStatus.BAD_REQUEST,
					"Cannot book a tour for more than 20 visitors in this time slot.");

		}

		Tour tour = new Tour();
		tour.setVisitor(visitor);
		tour.setDate(openDay);
		tour.setPricePerPerson(25);
		tour.setTourTime(tourTime);
		tour.setNumberOfParticipants(numberOfParticipants);

		tourRepository.save(tour);

		return tour;
	}

	/**
	 * Updates tour booking given id, date, and number of participants
	 * 
	 * @param id
	 * @param date
	 * @param numberOfParticipants
	 * @throws exception - visitor's username not found
	 * @throws exception - no open day
	 * @throws exception - 0 participants
	 * @throws exception - more than 20 participants
	 * @return tour
	 * @author Shyam Desai
	 */
	@Transactional
	public Tour updateTour(int id, Date date, int numberOfParticipants) {
		Tour tour = tourRepository.findTourByBookingId(id);
		OpenDay openDay = openDayRepository.findOpenDayByDate(date);

		if (tour == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "The tour with this Id was not found.");
		} else if (openDay == null) {
			throw new MmssException(HttpStatus.BAD_REQUEST, "Cannot update tours to this day.");
		} else if (numberOfParticipants == 0) {
			throw new MmssException(HttpStatus.BAD_REQUEST, "Cannot book a tour for 0 visitors.");
		} else if (numberOfParticipants > 20) {
			throw new MmssException(HttpStatus.BAD_REQUEST,
					"Cannot book a tour for more than 20 visitors in this time slot.");
		}

		tour.setDate(openDay);
		tour.setNumberOfParticipants(numberOfParticipants);
		Tour updatedTour = tourRepository.save(tour);

		String message = "Your request for tour booking id: " + String.valueOf(tour.getBookingId()) + " to modify to"
				+ tour.getDate().toString() + "and " + tour.getNumberOfParticipants()
				+ " participants has been processed! The following email from the Museum will have your updated tour booking.";

		notificationService.createNotificationByUsername(tour.getVisitor().getUsername(), message);

		return updatedTour;
	}

	/**
	 * Deletes tour given an Id
	 * 
	 * @param id
	 * @throws exception - id doesn't exist
	 * @author Shyam Desai
	 */
	@Transactional
	public void deleteTour(int id) {
		Tour tour = tourRepository.findTourByBookingId(id);

		if (tour == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "The tour with this Id was not found.");
		}

		tourRepository.delete(tour);
	}
}