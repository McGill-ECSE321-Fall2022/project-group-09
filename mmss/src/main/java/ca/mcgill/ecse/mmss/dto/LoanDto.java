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
     * Constructor that takes a loan as input
     * @param loan
     */
    public LoanDto (Loan loan) { 
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
     * Constructs a loanDto
     * @param exchangeId
     * @param dueDate
     * @param submittedDate
     * @param exchangeStatus
     * @param visitorId
     * @param artefactId
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


} 