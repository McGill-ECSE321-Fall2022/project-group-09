package ca.mcgill.ecse.mmss.dto;

import java.sql.Date;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;

public class LoanDto {

	private Integer exchangeId;
	private Date dueDate;
	private Date submittedDate;
	private ExchangeStatus exchangeStatus;
	private String visitorId;
	private Integer artefactId;

	/**
	 * Null constructor
	 * 
	 * @author Shyam Desai
	 */
	public LoanDto() {
	}

	/**
	 * Constructor that takes a loan as input
	 * 
	 * @author Shidan Javaheri
	 * @param loan the loan object
	 */
	public LoanDto(Loan loan) {
		if (loan.getDueDate() != null) {
			this.dueDate = loan.getDueDate().getDate();
		}
		this.exchangeId = loan.getExchangeId();
		this.submittedDate = loan.getSubmittedDate();
		this.exchangeStatus = loan.getExchangeStatus();
		this.visitorId = loan.getVisitor().getUsername();
		this.artefactId = loan.getArtefact().getArtefactId();

	}

	/**
	 * Constructor that takes in seperate inputs
	 * 
	 * @author Shidan Javaheri
	 * @param exchangeId the id of the loan
	 * @param dueDate the due date of the loan
	 * @param submittedDate the submitted date of the loan
	 * @param exchangeStatus the status of the loan
	 * @param visitorId the id of the visitor who asked for the loan
	 * @param artefactId the artefact associated with the loan
	 */
	public LoanDto(Integer exchangeId, Date dueDate, Date submittedDate, ExchangeStatus exchangeStatus,
			String visitorId, Integer artefactId) {
		this.exchangeId = exchangeId;
		this.dueDate = dueDate;
		this.submittedDate = submittedDate;
		this.exchangeStatus = exchangeStatus;
		this.visitorId = visitorId;
		this.artefactId = artefactId;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public Integer getExchangeId() {
		return exchangeId;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public ExchangeStatus getExchangeStatus() {
		return exchangeStatus;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public Integer getArtefactId() {
		return artefactId;
	}

	public void setExchangeId(Integer exchangeId) {
		this.exchangeId = exchangeId;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public void setExchangeStatus(ExchangeStatus exchangeStatus) {
		this.exchangeStatus = exchangeStatus;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public void setArtefactId(int artefactId) {
		this.artefactId = artefactId;
	}

}