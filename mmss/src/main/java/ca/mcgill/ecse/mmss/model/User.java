/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.util.*;

// line 41 "../../../../../mmss.ump"
// line 168 "../../../../../mmss.ump"
public class User
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextUserId = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String firstName;
  private String lastName;

  //Autounique Attributes
  private int userId;

  //User Associations
  private List<AccountType> accountTypes;
  private MuseumManagement museumManagement;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aFirstName, String aLastName, MuseumManagement aMuseumManagement, AccountType... allAccountTypes)
  {
    firstName = aFirstName;
    lastName = aLastName;
    userId = nextUserId++;
    accountTypes = new ArrayList<AccountType>();
    boolean didAddAccountTypes = setAccountTypes(allAccountTypes);
    if (!didAddAccountTypes)
    {
      throw new RuntimeException("Unable to create User, must have 1 to 2 accountTypes. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddMuseumManagement = setMuseumManagement(aMuseumManagement);
    if (!didAddMuseumManagement)
    {
      throw new RuntimeException("Unable to create user due to museumManagement. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public int getUserId()
  {
    return userId;
  }
  /* Code from template association_GetMany */
  public AccountType getAccountType(int index)
  {
    AccountType aAccountType = accountTypes.get(index);
    return aAccountType;
  }

  public List<AccountType> getAccountTypes()
  {
    List<AccountType> newAccountTypes = Collections.unmodifiableList(accountTypes);
    return newAccountTypes;
  }

  public int numberOfAccountTypes()
  {
    int number = accountTypes.size();
    return number;
  }

  public boolean hasAccountTypes()
  {
    boolean has = accountTypes.size() > 0;
    return has;
  }

  public int indexOfAccountType(AccountType aAccountType)
  {
    int index = accountTypes.indexOf(aAccountType);
    return index;
  }
  /* Code from template association_GetOne */
  public MuseumManagement getMuseumManagement()
  {
    return museumManagement;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAccountTypes()
  {
    return 1;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfAccountTypes()
  {
    return 2;
  }
  /* Code from template association_AddUnidirectionalMN */
  public boolean addAccountType(AccountType aAccountType)
  {
    boolean wasAdded = false;
    if (accountTypes.contains(aAccountType)) { return false; }
    if (numberOfAccountTypes() < maximumNumberOfAccountTypes())
    {
      accountTypes.add(aAccountType);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeAccountType(AccountType aAccountType)
  {
    boolean wasRemoved = false;
    if (!accountTypes.contains(aAccountType))
    {
      return wasRemoved;
    }

    if (numberOfAccountTypes() <= minimumNumberOfAccountTypes())
    {
      return wasRemoved;
    }

    accountTypes.remove(aAccountType);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalMN */
  public boolean setAccountTypes(AccountType... newAccountTypes)
  {
    boolean wasSet = false;
    ArrayList<AccountType> verifiedAccountTypes = new ArrayList<AccountType>();
    for (AccountType aAccountType : newAccountTypes)
    {
      if (verifiedAccountTypes.contains(aAccountType))
      {
        continue;
      }
      verifiedAccountTypes.add(aAccountType);
    }

    if (verifiedAccountTypes.size() != newAccountTypes.length || verifiedAccountTypes.size() < minimumNumberOfAccountTypes() || verifiedAccountTypes.size() > maximumNumberOfAccountTypes())
    {
      return wasSet;
    }

    accountTypes.clear();
    accountTypes.addAll(verifiedAccountTypes);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAccountTypeAt(AccountType aAccountType, int index)
  {  
    boolean wasAdded = false;
    if(addAccountType(aAccountType))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAccountTypes()) { index = numberOfAccountTypes() - 1; }
      accountTypes.remove(aAccountType);
      accountTypes.add(index, aAccountType);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAccountTypeAt(AccountType aAccountType, int index)
  {
    boolean wasAdded = false;
    if(accountTypes.contains(aAccountType))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAccountTypes()) { index = numberOfAccountTypes() - 1; }
      accountTypes.remove(aAccountType);
      accountTypes.add(index, aAccountType);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAccountTypeAt(aAccountType, index);
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
      existingMuseumManagement.removeUser(this);
    }
    museumManagement.addUser(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    accountTypes.clear();
    MuseumManagement placeholderMuseumManagement = museumManagement;
    this.museumManagement = null;
    if(placeholderMuseumManagement != null)
    {
      placeholderMuseumManagement.removeUser(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "userId" + ":" + getUserId()+ "," +
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "museumManagement = "+(getMuseumManagement()!=null?Integer.toHexString(System.identityHashCode(getMuseumManagement())):"null");
  }
}