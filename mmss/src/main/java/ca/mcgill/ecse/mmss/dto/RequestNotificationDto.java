package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Notification;

import java.sql.Date;

public class RequestNotificationDto {
    private String message;

    private String username;


    /**
     * Generic constructor
     * @param username
     * @param message
     */
    public RequestNotificationDto(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
