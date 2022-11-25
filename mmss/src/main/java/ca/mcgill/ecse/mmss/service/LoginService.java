package ca.mcgill.ecse.mmss.service;

import ca.mcgill.ecse.mmss.dao.EmployeeRepository;
import ca.mcgill.ecse.mmss.dao.ManagerRepository;
import ca.mcgill.ecse.mmss.dao.VisitorRepository;
import ca.mcgill.ecse.mmss.exception.MmssException;
import ca.mcgill.ecse.mmss.model.AccountType;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Manager;
import ca.mcgill.ecse.mmss.model.Visitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * Service class for logging in
 */
@Service
public class LoginService {

    @Autowired
    private VisitorRepository visitorRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Get an account from a username
     * @param username
     * @return an instance of visitor, employee, or manager
     */
    @Transactional
    public AccountType getAccountByUsername(String username) {
        // Try for each account type
        Visitor visitor = visitorRepository.findVisitorByUsername(username);
        Manager manager = managerRepository.findManagerByUsername(username);
        Employee employee = employeeRepository.findEmployeeByUsername(username);
        // Valid visitor
        if (visitor != null)
            return visitor;
        // Valid employee
        else if (employee != null)
            return employee;
        // Valid manager
        else if (manager != null)
            return manager;
        else
            throw new MmssException(HttpStatus.NOT_FOUND, "Account with this username not found");
    }
    
    /**
     * Logs a user into their account, or declines the login
     * 
     * @author Saviru Perera
     * @param userName, passWord
     * @return the account, or throw exceptions that the username or password was incorrect
     */
    @Transactional
    public AccountType loginToAccount(String userName, String passWord) {
    	AccountType loginInfo = getAccountByUsername(userName);
    	if (!loginInfo.getPassword().equals(passWord)) {
    		throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is incorrect. Please try again.");
    	}
    	return loginInfo;
    }

    /**
     * Logs a visitor into their account, or declines the login
     * @author Shidan Javaheri
     * @author Github Copilot
     * @param username the username of the visitor
     * @param password the password of the visitor
     * @return the visitor
     */
   
    @Transactional
    public Visitor loginVisitor ( String username, String password ) {
        Visitor visitor = visitorRepository.findVisitorByUsername(username);
        if (visitor == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Incorrect username entered. Please try again!");
        }
        if (!visitor.getPassword().equals(password)) {
            throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is incorrect. Please try again!");
        }
        return visitor;
    }

    
    /**
     * Logs an employee into their account, or declines the login
     * @author Shidan Javaheri
     * @author Github Copilot
     * @param username the username of the employee
     * @param password the password of the employee
     * @return the employee
     */
    @Transactional
    public Employee loginEmployee ( String username, String password ) {
        Employee employee = employeeRepository.findEmployeeByUsername(username);
        if (employee == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Incorrect username entered. Please try again!");
        }
        if (!employee.getPassword().equals(password)) {
            throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is incorrect. Please try again.");
        }
        return employee;
    }

    /**
     * Logs a manager into their account, or declines the login
     * @author Shidan Javaheri
     * @author Github Copilot
     * @param username the username of the manager
     * @param password the password of the manager
     * @return the manager
     */
    @Transactional
    public Manager loginManager ( String username, String password ) {
        Manager manager = managerRepository.findManagerByUsername(username);
        if (manager == null) {
            throw new MmssException(HttpStatus.NOT_FOUND, "Incorrect username entered. Please try again!");
        }
        if (!manager.getPassword().equals(password)) {
            throw new MmssException(HttpStatus.NOT_ACCEPTABLE, "The password entered is incorrect. Please try again.");
        }
        return manager;
    }


}
