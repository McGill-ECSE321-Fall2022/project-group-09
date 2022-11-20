package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Visitor;

public class VisitorDto {
	private double balance;
	private String username;
	private String password;
	private int personId;
	private int communicationId;

	/**
	 * Null constructor
	 * @author Shyam Desai
	 */
	public VisitorDto() {}
	
	/**
	 * Constructor that takes in a visitor as an argument
	 * 
	 * @author Shidan Javaheri
	 * @param visitor
	 */
	public VisitorDto(Visitor visitor) {
		this.balance = visitor.getBalance();
		this.username = visitor.getUsername();
		this.password = visitor.getPassword();
		this.personId = visitor.getPerson().getPersonId();
		this.communicationId = visitor.getCommunication().getCommunicationId();
	}

	/**
	 * Constructor that takes in separate arguments
	 * 
	 * @author Shidan Javaheri
	 * @param balance
	 * @param username
	 * @param password
	 * @param person
	 * @param communicationId
	 */
	public VisitorDto(int balance, String username, int personId, int communicationId) {
		this.balance = balance;
		this.username = username;
		this.personId = personId;
		this.communicationId = communicationId;
	}

	public double getBalance() {
		return balance;
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public int getCommunicationId() {
		return communicationId;
	}

	public void setCommunicationId(int communicationId) {
		this.communicationId = communicationId;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
