package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Communication;


public interface CommunicationRepository extends CrudRepository<Communication, Integer> {
    Communication findCommunicationByCommunicationId(int communicationId);
}