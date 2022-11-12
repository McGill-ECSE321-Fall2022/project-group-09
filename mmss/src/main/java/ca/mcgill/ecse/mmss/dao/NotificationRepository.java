package ca.mcgill.ecse.mmss.dao;
import ca.mcgill.ecse.mmss.model.Communication;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Notification;

import java.util.ArrayList;


public interface NotificationRepository extends CrudRepository<Notification, Integer> {
    Notification findNotificationByNotificationId(int notificationId);
    ArrayList<Notification> findAllByCommunication(Communication communication);
}