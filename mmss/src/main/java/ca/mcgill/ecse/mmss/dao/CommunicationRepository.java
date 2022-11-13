package ca.mcgill.ecse.mmss.dao;
import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Communication;
import ca.mcgill.ecse.mmss.model.Visitor;


public interface CommunicationRepository extends CrudRepository<Communication, Integer> {
    Communication findCommunicationByCommunicationId(int communicationId);

	
}