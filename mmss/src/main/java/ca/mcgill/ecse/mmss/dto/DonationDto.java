package ca.mcgill.ecse.mmss.dto;

import java.sql.Date;
import ca.mcgill.ecse.mmss.model.Donation;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;

public class DonationDto {

	private int exchangeId;
	private Date submittedDate;
	private ExchangeStatus exchangeStatus;
	private String itemName;
	private String description;
	private String visitorUsername;

	/**
	 * Null constructor
	 * @author Shyam Desai
	 */
	public DonationDto() {}
	
	/**
	 * Constructor that takes donation as argument
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param donation
	 */
	public DonationDto(Donation donation) {

		this.exchangeId = donation.getExchangeId();
		this.submittedDate = donation.getSubmittedDate();
		this.exchangeStatus = donation.getExchangeStatus();
		this.itemName = donation.getItemName();
		this.description = donation.getDescription();
		this.visitorUsername = donation.getVisitor().getUsername();
	}

	/**
	 * Constructor that takes separate arguments
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param exchangeId
	 * @param submittedDate
	 * @param exchangeStatus
	 * @param itemName
	 * @param description
	 * @param visitor
	 */
	public DonationDto(int exchangeId, Date submittedDate, ExchangeStatus exchangeStatus, String itemName,
			String description, String visitorUsername) {
		this.exchangeId = exchangeId;
		this.submittedDate = submittedDate;
		this.exchangeStatus = exchangeStatus;
		this.itemName = itemName;
		this.description = description;
		this.visitorUsername = visitorUsername;
	}

	public int getExchangeId() {
		return exchangeId;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public ExchangeStatus getExchangeStatus() {
		return exchangeStatus;
	}

	public String getItemName() {
		return itemName;
	}

	public String getDescription() {
		return description;
	}

	public String getVisitorUsername() {
		return visitorUsername;
	}

	public void setVisitorUsername(String visitorUsername) {
		this.visitorUsername = visitorUsername;
	}

	public void setExchangeId(int exchangeId) {
		this.exchangeId = exchangeId;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public void setExchangeStatus(ExchangeStatus exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
