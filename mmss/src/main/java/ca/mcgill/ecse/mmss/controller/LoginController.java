package ca.mcgill.ecse.mmss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse.mmss.dto.EmployeeDto;
import ca.mcgill.ecse.mmss.dto.ManagerDto;
import ca.mcgill.ecse.mmss.dto.VisitorDto;
import ca.mcgill.ecse.mmss.model.Employee;
import ca.mcgill.ecse.mmss.model.Manager;
import ca.mcgill.ecse.mmss.model.Visitor;
import ca.mcgill.ecse.mmss.service.LoginService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping({ "/login", "/login/" })
public class LoginController {

    @Autowired
    LoginService loginService;
    /**
     * Login a visitor 
     * @author Shidan Javaheri
     * @author Github Copilot
     * @param username the username of the visitor
     * @param password the password of the visitor
     * @return a resonse entity with the {@link VisitorDto} and the Http status
     */

    @GetMapping 
    public ResponseEntity<VisitorDto> loginVisitor(@RequestParam String username, @RequestParam String password) {
        // call the service
        Visitor visitor = loginService.loginVisitor(username, password);
        // return a response entity with the Dto
        return new ResponseEntity<VisitorDto>(new VisitorDto(visitor), HttpStatus.OK);
    }

    /**
     * Login an employee
     * @author Shidan Javaheri
     * @author Github Copilot
     * @param username the username of the employee
     * @param password the password of the employee
     * @return a resonse entity with the {@link EmployeeDto} and the Http status
     */
    
    @GetMapping({ "/employee", "/employee/" })
    public ResponseEntity<EmployeeDto> loginEmployee( @RequestParam String username, @RequestParam String password) {
        // call the service
        Employee employee = loginService.loginEmployee(username, password);
        // return a response entity with the Dto
        return new ResponseEntity<EmployeeDto>(new EmployeeDto(employee), HttpStatus.OK);
    }

    /**
     * Login a manager
     * @author Shidan Javaheri
     * @author Github Copilot
     * @param username the username of the manager
     * @param password the password of the manager
     * @return a resonse entity with the {@link ManagerDto} and the Http status
     */
    @GetMapping({ "/manager", "/manager/" })
    public ResponseEntity<ManagerDto> loginManager(@RequestParam String username, @RequestParam String password) {
        // call the service
        Manager manager = loginService.loginManager(username, password);
        // return a response entity with the Dto
        return new ResponseEntity<ManagerDto>(new ManagerDto(manager), HttpStatus.OK);
    }

}
