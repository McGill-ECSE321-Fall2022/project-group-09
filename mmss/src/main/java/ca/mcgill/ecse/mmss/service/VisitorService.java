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
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.AccountType;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Visitor;

/**
 * Business logic for the Visitor class
 */
@Service
public class VisitorService {
	
	@Autowired
	PersonRepository personRepository;
	@Autowired
	CommunicationRepository communicationRepository;
	@Autowired
	VisitorRepository visitorRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	LoginService loginService;

	/**
	 * Create a visitor
	 *
	 * @param firstName the visitor's first name
	 * @param lastName the visitor's last name
	 * @param userName the visitor's username
	 * @param passWord the visitor's password
	 * @return a visitor instance
	 * @throws MmssException
	 */
	@Transactional
	public Visitor createVisitor(String firstName, String lastName, String userName, String passWord) {
		if (checkValidUser(userName)==false) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is an invalid email address. Please enter another username.");
		}
		AccountType existingAccount = loginService.getAccountByUsername(userName);
		if (existingAccount!=null) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is taken. Please enter another username.");
		}
		if(checkValidPassword(passWord)==false) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.");
		}
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		personRepository.save(person);
		Communication communication = new Communication();
		communicationRepository.save(communication);
		Visitor visitor = new Visitor();
		visitor.setUsername(userName);
		visitor.setPassword(passWord);
		visitor.setCommunication(communication);
		visitor.setPerson(person);
		visitorRepository.save(visitor);
		return visitor;
	}

	/**
	 * Create an additional account for a visitor
	 *
	 * @param userName the visitor's old username
	 * @param newUserName the visitor's new username
	 * @param newPassWord the visitor's new password
	 * @return a visitor instance
	 * @throws MmssException
	 */
	@Transactional
	public Visitor createAdditionalVisitor(String userName, String newUserName, String newPassWord) {
		Visitor existingVisitor = visitorRepository.findVisitorByUsername(userName);
		if (existingVisitor==null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no visitor with this username.");
		}
		if (checkValidUser(newUserName)==false) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is an invalid email address. Please enter another username.");
		}
		AccountType existingAccount = loginService.getAccountByUsername(newUserName);
		if (existingAccount!=null) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is taken. Please enter another username.");
		}
		if (checkValidPassword(newPassWord)==false) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.");
		}
		Person person = existingVisitor.getPerson();
		// create a communication
		Communication communication = new Communication();
		communicationRepository.save(communication);
		Visitor newVisitor = new Visitor();
		newVisitor.setUsername(newUserName);
		newVisitor.setPassword(newPassWord);
		newVisitor.setCommunication(communication);
		newVisitor.setPerson(person);
		visitorRepository.save(newVisitor);
		return newVisitor;
	}
	
	/**
     * Get a visitor account by its primary key
     * 
     * @author Saviru Perera
     * @param username the visitor's username
	 * @return a visitor instance
	 * @throws MmssException
     */
	@Transactional
	public Visitor getVisitorByUsername(String username){
		Visitor visitor = visitorRepository.findVisitorByUsername(username);
		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such visitor account with this username.");
		}
		return visitor;
	}

	/**
	 * Update a visitor's username
	 *
	 * @param username the visitor's old username
	 * @param newUser the visitor's new username
	 * @return a visitor instance
	 * @throws MmssException
	 */
	@Transactional
	public Visitor updateVisitorUsername(String username, String newUser) {
		Visitor visitor = visitorRepository.findVisitorByUsername(username);
		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such visitor account with this username.");
		}
		if (checkValidUser(newUser)==false) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is an invalid email address. Please enter another username.");
		}
		AccountType existingAccount = loginService.getAccountByUsername(newUser);
		if (existingAccount!=null) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is taken. Please enter another username.");
		}
		visitor.setUsername(newUser);
		visitorRepository.save(visitor);
		return visitor;
	}

	/**
	 * Update a visitor's password
	 *
	 * @param username the visitor's username
	 * @param oldPass the visitor's old password
	 * @param newPass the visitor's new password
	 * @return a visitor instance
	 * @throws MmssException
	 */
	@Transactional
	public Visitor updateVisitorPassword(String username, String oldPass, String newPass) {
		Visitor visitor = visitorRepository.findVisitorByUsername(username);
		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such visitor account with this username.");
		}
		if (!oldPass.equals(visitor.getPassword())) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is not correct. Please enter correct password.");
		}
		if (checkValidPassword(newPass)==false) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.");
		}
		visitor.setPassword(newPass);
		visitorRepository.save(visitor);
		return visitor;
	}

	/**
	 * Update a visitor's balance
	 *
	 * @param username the visitor's username
	 * @param newBalance the visitor's updated balance
	 * @return a visitor instance
	 * @throws MmssException
	 */
	@Transactional
	public Visitor updateVisitorBalance(String username, double newBalance) {
		Visitor visitor = visitorRepository.findVisitorByUsername(username);
		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such visitor account with this username.");
		}
		visitor.setBalance(newBalance);
		visitorRepository.save(visitor);
		return visitor;
	}
	
	/**
     * Delete a visitor from the system
     * 
     * @author Saviru Perera
     * @param username the visitor's username
	 * @throws MmssException
     */
	@Transactional
    public void deleteVisitor(String username) {

        Visitor visitor = visitorRepository.findVisitorByUsername(username);
        if (visitor == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The visitor with this username was not found");
        // calls the repository to delete the visitor
        Person person = visitor.getPerson();
        int count = 1;
        ArrayList<Visitor> allVisitors = visitorRepository.findAll();
        for (Visitor additionalVisit : allVisitors) {
        	if (additionalVisit.getPerson().equals(person)) count++;
        }
        
        ArrayList<Employee> allEmployees = employeeRepository.findAll();
        for (Employee additionalEmp : allEmployees) {
        	if (additionalEmp.getPerson().equals(person)) count++;
        }
        visitorRepository.deleteById(visitor.getUsername());
        if (count==1) {
        	personRepository.deleteById(person.getPersonId());
        }
    }
	
	/**
     * Get all visitor accounts belonging to a person
     * 
     * @author Saviru Perera
     * @param personID the person's primary key
     * @return An arraylist of visitor instances
	 * @throws MmssException
     */
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
	
	/**
     * Get all visitor accounts in the system
     * 
     * @author Saviru Perera
     * @return an arraylist of visitor instances
     */
	@Transactional
	public List<Visitor> getAllVisitors(){
		ArrayList<Visitor> allVisitors = visitorRepository.findAll();
        return allVisitors;
	}
	
	/**
     * helper method to check if username is valid
     * 
     * @author Saviru Perera
     * @param userInputName the visitor's username
     * @return boolean indicating whether the entered username is valid
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
     * helper method to check if password is valid
     * 
     * @author Saviru Perera
     * @param inputPassword the visitor's password
     * @return boolean indicating whether or not the entered password is valid
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
