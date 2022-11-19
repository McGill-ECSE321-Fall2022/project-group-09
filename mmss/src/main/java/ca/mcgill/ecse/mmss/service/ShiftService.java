package ca.mcgill.ecse.mmss.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse.mmss.dao.EmployeeRepository;
import ca.mcgill.ecse.mmss.dao.ShiftRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Schedule;
import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Shift.ShiftTime;

@Service
public class ShiftService {
	@Autowired
    private ShiftRepository shiftRepository;
	@Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private EmployeeService employeeService;

    /**
     * Get a shift by its primary key
     *
     * @author Athmane Benarous
     * @param id
     * @return the shift or an exception
     */
    @Transactional
    public Shift getShiftById(int id) {
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
    
    /**
     * If the employee exists, get the shift of an employee
     *
     * @param username
     * @return Shift
     */
    @Transactional
    public Shift getShiftFromEmployee(String username) {
    	Employee employee = employeeService.getEmployeeByUsername(username);
    	if (employee == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Employee not found");
        }
    	else if (employee.getShift() == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Employee shift not found");
        }
    	return employee.getShift();
    }
    
    /**
     * If both the shift and the employee exist, assign the shift to a single employee
     *
     * @param shiftId
     * @param employee username
     */
    @Transactional
    public void assignShiftToEmployee(int shiftId, String username) {
    	Employee employee = employeeService.getEmployeeByUsername(username);
        if (getShiftById(shiftId) == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Shift not found");
        }
        else if (employee == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Employee not found");
        }
    	employee.setShift(getShiftById(shiftId));
    	employeeRepository.save(employee);
    }

    /**
     * If the shift exists, assign the shift to a list of employees
     *
     * @param shiftId
     * @param employeeList
     */
    @Transactional
    public void assignShiftToEmployees(int shiftId, ArrayList<Employee> employeeList) {
        if (getShiftById(shiftId) == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Shift not found");
        }
        for(Employee employee : employeeList) {
        	assignShiftToEmployee(shiftId, employee.getUsername());
        }
    }
    

    /**
     * If the employee exists, assign the shift of an employee to null
     *
     * @param username
     */
    @Transactional
    public void removeShiftFromEmployee(String username) {
    	Employee employee = employeeService.getEmployeeByUsername(username);
    	if (employee == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Employee not found");
        }
    	employee.setShift(null);
    	employeeRepository.save(employee);
    }
}
