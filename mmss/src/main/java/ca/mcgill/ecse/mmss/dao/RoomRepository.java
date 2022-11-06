package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Room;

import java.util.ArrayList;


public interface RoomRepository extends CrudRepository<Room, Integer> {
    Room findRoomByRoomId(int roomId);
    ArrayList<Room> findAll();
}