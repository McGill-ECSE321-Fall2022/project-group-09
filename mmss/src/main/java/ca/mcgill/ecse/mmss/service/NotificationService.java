package ca.mcgill.ecse.mmss.service;

import ca.mcgill.ecse.mmss.dao.NotificationRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Business logic for the Notification class
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private CommunicationService communicationService;

    /**
     * Get a notification by its primary key
     *
     * @param id the notification's primary key
     * @return the notification instance
     * @throws MmssException
     */
    @Transactional
    public Notification getNotificationById(int id) {
        Notification notification = notificationRepository.findNotificationByNotificationId(id);
        if (notification == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "Notification not found.");
        return notification;
    }

    /**
     * Get all notifications associated with a username
     *
     * @param username an account's primary key
     * @return an array list of notification instances
     */
    @Transactional
    public ArrayList<Notification> getAllNotificationsByUsername(String username) {
        Communication communication = communicationService.getCommunicationByUsername(username);
        return notificationRepository.findAllBySentNotification(communication);
    }

    /**
     * Create a notification to be sent to an account
     *
     * @param username an account's primary key
     * @param message the message to be sent
     * @return a notification instance
     * @throws MmssException
     */
    @Transactional
    public Notification createNotificationByUsername(String username, String message) {
        Communication communication = communicationService.getCommunicationByUsername(username);
        // Check for valid message
        if (message.length() >= 300 || message.isBlank())
            throw new MmssException(HttpStatus.BAD_REQUEST, "The notification’s message cannot be empty or longer than 300 characters.");
        // Create notification
        Notification notification = new Notification();
        notification.setSentNotification(communication);
        notification.setMessage(message);
        notification.setDate(new Date(System.currentTimeMillis()));
        // Persist in DB
        notificationRepository.save(notification);
        return notification;
    }

    /**
     * Delete a notification with a specific id
     *
     * @param id the notification's primary key
     */
    @Transactional
    public void deleteNotification(int id) {
        Notification notification = getNotificationById(id);
        notificationRepository.delete(notification);
    }
    
}
