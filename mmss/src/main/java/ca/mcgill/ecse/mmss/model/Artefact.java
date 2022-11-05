/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;

import javax.persistence.*;

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

  @Column(nullable = false)
  private String artefactName;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private boolean canLoan;
  private double insuranceFee;
  private double loanFee;

  //Artefact Associations
  @ManyToOne
  private Room room;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Artefact () {}
  
  public Artefact(int aArtefactId, String aArtefactName, String aDescription, boolean aCanLoan, double aInsuranceFee, double aLoanFee)
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

  public boolean setInsuranceFee(double aInsuranceFee)
  {
    boolean wasSet = false;
    insuranceFee = aInsuranceFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setLoanFee(double aLoanFee)
  {
    boolean wasSet = false;
    loanFee = aLoanFee;
    wasSet = true;
    return wasSet;
  }

  public  int getArtefactId()
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

  public double getInsuranceFee()
  {
    return insuranceFee;
  }

  public double getLoanFee()
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