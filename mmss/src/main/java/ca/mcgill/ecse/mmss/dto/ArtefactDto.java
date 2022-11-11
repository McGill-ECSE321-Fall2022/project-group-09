package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Artefact;

public class ArtefactDto {
	private int artefactId;
	private String artefactName;
	private String description;
	private Boolean canLoan;
	private double insuranceFee;
	private double loanFee;
	private boolean currentlyOnLoan;
	private int roomId;

	/**
	 * Null constructor
	 * @author Shyam Desai
	 */
	public ArtefactDto() {}

	/**
	 * Constructor that takes in an artefact
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param artefact
	 */
	public ArtefactDto(Artefact artefact) {
		this.artefactId = artefact.getArtefactId();
		this.artefactName = artefact.getArtefactName();
		this.description = artefact.getDescription();
		this.canLoan = artefact.getCanLoan();
		this.insuranceFee = artefact.getInsuranceFee();
		this.loanFee = artefact.getLoanFee();
		this.currentlyOnLoan = artefact.getCurrentlyOnLoan();
		this.roomId = artefact.getRoom().getRoomId();
	}

	/**
	 * Constructor that takes in all arguments
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param artefactId
	 * @param artefactName
	 * @param description
	 * @param canLoan
	 * @param insuranceFee
	 * @param loanFee
	 * @param roomId
	 */
	public ArtefactDto(Integer artefactId, String artefactName, String description, Boolean canLoan,
			double insuranceFee, double loanFee, boolean currentlyOnLoan, int roomId) {
		this.artefactId = artefactId;
		this.artefactName = artefactName;
		this.description = description;
		this.canLoan = canLoan;
		this.insuranceFee = insuranceFee;
		this.loanFee = loanFee;
		this.roomId = roomId;
	}

	public int getArtefactId() {
		return artefactId;
	}

	public String getArtefactName() {
		return artefactName;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getCanLoan() {
		return canLoan;
	}

	public double getInsuranceFee() {
		return insuranceFee;
	}

	public double getLoanFee() {
		return loanFee;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public void setArtefactId(int artefactId) {
		this.artefactId = artefactId;
	}

	public void setArtefactName(String artefactName) {
		this.artefactName = artefactName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCanLoan(Boolean canLoan) {
		this.canLoan = canLoan;
	}

	public void setInsuranceFee(double insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public void setLoanFee(double loanFee) {
		this.loanFee = loanFee;
	}

	public void setCurrentlyOnLoan(boolean currentlyOnLoan) {
		this.currentlyOnLoan = currentlyOnLoan;
	}

	public boolean isCurrentlyOnLoan() {
		return currentlyOnLoan;
	}
}
