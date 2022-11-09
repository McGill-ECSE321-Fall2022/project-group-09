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
        Room room = roomRepository.findRoomByRoomId(roomId);
        if (room == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "Room was not found");
        return artefactRepository.findAllByRoom(room);
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
        Room room = roomRepository.findRoomByRoomId(roomId);
        if (room == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "Room was not found");
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
        for (Room smallRoom : roomRepository.findAllByRoomType(Room.RoomType.Small)) {
            ArrayList<Artefact> artefacts = getAllArtefactsByRoom(smallRoom.getRoomId());
            artefactsInDisplay.addAll(artefacts);
        }
        // Get all artefacts from large rooms
        for (Room largeRoom : roomRepository.findAllByRoomType(Room.RoomType.Large)) {
            ArrayList<Artefact> artefacts = getAllArtefactsByRoom(largeRoom.getRoomId());
            artefactsInDisplay.addAll(artefacts);
        }
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
        for (Room smallRoom : roomRepository.findAllByRoomType(Room.RoomType.Small)) {
            ArrayList<Artefact> artefacts = getAllArtefactsByRoomAndByCanLoan(smallRoom.getRoomId(), canLoan);
            artefactsInDisplayByCanLoan.addAll(artefacts);
        }
        // Get all artefacts for given caLoan value from large rooms
        for (Room largeRoom : roomRepository.findAllByRoomType(Room.RoomType.Large)) {
            ArrayList<Artefact> artefacts = getAllArtefactsByRoomAndByCanLoan(largeRoom.getRoomId(), canLoan);
            artefactsInDisplayByCanLoan.addAll(artefacts);
        }
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
        Artefact artefact = artefactRepository.findArtefactByArtefactId(artefactId);
        if (artefact == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The artefact with this Id was not found.");
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
     * Move an artefact to a room
     *
     * @param artefactId
     * @param roomId
     */
    @Transactional
    public void moveArtefactToRoom(int artefactId, int roomId) {
        // Valid artefact id
        Artefact artefact = artefactRepository.findArtefactByArtefactId(artefactId);
        if (artefact == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The artefact with this Id was not found.");
        // Valid room id
        Room room = roomRepository.findRoomByRoomId(roomId);
        if (room == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The room with this Id was not found.");
        // Destination and current rooms are the same
        if (artefact.getRoom().getRoomId() == roomId)
            throw new MmssException(HttpStatus.BAD_REQUEST, "The destination and current rooms cannot be the same.");
        // Destination room full
        if (roomService.isRoomFull(roomId))
            throw new MmssException(HttpStatus.BAD_REQUEST, "The destination room is already full.");
        // Not previously in a room
        Room.RoomType destinationRoomType = room.getRoomType();
        if (!artefact.hasRoom()) {
            // Going to the display
            if (!(destinationRoomType == Room.RoomType.Storage)) {
                // Display already full
                if (roomService.getDisplayCapacity() == 1000) {
                    throw new MmssException(HttpStatus.BAD_REQUEST, "The display is already full.");
                }
                // Go to display
                else {
                    artefact.setRoom(room);
                    room.setArtefactCount(room.getArtefactCount() + 1);
                }
            }
            // Going to the storage
            else {
                artefact.setRoom(room);
                room.setArtefactCount(room.getArtefactCount() + 1);
            }
        }
        // Previously in a room
        else {
            Room currentRoom = artefact.getRoom();
            Room.RoomType currentRoomType = currentRoom.getRoomType();
            // Storage to display
            if (currentRoomType == Room.RoomType.Storage) {
                // Display already full
                if (roomService.getDisplayCapacity() == 1000) {
                    throw new MmssException(HttpStatus.BAD_REQUEST, "The display is already full.");
                }
                // Go to display
                else {
                    artefact.setRoom(room);
                    currentRoom.setArtefactCount(currentRoom.getArtefactCount() - 1);
                    room.setArtefactCount(room.getArtefactCount() + 1);
                }
            }
            else {
                // Display to storage
                if (destinationRoomType == Room.RoomType.Storage) {
                    artefact.setRoom(room);
                    currentRoom.setArtefactCount(currentRoom.getArtefactCount() - 1);
                    room.setArtefactCount(room.getArtefactCount() + 1);
                }
                // Display to display
                else {
                    artefact.setRoom(room);
                    currentRoom.setArtefactCount(currentRoom.getArtefactCount() - 1);
                    room.setArtefactCount(room.getArtefactCount() + 1);
                }
            }
        }
        // Persist DB
        roomRepository.save(room);
        artefactRepository.save(artefact);
    }

    @Transactional
    public void deleteArtefact(int artefactId) {
        // Check for valid artefact id
        Artefact artefact = artefactRepository.findArtefactByArtefactId(artefactId);
        if (artefact == null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The artefact with this Id was not found.");
        // Check if loan are associated with it
        ArrayList<Loan> loans = loanRepository.findAllByArtefact(artefact);
        if (loans != null)
            throw new MmssException(HttpStatus.NOT_FOUND, "The artefact is loaned or a loan request for this artefact is pending.");
        // Update the artefact count for a room
        if (artefact.hasRoom()) {
            Room room = artefact.getRoom();
            room.setArtefactCount(room.getArtefactCount() - 1);
        }
        // Delete from DB
        artefactRepository.delete(artefact);
    }
}
