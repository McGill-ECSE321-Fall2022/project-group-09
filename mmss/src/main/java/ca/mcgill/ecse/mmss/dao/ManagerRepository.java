package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
package ca.mcgill.ecse.mmss.model.Manager;

public interface ManagerRepository extends CrudRepository<Manager, String> {
    Manager findManagerByUsername(String userName);
}