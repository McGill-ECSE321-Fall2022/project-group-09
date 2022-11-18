package ca.mcgill.ecse.mmss.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import ca.mcgill.ecse.mmss.dao.ArtefactRepository;
import ca.mcgill.ecse.mmss.dao.LoanRepository;
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

/**
 * Tests for the ArtefactService class
 */
@ExtendWith(MockitoExtension.class)
public class ArtefactServiceTests {

    @Mock
    private ArtefactRepository artefactRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private RoomService roomService;
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
            if (i % 3 == 0) {
                artefact.setRoom(smallRoom);
                smallRoom.setArtefactCount(smallRoom.getArtefactCount() + 1);
            }
            else if (i % 3 == 1) {
                artefact.setRoom(largeRoom);
                largeRoom.setArtefactCount(largeRoom.getArtefactCount() + 1);
            }
            else {
                artefact.setRoom(storage);
                storage.setArtefactCount(storage.getArtefactCount() + 1);
            }
            setCanLoan();
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

    /**
     * Test to get all artefacts in the museum
     */
    @Test
    public void testGetAllArtefacts() {
        // setup mocks
        when(artefactRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> artefacts);
        // call service layer
        ArrayList<Artefact> artefactsTest = artefactService.getAllArtefacts();
        // assertion
        assertEquals(artefacts, artefactsTest);
        // Verify
        verify(artefactRepository, times(1)).findAll();
    }

    /**
     * Test to get all artefacts in the museum that can be loaned
     */
    @Test
    public void testGetAllArtefactsByCanLoanTrue() {
        ArrayList<Artefact> artefactsTest = getArtefactsByCanLoanTest(artefacts, true);
        // setup mocks
        when(artefactRepository.findAllByCanLoan(true)).thenAnswer((InvocationOnMock invocation) -> artefactsTest);
        // call service layer
        ArrayList<Artefact> artefactsService = artefactService.getAllArtefactsByCanLoan(true);
        // assertion
        assertEquals(artefactsTest.size(), artefactsService.size());
        // Verify
        verify(artefactRepository, times(1)).findAllByCanLoan(true);
    }

    /**
     * Test to get all artefacts in the storage
     */
    @Test
    public void testGetAllArtefactsInStorage() {
        ArrayList<Artefact> artefactsTest = getArtefactsByRoomTest(Room.RoomType.Storage);
        // setup mocks
        when(roomService.retrieveRoomById(any(int.class))).thenAnswer((InvocationOnMock invocation) -> storage);
        when(artefactRepository.findAllByRoom(any(Room.class))).thenAnswer((InvocationOnMock invocation) -> artefactsTest);
        // call service layer
        ArrayList<Artefact> artefactsService = artefactService.getAllArtefactsByRoom(3);
        // assertion
        assertEquals(artefactsTest.size(), artefactsService.size());
        // Verify
        verify(artefactRepository, times(1)).findAllByRoom(any(Room.class));
        verify(roomService, times(1)).retrieveRoomById(any(int.class));
    }

    /**
     * Test to get all artefacts not available for loan and in the large room
     */
    @Test
    public void testGetAllArtefactsNoLoanAndInLargeRoom() {
        ArrayList<Artefact> artefactsTest = getArtefactsByRoomTest(Room.RoomType.Large);
        // setup mocks
        when(roomService.retrieveRoomById(any(int.class))).thenAnswer((InvocationOnMock invocation) -> largeRoom);
        when(artefactRepository.findAllByRoomAndCanLoan(any(Room.class), any(boolean.class))).thenAnswer((InvocationOnMock invocation) -> artefactsTest);
        // call service layer
        ArrayList<Artefact> artefactsService = artefactService.getAllArtefactsByRoomAndByCanLoan(2, false);
        // assertion
        assertEquals(artefactsTest.size(), artefactsService.size());
        // Verify
        verify(artefactRepository, times(1)).findAllByRoomAndCanLoan(any(Room.class), any(boolean.class));
        verify(roomService, times(1)).retrieveRoomById(any(int.class));
    }

