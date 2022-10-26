package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Tour;


public interface TourRepository extends CrudRepository<Tour, Integer> {
    Tour findTourByBookingId(int bookingId);
}