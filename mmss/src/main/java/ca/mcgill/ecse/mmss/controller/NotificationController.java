package ca.mcgill.ecse.mmss.controller;

import ca.mcgill.ecse.mmss.dto.NotificationDto;
import ca.mcgill.ecse.mmss.dto.RequestNotificationDto;
import ca.mcgill.ecse.mmss.model.Notification;
import ca.mcgill.ecse.mmss.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * REST API for the Notification class
 */
@RestController
@CrossOrigin
@RequestMapping ({"/notification", "/notification/"})
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     * Get a notification by its primary key
     *
     * @param id the notification's primary key
     * @return a response entity with the {@link NotificationDto} instance and the HttpStatus
     */
    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<NotificationDto> getNotification(@PathVariable int id) {
        Notification notification = notificationService.getNotificationById(id);
        return new ResponseEntity<NotificationDto>(new NotificationDto(notification), HttpStatus.OK);
    }

    /**
     * Get all the notifications associated with a username
     *
     * @param username an account's primary key
     * @return a response entity with an array list of {@link NotificationDto} instances and the HttpStatus
     */
    @GetMapping({"/username", "/username/"})
    public ResponseEntity<ArrayList<NotificationDto>> getAllNotificationsByUsername(@RequestParam String username) {
        ArrayList<Notification> notifications = notificationService.getAllNotificationsByUsername(username);
        ArrayList<NotificationDto> notificationDTOs = new ArrayList<>();
        for (Notification notification : notifications) {
            notificationDTOs.add(new NotificationDto(notification));
        }
        return new ResponseEntity<ArrayList<NotificationDto>>(notificationDTOs, HttpStatus.OK);
    }

    /**
     * Create a notification based on an input request
     *
     * @param request a {@link NotificationDto} instance
     * @return a response entity with a message and the HttpStatus
     */
    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody RequestNotificationDto request) {
        // get parameters
        String username = request.getUsername();
        String message = request.getMessage();
        // create the notification
        notificationService.createNotificationByUsername(username, message);
        // return it in the response entity
        return new ResponseEntity<String>("Notification successfully created.", HttpStatus.CREATED);
    }

    /**
     * Delete a notification given its primary key
     *
     * @param id the notification's primary key
     * @return a response entity with a message and the HttpStatus
     */
    @DeleteMapping({"/{id}", "/{id}/"})
    public ResponseEntity<String> deleteNotification(@PathVariable int id) {
        notificationService.deleteNotification(id);
        return new ResponseEntity<String>("Notification successfully deleted.", HttpStatus.OK);
    }
}
