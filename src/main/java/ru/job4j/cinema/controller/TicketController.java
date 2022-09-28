package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;
import ru.job4j.cinema.service.TicketService;
import ru.job4j.cinema.util.CheckHttpSession;

import javax.servlet.http.HttpSession;

@Controller
public class TicketController {

    private final TicketService ticketService;
    private final SessionService sessionService;

    public TicketController(TicketService ticketService, SessionService sessionService) {
        this.ticketService = ticketService;
        this.sessionService = sessionService;
    }

    @PostMapping("/createTicket")
    public String createTicket(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        Ticket sessionTicket = (Ticket) httpSession.getAttribute("ticket");
        CheckHttpSession.checkUserAuthorization(model, user);
        sessionTicket.setUserId(user.getId());
        Ticket tempTicket = ticketService.findBySessionIdAndRowAndCell(
                sessionTicket.getSessionId(),
                sessionTicket.getRow(),
                sessionTicket.getCell());
        if (tempTicket != null
                && sessionTicket.getSessionId() == tempTicket.getSessionId()
                && sessionTicket.getRow() == tempTicket.getRow()
                && sessionTicket.getCell() == tempTicket.getCell()
        ) {
            return "failPurchase";
        } else {
            ticketService.add(sessionTicket);
            return "successfulPurchase";
        }

    }

    @GetMapping("/showInfo")
    public String showInfo(Model model,
                           HttpSession httpSession,
                           @ModelAttribute("ticket") Ticket ticket) {
        User user = (User) httpSession.getAttribute("user");
        Ticket sessionTicket = (Ticket) httpSession.getAttribute("ticket");
        sessionTicket.setCell(ticket.getCell());
        model.addAttribute("user", user);
        model.addAttribute("movie", sessionService.findById(sessionTicket.getSessionId()));
        return "showInfo";
    }
}