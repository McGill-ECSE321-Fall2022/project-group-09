package ca.mcgill.ecse.mmss.dao;

import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse.mmss.model.OpenDay;
import ca.mcgill.ecse.mmss.model.Ticket;
import ca.mcgill.ecse.mmss.model.Visitor;

public interface TicketRepository extends CrudRepository<Ticket, Integer> {
    Ticket findTicketByBookingId(int bookingId);

    ArrayList<Ticket> findByDate(OpenDay date);

    ArrayList<Ticket> findByVisitor(Visitor visitor);

    ArrayList<Ticket> findAll();
}