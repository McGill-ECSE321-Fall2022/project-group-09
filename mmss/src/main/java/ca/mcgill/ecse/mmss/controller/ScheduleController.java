package ca.mcgill.ecse.mmss.controller;
import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse.mmss.dto.ScheduleDto;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Schedule;
import ca.mcgill.ecse.mmss.service.ScheduleService;

@RestController
@RequestMapping ("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    

    /**
     * Get a schedule by its primary key
     *
     * @param id the schedule's primary key
     * @return a response entity with the ScheduleDto instance and the HttpStatus
     */
    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable int id) {
        Schedule schedule = scheduleService.getScheduleById(id);
        return new ResponseEntity<ScheduleDto>(new ScheduleDto(schedule.getScheduleId()), HttpStatus.OK);
    }
    
    /**
     * Create new schedule based on an input request
     *
     * @param request a ScheduleDto instance {@link ScheduleDto}
     * @return a response entity with the ScheduleDto ArrayList and the HttpStatus
     */
    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule() {
        // create the schedule
        Schedule schedule = scheduleService.createSchedule();
        // return them in the response entity
        return new ResponseEntity<ScheduleDto>(new ScheduleDto(schedule.getScheduleId()), HttpStatus.CREATED);
    }
    
    /**
     * Move a schedule to another openDay
     *
     * @param scheduleId the schedule's primary key
     * @param date the openDay's primary key
     * @return a response entity with a message and the HttpStatus
     */
    @PutMapping({"/assign", "/assign/"})
    public ResponseEntity<String> assignScheduleToOpenDay(@RequestParam int scheduleId, @RequestParam Date date) {
        scheduleService.assignScheduleToOpenDay(scheduleId, date);
        return new ResponseEntity<String>("Schedule successfully assigned.", HttpStatus.OK);
    }

    /**
     * Move a schedule to other openDays
     *
     * @param scheduleId the schedule's primary key
     * @param openDayList the list of openDays
     * @return a response entity with a message and the HttpStatus
     */
    @PutMapping({"/assign", "/assign/"})
    public ResponseEntity<String> assignScheduleToOpenDays(@RequestParam int scheduleId, @RequestParam ArrayList<OpenDay> openDayList) {
        scheduleService.assignScheduleToOpenDays(scheduleId, openDayList);
        return new ResponseEntity<String>("Schedule successfully assigned.", HttpStatus.OK);
    }

    /**
     * Remove a schedule from an open day
     *
     * @param date the open day's primary key
     * @return a response entity with a message and the HttpStatus
     */
    @DeleteMapping({"/{date}", "/{date}/"})
    public ResponseEntity<String> removeScheduleFromOpenDay(@PathVariable Date date) {
        scheduleService.removeScheduleFromOpenDay(date);
        return new ResponseEntity<String>("Artefact successfully removed", HttpStatus.OK);
    }
    
    /**
     * Assign schedule to all shifts
     *
     * @param scheduleId the schedule's primary key
     * @return a response entity with a message and the HttpStatus
     */
    @PutMapping({"/assign", "/assign/"})
    public ResponseEntity<String> assignScheduleToShifts(@RequestParam int scheduleId) {
        scheduleService.assignScheduleToShifts(scheduleId);
        return new ResponseEntity<String>("Schedule successfully assigned.", HttpStatus.OK);
    }
}
