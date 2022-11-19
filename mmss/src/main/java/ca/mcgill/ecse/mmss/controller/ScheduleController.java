package ca.mcgill.ecse.mmss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse.mmss.dto.ScheduleDto;
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
     * @return a response entity with the ScheduleDto instance {@link ScheduleDto} and the HttpStatus
     */
    @GetMapping
    public ResponseEntity<ScheduleDto> getSchedule() {
        Schedule schedule = scheduleService.getSchedule();
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
}
