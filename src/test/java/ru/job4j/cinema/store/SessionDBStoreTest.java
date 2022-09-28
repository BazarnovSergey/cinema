package ru.job4j.cinema.store;

import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Session;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class SessionDBStoreTest {

    @Test
    void whenFindAll() {
        SessionDBStore store = new SessionDBStore(new Main().loadPool());
        Session session = new Session(0, "session name", new byte[0]);
        store.add(session);
        List<Session> sessionList = store.findAll();
        assertThat(sessionList.get(0).getName(), is(session.getName()));
    }

    @Test
    void whenAdd() {
        SessionDBStore store = new SessionDBStore(new Main().loadPool());
        Session session = new Session(0, "session name", new byte[0]);
        store.add(session);
        Session sessionInDb = store.findById(session.getId());
        assertThat(sessionInDb.getName(), is(session.getName()));
    }

    @Test
    void whenFindById() {
        SessionDBStore store = new SessionDBStore(new Main().loadPool());
        Session session = new Session(0, "session name", new byte[0]);
        Session sessionTwo = new Session(1, "session name", new byte[0]);
        store.add(session);
        store.add(sessionTwo);
        Session sessionInDb = store.findById(session.getId());
        Session sessionTwoInDb = store.findById(sessionTwo.getId());
        assertThat(sessionInDb.getName(), is(session.getName()));
        assertThat(sessionTwoInDb.getName(), is(session.getName()));
    }
}