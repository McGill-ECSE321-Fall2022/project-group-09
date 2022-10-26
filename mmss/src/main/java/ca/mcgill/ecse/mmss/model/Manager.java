/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.Entity;

// line 35 "../../../../../mmss.ump"
// line 143 "../../../../../mmss.ump"

@Entity
public class Manager extends AccountType
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Manager () {}
  
  public Manager(String aUsername, String aPassword, Person aPerson)
  {
    super(aUsername, aPassword, aPerson);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

}