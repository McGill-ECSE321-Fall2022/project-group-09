package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Employee;

public class EmployeeDto {
	private String userName;
	private String phoneNumber;
	private String firstName;
	private String lastName;

	/**
	 * Null constructor
	 * 
	 * @author Shyam Desai
	 */
	public EmployeeDto() {
	}

	/**
	 * Constructor that takes in an employee as the argument
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param employee
	 */
	public EmployeeDto(Employee employee) {
		this.phoneNumber = employee.getPhoneNumber();
		this.userName = employee.getUsername();
		this.firstName = employee.getPerson().getFirstName();
		this.lastName = employee.getPerson().getLastName();
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
	public EmployeeDto(String phoneNumber, String userName, String firstName, String lastName) {
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
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

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
