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
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Shift;

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
	@Autowired
	VisitorRepository visitorRepository;
	
	@Transactional
	public Employee createEmployee(String firstName, String lastName, String userName, String phoneNumber) {
		
		Employee existEmp = employeeRepository.findEmployeeByUsername(userName);
		if (existEmp!=null) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is already taken. Please enter another username.");
		}
				
		if(!checkValidUser(userName)) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is an invalid email address. Please enter another username.");
		}
		
		// check for valid phone number
		if (phoneNumber.length()!=12) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "Please enter a valid phone number in the format xxx-xxx-xxxx.");
		}

		// pass all tests before creating account
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		personRepository.save(person);
		
		Communication communication = new Communication();
		communicationRepository.save(communication);
		
		Employee employee = new Employee();
		employee.setUsername(userName);
		employee.setPhoneNumber(phoneNumber);
		employee.setCommunication(communication);
		employee.setPerson(person);
		employeeRepository.save(employee);
		return employee;
	}
	
	@Transactional
	public Visitor createVisitorForEmployee(String userName, String newUserName, String newPassword) {
		Employee employee = employeeRepository.findEmployeeByUsername(userName);
		
		if (employee == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no employee account with that username.");
		}
		
		if (!checkValidUser(newUserName)) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is an invalid email address. Please enter another username.");
		}
		
		if (!checkValidPassword(newPassword)) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.");
		}
		
		// all tests should have passed by now
		Person person = employee.getPerson();
		
		// create a communication
		Communication communication = new Communication();
		communicationRepository.save(communication);
		
		Visitor newVisitor = new Visitor();
		newVisitor.setUsername(newUserName);
		newVisitor.setPassword(newPassword);
		newVisitor.setCommunication(communication);
		newVisitor.setPerson(person);
		visitorRepository.save(newVisitor);
		return newVisitor;
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
		
		Employee currentEmp = employeeRepository.findEmployeeByUsername(newUser);
		if (currentEmp!=null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "This username is already taken. Please enter another username.");
		}
		
		if(!checkValidUser(newUser)) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is an invalid email address. Please enter another username.");
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
		
		if (!checkValidPassword(newPass)) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.");
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
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "Enter a valid phone number in the format xxx-xxx-xxxx.");
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
	
	// helper method that checks if a username is valid
	private boolean checkValidUser (String userInputName) {
		for (int i=0; i<userInputName.length(); i++) {
			if (userInputName.charAt(i) == '@') {
				return true;
			}
		}
		return false;
	}
		
	// helper method that checks if a password is valid
	private boolean checkValidPassword(String inputPassword) {
		boolean result;
		int validUpper = 0;
		int validDigit = 0;
		for (int i=0; i<inputPassword.length(); i++) {
			char cur = inputPassword.charAt(i);
			if((cur>=65&&cur<=90)) { // check if character is an upperCase letter 
				validUpper++;
			}
			if (cur>=48&&cur<=57) {
				validDigit++;
			}
		}
			
		if (validDigit==0 || validUpper==0 || inputPassword.length()<8) { 
			result = false;
		} else {
			result = true;
		}
		return result;
	}
}