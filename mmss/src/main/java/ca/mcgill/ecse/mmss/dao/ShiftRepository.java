package ca.mcgill.ecse.mmss.dao;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse.mmss.model.Shift;


public interface ShiftRepository extends CrudRepository<Shift, Integer> {
    Shift findShiftByShiftId(int shiftId);
    ArrayList<Shift> findAll();
    ArrayList<Shift> findAllByShiftTime(Shift.ShiftTime time);
}