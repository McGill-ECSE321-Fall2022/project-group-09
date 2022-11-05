package ca.mcgill.ecse.mmss.dto;

import java.sql.Date;


import ca.mcgill.ecse.mmss.model.Notification;

public class NotificationDto {
    private int notificationId; 
    private String message; 
    private Date date;
    private CommunicationDto communication; 

    /**
     * @author Shidan Javaheri
     * Constructor that takes in a notification as an argument
     * @param notification
     */
    public NotificationDto (Notification notification) { 
        this.notificationId = notification.getNotificationId();
        this.message = notification.getMessage();
        this.date = notification.getDate();
        this.communication = new CommunicationDto(notification.getSentNotification().getCommunicationId());
        
    }

    /**
     * Generic constructor with seperate arguments
     * @param notificationId
     * @param message
     * @param date
     * @param communication
     */
    public NotificationDto(int notificationId, String message, Date date, CommunicationDto communication) {
        this.notificationId = notificationId;
        this.message = message;
        this.date = date;
        this.communication = communication;
    }

    public int getNotificationId() {
        return notificationId;
    }
    public String getMessage() {
        return message;
    }
    public Date getDate() {
        return date;
    }

    public CommunicationDto getCommunication() {
        return communication;
    } 

    
    
   
}
