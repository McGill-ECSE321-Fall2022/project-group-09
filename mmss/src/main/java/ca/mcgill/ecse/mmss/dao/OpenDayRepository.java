package ca.mcgill.ecse.mmss.dao;
import java.sql.Date;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.OpenDay;


public interface OpenDayRepository extends CrudRepository<OpenDay, Date> {
    OpenDay findOpenDayByDate(Date date);
}
