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
    public Schedule retrieveScheduleById(int id) {
        Schedule schedule = scheduleRepository.findScheduleByScheduleId(id);
        if (schedule == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
        return schedule;
    }

    /**
     * Get all the schedules
     * @return a array list of schedules
     */
    @Transactional
    public ArrayList<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }
}