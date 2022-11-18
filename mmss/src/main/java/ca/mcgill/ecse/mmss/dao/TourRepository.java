package ca.mcgill.ecse.mmss.dao;

import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Tour;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Tour.ShiftTime;

public interface TourRepository extends CrudRepository<Tour, Integer> {
	Tour findTourByBookingId(int bookingId);

	ArrayList<Tour> findByDate(OpenDay date);

	ArrayList<Tour> findByVisitor(Visitor visitor);

	ArrayList<Tour> findByNumberOfParticipantsLessThan(int numberOfParticipants);

	ArrayList<Tour> findByTourTime(ShiftTime tourTime);

	ArrayList<Tour> findByDateAndTourTime(OpenDay date, ShiftTime tourTime);

	ArrayList<Tour> findByTourTimeAndDateAndNumberOfParticipantsLessThan(ShiftTime shiftTime, OpenDay date,
			int numberOfParticipants);

	ArrayList<Tour> findAll();
}
