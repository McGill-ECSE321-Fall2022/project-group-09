package ca.mcgill.ecse.mmss.dao;
import ca.mcgill.ecse.mmss.model.Room;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Artefact;

import java.util.ArrayList;


public interface ArtefactRepository extends CrudRepository<Artefact, Integer> {
    Artefact findArtefactByArtefactId(int artefactId);
    ArrayList<Artefact> findAll();
    ArrayList<Artefact> findAllByCanLoan(boolean canLoan);
    ArrayList<Artefact> findAllByRoom(Room room);
    ArrayList<Artefact> findAllByRoomAndByCanLoan(Room room, boolean canLoan);
}