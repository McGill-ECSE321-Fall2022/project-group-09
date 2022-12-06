package ca.mcgill.ecse.mmss.controller;

import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse.mmss.dto.ManagerDto;
import ca.mcgill.ecse.mmss.model.Manager;
import ca.mcgill.ecse.mmss.service.ManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** 
 * Exposes relevant Manager service methods at the URL "/manager"
 * @author Shidan Javaheri
 */

@RestController
@CrossOrigin
@RequestMapping({ "/manager", "/manager/" })
public class ManagerController {

    @Autowired
    ManagerService managerService;

    /**
     * Get the manager
     * 
     * @author Shidan Javaheri
     * @return a response entity with a {@link ManagerDto} and HttpStatus
     */
    @GetMapping
    public ResponseEntity<ManagerDto> getMangaer() {
        Manager manager = managerService.getManager();
        ManagerDto response = new ManagerDto(manager);
        return new ResponseEntity<ManagerDto>(response, HttpStatus.OK);
    }

    /**
     * Update the password of the manager
     * 
     * @author Shidan Javaheri
     * @param oldPassword the manager's old password
     * @param newPassword the manager's new password
     * @return a response entity with a {@link ManagerDto} (password not included in
     *         Dto), and an HttpStatus
     */
    @PutMapping
    public ResponseEntity<ManagerDto> updatePassword(@RequestParam String oldPassword,
            @RequestParam String newPassword) {

        Manager manager = managerService.updateMangagerPassword(oldPassword, newPassword);

        return new ResponseEntity<ManagerDto>(new ManagerDto(manager), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<ManagerDto> createManager() {
        Manager manager = managerService.createManager();
        return new ResponseEntity<ManagerDto>(new ManagerDto(manager), HttpStatus.OK);
    }

}
