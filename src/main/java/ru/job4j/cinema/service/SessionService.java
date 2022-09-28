package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.SessionDBStore;
import ru.job4j.cinema.store.TicketDBStore;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
@Service
public class SessionService {

    private final static int ROW = 5;
    private final static int CELL = 10;

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

    public List<Integer> rows() {
        List<Integer> listRows = new ArrayList<>();
        for (int i = 1; i <= ROW; i++) {
            listRows.add(i);
        }
        return listRows;
    }

    public List<Integer> cells() {
        List<Integer> listCells = new ArrayList<>();
        for (int i = 1; i <= CELL; i++) {
            listCells.add(i);
        }
        return listCells;
    }

    private List<Ticket> getByPosRowAndMovieId(int sessionId, int posRow) {
        return ticketDBStore.purchasedTicketsInRow(sessionId, posRow);
    }

    public List<Integer> getFreeCells(int sessionId, int posRow) {
        List<Integer> cells = cells();
        getByPosRowAndMovieId(sessionId, posRow).forEach(x -> {
            if (cells.contains(x.getCell())) {
                cells.remove(Integer.valueOf(x.getCell()));
            }
        });
        return cells;
    }

}
