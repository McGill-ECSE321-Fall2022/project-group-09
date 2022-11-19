package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Employee;

public class EmployeeRequestDto {
	private String firstName;
	private String lastName;
	private String userName;
	private String passWord;
	private String phoneNumber;
	private String newUserName;
	private String newPassword;
	private String newPhoneNumber;
	
	/**
	 * Null constructor
	 * @author Saviru Perera
	 */
	public EmployeeRequestDto() {}
	
	/**
	 * Constructor that takes in a visitor as an argument
	 * 
	 * @author Saviru Perera
	 * @param visitor
	 */
	public EmployeeRequestDto(Employee employee, String newUserName, String newPassword, String newPhoneNumber) {
		this.firstName = employee.getPerson().getFirstName();
		this.lastName = employee.getPerson().getLastName();
		this.userName = employee.getUsername();
		this.passWord = employee.getPassword();
		this.phoneNumber = employee.getPhoneNumber();
		this.newUserName = newUserName;
		this.newPassword = newPassword;
		this.newPhoneNumber = newPhoneNumber;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getUsername() {
		return userName;
	}

	public String getPassword() {
		return passWord;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getNewUserName() {
		return newUserName;
	}

	public String getNewPassword() {
		return newPassword;
	}
	
	public String getNewPhoneNumber() {
		return newPhoneNumber;
	}
	
	public void setFirstName(String newFirst) {
		this.firstName = newFirst;
	}

	public void setLastName(String newLast) {
		this.lastName = newLast;
	}

	public void setUsername(String newUser) {
		this.userName = newUser;
	}
	
	public void setPassword (String newPass) {
		this.passWord = newPass;
	}
	
	public void setPhoneNumber (String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setNewUsername(String newUser) {
		this.newUserName = newUser;
	}

	public void setNewPassword(String newPass) {
		this.newPassword = newPass;
	}
	
	public void setNewPhoneNumber (String newPhoneNumber) {
		this.newPhoneNumber = newPhoneNumber;
	}
}
