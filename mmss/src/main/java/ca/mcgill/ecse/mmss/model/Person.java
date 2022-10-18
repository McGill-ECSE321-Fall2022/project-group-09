/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.mmss.model;
import java.util.*;

// line 22 "../../../../../mmss.ump"
// line 186 "../../../../../mmss.ump"
public class Person
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Person Attributes
  private String userId;
  private String firstName;
  private String lastName;

  //Person Associations
  private List<AccountType> accounts;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Person(String aUserId, String aFirstName, String aLastName, AccountType... allAccounts)
  {
    userId = aUserId;
    firstName = aFirstName;
    lastName = aLastName;
    accounts = new ArrayList<AccountType>();
    boolean didAddAccounts = setAccounts(allAccounts);
    if (!didAddAccounts)
    {
      throw new RuntimeException("Unable to create Person, must have 1 to 2 accounts. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  /* Code from template association_GetMany */
  public AccountType getAccount(int index)
  {
    AccountType aAccount = accounts.get(index);
    return aAccount;
  }

  public List<AccountType> getAccounts()
  {
    List<AccountType> newAccounts = Collections.unmodifiableList(accounts);
    return newAccounts;
  }

  public int numberOfAccounts()
  {
    int number = accounts.size();
    return number;
  }

  public boolean hasAccounts()
  {
    boolean has = accounts.size() > 0;
    return has;
  }

  public int indexOfAccount(AccountType aAccount)
  {
    int index = accounts.indexOf(aAccount);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAccounts()
  {
    return 1;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfAccounts()
  {
    return 2;
  }
  /* Code from template association_AddUnidirectionalMN */
  public boolean addAccount(AccountType aAccount)
  {
    boolean wasAdded = false;
    if (accounts.contains(aAccount)) { return false; }
    if (numberOfAccounts() < maximumNumberOfAccounts())
    {
      accounts.add(aAccount);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeAccount(AccountType aAccount)
  {
    boolean wasRemoved = false;
    if (!accounts.contains(aAccount))
    {
      return wasRemoved;
    }

    if (numberOfAccounts() <= minimumNumberOfAccounts())
    {
      return wasRemoved;
    }

    accounts.remove(aAccount);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalMN */
  public boolean setAccounts(AccountType... newAccounts)
  {
    boolean wasSet = false;
    ArrayList<AccountType> verifiedAccounts = new ArrayList<AccountType>();
    for (AccountType aAccount : newAccounts)
    {
      if (verifiedAccounts.contains(aAccount))
      {
        continue;
      }
      verifiedAccounts.add(aAccount);
    }

    if (verifiedAccounts.size() != newAccounts.length || verifiedAccounts.size() < minimumNumberOfAccounts() || verifiedAccounts.size() > maximumNumberOfAccounts())
    {
      return wasSet;
    }

    accounts.clear();
    accounts.addAll(verifiedAccounts);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAccountAt(AccountType aAccount, int index)
  {  
    boolean wasAdded = false;
    if(addAccount(aAccount))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAccounts()) { index = numberOfAccounts() - 1; }
      accounts.remove(aAccount);
      accounts.add(index, aAccount);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAccountAt(AccountType aAccount, int index)
  {
    boolean wasAdded = false;
    if(accounts.contains(aAccount))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAccounts()) { index = numberOfAccounts() - 1; }
      accounts.remove(aAccount);
      accounts.add(index, aAccount);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAccountAt(aAccount, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    accounts.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "userId" + ":" + getUserId()+ "," +
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "]";
  }
}