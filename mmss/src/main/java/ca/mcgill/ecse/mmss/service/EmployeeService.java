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
	public Employee createEmployee(String firstName, String lastName, String userName, String phoneNumber) {
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		personRepository.save(person);
		
		Communication communication = new Communication();
		communicationRepository.save(communication);
		
		// check for valid phone number
		if (phoneNumber.length()!=12) {
			throw new MmssException(HttpStatus.NOT_FOUND, "Please enter a valid phone number.");
		}
		
		// check for valid username
		int validUser = 0;
		for (int i=0; i<userName.length(); i++) {
			if (userName.charAt(i) == '@') {
				validUser++;
			}
		}
		if (validUser==0) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is not a valid email address. Please enter another username.");
		}
		
		Employee employee = new Employee();
		employee.setUsername(userName);
		employee.setPhoneNumber(phoneNumber);
		employee.setCommunication(communication);
		employee.setPerson(person);
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
		// check for valid username
		int validUser = 0;
		for (int i=0; i<newUser.length(); i++) {
			if (newUser.charAt(i) == '@') {
				validUser++;
			}
		}
		
		if (validUser==0) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is not a valid email address. Please enter another username.");
		}
		employee.setUsername(newUser);
		employeeRepository.save(employee);
		return employee;
	}
	
	@Transactional
	public Employee updateEmployeePassword(String username, String oldPass, String newPass) {
		Employee employee = employeeRepository.findEmployeeByUsername(username);
		if (employee == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such employee account with this password.");
		}
		if (!oldPass.equals(employee.getPassword())) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is not correct. Please enter correct password.");
		}
		if (newPass.length()<8) { // check for acceptable password length
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is not a valid password. Please enter another password.");
		}
		
		int validUpper = 0;
		int validDigit = 0;
		for (int i=0; i<newPass.length(); i++) {
			char cur = newPass.charAt(i);
			if((cur>=65&&cur<=90)) { // check if character is an upperCase letter 
				validUpper++;
			}
			if (cur>=48&&cur<=57) {
				validDigit++;
			}
		}
		if (validDigit==0 || validUpper==0) { 
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is not a valid password. Please enter another password.");
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