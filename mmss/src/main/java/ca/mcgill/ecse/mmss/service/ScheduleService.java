package ca.mcgill.ecse.mmss.service;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse.mmss.dao.ScheduleRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Schedule;

@Service
public class ScheduleService {
	@Autowired
    private ScheduleRepository scheduleRepository;

    /**
     * Get a schedule by its primary key
     *
     * @author Athmane Benarous
     * @param id
     * @return the schedule or an exception
     */
    @Transactional
    public Schedule getScheduleById(int scheduleId) {
        Schedule schedule = scheduleRepository.findScheduleByScheduleId(scheduleId);
        if (schedule == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
        return schedule;
    }
    
    /**
     * Create a schedule for the museum
     */
    @Transactional
    public Schedule createSchedule() {
    	ArrayList<Schedule> existingSchedule = scheduleRepository.findAll();
    	if (existingSchedule != null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "A schedule already exists.");
    	}
    	Schedule schedule = new Schedule();
    	scheduleRepository.save(schedule);
        return schedule;
    }
    
    // schedules are never supposed to get deleted, so only the createSchedule and getScheduleById are necessary
}