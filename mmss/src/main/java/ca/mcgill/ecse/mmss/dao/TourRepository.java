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
    ArrayList<Tour> findByNumberOfParticipants(int numberOfParticipants);
    ArrayList<Tour> findByShiftTimeAndDateAndNumberOfParticipantsLessThan(ShiftTime shift, OpenDay date, int numberOfParticipants);
    ArrayList<Tour> findByShiftTime(ShiftTime tourTime);
    ArrayList<Tour> findAll();
}
