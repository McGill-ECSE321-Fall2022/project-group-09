package ca.mcgill.ecse.mmss.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.EmployeeRepository;
import ca.mcgill.ecse.mmss.dao.ShiftRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.AccountType;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Shift.ShiftTime;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;

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
	public Employee employee(int personID, int communicationID, String phoneNumber, int shiftID) {
		if (phoneNumber.length()!=12) { //xxx-xxx-xxxx format
			throw new MmssException(HttpStatus.NOT_FOUND, " Please enter a valid phone number.");
		} 
		Person person = personRepository.findPersonByPersonId(personID);
		if (person == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such person with this ID.");
		}
		
		Communication communication = communicationRepository.findCommunicationByCommunicationId(communicationID);
		if (communication == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such communication with this ID.");
		}
		
		Shift shift = shiftRepository.findShiftByShiftId(shiftID);
		if (shift == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such shift with this ID.");
		}
		
		// all tests should have passed 
		Employee employee = new Employee();
		employee.setPhoneNumber(phoneNumber);
		employee.setPerson(person);
		employee.setCommunication(communication);
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
	public Employee getEmployeeByPassword(String password){
		Employee employee = employeeRepository.findEmployeeByPassword(password);
		if (employee == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such employee with this password.");
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
    public ArrayList<Employee> getAllEmployeesByPerson(int personID) {

        Person person = personRepository.findPersonByPersonId(personID);
        if (person == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The person with this id was not found");
        }

        // use the repository
        ArrayList<Employee> allEmployees = employeeRepository.findByPerson(person);

        return allEmployees;
    }
	
	@Transactional
    public ArrayList<Employee> getAllEmployeesByCommunication(int communicationID) {

        Communication communication = communicationRepository.findCommunicationByCommunicationId(communicationID);
        if (communication == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The communication with this id was not found");
        }

        // use the repository
        ArrayList<Employee> allEmployees = employeeRepository.findByCommunication(communication);

        return allEmployees;
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
		return toList(employeeRepository.findAll());
	}

	private List<Employee> toList(Iterable<Employee> findAll) {
		// TODO Auto-generated method stub
		return null;
	}
}