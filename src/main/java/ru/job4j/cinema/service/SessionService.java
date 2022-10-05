package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.SessionDBStore;
import ru.job4j.cinema.store.TicketDBStore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@ThreadSafe
@Service
public class SessionService {

    private final static List<Integer> ROW_NUMBERS = IntStream.rangeClosed(1, 5).boxed().toList();
    private final static List<Integer> CELL_NUMBERS = IntStream.rangeClosed(1, 10).boxed().toList();

    private final SessionDBStore sessionDBStore;
    private final TicketDBStore ticketDBStore;

    public SessionService(SessionDBStore sessionDBStore, TicketDBStore ticketDBStore) {
        this.sessionDBStore = sessionDBStore;
        this.ticketDBStore = ticketDBStore;
    }

    public List<Session> findAll() {
        return sessionDBStore.findAll();
    }

    public Session add(Session session) {
        return sessionDBStore.add(session);
    }

    public Session findById(int id) {
        return sessionDBStore.findById(id);
    }

    public List<Integer> getRowNumbers() {
        return new ArrayList<>(ROW_NUMBERS);
    }

    public List<Integer> getCellNumbers() {
        return new ArrayList<>(CELL_NUMBERS);
    }

    private List<Ticket> getByPosRowAndMovieId(int sessionId, int posRow) {
        return ticketDBStore.findBySessionIdAndRow(sessionId, posRow);
    }

    public List<Integer> getFreeCells(int sessionId, int posRow) {
        List<Integer> cells = getCellNumbers();
        getByPosRowAndMovieId(sessionId, posRow).forEach(x -> {
            if (cells.contains(x.getCell())) {
                cells.remove(Integer.valueOf(x.getCell()));
            }
        });
        return cells;
    }

}
