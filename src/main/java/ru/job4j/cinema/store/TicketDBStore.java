package ru.job4j.cinema.store;

import net.jcip.annotations.ThreadSafe;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Repository
public class TicketDBStore {
    private final BasicDataSource pool;

    public TicketDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    private static final Logger LOG = LoggerFactory.getLogger(TicketDBStore.class.getName());

    public Optional<Ticket> add(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO ticket (session_id,pos_row,cell,user_id) VALUES (?,?,?,?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getRow());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getUserId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("exception: ", e);
            return Optional.empty();
        }
        return Optional.of(ticket);
    }

    /**
     * @param sessionId - id сессии
     * @param row       - ряд
     * @return возвращает коллекцию List с оплаченными билетами в ряду
     */
    public List<Ticket> findBySessionIdAndRow(int sessionId, int row) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM ticket WHERE session_id = ? AND pos_row = ?")
        ) {
            ps.setInt(1, sessionId);
            ps.setInt(2, row);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(getTicketFromResultSet(it));
                }
            }
        } catch (Exception e) {
            LOG.error("exception: ", e);
        }
        return tickets;
    }

    private Ticket getTicketFromResultSet(ResultSet it) throws SQLException {
        return new Ticket(it.getInt("id"),
                it.getInt("session_id"),
                it.getInt("pos_row"),
                it.getInt("cell"),
                it.getInt("user_id"));
    }

}
