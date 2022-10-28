package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Schedule;


public interface ScheduleRepository extends CrudRepository<Schedule, Integer> {
    Schedule findScheduleByScheduleId(int ScheduleId);
}