
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

/**
 * Business logic for the Employee class
 */
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


	/**
	 * Create an employee
	 *
	 * @param firstName the employee's first name
	 * @param lastName the employee's last name
	 * @param userName the employee's username
	 * @param password the employee's password
	 * @param phoneNumber the employee's phone number
	 * @return a employee instance
	 * @throws MmssException
	 */
	@Transactional
	public Employee createEmployee(String firstName, String lastName, String userName, String password, String phoneNumber) {
		if(checkValidUser(userName)==false) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is an invalid email address. Please enter another username.");
		}
		Employee existEmp = employeeRepository.findEmployeeByUsername(userName);
		if (existEmp!=null) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is taken. Please enter another username.");
		}
		// check for valid phone number
		if ( phoneNumber == null) { 
			throw new MmssException (HttpStatus.NOT_ACCEPTABLE, "Please enter a phone number");
		}
		if (phoneNumber.length()!=12) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "Please enter a valid phone number in the format xxx-xxx-xxxx.");
		}
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
		employee.setPassword(password);
		Employee savedEmployee = employeeRepository.save(employee);
		return savedEmployee;
	}

	/**
	 * Create a visitor account for an employee
	 *
	 * @param userName the employee's old username
	 * @param newUserName the employee's new username
	 * @param newPassword the employee's new password
	 * @return a employee instance
	 * @throws MmssException
	 */
	@Transactional
	public Visitor createVisitorForEmployee(String userName, String newUserName, String newPassword) {
		Employee employee = employeeRepository.findEmployeeByUsername(userName);
		if (employee == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no employee account with that username.");
		}
		if (checkValidUser(newUserName)==false) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is an invalid email address. Please enter another username.");
		}
		Visitor existVisit = visitorRepository.findVisitorByUsername(newUserName);
		if (existVisit!=null) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is taken. Please enter another username.");
		}
		if (checkValidPassword(newPassword)==false) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.");
		}
		Person person = employee.getPerson();
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
	
	/**
     * Get an employee by its primary key
     * 
     * @author Saviru Perera
     * @param username the employee's username
     * @return an employee instance
	 * @throws MmssException
     */
	@Transactional
	public Employee getEmployeeByUsername(String username){
		Employee employee = employeeRepository.findEmployeeByUsername(username);
		if (employee == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such employee with this username.");
		}
		return employee;
	}
	
	/**
     * Update an employee username
     * 
     * @author Saviru Perera
     * @param username the employee's old username
	 * @param newUser the employee's new username
	 * @return a employee instance
	 * @throws MmssException
     */
	@Transactional
	public Employee updateEmployeeUsername(String username, String newUser) {
		Employee employee = employeeRepository.findEmployeeByUsername(username);
		if (employee == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such employee account with this username.");
		}
		if(checkValidUser(newUser)==false) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is an invalid email address. Please enter another username.");
		}
		Employee existEmp = employeeRepository.findEmployeeByUsername(newUser);
		if (existEmp!=null) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is taken. Please enter another username.");
		}
		employee.setUsername(newUser);
		employeeRepository.save(employee);
		return employee;
	}
	
	/**
     * Update an employee password
     * 
     * @author Saviru Perera
	 * @param username the employee's username
	 * @param oldPass the employee's old password
	 * @param newPass the employee's new password
	 * @return a employee instance
	 * @throws MmssException
     */
	@Transactional
	public Employee updateEmployeePasswordAndPhone(String username, String oldPass, String newPass, String newPhoneNumber) {
		Employee employee = employeeRepository.findEmployeeByUsername(username);
		if (employee == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such employee account with this password.");
		}
		if (!oldPass.equals(employee.getPassword())) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is not correct. Please enter correct password.");
		}
		if (checkValidPassword(newPass)==false) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.");
		}
		if (newPhoneNumber.length()!=12) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "Enter a valid phone number in the format xxx-xxx-xxxx.");
		}
		employee.setPhoneNumber(newPhoneNumber);
		employee.setPassword(newPass);
		employeeRepository.save(employee);
		return employee;
	}
	
	/**
     * Delete an employee account from the system
     *
	 * @author Saviru Perera
	 * @param username the employee's username
	 * @throws MmssException
     */
	@Transactional
    public void deleteEmployee(String username) {

        Employee employee = employeeRepository.findEmployeeByUsername(username);
        if (employee == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The employee with this username was not found");
        Person person = employee.getPerson();
        int count = 1;
        ArrayList<Visitor> allVisitors = visitorRepository.findAll();
        for (Visitor additionalVisit : allVisitors) {
        	if (additionalVisit.getPerson().equals(person)) count++;
        }
        
        ArrayList<Employee> allEmployees = employeeRepository.findAll();
        for (Employee additionalEmp : allEmployees) {
        	if (additionalEmp.getPerson().equals(person)) count++;
        }
        employeeRepository.deleteById(employee.getUsername());
        if (count==1) {
        	personRepository.deleteById(person.getPersonId());
        }
    }
	
	/**
     * Get all employees assigned to a specific shift
     * 
     * @author Saviru Perera
     * @param shiftID the shift's primary key
	 * @return An arraylist of employee instances
	 * @throws MmssException
     */
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
	
	/**
     * get all employee accounts in the system
     * 
     * @author Saviru Perera
	 * @return an arraylist of employee instances
     */
	@Transactional
	public List<Employee> getAllEmployees(){
		ArrayList<Employee> allEmployees = employeeRepository.findAll();

        return allEmployees;
	}
	
	/**
     * Helper method to check if a username is valid
     * 
     * @author Saviru Perera
     * @param userInputName the employee's username
     * @return a boolean indicating whether the username is valid or not
     */
	public boolean checkValidUser (String userInputName) {
		int validUser = 0;
		for (int i=0; i<userInputName.length(); i++) {
			if (userInputName.charAt(i) == '@') {
				validUser++;
			}
		}
		if (validUser == 1) {
			return true;
		}
		return false;
	}
		
	/**
     * Helper method to check if a password is valid
     * 
     * @author Saviru Perera
     * @param inputPassword the visitor's password
     * @return a boolean indicating whether the password is valid or not
     */
	public boolean checkValidPassword(String inputPassword) {
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
