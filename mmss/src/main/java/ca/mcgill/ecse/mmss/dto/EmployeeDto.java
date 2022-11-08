package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Employee;

public class EmployeeDto {
    private String phoneNumber; 
    private String userName; 
    private PersonDto person; 
    private CommunicationDto communication;

    /**
     * @author Shidan Javaheri
     * Constructor that takes in an employee as the argument
     * @param employee
     */
    public void EmpoloyeeDto (Employee employee) { 
        this.phoneNumber = employee.getPhoneNumber();
        this.userName = employee.getUsername();
        this.person = new PersonDto(employee.getPerson());
        this.communication = new CommunicationDto (employee.getCommunication().getCommunicationId());
    }


    /**
     * @author Shidan Javaheri
     * Constructor that takes in seperate arguments
     * @param phoneNumber
     * @param userName
     * @param password
     * @param person
     * @param communication
     */
    public EmployeeDto(String phoneNumber, String userName,  PersonDto person,CommunicationDto communication) {
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.person = person;
        this.communication = communication;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getUserName() {
        return userName;
    }
    public PersonDto getPerson() {
        return person;
    }
    public CommunicationDto getCommunication() {
        return communication;
    }

    
}
