package ca.mcgill.ecse.mmss.controller;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse.mmss.dto.EmployeeDto;
import ca.mcgill.ecse.mmss.dto.EmployeeRequestDto;
import ca.mcgill.ecse.mmss.dto.VisitorDto;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.service.EmployeeService;

@RestController
@RequestMapping({"/employee", "/employee/"})
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeRequestDto request) {
        // get parameters
		String firstname = request.getFirstName();
		String lastname = request.getLastName();
        String username = request.getUsername();
		String phoneNumber = request.getPhoneNumber();

        // create the object with the service
        Employee persistedEmployee = employeeService.createEmployee(firstname,lastname,username,phoneNumber);

        // return it in the response entity
        return new ResponseEntity<EmployeeDto>(new EmployeeDto(persistedEmployee), HttpStatus.CREATED);

    }
	
	@PostMapping ({"/{addVisitorAccount}", "/{addVIsitorAccount}/"})
    public ResponseEntity<VisitorDto> createAdditionalVisitor(@RequestBody EmployeeRequestDto request) {
        // get parameters
        String username = request.getUsername();
        String newUsername = request.getNewUserName();
		String newPassword = request.getNewPassword();

        // create the object with the service
        Visitor persistedVisitor = employeeService.createVisitorForEmployee(username,newUsername,newPassword);

        // return it in the response entity
        return new ResponseEntity<VisitorDto>(new VisitorDto(persistedVisitor), HttpStatus.CREATED);
    }
	
	@GetMapping({"/{empUsername}", "/{empUsername}/"})
	public ResponseEntity<EmployeeDto> getEmployee(@PathVariable String username) {
        // call service
        Employee retrievedEmployee = employeeService.getEmployeeByUsername(username);
        // return response entity with Dto
        return new ResponseEntity<EmployeeDto>(new EmployeeDto(retrievedEmployee), HttpStatus.OK);
    }
	
	@PutMapping
    public ResponseEntity<EmployeeDto> updateEmployeePasswordAndPhone(@RequestBody EmployeeRequestDto request) {
        // get parameters
        String username = request.getUsername();
        String oldPassword = request.getPassword();
        String newPassword = request.getNewPassword();
        String newPhoneNumber = request.getNewPhoneNumber();
        // call service layer
        Employee updatedEmployee = employeeService.updateEmployeePasswordAndPhone(username, oldPassword, newPassword, newPhoneNumber);

        // return updated Employee as Dto
        return new ResponseEntity<EmployeeDto>(new EmployeeDto(updatedEmployee), HttpStatus.OK);
    }
	
	@DeleteMapping({"/{username}", "/{username}/"})
    public ResponseEntity<String> deleteEmployee(@PathVariable String username) {
        // call service layer
        employeeService.deleteEmployee(username);

        // return updated Employee as Dto
        return new ResponseEntity<String>("Employee successfully deleted", HttpStatus.OK);
    }
	
	@GetMapping
    public ResponseEntity<ArrayList<EmployeeDto>> getAllEmployees() {

        // get all visitors
        ArrayList<Employee> retrievedEmployees = (ArrayList<Employee>) employeeService.getAllEmployees();

        // make Dtos
        ArrayList<EmployeeDto> allEmployeesDto = new ArrayList<>();
        for (Employee employee : retrievedEmployees) {
            allEmployeesDto.add(new EmployeeDto(employee));
        }
        // return the Dtos
        return new ResponseEntity<ArrayList<EmployeeDto>>(allEmployeesDto, HttpStatus.OK);

    }
	
	@GetMapping ({"/byShift", "/byShift/"})
    public ResponseEntity<ArrayList<EmployeeDto>> getAllEmployeesByShift(@RequestParam int id) {

        // get all employees
        ArrayList<Employee> retrievedEmployees = (ArrayList<Employee>) employeeService.getAllEmployeesByShift(id);

        // make Dtos
        ArrayList<EmployeeDto> allEmployeesDto = new ArrayList<>();
        for (Employee employee : retrievedEmployees) {
            allEmployeesDto.add(new EmployeeDto(employee));
        }
        // return the Dtos
        return new ResponseEntity<ArrayList<EmployeeDto>>(allEmployeesDto, HttpStatus.OK);

    }
	
}
