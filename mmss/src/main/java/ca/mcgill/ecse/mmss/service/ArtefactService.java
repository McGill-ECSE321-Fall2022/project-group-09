package ca.mcgill.ecse.mmss.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.LoanRepository;
import ca.mcgill.ecse.mmss.dao.RoomRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.Room;

/**
 * Business logic for the Artefact class
 */
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
     * @param id the primary key of an artefact
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
     * Get all artefacts in the museum
     *
     * @return an array list of artefact instance
     */
    @Transactional
    public ArrayList<Artefact> getAllArtefacts() {
        return artefactRepository.findAll();
    }

    /**
     * Get all artefacts with a specific canLoan value
     *
     * @param canLoan whether an artefact is available for loan
     * @return an array list of artefact instances
     */
    @Transactional
    public ArrayList<Artefact> getAllArtefactsByCanLoan(boolean canLoan) {
        return artefactRepository.findAllByCanLoan(canLoan);
    }

    /**
     * Get all the artefacts in a given room
     *
     * @param roomId a room's primary key
     * @return an array list of artefact instances
     * @throws MmssException
     */
    @Transactional
    public ArrayList<Artefact> getAllArtefactsByRoom(int roomId) {
        return artefactRepository.findAllByRoom(roomService.getRoomById(roomId));
    }

    /**
     * Get all the artefacts associated with a given room and a given canLoan value
     *
     * @param roomId a room's primary key
     * @param canLoan whether an artefact is available for loan
     * @return an array list of artefact instances
     * @throws MmssException
     */
    @Transactional
    public ArrayList<Artefact> getAllArtefactsByRoomAndByCanLoan(int roomId, boolean canLoan) {
        // Valid room
        Room room = roomService.getRoomById(roomId);
        return artefactRepository.findAllByRoomAndCanLoan(room, canLoan);
    }

    /**
     * Get all artefacts in display
     *
     * @return an array list of artefact instance
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
     * @param canLoan whether an artefact is available for loan
     * @return an array list of artefact instance
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
     * Create an artefact
     *
     * @param name the artefact's name
     * @param description the artefact's description
     * @param canLoan whether an artefact is available for loan
     * @param insuranceFee the artefact's insurance fee
     * @param loanFee the artefact's loan fee
     * @param url the artefact's image url
     * @return the artefact instance
     * @throws MmssException
     */
    @Transactional
    public Artefact createArtefact(String name, String description, boolean canLoan, double insuranceFee, double loanFee, String url) {
        // Check valid parameters
        checkValidArtefactParams(name, description, canLoan, insuranceFee, loanFee);
        // Create artefact
        Artefact artefact = new Artefact();
        artefact.setArtefactName(name);
        artefact.setDescription(description);
        artefact.setCanLoan(canLoan);
        artefact.setInsuranceFee(insuranceFee);
        artefact.setLoanFee(loanFee);
        artefact.setImageUrl(url);
        // Persist to DB
        return artefactRepository.save(artefact);
    }

    /**
     * Update a valid artefact
     *
     * @param artefactId the artefact's primary key
     * @param name the artefact's name
     * @param description the artefact's description
     * @param canLoan whether an artefact is available for loan
     * @param insuranceFee the artefact's insurance fee
     * @param loanFee the artefact's loan fee
     * @param url the artefact's image url
     * @return the artefact instance
     * @throws MmssException
     */
    @Transactional
    public Artefact updateArtefact(int artefactId, String name, String description, boolean canLoan, double insuranceFee, double loanFee, String url) {
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
        artefact.setImageUrl(url);
        // Persist to DB
        return artefactRepository.save(artefact);
    }

    /**
     * Remove an artefact from its assigned room.
     * Works also if the artefact has no room.
     *
     * @param artefactId the artefact's primary key
     * @throws MmssException
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
     * @param artefactId the artefact's primary key
     * @param roomId the room's primary key
     * @throws MmssException
     */
    @Transactional
    public void addArtefactToRoom(int artefactId, int roomId) {
        // ASSUMPTION: the artefact has no room associated with it
        // Valid artefact id
        Artefact artefact = getArtefactById(artefactId);
        // Valid room id
        Room room = roomService.getRoomById(roomId);
        // Destination room full
        if (roomService.isRoomFull(roomId))
            throw new MmssException(HttpStatus.BAD_REQUEST, "The destination room is already full.");
        // Display full
        if (!(room.getRoomType() == Room.RoomType.Storage) && roomService.getDisplayArtefactCount() >= 1000)
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
     * @param artefactId the artefact's primary key
     * @param roomId the room's primary key
     * @throws MmssException
     */
    @Transactional
    public void moveArtefactToRoom(int artefactId, int roomId) {
        // Valid artefact id
        Artefact artefact = getArtefactById(artefactId);
        // Valid room id
        roomService.getRoomById(roomId);
        // Destination and current rooms are the same
        if (artefact.hasRoom() && artefact.getRoom().getRoomId() == roomId)
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

    /**
     * Delete an artefact with a specific id
     * @param artefactId the artefact's primary key
     * @throws MmssException
     */
    @Transactional
    public void deleteArtefact(int artefactId) {
        // Valid artefact id
        Artefact artefact = getArtefactById(artefactId);
        // Check if loan are associated with it
        ArrayList<Loan> loans = loanRepository.findAllByArtefact(artefact);
        if (!loans.isEmpty())
            throw new MmssException(HttpStatus.NOT_FOUND, "The artefact is loaned or a loan request for this artefact is pending.");
        // Update the artefact count for a room
        removeArtefactFromItsRoom(artefactId);
        // Delete from DB
        artefactRepository.delete(artefact);
    }

    /**
     * Check for valid artefact parameters
     *
     * @param name the artefact's name
     * @param description the artefact's description
     * @param canLoan whether an artefact is available for loan
     * @param insuranceFee the artefact's insurance fee
     * @param loanFee the artefact's loan fee
     * @throws MmssException
     */
    public void checkValidArtefactParams(String name, String description, boolean canLoan, double insuranceFee, double loanFee) {
        // Check for valid name
        if ( name.length() > 50 || name.isBlank())
            throw new MmssException(HttpStatus.BAD_REQUEST,
                    "The artefact’s name cannot be empty or longer than 50 characters.");
        // Check for valid description
        if (description.length() > 300 || description.isBlank())
            throw new MmssException(HttpStatus.BAD_REQUEST,
                    "The artefact’s description cannot be empty or longer than 300 characters.");
        // Check for fees
        if (canLoan & (insuranceFee == 0 || loanFee == 0))
            throw new MmssException(HttpStatus.BAD_REQUEST, "If the artefact is available for loan, the fees cannot be $0.");
    }
}
