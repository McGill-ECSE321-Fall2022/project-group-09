package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Notification;


public interface NotificationRepository extends CrudRepository<Notification, Integer> {
    Notification findNotificationByNotificationId(int notificationId);
}