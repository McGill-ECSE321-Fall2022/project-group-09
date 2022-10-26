package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
    Employee findEmployeeByUsername(String userName);
}