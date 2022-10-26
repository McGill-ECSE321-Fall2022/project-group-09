package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
package ca.mcgill.ecse.mmss.model.Employee;

public interface EmployeeTypeRepository extends CrudRepository<Employee, String> {
    Employee findEmployeeTypeByUsername(String userName);
}