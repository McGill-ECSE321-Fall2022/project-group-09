package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
package ca.mcgill.ecse.mmss.model.OpenDay;

public interface OpenDayRepository extends CrudRepository<OpenDay, Date> {
    OpenDay findOpenDayByDate(Date date);
}
