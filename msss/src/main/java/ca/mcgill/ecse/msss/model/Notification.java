/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.sql.Date;

// line 107 "../../../../../mmss.ump"
// line 259 "../../../../../mmss.ump"

@Entity
public class Notification
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Notification Attributes
  
  @Id
  @GeneratedValue(Strategy = GenerationType.AUTO)
  private int notificationId;
  
  private String message;
  private Date date;

  //Notification Associations
  @ManyToOne(optional = false)
  @Column(nullable = false)
  private Communication sentNotification;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  protected Notification () {}
  
  public Notification(int aNotificationId, String aMessage, Date aDate, Communication aSentNotification)
  {
    notificationId = aNotificationId;
    message = aMessage;
    date = aDate;
    if (!setSentNotification(aSentNotification))
    {
      throw new RuntimeException("Unable to create Notification due to aSentNotification. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNotificationId(int aNotificationId)
  {
    boolean wasSet = false;
    notificationId = aNotificationId;
    wasSet = true;
    return wasSet;
  }

  public boolean setMessage(String aMessage)
  {
    boolean wasSet = false;
    message = aMessage;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public int getNotificationId()
  {
    return notificationId;
  }

  public String getMessage()
  {
    return message;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public Communication getSentNotification()
  {
    return sentNotification;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSentNotification(Communication aNewSentNotification)
  {
    boolean wasSet = false;
    if (aNewSentNotification != null)
    {
      sentNotification = aNewSentNotification;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    sentNotification = null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "notificationId" + ":" + getNotificationId()+ "," +
            "message" + ":" + getMessage()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "sentNotification = "+(getSentNotification()!=null?Integer.toHexString(System.identityHashCode(getSentNotification())):"null");
  }
}