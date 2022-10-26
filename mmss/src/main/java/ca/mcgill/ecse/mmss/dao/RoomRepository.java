package ca.mcgill.ecse.mmss.dao;
import org.springframework.data.repository.CrudRepository;
package ca.mcgill.ecse.mmss.model.Room;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    Room findRoomByRoomId(int roomId);
}