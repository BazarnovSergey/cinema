package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.job4j.cinema.config.DataSourceConfig;
import ru.job4j.cinema.model.Session;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class SessionDBStoreTest {

    @Test
    void whenFindAll() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        BasicDataSource pool = context.getBean("loadPool",BasicDataSource.class);
        SessionDBStore store = new SessionDBStore(pool);
        Session session = new Session(0, "session name", new byte[0]);
        store.add(session);
        List<Session> sessionList = store.findAll();
        assertThat(sessionList.get(0).getName(), is(session.getName()));
    }

    @Test
    void whenAdd() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        BasicDataSource pool = context.getBean("loadPool",BasicDataSource.class);
        SessionDBStore store = new SessionDBStore(pool);
        Session session = new Session(0, "session name", new byte[0]);
        store.add(session);
        Session sessionInDb = store.findById(session.getId());
        assertThat(sessionInDb.getName(), is(session.getName()));
    }

    @Test
    void whenFindById() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DataSourceConfig.class);
        BasicDataSource pool = context.getBean("loadPool",BasicDataSource.class);
        SessionDBStore store = new SessionDBStore(pool);
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