/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// line 14 "../../../../../../model.ump"
// line 198 "../../../../../../model.ump"

@Entity
public class Room
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum RoomType { Large, Small, Storage }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int roomId;
  
  private int artefactCount;
  private RoomType roomType;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  public Room () {}

  public Room(int aRoomId, RoomType aRoomType)
  {
    roomId = aRoomId;
    artefactCount = 0;
    roomType = aRoomType;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRoomId(int aRoomId)
  {
    boolean wasSet = false;
    roomId = aRoomId;
    wasSet = true;
    return wasSet;
  }

  public boolean setArtefactCount(int aArtefactCount)
  {
    boolean wasSet = false;
    artefactCount = aArtefactCount;
    wasSet = true;
    return wasSet;
  }

  public boolean setRoomType(RoomType aRoomType)
  {
    boolean wasSet = false;
    roomType = aRoomType;
    wasSet = true;
    return wasSet;
  }

  public int getRoomId()
  {
    return roomId;
  }

  public int getArtefactCount()
  {
    return artefactCount;
  }

  public RoomType getRoomType()
  {
    return roomType;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "roomId" + ":" + getRoomId()+ "," +
            "artefactCount" + ":" + getArtefactCount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "roomType" + "=" + (getRoomType() != null ? !getRoomType().equals(this)  ? getRoomType().toString().replaceAll("  ","    ") : "this" : "null");
  }
}