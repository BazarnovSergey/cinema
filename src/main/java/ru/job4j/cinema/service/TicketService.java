package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.TicketDBStore;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
public class TicketService {

    private final TicketDBStore ticketDBStore;

    public TicketService(TicketDBStore ticketDBStore) {
        this.ticketDBStore = ticketDBStore;
    }

    public Optional<Ticket> add(Ticket ticket) {
        return ticketDBStore.add(ticket);
    }

    public List<Ticket> getByPosRowAndMovieId(int sessionId, int posRow) {
        return ticketDBStore.findBySessionIdAndRow(sessionId, posRow);
    }

}
