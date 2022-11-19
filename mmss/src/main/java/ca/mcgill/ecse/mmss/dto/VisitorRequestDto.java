package ca.mcgill.ecse.mmss.dto;
import ca.mcgill.ecse.mmss.model.Visitor;

public class VisitorRequestDto {
	private String firstName;
	private String lastName;
	private String userName;
	private String passWord;
	private String newUserName;
	private String newPassword;
	private double balance;
	
	/**
	 * Null constructor
	 * @author Saviru Perera
	 */
	public VisitorRequestDto() {}
	
	/**
	 * Constructor that takes in a visitor as an argument
	 * 
	 * @author Saviru Perera
	 * @param visitor
	 */
	public VisitorRequestDto(Visitor visitor, String newUserName, String newPassword) {
		this.firstName = visitor.getPerson().getFirstName();
		this.lastName = visitor.getPerson().getLastName();
		this.userName = visitor.getUsername();
		this.passWord = visitor.getPassword();
		this.balance = visitor.getBalance();
		this.newUserName = newUserName;
		this.newPassword = newPassword;
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
	
	public double getBalance() {
		return balance;
	}
	
	public String getNewUserName() {
		return newUserName;
	}

	public String getNewPassword() {
		return newPassword;
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
	
	public void setBalance (double newBalance) {
		this.balance = newBalance;
	}
	
	public void setNewUsername(String newUser) {
		this.newUserName = newUser;
	}

	public void setNewPassword(String newPass) {
		this.newPassword = newPass;
	}

}
