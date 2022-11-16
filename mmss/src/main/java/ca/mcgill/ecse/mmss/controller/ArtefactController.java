package ca.mcgill.ecse.mmss.controller;

import ca.mcgill.ecse.mmss.dto.ArtefactDto;
import ca.mcgill.ecse.mmss.dto.LoanDto;
import ca.mcgill.ecse.mmss.dto.RoomDto;
import ca.mcgill.ecse.mmss.model.Artefact;
import ca.mcgill.ecse.mmss.model.Loan;
import ca.mcgill.ecse.mmss.model.Room;
import ca.mcgill.ecse.mmss.service.ArtefactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping({"/artefact", "/artefact/"})
public class ArtefactController {

    @Autowired
    private ArtefactService artefactService;

    /**
     * Get an artefact by its primary key
     *
     * @param id
     * @return a response entity with the artefact as DTO and ok status
     */
    @GetMapping({"/{id}", "/{id}/"})
    public ResponseEntity<ArtefactDto> getArtefact(@PathVariable int id) {
        Artefact artefact = artefactService.getArtefactById(id);
        return new ResponseEntity<ArtefactDto>(new ArtefactDto(artefact), HttpStatus.OK);
    }

    /**
     * Get all artefacts
     *
     * @return an array list of artefacts as DTOs
     */
    @GetMapping
    public ResponseEntity<ArrayList<ArtefactDto>> getAllArtefacts() {
        ArrayList<Artefact> artefacts = artefactService.getAllArtefacts();
        // DTOs
        ArrayList<ArtefactDto> artefactDTOs = new ArrayList<>();
        for (Artefact artefact : artefacts) {
            artefactDTOs.add(new ArtefactDto(artefact));
        }
        return new ResponseEntity<ArrayList<ArtefactDto>>(artefactDTOs, HttpStatus.OK);
    }

    /**
     * Get all artefacts with a specific canLoan value
     *
     * @param canLoan
     * @return an array list with the artefacts as DTOs
     */
    @GetMapping({"/canLoan", "/canLoan/"})
    public ResponseEntity<ArrayList<ArtefactDto>> getAllArtefactsByCanLoan(@RequestParam boolean canLoan) {
        ArrayList<Artefact> artefacts = artefactService.getAllArtefactsByCanLoan(canLoan);
        // DTOs
        ArrayList<ArtefactDto> artefactDTOs = new ArrayList<>();
        for (Artefact artefact : artefacts) {
            artefactDTOs.add(new ArtefactDto(artefact));
        }
        return new ResponseEntity<ArrayList<ArtefactDto>>(artefactDTOs, HttpStatus.OK);
    }

    /**
     * Get all the artefacts associated with a given room
     *
     * @param roomId, the primary key of a room
     * @return an array list with the artefacts as DTOs
     */
    @GetMapping({"/room", "/room/"})
    public ResponseEntity<ArrayList<ArtefactDto>> getAllArtefactsByRoom(@RequestParam int roomId) {
        ArrayList<Artefact> artefacts = artefactService.getAllArtefactsByRoom(roomId);
        // DTOs
        ArrayList<ArtefactDto> artefactDTOs = new ArrayList<>();
        for (Artefact artefact : artefacts) {
            artefactDTOs.add(new ArtefactDto(artefact));
        }
        return new ResponseEntity<ArrayList<ArtefactDto>>(artefactDTOs, HttpStatus.OK);
    }

    /**
     * Get all the artefacts associated with a given room and a given canLoan value
     *
     * @param roomId
     * @param canLoan
     * @return an array list with the artefacts as DTOs
     */
    @GetMapping({"/roomAndCanLoan", "/roomAndCanLoan/"})
    public ResponseEntity<ArrayList<ArtefactDto>> getAllArtefactsByRoomAndByCanLoan(@RequestParam int roomId, @RequestParam boolean canLoan) {
        ArrayList<Artefact> artefacts = artefactService.getAllArtefactsByRoomAndByCanLoan(roomId, canLoan);
        // DTOs
        ArrayList<ArtefactDto> artefactDTOs = new ArrayList<>();
        for (Artefact artefact : artefacts) {
            artefactDTOs.add(new ArtefactDto(artefact));
        }
        return new ResponseEntity<ArrayList<ArtefactDto>>(artefactDTOs, HttpStatus.OK);
    }

