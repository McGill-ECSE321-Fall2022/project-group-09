package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Shift.ShiftTime;

public class EmployeeDto {
	private String firstName;
	private String lastName;
	private String userName;
	private String phoneNumber;
	private ShiftTime shiftTime;

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
		this.firstName = employee.getPerson().getFirstName();
		this.lastName = employee.getPerson().getLastName();
		this.phoneNumber = employee.getPhoneNumber();
		this.userName = employee.getUsername();
		if(employee.getShift() != null) this.shiftTime = employee.getShift().getShiftTime();
	}

	/**
	 * Constructor that takes in separate arguments
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param phoneNumber
	 * @param userName
	 * @param password
	 * @param person
	 * @param shiftTime
	 */
	public EmployeeDto(String phoneNumber, String userName, String firstName, String lastName, ShiftTime shiftTime) {
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.shiftTime = shiftTime;
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

	public ShiftTime getShiftTime() {
		return shiftTime;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
