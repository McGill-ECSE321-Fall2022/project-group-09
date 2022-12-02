package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Visitor;

public class VisitorDto {
	private String userName;
	private String firstName;
	private String lastName;
	private double balance;


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
		this.userName = visitor.getUsername();
		this.firstName = visitor.getPerson().getFirstName();
		this.lastName = visitor.getPerson().getLastName();
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
	public VisitorDto(int balance, String username, String firstName, String lastName) {
		this.balance = balance;
		this.userName = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public double getBalance() {
		return balance;
	}

	public String getUserName() {
		return userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void setUserName(String username) {
		this.userName = username;
	}


}
