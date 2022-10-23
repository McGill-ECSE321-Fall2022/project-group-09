/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

// line 13 "../../../../../../mmss.ump"
// line 172 "../../../../../../mmss.ump"
public class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  private int roomID;
  private int artefactCount;

  //Room State Machines
  public enum Enum { Large, Small, Storage }
  private Enum enum;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(int aRoomID)
  {
    roomID = aRoomID;
    artefactCount = 0;
    setEnum(Enum.Large);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRoomID(int aRoomID)
  {
    boolean wasSet = false;
    roomID = aRoomID;
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

  public int getRoomID()
  {
    return roomID;
  }

  public int getArtefactCount()
  {
    return artefactCount;
  }

  public String getEnumFullName()
  {
    String answer = enum.toString();
    return answer;
  }

  public Enum getEnum()
  {
    return enum;
  }

  public boolean setEnum(Enum aEnum)
  {
    enum = aEnum;
    return true;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "roomID" + ":" + getRoomID()+ "," +
            "artefactCount" + ":" + getArtefactCount()+ "]";
  }
}