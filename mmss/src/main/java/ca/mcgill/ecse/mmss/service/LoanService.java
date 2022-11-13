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
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.AccountType;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;

@Service
public class LoanService {
	
	@Autowired
	PersonRepository personRepository;
	@Autowired
	CommunicationRepository communicationRepository;
	@Autowired
	VisitorRepository visitorRepository;

	@Transactional
	public Person createPerson(String firstName, String lastName) {
		Person person = new Person();
		person.setFirstName(firstName);
		person.setLastName(lastName);
		personRepository.save(person);
		return person;
	}
	
	@Transactional
	public Person getPerson(int idNum) {
		Person person = personRepository.findPersonByPersonId(idNum);
		return person;
	}
	
	@Transactional
	public Communication createCommunication() {
		Communication communication = new Communication();
		communicationRepository.save(communication);
		return communication;
	}
	
	@Transactional
	public Communication getCommunication(int idNum) {
		Communication communication = communicationRepository.findCommunicationByCommunicationId(idNum);
		return communication;
	}
	
	@Transactional
	public Visitor visitor(Person person, Communication communication, int balance) {
		Visitor visitor = new Visitor();
		visitor.setBalance(balance);
		visitor.setPerson(person);
		visitor.setCommunication(communication);

		visitorRepository.save(visitor);

		return visitor;
	}
	
	@Transactional
	public List<Visitor> getAllVisitors(){
		return toList(visitorRepository.findAll());
	}

	private List<Visitor> toList(Iterable<Visitor> findAll) {
		// TODO Auto-generated method stub
		return null;
	}
	


}
