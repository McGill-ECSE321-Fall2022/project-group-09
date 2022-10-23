/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

// line 20 "../../../../../../mmss.ump"
// line 184 "../../../../../../mmss.ump"
public class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String userId;
  private String firstName;
  private String lastName;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(String aUserId, String aFirstName, String aLastName)
  {
    userId = aUserId;
    firstName = aFirstName;
    lastName = aLastName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUserId(String aUserId)
  {
    boolean wasSet = false;
    userId = aUserId;
    wasSet = true;
    return wasSet;
  }

  public boolean setFirstName(String aFirstName)
  {
    boolean wasSet = false;
    firstName = aFirstName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLastName(String aLastName)
  {
    boolean wasSet = false;
    lastName = aLastName;
    wasSet = true;
    return wasSet;
  }

  public String getUserId()
  {
    return userId;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "userId" + ":" + getUserId()+ "," +
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "]";
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 21 "../../../../../../mmss.ump"
  1 - 1..2 AccountType accounts ;

  
}