package ca.mcgill.ecse.mmss.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.mmss.dao.CommunicationRepository;
import ca.mcgill.ecse.mmss.dao.PersonRepository;
import ca.mcgill.ecse.mmss.dao.ShiftRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.AccountType;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Person;
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
	
	/**
     * create a Visitor Account
     * 
     * @author Saviru Perera
     * @param firstName, lastName, userName, passWord
     * @return A Visitor Account object, or exceptions indicating invalid input information
     */
	@Transactional
	public Visitor createVisitor(String firstName, String lastName, String userName, String passWord) {
		
		Visitor existUser = visitorRepository.findVisitorByUsername(userName);
		if (existUser!=null) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is already taken. Please enter another username.");
		}
		
		if (!checkValidUser(userName)) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is an invalid email address. Please enter another username.");
		}
		
		if(!checkValidPassword(passWord)) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.");
		}
		
		// all tests should have passed now
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
     * create an additional Visitor Account for a person who already has a visitor account
     * 
     * @author Saviru Perera
     * @param userName, newUserName, newPassWord
     * @return A new Visitor Account object, or exceptions indicating invalid input information
     */
	@Transactional
	public AccountType createAdditionalVisitor(String userName, String newUserName, String newPassWord) {
		
		Visitor existingVisitor = visitorRepository.findVisitorByUsername(userName);
		if (existingVisitor==null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no visitor with this username.");
		}
		
		if (!checkValidUser(newUserName)) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is an invalid email address. Please enter another username.");
		}
		
		if (!checkValidPassword(newPassWord)) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.");
		}
		
		// all tests should have passed by now
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
     * get a specific visitor account
     * 
     * @author Saviru Perera
     * @param username
     * @return A Visitor Account object, or exceptions indicating invalid input information
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
     * update the username for a Visitor Account to a new username
     * 
     * @author Saviru Perera
     * @param username, newUser
     * @return A modified Visitor Account object, or exceptions indicating invalid input information
     */
	@Transactional
	public Visitor updateVisitorUsername(String username, String newUser) {
		Visitor visitor = visitorRepository.findVisitorByUsername(username);
		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such visitor account with this username.");
		}
		
		Visitor existVisit = visitorRepository.findVisitorByUsername(newUser);
		if (existVisit!=null) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is already taken. Please enter another username.");
		}
		
		if (!checkValidUser(newUser)) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The username entered is an invalid email address. Please enter another username.");
		}
		
		// all tests should have passed, so it is a valid username
		visitor.setUsername(newUser);
		visitorRepository.save(visitor);
		return visitor;
	}
	
	/**
     * update a Visitor Account password to a new one
     * 
     * @author Saviru Perera
     * @param username, oldPass, newPass
     * @return A modified Visitor Account object, or exceptions indicating invalid input information
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
		
		if (!checkValidPassword(newPass)) {
			throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is invalid. Please make sure to include one uppercase letter and one digit and make sure it is at least 8 characters long.");
		}
		
		visitor.setPassword(newPass);
		visitorRepository.save(visitor);
		return visitor;
	}
	
	/**
     * update a visitor account balance to a new one
     * 
     * @author Saviru Perera
     * @param username, newBalance
     * @return A modified Visitor Account object, or exceptions indicating invalid input information
     */
	@Transactional
	public Visitor updateVisitorBalance(String username, int newBalance) {
		Visitor visitor = visitorRepository.findVisitorByUsername(username);
		if (visitor == null) {
			throw new MmssException(HttpStatus.NOT_FOUND, "There is no such visitor account with this username.");
		}
		visitor.setBalance(newBalance);
		visitorRepository.save(visitor);
		return visitor;
	}
	
	/**
     * delete a Visitor Account from the system
     * 
     * @author Saviru Perera
     * @param username
     * @return void
     */
	@Transactional
    public void deleteVisitor(String username) {

        Visitor visitor = visitorRepository.findVisitorByUsername(username);
        if (visitor == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The visitor with this username was not found");
        // calls the repository to delete the visitor
        visitorRepository.deleteById(visitor.getUsername());
    }
	
	/**
     * get all visitor Accounts belonging to a Person
     * 
     * @author Saviru Perera
     * @param personID
     * @return An arraylist of all visitor accounts belonging to a person or exceptions indicating invalid input
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
     * get all visitor accounts belonging to a communication
     * 
     * @author Saviru Perera
     * @param communicationID
     * @return An arraylist of all visitors belonging to a communication 
     */
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
	
	/**
     * get all visitor accounts in the system
     * 
     * @author Saviru Perera
     * @param 
     * @return An arraylist of all visitor accounts in the system
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
     * @param userInputName
     * @return boolean indicating whether or not the entered username is valid
     */
	private boolean checkValidUser (String userInputName) {
		for (int i=0; i<userInputName.length(); i++) {
			if (userInputName.charAt(i) == '@') {
				return true;
			}
		}
		return false;
	}
	
	/**
     * helper method to check if password is valid
     * 
     * @author Saviru Perera
     * @param inputPassword
     * @return boolean indicating whether or not the entered password is valid
     */
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
