/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// line 14 "../../../../../../model.ump"
// line 194 "../../../../../../model.ump"

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

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  protected Room () {}

  public Room(int aRoomId)
  {
    roomId = aRoomId;
    artefactCount = 0;
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

  public int getRoomId()
  {
    return roomId;
  }

  public int getArtefactCount()
  {
    return artefactCount;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "roomId" + ":" + getRoomId()+ "," +
            "artefactCount" + ":" + getArtefactCount()+ "]";
  }
}