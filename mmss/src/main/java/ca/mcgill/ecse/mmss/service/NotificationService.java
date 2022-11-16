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

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private CommunicationService communicationService;

    /**
     * Get a notification by its primary key
     * @param id
     * @return a notification
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
     * @param username
     * @return an array list of notifications associated with a username
     */
    @Transactional
    public ArrayList<Notification> getAllNotificationsByUsername(String username) {
        Communication communication = communicationService.getCommunicationByUsername(username);
        return notificationRepository.findAllBySentNotification(communication);
    }

    /**
     * Create a notification to be sent to an account
     * @param username
     * @param message
     * @return a notification
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
     * @param id
     */
    @Transactional
    public void deleteNotification(int id) {
        Notification notification = getNotificationById(id);
        notificationRepository.delete(notification);
    }
    
}
