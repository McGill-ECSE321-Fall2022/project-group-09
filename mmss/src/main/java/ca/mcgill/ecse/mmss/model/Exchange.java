/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;
import javax.persistence.*;

// line 56 "../../../../../../model.ump"
// line 206 "../../../../../../model.ump"

@MappedSuperclass
public abstract class Exchange
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum ExchangeStatus { Pending, Declined, Approved }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Exchange Attributes
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int exchangeId;

  @Column(nullable = false)
  private Date submittedDate;
  @Column(nullable = false)
  private ExchangeStatus exchangeStatus;

  //------------------------
  // CONSTRUCTOR
  //------------------------
  
  public Exchange () {}

  public Exchange(int aExchangeId, Date aSubmittedDate)
  {
    exchangeId = aExchangeId;
    submittedDate = aSubmittedDate;
    exchangeStatus = ExchangeStatus.Pending;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setExchangeId(int aExchangeId)
  {
    boolean wasSet = false;
    exchangeId = aExchangeId;
    wasSet = true;
    return wasSet;
  }

  public boolean setSubmittedDate(java.util.Date date)
  {
    boolean wasSet = false;
    submittedDate = date;
    wasSet = true;
    return wasSet;
  }

  public boolean setExchangeStatus(ExchangeStatus aExchangeStatus)
  {
    boolean wasSet = false;
    exchangeStatus = aExchangeStatus;
    wasSet = true;
    return wasSet;
  }

  public int getExchangeId()
  {
    return exchangeId;
  }

  public Date getSubmittedDate()
  {
    return submittedDate;
  }

  public ExchangeStatus getExchangeStatus()
  {
    return exchangeStatus;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "exchangeId" + ":" + getExchangeId()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "submittedDate" + "=" + (getSubmittedDate() != null ? !getSubmittedDate().equals(this)  ? getSubmittedDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "exchangeStatus" + "=" + (getExchangeStatus() != null ? !getExchangeStatus().equals(this)  ? getExchangeStatus().toString().replaceAll("  ","    ") : "this" : "null");
  }
}