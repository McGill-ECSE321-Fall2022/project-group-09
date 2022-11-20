package ca.mcgill.ecse.mmss.dao;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Shift;
import ca.mcgill.ecse.mmss.model.Visitor;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
    Employee findEmployeeByUsername(String userName);

	Employee findEmployeeByPassword(String password);

	ArrayList<Employee> findByShift(Shift shift);
	
	ArrayList<Employee> findAll();
}