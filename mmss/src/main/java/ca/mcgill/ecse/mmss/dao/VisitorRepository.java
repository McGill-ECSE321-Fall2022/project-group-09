package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
package ca.mcgill.ecse.mmss.model.Visitor;

public interface VisitorRepository extends CrudRepository<Visitor, String> {
    Visitor findVisitorByUsername(String userName);
}