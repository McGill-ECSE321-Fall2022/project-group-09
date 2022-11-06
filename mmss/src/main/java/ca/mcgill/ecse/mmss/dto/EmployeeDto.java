package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Employee;

public class EmployeeDto {
    private String phoneNumber; 
    private String userName; 
    private String password; 
    private PersonDto person; 
    private Boolean availableForTour; 
    private CommunicationDto communication;

    /**
     * @author Shidan Javaheri
     * Constructor that takes in an employee as the argument
     * @param employee
     */
    public void EmpoloyeeDto (Employee employee) { 
        this.phoneNumber = employee.getPhoneNumber();
        this.userName = employee.getUsername();
        this.password = employee.getPassword();
        this.person = new PersonDto(employee.getPerson());
        this.availableForTour = employee.getAvailableForTour(); 
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
    public EmployeeDto(String phoneNumber, String userName, String password, PersonDto person, Boolean availableForTour,CommunicationDto communication) {
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
        this.person = person;
        this.availableForTour = availableForTour;
        this.communication = communication;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getUserName() {
        return userName;
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


    public Boolean getAvailableForTour() {
        return availableForTour;
    }
    
}
