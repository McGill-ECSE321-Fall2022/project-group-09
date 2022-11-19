package ca.mcgill.ecse.mmss.service;


import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse.mmss.dao.OpenDayRepository;
import ca.mcgill.ecse.mmss.dao.ScheduleRepository;
import ca.mcgill.ecse.mmss.dao.ShiftRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Schedule;
import ca.mcgill.ecse.mmss.model.Shift;

@Service
public class ScheduleService {
	@Autowired
    private ScheduleRepository scheduleRepository;
	@Autowired
    private OpenDayRepository openDayRepository;
    @Autowired
    private ShiftRepository shiftRepository;
    @Autowired
    private ShiftService shiftService;

    /**
     * Get a schedule by its primary key
     *
     * @author Athmane Benarous
     * @param id
     * @return the schedule or an exception
     */
    @Transactional
    public Schedule getSchedule() {
        Schedule schedule = scheduleRepository.findAll().get(0);
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
    
    /**
     * If both the schedule and the open day exist, assign the schedule to a single open day
     *
     * @param date
     */
    @Transactional
    public void assignScheduleToOpenDay(Date date) {
    	OpenDay openDay = openDayRepository.findOpenDayByDate(date);
        if (openDay == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
        else if (openDayRepository.findOpenDayByDate(date) == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Open day not found");
        }
    	openDay.setSchedule(getSchedule());
    	openDayRepository.save(openDay);
    }    

    /**
     * If the schedule exists, assign the schedule to a list of open days
     *
     * @param employeeList
     */
    @Transactional
    public void assignScheduleToOpenDays(ArrayList<OpenDay> dateList) {
        if (getSchedule() == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
        for(OpenDay openDay : dateList) {
        	assignScheduleToOpenDay(openDay.getDate());
        }
    }
    
    /**
     * If the schedule exists, assign the schedule to all shifts
     */
    @Transactional
    public void assignScheduleToShifts() {
        if (getSchedule() == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
        ArrayList<Shift> shiftList = shiftService.getAllShifts();
        for(Shift shift : shiftList) {
        	shift.setSchedule(getSchedule());
        	shiftRepository.save(shift);
        }
    }
}