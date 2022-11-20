package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Employee;

public class EmployeeDto {
	private String phoneNumber;
	private String userName;
	private String password;
	private int personId;
	private int communicationId;

	/**
	 * Null constructor
	 * @author Shyam Desai
	 */
	public EmployeeDto() {}
	
	/**
	 * Constructor that takes in an employee as the argument
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param employee
	 */
	public EmployeeDto(Employee employee) {
		this.phoneNumber = employee.getPhoneNumber();
		this.userName = employee.getUsername();
		this.password = employee.getPassword();
		this.personId = employee.getPerson().getPersonId();
		this.communicationId = employee.getCommunication().getCommunicationId();
	}

	/**
	 * Constructor that takes in separate arguments
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param phoneNumber
	 * @param userName
	 * @param password
	 * @param person
	 * @param communication
	 */
	public EmployeeDto(String phoneNumber, String userName, int personId, int communicationId) {
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.personId = personId;
		this.communicationId = communicationId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}

	public int getPersonId() {
		return personId;
	}

	public int getCommunicationId() {
		return communicationId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public void setCommunicationId(int communicationId) {
		this.communicationId = communicationId;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
