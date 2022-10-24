/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

// line 49 "../../../../../mmss.ump"
// line 129 "../../../../../mmss.ump"
public class Employee extends AccountType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Associations
  private Shift shift;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(String aUsername, String aPassword, Person aPerson)
  {
    super(aUsername, aPassword, aPerson);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Shift getShift()
  {
    return shift;
  }

  public boolean hasShift()
  {
    boolean has = shift != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setShift(Shift aNewShift)
  {
    boolean wasSet = false;
    shift = aNewShift;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    shift = null;
    super.delete();
  }

}