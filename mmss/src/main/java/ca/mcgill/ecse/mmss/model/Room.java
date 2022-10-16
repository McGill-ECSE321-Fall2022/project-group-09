/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.util.*;

// line 30 "../../../../../mmss.ump"
// line 225 "../../../../../mmss.ump"
public class Room
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Room> roomsByRoomID = new HashMap<String, Room>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Room Attributes
  private String roomID;
  private int itemCount;

  //Room State Machines
  public enum Enum { Large, Small, Storage }
  private Enum enum;

  //Room Associations
  private List<Item> items;
  private MuseumManagement museumManagement;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Room(String aRoomID, MuseumManagement aMuseumManagement)
  {
    itemCount = 0;
    if (!setRoomID(aRoomID))
    {
      throw new RuntimeException("Cannot create due to duplicate roomID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    items = new ArrayList<Item>();
    boolean didAddMuseumManagement = setMuseumManagement(aMuseumManagement);
    if (!didAddMuseumManagement)
    {
      throw new RuntimeException("Unable to create room due to museumManagement. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    setEnum(Enum.Large);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRoomID(String aRoomID)
  {
    boolean wasSet = false;
    String anOldRoomID = getRoomID();
    if (anOldRoomID != null && anOldRoomID.equals(aRoomID)) {
      return true;
    }
    if (hasWithRoomID(aRoomID)) {
      return wasSet;
    }
    roomID = aRoomID;
    wasSet = true;
    if (anOldRoomID != null) {
      roomsByRoomID.remove(anOldRoomID);
    }
    roomsByRoomID.put(aRoomID, this);
    return wasSet;
  }

  public boolean setItemCount(int aItemCount)
  {
    boolean wasSet = false;
    itemCount = aItemCount;
    wasSet = true;
    return wasSet;
  }

  public String getRoomID()
  {
    return roomID;
  }
  /* Code from template attribute_GetUnique */
  public static Room getWithRoomID(String aRoomID)
  {
    return roomsByRoomID.get(aRoomID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithRoomID(String aRoomID)
  {
    return getWithRoomID(aRoomID) != null;
  }

  public int getItemCount()
  {
    return itemCount;
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
  public Item getItem(int index)
  {
    Item aItem = items.get(index);
    return aItem;
  }

  public List<Item> getItems()
  {
    List<Item> newItems = Collections.unmodifiableList(items);
    return newItems;
  }

  public int numberOfItems()
  {
    int number = items.size();
    return number;
  }

  public boolean hasItems()
  {
    boolean has = items.size() > 0;
    return has;
  }

  public int indexOfItem(Item aItem)
  {
    int index = items.indexOf(aItem);
    return index;
  }
  /* Code from template association_GetOne */
  public MuseumManagement getMuseumManagement()
  {
    return museumManagement;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItems()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addItem(Item aItem)
  {
    boolean wasAdded = false;
    if (items.contains(aItem)) { return false; }
    items.add(aItem);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItem(Item aItem)
  {
    boolean wasRemoved = false;
    if (items.contains(aItem))
    {
      items.remove(aItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemAt(Item aItem, int index)
  {  
    boolean wasAdded = false;
    if(addItem(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemAt(Item aItem, int index)
  {
    boolean wasAdded = false;
    if(items.contains(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemAt(aItem, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setMuseumManagement(MuseumManagement aMuseumManagement)
  {
    boolean wasSet = false;
    if (aMuseumManagement == null)
    {
      return wasSet;
    }

    MuseumManagement existingMuseumManagement = museumManagement;
    museumManagement = aMuseumManagement;
    if (existingMuseumManagement != null && !existingMuseumManagement.equals(aMuseumManagement))
    {
      existingMuseumManagement.removeRoom(this);
    }
    museumManagement.addRoom(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    roomsByRoomID.remove(getRoomID());
    items.clear();
    MuseumManagement placeholderMuseumManagement = museumManagement;
    this.museumManagement = null;
    if(placeholderMuseumManagement != null)
    {
      placeholderMuseumManagement.removeRoom(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "roomID" + ":" + getRoomID()+ "," +
            "itemCount" + ":" + getItemCount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "museumManagement = "+(getMuseumManagement()!=null?Integer.toHexString(System.identityHashCode(getMuseumManagement())):"null");
  }
}