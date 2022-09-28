package ru.job4j.cinema.model;

import java.util.Objects;

public class Ticket {

    private int id;
    private int sessionId;
    private int row;
    private int cell;
    private int userId;

    public Ticket() {
    }

    public Ticket(int id, int sessionId, int row, int cell, int userId) {
        this.id = id;
        this.sessionId = sessionId;
        this.row = row;
        this.cell = cell;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id && sessionId == ticket.sessionId && row == ticket.row && cell == ticket.cell && userId == ticket.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sessionId, row, cell, userId);
    }

}

