package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Visitor;

public class VisitorDto {
    private  double balance; 
    private String username;
    private String password; 
    private PersonDto person; 
    private CommunicationDto communication;

    /**
     * @author Shidan Javaheri
     * Constructor that takes in a visitor as an argument
     * @param visitor
     */
    public VisitorDto (Visitor visitor) { 
        this.balance = visitor.getBalance();
        this.username = visitor.getUsername();
        this.password = visitor.getPassword();
        this.person = new PersonDto (visitor.getPerson());
        this.communication = new CommunicationDto(visitor.getCommunication().getCommunicationId());
    }

    /**
     * @author Shidan Javaheri
     * Constructor that takes in seperate arguments
     * @param balance
     * @param username
     * @param password
     * @param person
     * @param communication
     */
    public VisitorDto(int balance, String username, String password, PersonDto person, CommunicationDto communication) {
        this.balance = balance;
        this.username = username;
        this.password = password;
        this.person = person;
        this.communication = communication;
    }
    public double getBalance() {
        return balance;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public PersonDto getPerson() {
        return person;
    }
    public CommunicationDto getCommunication() {
        return communication;
    } 


    

}
