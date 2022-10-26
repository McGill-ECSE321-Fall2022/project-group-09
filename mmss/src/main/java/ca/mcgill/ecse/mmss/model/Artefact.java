/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

// line 3 "../../../../../mmss.ump"
// line 213 "../../../../../mmss.ump"
// line 220 "../../../../../mmss.ump"
// line 232 "../../../../../mmss.ump"

@Entity
public class Artefact
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Artefact Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int artefactId;
  
  
  private String artefactName;
  private String description;
  private boolean canLoan;
  private int insuranceFee;
  private int loanFee;

  //Artefact Associations
  @ManyToOne(optional = false)
  private Room room;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Artefact () {}
  
  public Artefact(int aArtefactId, String aArtefactName, String aDescription, boolean aCanLoan, int aInsuranceFee, int aLoanFee)
  {
    artefactId = aArtefactId;
    artefactName = aArtefactName;
    description = aDescription;
    canLoan = aCanLoan;
    insuranceFee = aInsuranceFee;
    loanFee = aLoanFee;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setArtefactId(int aArtefactId)
  {
    boolean wasSet = false;
    artefactId = aArtefactId;
    wasSet = true;
    return wasSet;
  }

  public boolean setArtefactName(String aArtefactName)
  {
    boolean wasSet = false;
    artefactName = aArtefactName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setCanLoan(boolean aCanLoan)
  {
    boolean wasSet = false;
    canLoan = aCanLoan;
    wasSet = true;
    return wasSet;
  }

  public boolean setInsuranceFee(int aInsuranceFee)
  {
    boolean wasSet = false;
    insuranceFee = aInsuranceFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setLoanFee(int aLoanFee)
  {
    boolean wasSet = false;
    loanFee = aLoanFee;
    wasSet = true;
    return wasSet;
  }

  public int getArtefactId()
  {
    return artefactId;
  }

  public String getArtefactName()
  {
    return artefactName;
  }

  public String getDescription()
  {
    return description;
  }

  public boolean getCanLoan()
  {
    return canLoan;
  }

  public int getInsuranceFee()
  {
    return insuranceFee;
  }

  public int getLoanFee()
  {
    return loanFee;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isCanLoan()
  {
    return canLoan;
  }
  /* Code from template association_GetOne */
  public Room getRoom()
  {
    return room;
  }

  public boolean hasRoom()
  {
    boolean has = room != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setRoom(Room aNewRoom)
  {
    boolean wasSet = false;
    room = aNewRoom;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    room = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "artefactId" + ":" + getArtefactId()+ "," +
            "artefactName" + ":" + getArtefactName()+ "," +
            "description" + ":" + getDescription()+ "," +
            "canLoan" + ":" + getCanLoan()+ "," +
            "insuranceFee" + ":" + getInsuranceFee()+ "," +
            "loanFee" + ":" + getLoanFee()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "room = "+(getRoom()!=null?Integer.toHexString(System.identityHashCode(getRoom())):"null");
  }
}