package ca.mcgill.ecse.mmss.controller;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse.mmss.dto.ScheduleDto;
import ca.mcgill.ecse.mmss.dto.ShiftDto;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.service.ShiftService;

@RestController
@RequestMapping ("/shift")
public class ShiftController {
    @Autowired
    private ShiftService shiftService;
    
    /**
     * Get shift with a specific shiftTime value
     *
     * @param shiftTime a shift's assigned time
     * @return a response entity with the ShiftDto {@link ScheduleDto} instance and the HttpStatus
     */
    @GetMapping({"/{shiftTime}", "/{shiftTime}/"})
    public ResponseEntity<ShiftDto> getShiftByShiftTime(@PathVariable Shift.ShiftTime shiftTime) {
        Shift shift = shiftService.getShiftByShiftTime(shiftTime);
        return new ResponseEntity<ShiftDto>(new ShiftDto(shift), HttpStatus.OK);
    }

    /**
     * Get all shifts available
     *
     * @return a response entity with an array list of ShiftDto {@link ScheduleDto} instances and the HttpStatus
     */
    @GetMapping
    public ResponseEntity<ArrayList<ShiftDto>> getAllShifts() {
        ArrayList<Shift> shifts = shiftService.getAllShifts();
        // DTOs
        ArrayList<ShiftDto> shiftDTOs = new ArrayList<>();
        for (Shift shift : shifts) {
            shiftDTOs.add(new ShiftDto(shift));
        }
        return new ResponseEntity<ArrayList<ShiftDto>>(shiftDTOs, HttpStatus.OK);
    }
    
    /**
     * Get shift of an employee
     *
     * @param shiftTime a shift's assigned time
     * @return a response entity with the ShiftDto {@link ScheduleDto} instance and the HttpStatus
     */
    @GetMapping({"/username", "/username/"})
    public ResponseEntity<ShiftDto> getShiftFromEmployee(@RequestParam String username) {
        Shift shift = shiftService.getShiftFromEmployee(username);
        return new ResponseEntity<ShiftDto>(new ShiftDto(shift), HttpStatus.OK);
    }
    
    /**
     * Assign a shift to an employee
     *
     * @param shiftId the shift's primary key
     * @param username the employee's primary key
     * @return a response entity with a message and the HttpStatus
     */
    @PutMapping
    public ResponseEntity<String> assignShiftToEmployee(@RequestParam int shiftId, @RequestParam String username) {
        shiftService.assignShiftToEmployee(shiftId, username);
        return new ResponseEntity<String>("Shift successfully assigned.", HttpStatus.OK);
    }

    /**
     * Assign a shift to a list of employees
     *
     * @param shiftId the shift's primary key
     * @param employeeList the list of employees
     * @return a response entity with a message and the HttpStatus
     */
    @PutMapping({"/assignToAll", "/assignToAll/"})
    public ResponseEntity<String> assignShiftToEmployees(@RequestParam int shiftId, @RequestParam ArrayList<Employee> employeeList) {
        shiftService.assignShiftToEmployees(shiftId, employeeList);
        return new ResponseEntity<String>("Shift successfully assigned.", HttpStatus.OK);
    }
    
    /**
     * Remove a shift from an employee
     *
     * @param username the employee's primary key
     * @return a response entity with a message and the HttpStatus
     */
    @DeleteMapping({"/{username}", "/{username}/"})
    public ResponseEntity<String> removeShiftFromEmployee(@PathVariable String username) {
        shiftService.removeShiftFromEmployee(username);
        return new ResponseEntity<String>("Artefact successfully removed", HttpStatus.OK);
    }
}
