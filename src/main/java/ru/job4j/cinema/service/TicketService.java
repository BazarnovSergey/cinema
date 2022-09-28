package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.TicketDBStore;

@ThreadSafe
@Service
public class TicketService {

    private final TicketDBStore ticketDBStore;

    public TicketService(TicketDBStore ticketDBStore) {
        this.ticketDBStore = ticketDBStore;
    }

    public Ticket add(Ticket ticket) {
        return ticketDBStore.add(ticket);
    }

    public Ticket findBySessionIdAndRowAndCell(int sessionId, int row, int cell) {
        return ticketDBStore.findBySessionIdAndRowAndCell(sessionId, row, cell);
    }

}
