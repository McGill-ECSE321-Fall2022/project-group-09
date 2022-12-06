package ca.mcgill.ecse.mmss.dao;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Shift;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
    Employee findEmployeeByUsername(String userName);

	Employee findEmployeeByPassword(String password);

	ArrayList<Employee> findByShift(Shift shift);
	
	ArrayList<Employee> findAll();
}