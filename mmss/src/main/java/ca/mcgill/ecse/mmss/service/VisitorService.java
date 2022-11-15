package ca.mcgill.ecse.mmss.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.EmployeeRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.ShiftRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.AccountType;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Shift.ShiftTime;
import ca.mcgill.ecse.mmss.model.Visitor;

@Service
public class VisitorService {
	
	@Autowired
	PersonRepository personRepository;
	@Autowired
	CommunicationRepository communicationRepository;
	@Autowired
	ShiftRepository shiftRepository;
	@Autowired
	VisitorRepository visitorRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	

	@Transactional
	public Visitor createVisitor(String firstName, String lastName, String userName, int balance) {
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		personRepository.save(person);
		
		Communication communication = new Communication();
		communicationRepository.save(communication);
		
		Visitor visitor = new Visitor();
		visitor.setUsername(userName);
		visitor.setBalance(balance);
		visitor.setCommunication(communication);
		visitor.setPerson(person);
		visitorRepository.save(visitor);
		return visitor;
	}
	
	@Transactional
	public AccountType createAdditionalAccount(String accType, String userName, String newUserName, int balance, String phoneNumber, ShiftTime shiftTime) {
		Visitor visitor = visitorRepository.findVisitorByUsername(userName);
		Employee employee = employeeRepository.findEmployeeByUsername(userName);
		if (visitor == null && employee == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such account with this username.");
		}
		
		
		Person person = visitor.getPerson();
		
		Communication communication = new Communication();
		communicationRepository.save(communication);
		AccountType result;
		
		if (accType.equals("Visitor")) {
			Visitor newVisitor = new Visitor();
			newVisitor.setUsername(newUserName);
			newVisitor.setBalance(balance);
			newVisitor.setCommunication(communication);
			newVisitor.setPerson(person);
			visitorRepository.save(newVisitor);
			result = newVisitor;
		} else {
			if (employee == null) {
				Shift shift = new Shift();
				shift.setShiftTime(shiftTime);
				shiftRepository.save(shift);
				
				if (phoneNumber.length()!=12) {
					throw new MmssException(HttpStatus.NOT_FOUND, "Please enter a valid phone number.");
				}
				Employee newEmployee = new Employee();
				newEmployee.setUsername(newUserName);
				newEmployee.setPhoneNumber(phoneNumber);
				newEmployee.setCommunication(communication);
				newEmployee.setPerson(person);
				employeeRepository.save(newEmployee);
				result = newEmployee;
			} else {
				throw new MmssException(HttpStatus.NOT_FOUND, "Cannot create multiple employee accounts.");
			}
		}
		return result;
	}
	
	@Transactional
	public Visitor getVisitorByUsername(String username){
		Visitor visitor = visitorRepository.findVisitorByUsername(username);
		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such visitor account with this username.");
		}
		return visitor;
	}
	
	@Transactional
	public Visitor updateVisitorUsername(String username, String newUser) {
		Visitor visitor = visitorRepository.findVisitorByUsername(username);
		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such visitor account with this username.");
		}
		visitor.setUsername(newUser);
		visitorRepository.save(visitor);
		return visitor;
	}
	
	@Transactional
	public Visitor updateVisitorPassword(String username, String newPass) {
		Visitor visitor = visitorRepository.findVisitorByUsername(username);
		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such visitor account with this password.");
		}
		visitor.setPassword(newPass);
		visitorRepository.save(visitor);
		return visitor;
	}
	
	@Transactional
	public Visitor updateVisitorBalance(String username, int newBalance) {
		Visitor visitor = visitorRepository.findVisitorByUsername(username);
		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such employee account with this username.");
		}
		visitor.setBalance(newBalance);
		visitorRepository.save(visitor);
		return visitor;
	}
	
	@Transactional
    public void deleteVisitor(String username) {

        Visitor visitor = visitorRepository.findVisitorByUsername(username);
        if (visitor == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The visitor with this username was not found");
        // calls the repository to delete the visitor
        visitorRepository.deleteById(visitor.getUsername());
    }
	
	@Transactional
    public ArrayList<Visitor> getAllVisitorsByPerson(int personID) {

        Person person = personRepository.findPersonByPersonId(personID);
        if (person == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The person with this id was not found");
        }

        // use the repository
        ArrayList<Visitor> allVisitors = visitorRepository.findByPerson(person);

        return allVisitors;
    }
	
	@Transactional
    public ArrayList<Visitor> getAllVisitorsByCommunication(int communicationID) {

        Communication communication = communicationRepository.findCommunicationByCommunicationId(communicationID);
        if (communication == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "The communication with this id was not found");
        }

        // use the repository
        ArrayList<Visitor> allVisitors = visitorRepository.findByCommunication(communication);

        return allVisitors;
    }
	
	@Transactional
	public List<Visitor> getAllVisitors(){
		ArrayList<Visitor> allVisitors = visitorRepository.findAll();

        return allVisitors;
	}
}