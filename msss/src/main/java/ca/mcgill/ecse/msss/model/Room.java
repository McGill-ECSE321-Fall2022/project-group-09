/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

// line 13 "../../../../../mmss.ump"
// line 192 "../../../../../mmss.ump"

@Entity
public class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  
  @Id
  @GeneratedValue(Strategy = GenerationType.AUTO)
  private int roomId;
  
  private int artefactCount;

  //Room State Machines
  public enum Enum { Large, Small, Storage }
  private Enum enum;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  protected Room () {}

  public Room(int aRoomId)
  {
    roomId = aRoomId;
    artefactCount = 0;
    setEnum(Enum.Large);
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
            "roomId" + ":" + getRoomId()+ "," +
            "artefactCount" + ":" + getArtefactCount()+ "]";
  }
}