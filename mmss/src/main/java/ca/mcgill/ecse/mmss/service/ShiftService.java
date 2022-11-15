package ca.mcgill.ecse.mmss.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse.mmss.dao.ShiftRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Schedule;
import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Shift.ShiftTime;

@Service
public class ShiftService {
	@Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private ScheduleService scheduleService;

    /**
     * Get a shift by its primary key
     *
     * @author Athmane Benarous
     * @param id
     * @return the shift or an exception
     */
    @Transactional
    public Shift retrieveShiftById(int id) {
        Shift shift = shiftRepository.findShiftByShiftId(id);
        if (shift == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Shift not found");
        }
        return shift;
    }

    /**
     * Get shift by shift time
     *
     * @param time
     * @return an array list of (normally) one correct shift
     */
    @Transactional
    public Shift getShiftByShiftTime(Shift.ShiftTime time) {
    	ArrayList<Shift> shiftList = shiftRepository.findAllByShiftTime(time);
        if (shiftList == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Shift not found");
        }
        return shiftList.get(0); // get first (and only) shift if it exists
    }


    /**
     * Get all the shifts
     * @return a array list of shifts
     */
    @Transactional
    public ArrayList<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    /**
     * Create shifts, link them to schedule and persist them in the DB
     *
     * @param time
     * @return the shift ArrayList
     */
    @Transactional
    public ArrayList<Shift> createShifts(int scheduleId) {
        if (scheduleService.getScheduleById(scheduleId) == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
    	for(ShiftTime time : ShiftTime.values()) {
    		if (shiftRepository.findAllByShiftTime(time) != null)
                throw new MmssException(HttpStatus.BAD_REQUEST, "The shift " + time.name() + " already exists.");
        	Shift shift = new Shift();
        	shift.setShiftTime(time);
        	Schedule schedule = scheduleService.getScheduleById(scheduleId);
        	shift.setSchedule(schedule);
        	shiftRepository.save(shift);
    	}
        return getAllShifts();
    }
}
