package ca.mcgill.ecse.mmss.dao;

import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Integer> {
    Ticket findTicketByBookingId(int bookingId);
    ArrayList<Ticket> findAll();
}