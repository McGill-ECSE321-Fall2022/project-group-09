package ca.mcgill.ecse.mmss.service;

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.LoanRepository;
import ca.mcgill.ecse.mmss.dao.RoomRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class ArtefactService {

    @Autowired
    private ArtefactRepository artefactRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private RoomService roomService;

    /**
     * Get an artefact by its primary key
     *
     * @author Sasha Denouvilliez-Pech
     * @param id
     * @return an artefact or an exception
     */
    @Transactional
    public Artefact getArtefactById(int id) {
        Artefact artefact = artefactRepository.findArtefactByArtefactId(id);
        if (artefact == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Artefact not found");
        }
        return artefact;
    }

    /**
     * Get all artefacts
     *
     * @return an array list of artefacts
     */
    @Transactional
    public ArrayList<Artefact> getAllArtefacts() {
        return artefactRepository.findAll();
    }

    /**
     * Get all artefacts with a specific canLoan value
     *
     * @param canLoan boolean, available for loan
     * @return an array list all artefacts with a given canLoan value
     */
    @Transactional
    public ArrayList<Artefact> getAllArtefactsByCanLoan(boolean canLoan) {
        return artefactRepository.findAllByCanLoan(canLoan);
    }

    /**
     * Gets all the loans associated with a given room
     *
     * @param roomId, the primary key of a room
     * @return an array list of artefacts for a given room
     */
    @Transactional
    public ArrayList<Artefact> getAllArtefactsByRoom(int roomId) {
        return artefactRepository.findAllByRoom(roomService.retrieveRoomById(roomId));
    }

    /**
     * Gets all the loans associated with a given room and a given canLoan value
     *
     * @param roomId
     * @param canLoan
     * @return an array list of artefacts gor a given room and canLoan value
     */
    @Transactional
    public ArrayList<Artefact> getAllArtefactsByRoomAndByCanLoan(int roomId, boolean canLoan) {
        // Valid room
        Room room = roomService.retrieveRoomById(roomId);
        return artefactRepository.findAllByRoomAndByCanLoan(room, canLoan);
    }

    /**
     * Get all artefacts in display
     *
     * @return an array list of artefacts in display
     */
    @Transactional
    public ArrayList<Artefact> getAllArtefactsInDisplay() {
        ArrayList<Artefact> artefactsInDisplay = new ArrayList<>();
        // Get all artefacts from small rooms
        for (Room smallRoom : roomRepository.findAllByRoomType(Room.RoomType.Small))
            artefactsInDisplay.addAll(getAllArtefactsByRoom(smallRoom.getRoomId()));
        // Get all artefacts from large rooms
        for (Room largeRoom : roomRepository.findAllByRoomType(Room.RoomType.Large))
            artefactsInDisplay.addAll(getAllArtefactsByRoom(largeRoom.getRoomId()));
        return artefactsInDisplay;
    }

    /**
     * Get all artefacts in display with a given canLoan value
     *
     * @param canLoan
     * @return an array list of artefacts in display with a given canLoan value
     */
    @Transactional
    public ArrayList<Artefact> getAllArtefactsInDisplayByCanLoan(boolean canLoan) {
        ArrayList<Artefact> artefactsInDisplayByCanLoan = new ArrayList<>();
        // Get all artefacts for given canLoan value from small rooms
        for (Room smallRoom : roomRepository.findAllByRoomType(Room.RoomType.Small))
            artefactsInDisplayByCanLoan.addAll(getAllArtefactsByRoomAndByCanLoan(smallRoom.getRoomId(), canLoan));
        // Get all artefacts for given caLoan value from large rooms
        for (Room largeRoom : roomRepository.findAllByRoomType(Room.RoomType.Large))
            artefactsInDisplayByCanLoan.addAll(getAllArtefactsByRoomAndByCanLoan(largeRoom.getRoomId(), canLoan));
        return artefactsInDisplayByCanLoan;
    }

    /**
     * Create an artefact and persist it in the DB
     *
     * @param name
     * @param description
     * @param canLoan
     * @param insuranceFee
     * @param loanFee
     * @return the artefact instance
     */
    @Transactional
    public Artefact createArtefact(String name, String description, boolean canLoan, double insuranceFee, double loanFee) {
        // Check valid parameters
        checkValidArtefactParams(name, description, canLoan, insuranceFee, loanFee);
        // Create artefact
        Artefact artefact = new Artefact();
        artefact.setArtefactName(name);
        artefact.setDescription(description);
        artefact.setCanLoan(canLoan);
        artefact.setInsuranceFee(insuranceFee);
        artefact.setLoanFee(loanFee);
        // Persist to DB
        artefactRepository.save(artefact);
        return artefact;
    }

    /**
     * Update a valid artefact
     *
     * @param artefactId
     * @param name
     * @param description
     * @param canLoan
     * @param insuranceFee
     * @param loanFee
     * @return
     */
    @Transactional
    public Artefact updateArtefact(int artefactId, String name, String description, boolean canLoan, double insuranceFee, double loanFee) {
        // Check for valid artefact id
        Artefact artefact = getArtefactById(artefactId);
        // Check valid parameters
        checkValidArtefactParams(name, description, canLoan, insuranceFee, loanFee);
        // Create artefact
        artefact.setArtefactName(name);
        artefact.setDescription(description);
        artefact.setCanLoan(canLoan);
        artefact.setInsuranceFee(insuranceFee);
        artefact.setLoanFee(loanFee);
        // Persist to DB
        artefactRepository.save(artefact);
        return artefact;
    }

    /**
     * Remove an artefact from its assigned room
     * Works also if the room has no room.
     *
     * @param artefactId
     */
    @Transactional
    public void removeArtefactFromItsRoom(int artefactId) {
        // Valid artefact id
        Artefact artefact = getArtefactById(artefactId);
        // Decrement room's artefact count
        Room room = artefact.getRoom();
        if (room != null) {
            room.setArtefactCount(room.getArtefactCount() - 1);
            roomRepository.save(room);
        }
        // Remove room association
        artefact.setRoom(null);
        // Persist in DB
        artefactRepository.save(artefact);
    }

    /**
     * Add an artefact to a valid room
     * Assume the artefact did not have a room previously
     *
     * @param artefactId
     * @param roomId
     */
    @Transactional
    public void addArtefactToRoom(int artefactId, int roomId) {
        // ASSUMPTION: the artefact has no room associated with it
        // Valid artefact id
        Artefact artefact = getArtefactById(artefactId);
        // Valid room id
        Room room = roomService.retrieveRoomById(roomId);
        // Destination room full
        if (roomService.isRoomFull(roomId))
            throw new MmssException(HttpStatus.BAD_REQUEST, "The destination room is already full.");
        // Display full
        if (!(room.getRoomType() == Room.RoomType.Storage) && roomService.getDisplayCapacity() >= 1000)
            throw new MmssException(HttpStatus.BAD_REQUEST, "The display is already full.");
        // Increment room's artefact count
        room.setArtefactCount(room.getArtefactCount() + 1);
        // Add room association
        artefact.setRoom(room);
        // Persist in DB
        roomRepository.save(room);
        artefactRepository.save(artefact);
    }

    /**
     * Move an artefact to a room
     *
     * @param artefactId
     * @param roomId
     */
    @Transactional
    public void moveArtefactToRoom(int artefactId, int roomId) {
        // Valid artefact id
        Artefact artefact = getArtefactById(artefactId);
        // Valid room id
        Room room = roomService.retrieveRoomById(roomId);
        // Destination and current rooms are the same
        if (artefact.getRoom().getRoomId() == roomId)
            throw new MmssException(HttpStatus.BAD_REQUEST, "The destination and current rooms cannot be the same.");
        // Not previously in a room
        if (!artefact.hasRoom())
            addArtefactToRoom(artefactId, roomId);
        // Previously in a room
        else {
            removeArtefactFromItsRoom(artefactId);
            addArtefactToRoom(artefactId, roomId);
        }
    }

    @Transactional
    public void deleteArtefact(int artefactId) {
        // Valid artefact id
        Artefact artefact = getArtefactById(artefactId);
        // Check if loan are associated with it
        ArrayList<Loan> loans = loanRepository.findAllByArtefact(artefact);
        if (loans != null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The artefact is loaned or a loan request for this artefact is pending.");
        // Update the artefact count for a room
        removeArtefactFromItsRoom(artefactId);
        // Delete from DB
        artefactRepository.delete(artefact);
    }

    /**
     * Check for valid artefact parameters
     *
     * @param name
     * @param description
     * @param canLoan
     * @param insuranceFee
     * @param loanFee
     */
    private void checkValidArtefactParams(String name, String description, boolean canLoan, double insuranceFee, double loanFee) {
        // Check for valid name
        if ( name.length() > 50 || name.isEmpty())
            throw new MmssException(HttpStatus.BAD_REQUEST,
                    "The artefact’s name cannot be empty or longer than 50 characters.");
        // Check for valid description
        if (description.length() > 300 || description.isEmpty())
            throw new MmssException(HttpStatus.BAD_REQUEST,
                    "The artefact’s description cannot be empty or longer than 300 characters.");
        // Check for fees
        if (canLoan & (insuranceFee == 0 || loanFee == 0))
            throw new MmssException(HttpStatus.BAD_REQUEST, "If the artefact is available for loan, the fees cannot be $0.");
    }
}