    /**
     * Get all artefacts in display
     *
     * @return an array list of artefacts as DTOs
     */
    @GetMapping({"/display", "/display/"})
    public ResponseEntity<ArrayList<ArtefactDto>> getAllArtefactsInDisplay() {
        ArrayList<Artefact> artefacts = artefactService.getAllArtefactsInDisplay();
        // DTOs
        ArrayList<ArtefactDto> artefactDTOs = new ArrayList<>();
        for (Artefact artefact : artefacts) {
            artefactDTOs.add(new ArtefactDto(artefact));
        }
        return new ResponseEntity<ArrayList<ArtefactDto>>(artefactDTOs, HttpStatus.OK);
    }

    /**
     * Get all artefacts in display with a specific canLoan value
     *
     * @param canLoan
     * @return an array list with the artefacts as DTOs
     */
    @GetMapping({"/display/canLoan", "/display/canLoan/"})
    public ResponseEntity<ArrayList<ArtefactDto>> getAllArtefactsInDisplayByCanLoan(@RequestParam boolean canLoan) {
        ArrayList<Artefact> artefacts = artefactService.getAllArtefactsInDisplayByCanLoan(canLoan);
        // DTOs
        ArrayList<ArtefactDto> artefactDTOs = new ArrayList<>();
        for (Artefact artefact : artefacts) {
            artefactDTOs.add(new ArtefactDto(artefact));
        }
        return new ResponseEntity<ArrayList<ArtefactDto>>(artefactDTOs, HttpStatus.OK);
    }

    /**
     * Create a new artefact based on an input request and add it to a room
     *
     * @param request
     * @return a response entity with the artefact as DTO and CREATED status
     */
    @PostMapping
    public ResponseEntity<ArtefactDto> createArtefact(@RequestBody ArtefactDto request) {
        // get parameters
        String name = request.getArtefactName();
        String description = request.getDescription();
        boolean canLoan = request.getCanLoan();
        double insuranceFee = request.getInsuranceFee();
        double loanFee = request.getLoanFee();
        int roomId = request.getRoomId();
        // create the artefact
        Artefact artefact = artefactService.createArtefact(name, description, canLoan, insuranceFee, loanFee);
        // add the artefact to a room
        artefactService.moveArtefactToRoom(artefact.getArtefactId(), roomId);
        // return it in the response entity
        return new ResponseEntity<ArtefactDto>(new ArtefactDto(artefact), HttpStatus.CREATED);
    }

    /**
     * Update an artefact based on an input request
     *
     * @param request
     * @return a response entity with the artefact as DTO and ok status
     */
    @PutMapping
    public ResponseEntity<ArtefactDto> updateArtefact(@RequestBody ArtefactDto request) {
        // get parameters
        int artefactId = request.getArtefactId();
        String name = request.getArtefactName();
        String description = request.getDescription();
        boolean canLoan = request.getCanLoan();
        double insuranceFee = request.getInsuranceFee();
        double loanFee = request.getLoanFee();
        // Update the artefact
        Artefact artefact = artefactService.updateArtefact(artefactId, name, description, canLoan, insuranceFee, loanFee);
        // return it in the response entity
        return new ResponseEntity<ArtefactDto>(new ArtefactDto(artefact), HttpStatus.OK);
    }

    /**
     * Move an artefact to another room
     *
     * @param artefactId
     * @param roomId
     * @return A message saying the artefact was moved
     */
    @PutMapping({"/move", "/move/"})
    public ResponseEntity<String> moveArtefactToRoom(@RequestParam int artefactId, @RequestParam int roomId) {
        artefactService.moveArtefactToRoom(artefactId, roomId);
        return new ResponseEntity<String>("Artefact successfully moved.", HttpStatus.OK);
    }

    /**
     * Delete an artefact given its primary key
     *
     * @param id
     * @return A message saying the artefact was deleted
     */
    @DeleteMapping({"/{id}", "/{id}/"})
    public ResponseEntity<String> deleteLoan(@PathVariable int id) {
        artefactService.deleteArtefact(id);
        return new ResponseEntity<String>("Artefact successfully deleted", HttpStatus.OK);
    }
}
