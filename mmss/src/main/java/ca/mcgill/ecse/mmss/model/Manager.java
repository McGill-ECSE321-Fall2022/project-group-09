/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.util.*;

// line 57 "../../../../../mmss.ump"
// line 179 "../../../../../mmss.ump"
public class Manager extends AccountType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Associations
  private MuseumManagement museumManagement;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Manager(String aUsername, String aPassword, MuseumManagement aMuseumManagement)
  {
    super(aUsername, aPassword);
    boolean didAddMuseumManagement = setMuseumManagement(aMuseumManagement);
    if (!didAddMuseumManagement)
    {
      throw new RuntimeException("Unable to create manager due to museumManagement. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public MuseumManagement getMuseumManagement()
  {
    return museumManagement;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setMuseumManagement(MuseumManagement aNewMuseumManagement)
  {
    boolean wasSet = false;
    if (aNewMuseumManagement == null)
    {
      //Unable to setMuseumManagement to null, as manager must always be associated to a museumManagement
      return wasSet;
    }
    
    Manager existingManager = aNewMuseumManagement.getManager();
    if (existingManager != null && !equals(existingManager))
    {
      //Unable to setMuseumManagement, the current museumManagement already has a manager, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    MuseumManagement anOldMuseumManagement = museumManagement;
    museumManagement = aNewMuseumManagement;
    museumManagement.setManager(this);

    if (anOldMuseumManagement != null)
    {
      anOldMuseumManagement.setManager(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MuseumManagement existingMuseumManagement = museumManagement;
    museumManagement = null;
    if (existingMuseumManagement != null)
    {
      existingMuseumManagement.setManager(null);
    }
    super.delete();
  }

}