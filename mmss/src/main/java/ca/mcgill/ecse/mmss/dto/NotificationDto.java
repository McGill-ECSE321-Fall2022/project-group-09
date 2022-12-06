package ca.mcgill.ecse.mmss.dto;

import java.sql.Date;

import ca.mcgill.ecse.mmss.model.Notification;

public class NotificationDto {
	private int notificationId;
	private String message;
	private Date date;

	/**
	 * Null constructor
	 * @author Shyam Desai
	 */
	public NotificationDto() {}
	
	/**
	 * Constructor that takes in a notification as an argument
	 * 
	 * @author Shidan Javaheri, Shyam Desai
	 * @param notification
	 */
	public NotificationDto(Notification notification) {
		this.notificationId = notification.getNotificationId();
		this.message = notification.getMessage();
		this.date = notification.getDate();

	}

	/**
	 * Generic constructor with separate arguments
	 * 
	 * @param notificationId
	 * @param message
	 * @param date
	 * @author Shidan Javaheri, Shyam Desai
	 */
	public NotificationDto(int notificationId, String message, Date date) {
		this.notificationId = notificationId;
		this.message = message;
		this.date = date;
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

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
