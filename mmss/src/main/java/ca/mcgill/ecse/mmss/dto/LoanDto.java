package ca.mcgill.ecse.mmss.dto;

import java.sql.Date;

import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.Exchange.ExchangeStatus;

public class LoanDto {

    private int exchangeId; 
    private Date dueDate;
    private Date submittedDate; 
    private ExchangeStatus exchangeStatus; 
    private String visitorId; 
    private int artefactId; 

    public LoanDto () { 
        
    }
     
    /**
     * @author Shidan Javaheri
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
     * @author Shidan Javaheri
     * Constructor that takes in seperate inputs 
     * @param exchangeId
     * @param dueDate
     * @param submittedDate
     * @param exchangeStatus
     * @param visitorId
     * @param artefactId
     */
    public LoanDto(int exchangeId, Date dueDate, Date submittedDate, ExchangeStatus exchangeStatus,
            String visitorId, int artefactId) {
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
    public int getExchangeId() {
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
    public int getArtefactId() {
        return artefactId;
    }


    public void setExchangeId(int exchangeId) {
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