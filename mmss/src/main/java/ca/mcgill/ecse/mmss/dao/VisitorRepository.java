package ca.mcgill.ecse.mmss.dao;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Person;
import ca.mcgill.ecse.mmss.model.Visitor;


public interface VisitorRepository extends CrudRepository<Visitor, String> {
    Visitor findVisitorByUsername(String userName);
    
    Visitor findVisitorByPassword(String password);

	ArrayList<Visitor> findByPerson(Person person);

	ArrayList<Visitor> findByCommunication(Communication communication);
    
    
}