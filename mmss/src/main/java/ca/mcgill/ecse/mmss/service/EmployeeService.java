package ca.mcgill.ecse.mmss.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.EmployeeRepository;
import ca.mcgill.ecse.mmss.dao.ShiftRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Shift.ShiftTime;

@Service
public class EmployeeService {
	
	@Autowired
	PersonRepository personRepository;
	@Autowired
	CommunicationRepository communicationRepository;
	@Autowired
	ShiftRepository shiftRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Transactional
	public Employee createEmployee(String firstName, String lastName, String userName, ShiftTime shiftTime, String phoneNumber) {
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		personRepository.save(person);
		
		Communication communication = new Communication();
		communicationRepository.save(communication);
		
		Shift shift = new Shift();
		shift.setShiftTime(shiftTime);
		shiftRepository.save(shift);
		
		if (phoneNumber.length()!=12) {
			throw new MmssException(HttpStatus.NOT_FOUND, "Please enter a valid phone number.");
		}
		
		Employee employee = new Employee();
		employee.setPhoneNumber(phoneNumber);
		employee.setCommunication(communication);
		employee.setPerson(person);
		employee.setShift(shift);
		employeeRepository.save(employee);
		return employee;
	}
	
	@Transactional
	public Employee getEmployeeByUsername(String username){
		Employee employee = employeeRepository.findEmployeeByUsername(username);
		if (employee == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such employee with this username.");
		}
		return employee;
	}
	
	@Transactional
	public Employee updateEmployeeUsername(String username, String newUser) {
		Employee employee = employeeRepository.findEmployeeByUsername(username);
		if (employee == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such employee account with this username.");
		}
		employee.setUsername(newUser);
		employeeRepository.save(employee);
		return employee;
	}
	
	@Transactional
	public Employee updateEmployeePassword(String username, String newPass) {
		Employee employee = employeeRepository.findEmployeeByUsername(username);
		if (employee == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such employee account with this password.");
		}
		employee.setPassword(newPass);
		employeeRepository.save(employee);
		return employee;
	}
	
	@Transactional
	public Employee updateEmployeePhoneNumber(String username, String newPhoneNumber) {
		Employee employee = employeeRepository.findEmployeeByUsername(username);
		if (employee == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such employee account with this username.");
		}
		if (newPhoneNumber.length()!=12) {
			throw new MmssException(HttpStatus.NOT_FOUND, "Enter a valid phone number.");
		}
		employee.setPhoneNumber(newPhoneNumber);
		employeeRepository.save(employee);
		return employee;
	}
	
	@Transactional
    public void deleteEmployee(String username) {

        Employee employee = employeeRepository.findEmployeeByUsername(username);
        if (employee == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The employee with this username was not found");
        // calls the repository to delete the employee
        employeeRepository.deleteById(employee.getUsername());
    }
	
	
	@Transactional
    public ArrayList<Employee> getAllEmployeesByShift(int shiftID) {

        Shift shift = shiftRepository.findShiftByShiftId(shiftID);
        if (shift == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The shift with this id was not found");
        }

        // use the repository
        ArrayList<Employee> allEmployees = employeeRepository.findByShift(shift);

        return allEmployees;
    }
	
	@Transactional
	public List<Employee> getAllEmployees(){
		ArrayList<Employee> allEmployees = employeeRepository.findAll();

        return allEmployees;
	}

}