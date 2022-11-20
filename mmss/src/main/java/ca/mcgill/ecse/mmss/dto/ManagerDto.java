package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Manager;

public class ManagerDto {

	private String userName;
	private int personId;
	private int communicationId;

	/**
	 * Null constructor
	 * 
	 * @author Shyam Desai
	 */
	public ManagerDto() {
	}

	/**
	 * Constructor that takes in the manager as the argument
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param manager
	 */
	public ManagerDto(Manager manager) {
		this.userName = manager.getUsername();
		this.personId = manager.getPerson().getPersonId();
		if (manager.getCommunication() != null)
			this.communicationId = manager.getCommunication().getCommunicationId();
	}

	/**
	 * Constructor that takes in separate arguments
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param userName
	 * @param password
	 * @param personId
	 * @param communicationId
	 */
	public ManagerDto(String userName, int personId, int communicationId) {
		this.userName = userName;
		this.personId = personId;
		this.communicationId = communicationId;
	}

	public String getUserName() {
		return userName;
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

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
