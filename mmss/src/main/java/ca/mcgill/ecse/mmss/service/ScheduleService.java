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
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Room;
import ca.mcgill.ecse.mmss.model.Schedule;
import ca.mcgill.ecse.mmss.model.Shift;

@Service
public class ScheduleService {
	@Autowired
    private ScheduleRepository scheduleRepository;
	@Autowired
    private OpenDayRepository openDayRepository;
    @Autowired
    private OpenDayService openDayService;
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
    
    /**
     * If both the schedule and the open day exist, assign the schedule to a single open day
     *
     * @param scheduleId
     * @param date
     */
    @Transactional
    public void assignScheduleToOpenDay(int scheduleId, Date date) {
    	OpenDay openDay = openDayService.getOpenDayByDate(date);
        if (openDay == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
        else if (openDayService.getOpenDayByDate(date) == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Open day not found");
        }
    	openDay.setSchedule(getScheduleById(scheduleId));
    	openDayRepository.save(openDay);
    }    

    /**
     * If the schedule exists, assign the schedule to a list of open days
     *
     * @param scheduleId
     * @param employeeList
     */
    @Transactional
    public void assignScheduleToOpenDays(int scheduleId, ArrayList<OpenDay> dateList) {
        if (getScheduleById(scheduleId) == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
        for(OpenDay openDay : dateList) {
        	assignScheduleToOpenDay(scheduleId, openDay.getDate());
        }
    }
    
    /**
     * If the open day exists, assign the schedule of an open day to null
     *
     * @param date
     */
    @Transactional
    public void removeScheduleFromOpenDay(Date date) {
    	OpenDay openDay = openDayService.getOpenDayByDate(date);
    	if (openDay == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Open day not found");
    	}
    	openDay.setSchedule(null);
    	openDayRepository.save(openDay);
    }
    
    /**
     * If the schedule exists, assign the schedule to all shifts
     *
     * @param scheduleId
     */
    @Transactional
    public void assignScheduleToShifts(int scheduleId) {
        if (getScheduleById(scheduleId) == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
        ArrayList<Shift> shiftList = shiftService.getAllShifts();
        for(Shift shift : shiftList) {
        	shift.setSchedule(getScheduleById(scheduleId));
        	shiftRepository.save(shift);
        }
    }
}