package ca.mcgill.ecse.mmss.dao;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Shift;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
    Employee findEmployeeByUsername(String userName);

	Employee findEmployeeByPassword(String password);

	ArrayList<Employee> findByPerson(Person person);

	ArrayList<Employee> findByCommunication(Communication communication);

	ArrayList<Employee> findByShift(Shift shift);
}