package ru.job4j.cinema.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.SessionService;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

import static ru.job4j.cinema.util.CheckHttpSession.checkUserAuthorization;

@Controller
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/sessions")
    public String sessions(Model model, HttpSession httpSession) {
        model.addAttribute("sessions", sessionService.findAll());
        User user = (User) httpSession.getAttribute("user");
        checkUserAuthorization(model, user);
        model.addAttribute("user", user);
        return "sessions";
    }

    @GetMapping("/formChoiceRow/{sessionId}")
    public String formChoiceRow(Model model,
                                @PathVariable("sessionId") int id,
                                HttpSession httpSession) {
        Ticket sessionTicket = new Ticket();
        Optional<Session> session = sessionService.findById(id);
        if (session.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        sessionTicket.setSessionId(id);
        httpSession.setAttribute("ticket", sessionTicket);
        model.addAttribute("session", session.get());
        model.addAttribute("rows", sessionService.getRowNumbers());
        User user = (User) httpSession.getAttribute("user");
        checkUserAuthorization(model, user);
        model.addAttribute("user", user);
        return "choiceSession";
    }

    @GetMapping("/formAddSession")
    public String addSession(Model model, HttpSession httpSession) {
        model.addAttribute("session", new Session(0, "session name", new byte[0]));
        User user = (User) httpSession.getAttribute("user");
        checkUserAuthorization(model, user);
        model.addAttribute("user", user);
        return "addSession";
    }

    @PostMapping("/createSession")
    public String createSession(@ModelAttribute Session session,
                                @RequestParam("file") MultipartFile file) throws IOException {
        session.setPoster(file.getBytes());
        sessionService.add(session);
        return "redirect:/sessions";
    }

    @GetMapping("/posterSession/{sessionId}")
    public ResponseEntity<Resource> download(@PathVariable("sessionId") Integer sessionId) {
        Optional<Session> session = sessionService.findById(sessionId);
        if (session.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return session.<ResponseEntity<Resource>>map(value -> ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(value.getPoster().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(value.getPoster()))).orElse(null);
    }

    @GetMapping("/formChoiceCell")
    public String formChoiceCell(Model model, HttpSession httpSession,
                                 @ModelAttribute("ticket") Ticket ticket) {
        Ticket sessionTicket = (Ticket) httpSession.getAttribute("ticket");
        model.addAttribute("freeCells", sessionService.getFreeCells(sessionTicket.getSessionId(),
                ticket.getRow()));
        sessionTicket.setRow(ticket.getRow());
        User user = (User) httpSession.getAttribute("user");
        checkUserAuthorization(model, user);
        model.addAttribute("user", user);
        return "choiceCell";
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException() {
        return "404";
    }
}