    /**
     * Test to get all artefacts in display
     */
    @Test
    public void testGetAllArtefactsInDisplay() {
        ArrayList<Artefact> artefactsInLarge = getArtefactsByRoomTest(Room.RoomType.Large);
        ArrayList<Artefact> artefactsInSmall = getArtefactsByRoomTest(Room.RoomType.Small);
        ArrayList<Artefact> artefactsInDisplay = new ArrayList<>();
        artefactsInDisplay.addAll(artefactsInSmall);
        artefactsInDisplay.addAll(artefactsInLarge);
        ArrayList<Room> smallRooms = new ArrayList<>();
        smallRooms.add(smallRoom);
        ArrayList<Room> largeRooms = new ArrayList<>();
        largeRooms.add(largeRoom);
        // setup mocks
        when(roomRepository.findAllByRoomType(Room.RoomType.Small)).thenAnswer((InvocationOnMock invocation) -> smallRooms);
        when(roomRepository.findAllByRoomType(Room.RoomType.Large)).thenAnswer((InvocationOnMock invocation) -> largeRooms);
        when(artefactService.getAllArtefactsByRoom(smallRoom.getRoomId())).thenAnswer((InvocationOnMock invocation) -> artefactsInSmall);
        when(artefactService.getAllArtefactsByRoom(largeRoom.getRoomId())).thenAnswer((InvocationOnMock invocation) -> artefactsInLarge);
        // call service layer
        ArrayList<Artefact> artefactsService = artefactService.getAllArtefactsInDisplay();
        // assertion
        assertEquals(artefactsInDisplay.size(), artefactsService.size());
        // Verify
        verify(roomRepository, times(2)).findAllByRoomType(any(Room.RoomType.class));
    }

    /**
     * Test to get all artefacts in display available for loan
     */
    @Test
    public void testGetAllArtefactsInDisplayWithCanLoan() {
        ArrayList<Artefact> artefactsInLargeCanLoan = getArtefactsByCanLoanTest(getArtefactsByRoomTest(Room.RoomType.Large), true);
        ArrayList<Artefact> artefactsInSmallCanLoan = getArtefactsByCanLoanTest(getArtefactsByRoomTest(Room.RoomType.Small), true);
        ArrayList<Artefact> artefactsInDisplayCanLoan = new ArrayList<>();
        artefactsInDisplayCanLoan.addAll(artefactsInSmallCanLoan);
        artefactsInDisplayCanLoan.addAll(artefactsInLargeCanLoan);
        ArrayList<Room> smallRooms = new ArrayList<>();
        smallRooms.add(smallRoom);
        ArrayList<Room> largeRooms = new ArrayList<>();
        largeRooms.add(largeRoom);
        // setup mocks
        when(roomRepository.findAllByRoomType(Room.RoomType.Small)).thenAnswer((InvocationOnMock invocation) -> smallRooms);
        when(roomRepository.findAllByRoomType(Room.RoomType.Large)).thenAnswer((InvocationOnMock invocation) -> largeRooms);
        when(artefactService.getAllArtefactsByRoomAndByCanLoan(smallRoom.getRoomId(), true)).thenAnswer((InvocationOnMock invocation) -> artefactsInSmallCanLoan);
        when(artefactService.getAllArtefactsByRoomAndByCanLoan(largeRoom.getRoomId(), true)).thenAnswer((InvocationOnMock invocation) -> artefactsInLargeCanLoan);
        // call service layer
        ArrayList<Artefact> artefactsService = artefactService.getAllArtefactsInDisplayByCanLoan(true);
        // assertion
        assertEquals(artefactsInDisplayCanLoan.size(), artefactsService.size());
        // Verify
        verify(roomRepository, times(2)).findAllByRoomType(any(Room.RoomType.class));
    }

