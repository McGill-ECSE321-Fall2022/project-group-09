/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// line 113 "../../../../../mmss.ump"
// line 264 "../../../../../mmss.ump"

@Entity
public class Communication
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Communication Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int communicationId;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  protected Communication () {}
  
  public Communication(int aCommunicationId)
  {
    communicationId = aCommunicationId;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCommunicationId(int aCommunicationId)
  {
    boolean wasSet = false;
    communicationId = aCommunicationId;
    wasSet = true;
    return wasSet;
  }

  public int getCommunicationId()
  {
    return communicationId;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "communicationId" + ":" + getCommunicationId()+ "]";
  }
}