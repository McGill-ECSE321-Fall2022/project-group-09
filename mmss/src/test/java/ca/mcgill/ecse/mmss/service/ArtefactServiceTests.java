package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.RoomRepository;
import ca.mcgill.ecse.mmss.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse.mmss.exception.MmssException;

@ExtendWith(MockitoExtension.class)
public class ArtefactServiceTests {

    @Mock
    private ArtefactRepository artefactRepository;
    @InjectMocks
    private ArtefactService artefactService;
    // Acts like the DB
    ArrayList<Artefact> artefacts;
    Room smallRoom;
    Room largeRoom;
    Room storage;

    /**
     * @author: Sasha Denouvilliez-Pech
     * Create the rooms and the artefacts needed by all the tests
     */
    @BeforeEach
    public void createObjects() {
        // Create rooms
        smallRoom = new Room(1, Room.RoomType.Small);
        largeRoom = new Room(2, Room.RoomType.Large);
        storage = new Room(3, Room.RoomType.Storage);
        // Create artefacts
        artefacts = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Artefact artefact = new Artefact();
            artefact.setArtefactId(i);
            if (i % 3 == 0)
                artefact.setRoom(smallRoom);
            else if (i % 3 == 1)
                artefact.setRoom(largeRoom);
            else
                artefact.setRoom(storage);
            // Store in array list
            artefacts.add(artefact);
        }
    }

    /**
     * Delete the rooms and the artefacts
     */
    @AfterEach
    public void deleteDelete(){
        smallRoom.delete();
        largeRoom.delete();
        storage.delete();
        artefacts.clear();
    }

    /**
     * Test retrieving an artefact with a valid id
     */
    @Test
    public void testRetrieveArtefactById() {
        // setup mocks
        when(artefactRepository.findArtefactByArtefactId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> artefacts.get(0));
        // call service layer
        Artefact artefact = artefactService.getArtefactById(1);
        // assertion
        assertEquals(artefacts.get(0), artefact);
        // verify calls to repositories
        verify(artefactRepository, times (1)).findArtefactByArtefactId(any(int.class));
    }

    /**
     * Test retrieving a room with an invalid id
     */
    @Test
    public void testRetrieveArtefactByInvalidId() {
        final int invalidId = 99;
        // setup mocks
        when(artefactRepository.findArtefactByArtefactId(invalidId)).thenAnswer((InvocationOnMock invocation) -> null);
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> artefactService.getArtefactById(invalidId));
        // assertion
        assertEquals("Artefact not found", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        // verify calls to repositories
        verify(artefactRepository, times (1)).findArtefactByArtefactId(invalidId);
    }
}
