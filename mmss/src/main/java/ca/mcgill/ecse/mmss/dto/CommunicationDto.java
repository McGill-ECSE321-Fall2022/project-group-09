package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Communication;

public class CommunicationDto {
	private int communicationId;

	/**
	 * Null constructor
	 * @author Shyam Desai
	 */
	public CommunicationDto() {}
	
	/**
	 * Constructor for communicationId
	 * @author Shidan Javaheri, Shyam Desai
	 * @param communicationId
	 */
	public CommunicationDto(int communicationId) {
		this.communicationId = communicationId;
	}

	public CommunicationDto(Communication communication) {
		this.communicationId = communication.getCommunicationId();
	}
	public void setCommunicationId(int communicationId) {
		this.communicationId = communicationId;
	}

	public int getCommunicationId() {
		return communicationId;
	}
}
