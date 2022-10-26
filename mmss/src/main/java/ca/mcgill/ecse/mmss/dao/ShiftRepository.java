package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Shift;


public interface ShiftRepository extends CrudRepository<Shift, Integer> {
    Shift findShiftByShiftId(int shiftId);
}