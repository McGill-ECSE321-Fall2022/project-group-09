package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
package ca.mcgill.ecse.mmss.model.WeeklySchedule;

public interface WeeklyScheduleRepository extends CrudRepository<WeeklySchedule, Integer> {
    WeeklySchedule findWeeklyScheduleByWeeklyScheduleId(int weeklyScheduleId);
}