    /**
     * Test to check params with empty name
     */
    @Test
    public void testForEmptyName() {
        // Params
        String name = " ";
        String description = "Hello";
        boolean canLoan = false;
        double insuranceFee = 0;
        double loanFee = 0;
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> artefactService.checkValidArtefactParams(name, description, canLoan, insuranceFee, loanFee));
        // assertion
        assertEquals("The artefact’s name cannot be empty or longer than 50 characters.", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    /**
     * Test to check params with name to long
     */
    @Test
    public void testForInvalidName() {
        // Params
        String name = "aaanefjbjedbvjksnkdslhjflskdjhflaksdbvlkjasbfljkdsbfljdsab";
        String description = "Hello";
        boolean canLoan = false;
        double insuranceFee = 0;
        double loanFee = 0;
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> artefactService.checkValidArtefactParams(name, description, canLoan, insuranceFee, loanFee));
        // assertion
        assertEquals("The artefact’s name cannot be empty or longer than 50 characters.", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    /**
     * Test to check params with description to long
     */
    @Test
    public void testForInvalidDescription() {
        // Params
        String name = "hello";
        String description = "hdfjhlsadbfjshhflkjashflksuaghfhsjdlhfjkhlsghfsdjldgf" +
                "jdhsbfhljsdbfljhsbfdkjhsbfkjhsdhbfkjhsbdfkjhsdbfkjhsadbfkjhsdbfjkhsb" +
                "dhvfkwjhfvbdlsjhbflsjhbflsjfdbsdlkjhbfsjkvbdfkjhsdbvflhjsdbvflhjsdbflhjs" +
                "jhfvdsaljkhfblsdbdflhjsdbflkjsbflkjsbkjlsdbfljkasbflkjsbflkjsbfljksdbdflkjs" +
                "fkjdshbfljhsdbflhjsdbflhjdskhbfhjksdbfhkjsdbfkhjsdbdfkjhsdbfkjhdsbfkjhfa" +
                "dshfblasdjbfosdhbhfjhksdbfosdhfljhsdbfjhosdhfjhosdghfhjsdgfkjhsdgfkjhsdgf" +
                "sdlkfblabflsdbflksbflkdsbflkdsbflkdsbflkjdsbflksdbflksdbflkdsjbnfkjdsbfljhsd" +
                "sdufghbsljhbfsdhjobflsjhdbfjlhsdbfhjsdbfjhlsdbfkljsdbf;sdkj;bfldksbfslkbfl";
        boolean canLoan = false;
        double insuranceFee = 0;
        double loanFee = 0;
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> artefactService.checkValidArtefactParams(name, description, canLoan, insuranceFee, loanFee));
        // assertion
        assertEquals("The artefact’s description cannot be empty or longer than 300 characters.", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    /**
     * Test to check params with no fees if canLoan is true
     */
    @Test
    public void testForCanLoanAndInvalidFees() {
        // Params
        String name = "hello";
        String description = "Hello";
        boolean canLoan = true;
        double insuranceFee = 1;
        double loanFee = 0;
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> artefactService.checkValidArtefactParams(name, description, canLoan, insuranceFee, loanFee));
        // assertion
        assertEquals("If the artefact is available for loan, the fees cannot be $0.", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }

    /**
     * Test to create an artefact
     */
    @Test
    public void testCreateArtefact() {
        // setup mocks
        when(artefactRepository.save(any(Artefact.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        // call service layer
        Artefact artefact = artefactService.createArtefact("Hello", "World", true, 2, 3);
        // assertion
        assertEquals("Hello", artefact.getArtefactName());
        assertEquals("World", artefact.getDescription());
        assertTrue(artefact.getCanLoan());
        assertEquals(2, artefact.getInsuranceFee());
        assertEquals(3, artefact.getLoanFee());
        // Verify
        verify(artefactRepository, times(1)).save(any(Artefact.class));
    }

    /**
     * Test to update an artefact
     */
    @Test
    public void testUpdateArtefact() {
        Artefact artefactTest = new Artefact( 10, "Hello Jack", "World is ours", false, 0, 0);
        artefacts.add(artefactTest);
        // setup mocks
        when(artefactRepository.findArtefactByArtefactId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> artefactTest);
        when(artefactRepository.save(any(Artefact.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        // call service layer
        Artefact artefact = artefactService.updateArtefact(10,"Hello", "World", true, 2, 3);
        // assertion
        assertEquals("Hello", artefact.getArtefactName());
        assertEquals("World", artefact.getDescription());
        assertTrue(artefact.getCanLoan());
        assertEquals(2, artefact.getInsuranceFee());
        assertEquals(3, artefact.getLoanFee());
        // Verify
        verify(artefactRepository, times(1)).findArtefactByArtefactId(any(int.class));
        verify(artefactRepository, times(1)).save(any(Artefact.class));
    }

    /**
     * Test to remove an artefact from the small room
     */
    @Test
    public void testRemoveArtefactFromSmallRoom() {
        Artefact artefact = getArtefactsByRoomTest(Room.RoomType.Small).get(0);
        // setup mocks
        when(artefactRepository.findArtefactByArtefactId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> artefact);
        when(artefactRepository.save(any(Artefact.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        when(roomRepository.save(any(Room.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        // call service layer
        artefactService.removeArtefactFromItsRoom(artefact.getArtefactId());
        // assertion
        assertNull(artefact.getRoom());
        assertEquals(3-1, smallRoom.getArtefactCount());
        // Verify
        verify(artefactRepository, times(1)).findArtefactByArtefactId(any(int.class));
        verify(artefactRepository, times(1)).save(any(Artefact.class));
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    /**
     * Test add an artefact to a full small room
     */
    @Test
    public void testAddArtefactToFullSmallRoom() {
        Artefact artefact = artefacts.get(0);
        artefact.setRoom(null);
        smallRoom.setArtefactCount(smallRoom.getArtefactCount() - 1);
        // setup mocks
        when(artefactRepository.findArtefactByArtefactId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> artefact);
        when(roomService.retrieveRoomById(any(int.class))).thenAnswer((InvocationOnMock invocation) -> smallRoom);
        when(roomService.isRoomFull(any(int.class))).thenAnswer((InvocationOnMock invocation) -> true);
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> artefactService.addArtefactToRoom(artefact.getArtefactId(), smallRoom.getRoomId()));
        // assertion
        assertEquals("The destination room is already full.", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        // verify calls to repositories
        verify(artefactRepository, times (1)).findArtefactByArtefactId(any(int.class));
        verify(roomService, times (1)).retrieveRoomById(any(int.class));
        verify(roomService, times (1)).isRoomFull(any(int.class));
    }

    /**
     * Test add an artefact to a full display
     */
    @Test
    public void testAddArtefactToFullDisplay() {
        Artefact artefact = artefacts.get(0);
        artefact.setRoom(null);
        smallRoom.setArtefactCount(smallRoom.getArtefactCount() - 1);
        // setup mocks
        when(artefactRepository.findArtefactByArtefactId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> artefact);
        when(roomService.retrieveRoomById(any(int.class))).thenAnswer((InvocationOnMock invocation) -> smallRoom);
        when(roomService.isRoomFull(any(int.class))).thenAnswer((InvocationOnMock invocation) -> false);
        when(roomService.getDisplayCapacity()).thenAnswer((InvocationOnMock invocation) -> 1000);
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> artefactService.addArtefactToRoom(artefact.getArtefactId(), smallRoom.getRoomId()));
        // assertion
        assertEquals("The display is already full.", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        // verify calls to repositories
        verify(artefactRepository, times (1)).findArtefactByArtefactId(any(int.class));
        verify(roomService, times (1)).retrieveRoomById(any(int.class));
        verify(roomService, times (1)).isRoomFull(any(int.class));
        verify(roomService, times (1)).getDisplayCapacity();
    }

    /**
     * Test add an artefact to a room
     */
    @Test
    public void testAddArtefactToSmallRoom() {
        Artefact artefact = artefacts.get(0);
        artefact.setRoom(null);
        smallRoom.setArtefactCount(smallRoom.getArtefactCount() - 1);
        // setup mocks
        when(artefactRepository.findArtefactByArtefactId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> artefact);
        when(roomService.retrieveRoomById(any(int.class))).thenAnswer((InvocationOnMock invocation) -> smallRoom);
        when(roomService.isRoomFull(any(int.class))).thenAnswer((InvocationOnMock invocation) -> false);
        when(roomService.getDisplayCapacity()).thenAnswer((InvocationOnMock invocation) -> 5); // 6 - 1
        when(roomRepository.save(any(Room.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        when(artefactRepository.save(any(Artefact.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        // call service layer
        artefactService.addArtefactToRoom(artefact.getArtefactId(), smallRoom.getRoomId());
        // assertion
        assertEquals(Room.RoomType.Small, artefact.getRoom().getRoomType());
        assertEquals(3, smallRoom.getArtefactCount());
        // verify calls to repositories
        verify(artefactRepository, times (1)).findArtefactByArtefactId(any(int.class));
        verify(roomService, times (1)).retrieveRoomById(any(int.class));
        verify(roomService, times (1)).isRoomFull(any(int.class));
        verify(roomService, times (1)).getDisplayCapacity();
        verify(artefactRepository, times (1)).save(any(Artefact.class));
        verify(roomRepository, times (1)).save(any(Room.class));
    }

    /**
     * Test to move an artefact to the same small room
     */
    @Test
    public void testMoveArtefactToSameSmallRoom() {
        Artefact artefact = artefacts.get(2);
        // setup mocks
        when(artefactRepository.findArtefactByArtefactId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> artefact);
        when(roomService.retrieveRoomById(any(int.class))).thenAnswer((InvocationOnMock invocation) -> smallRoom);
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> artefactService.moveArtefactToRoom(artefact.getArtefactId(), smallRoom.getRoomId()));
        // assertion
        assertEquals("The destination and current rooms cannot be the same.", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
        // verify calls to repositories
        verify(artefactRepository, times (1)).findArtefactByArtefactId(any(int.class));
        verify(roomService, times (1)).retrieveRoomById(any(int.class));
    }

    /**
     * Test to move an artefact without room to a small room
     */
    @Test
    public void testMoveArtefactToRoomNoPriorRoom() {
        Artefact artefact = new Artefact();
        artefact.setArtefactId(10);
        // setup mocks
        when(artefactRepository.findArtefactByArtefactId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> artefact);
        when(roomService.retrieveRoomById(any(int.class))).thenAnswer((InvocationOnMock invocation) -> smallRoom);
        // call service layer
        artefactService.moveArtefactToRoom(artefact.getArtefactId(), smallRoom.getRoomId());
        // assertion
        assertEquals(Room.RoomType.Small, artefact.getRoom().getRoomType());
        assertEquals(3 + 1, smallRoom.getArtefactCount());
        // verify calls to repositories
        verify(artefactRepository, times (2)).findArtefactByArtefactId(any(int.class));
        verify(roomService, times (2)).retrieveRoomById(any(int.class));
    }

    /**
     * Test to move an artefact in large room to a small room
     */
    @Test
    public void testMoveArtefactFromLargeToSmallRoom() {
        Artefact artefact = artefacts.get(0);
        // setup mocks
        when(artefactRepository.findArtefactByArtefactId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> artefact);
        when(roomService.retrieveRoomById(any(int.class))).thenAnswer((InvocationOnMock invocation) -> smallRoom);
        // call service layer
        artefactService.moveArtefactToRoom(artefact.getArtefactId(), smallRoom.getRoomId());
        // assertion
        assertEquals(Room.RoomType.Small, artefact.getRoom().getRoomType());
        assertEquals(3 + 1, smallRoom.getArtefactCount());
        assertEquals(3 - 1,  largeRoom.getArtefactCount());
        // verify calls to repositories
        verify(artefactRepository, times (3)).findArtefactByArtefactId(any(int.class));
        verify(roomService, times (2)).retrieveRoomById(any(int.class));
    }

    /**
     * Test to delete an artefact to is associated with a loan
     */
    @Test
    public void testDeleteArtefactAssociatedWithLoan() {
        Artefact artefact = artefacts.get(0);
        ArrayList<Loan> loans = new ArrayList<>();
        loans.add(new Loan());
        // setup mocks
        when(artefactRepository.findArtefactByArtefactId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> artefact);
        when(loanRepository.findAllByArtefact(any(Artefact.class))).thenAnswer((InvocationOnMock invocation) -> loans);
        // call service layer and get the exception
        MmssException ex = assertThrows(MmssException.class, () -> artefactService.deleteArtefact(1));
        // assertion
        assertEquals("The artefact is loaned or a loan request for this artefact is pending.", ex.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        // verify calls to repositories
        verify(artefactRepository, times (1)).findArtefactByArtefactId(any(int.class));
        verify(loanRepository, times (1)).findAllByArtefact(any(Artefact.class));
    }

    /**
     * Test to delete an artefact
     */
    @Test
    public void testDeleteArtefact() {
        Artefact artefact = artefacts.get(0);
        // setup mocks
        when(artefactRepository.findArtefactByArtefactId(any(int.class))).thenAnswer((InvocationOnMock invocation) -> artefact);
        // call service layer and get the exception
        artefactService.deleteArtefact(1);
        // assertion
        assertEquals(3 - 1, largeRoom.getArtefactCount());
        // verify calls to repositories
        verify(artefactRepository, times (2)).findArtefactByArtefactId(any(int.class));
    }

    /**
     * Helper method to set the artefacts to a particular loan value
     */
    private void setCanLoan() {
        for (Artefact artefact : artefacts) {
            if (artefact.getArtefactId() % 2 == 0)
                artefact.setCanLoan(true);
            else
                artefact.setCanLoan(false);
        }
    }

    /**
     * Helper method to get all artefacts by canLoan value
     * @param canLoan
     * @return an array list of artefacts with a given canLoan value
     */
    private ArrayList<Artefact> getArtefactsByCanLoanTest(ArrayList<Artefact> artefactList, boolean canLoan) {
        ArrayList<Artefact> artefacts1 = new ArrayList<>();
        for (Artefact artefact : artefactList) {
            if (artefact.getCanLoan() == canLoan)
                artefacts1.add(artefact);
        }
        return artefacts1;
    }

    /**
     * Helper method to get all artefacts from a room
      * @param type
     * @return an array list of artefacts for a given room
     */
    private ArrayList<Artefact> getArtefactsByRoomTest(Room.RoomType type) {
        ArrayList<Artefact> artefacts1 = new ArrayList<>();
        for (Artefact artefact : artefacts) {
            if (artefact.getRoom().getRoomType() == type)
                artefacts1.add(artefact);
        }
        return artefacts1;
    }
}
