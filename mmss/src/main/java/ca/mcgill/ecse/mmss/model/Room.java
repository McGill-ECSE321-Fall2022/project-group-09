/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.util.*;

// line 13 "../../../../../mmss.ump"
// line 174 "../../../../../mmss.ump"
public class Room
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  private String roomID;
  private int artefactCount;

  //Room State Machines
  public enum Enum { Large, Small, Storage }
  private Enum enum;

  //Room Associations
  private List<Artefact> artefacts;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(String aRoomID)
  {
    roomID = aRoomID;
    artefactCount = 0;
    artefacts = new ArrayList<Artefact>();
    setEnum(Enum.Large);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRoomID(String aRoomID)
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

  public String getRoomID()
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
  /* Code from template association_GetMany */
  public Artefact getArtefact(int index)
  {
    Artefact aArtefact = artefacts.get(index);
    return aArtefact;
  }

  public List<Artefact> getArtefacts()
  {
    List<Artefact> newArtefacts = Collections.unmodifiableList(artefacts);
    return newArtefacts;
  }

  public int numberOfArtefacts()
  {
    int number = artefacts.size();
    return number;
  }

  public boolean hasArtefacts()
  {
    boolean has = artefacts.size() > 0;
    return has;
  }

  public int indexOfArtefact(Artefact aArtefact)
  {
    int index = artefacts.indexOf(aArtefact);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArtefacts()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addArtefact(Artefact aArtefact)
  {
    boolean wasAdded = false;
    if (artefacts.contains(aArtefact)) { return false; }
    artefacts.add(aArtefact);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeArtefact(Artefact aArtefact)
  {
    boolean wasRemoved = false;
    if (artefacts.contains(aArtefact))
    {
      artefacts.remove(aArtefact);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addArtefactAt(Artefact aArtefact, int index)
  {  
    boolean wasAdded = false;
    if(addArtefact(aArtefact))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtefacts()) { index = numberOfArtefacts() - 1; }
      artefacts.remove(aArtefact);
      artefacts.add(index, aArtefact);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveArtefactAt(Artefact aArtefact, int index)
  {
    boolean wasAdded = false;
    if(artefacts.contains(aArtefact))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtefacts()) { index = numberOfArtefacts() - 1; }
      artefacts.remove(aArtefact);
      artefacts.add(index, aArtefact);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addArtefactAt(aArtefact, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    artefacts.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "roomID" + ":" + getRoomID()+ "," +
            "artefactCount" + ":" + getArtefactCount()+ "]";
  }
}