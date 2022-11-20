package ca.mcgill.ecse.mmss.dto;

import ca.mcgill.ecse.mmss.model.Room;
import ca.mcgill.ecse.mmss.model.Room.RoomType;

public class RoomDto {
    private int roomId; 
    private int artefactCount; 
    private RoomType roomtype;

	/**
	 * Null constructor
	 * @author Shyam Desai
	 */
	public RoomDto() {}
    
    /**
     * @author Shidan Javaheri
     * Constructor that takes in a room as an arugment
     * @param room
     */
    public RoomDto (Room room ) { 
        this.roomId = room.getRoomId();
        this.artefactCount = room.getArtefactCount();
        this.roomtype = room.getRoomType();
    }
    
    /**
     * Generic constructor with all arguments
     * @param roomId
     * @param artefactCount
     * @param roomtype
     */
    public RoomDto(int roomId, int artefactCount, RoomType roomtype) {
        this.roomId = roomId;
        this.artefactCount = artefactCount;
        this.roomtype = roomtype;
    }
    public int getRoomId() {
        return roomId;
    }
    public int getArtefactCount() {
        return artefactCount;
    }
    public RoomType getRoomtype() {
        return roomtype;
    } 

    
    
    
}
