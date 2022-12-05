package ca.mcgill.ecse.mmss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse.mmss.dto.ScheduleDto;
import ca.mcgill.ecse.mmss.model.Schedule;
import ca.mcgill.ecse.mmss.service.ScheduleService;

@RestController
@CrossOrigin
@RequestMapping ("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    
    /**
     * Get the current schedule
     *
     * @return a response entity with the ScheduleDto instance {@link ScheduleDto} and the HttpStatus
     */
    @GetMapping
    public ResponseEntity<ScheduleDto> getSchedule() {
        Schedule schedule = scheduleService.getSchedule();
        return new ResponseEntity<ScheduleDto>(new ScheduleDto(schedule.getScheduleId()), HttpStatus.OK);
    }
}
