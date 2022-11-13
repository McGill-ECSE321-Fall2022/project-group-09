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
import org.springframework.transaction.annotation.Transactional;

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
}
