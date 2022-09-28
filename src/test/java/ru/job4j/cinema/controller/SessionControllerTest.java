package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.model.Session;
import ru.job4j.cinema.service.SessionService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class SessionControllerTest {

    @Test
    public void whenSessions() {
        List<Session> sessions = Arrays.asList(
                new Session(1, "New session", new byte[0]),
                new Session(2, "New session", new byte[0])
        );
        Model model = mock(Model.class);
        SessionService sessionService = mock(SessionService.class);
        HttpSession httpSession = mock(HttpSession.class);
        when(sessionService.findAll()).thenReturn(sessions);
        SessionController sessionController = new SessionController(
                sessionService
        );
        String page = sessionController.sessions(model, httpSession);
        verify(model).addAttribute("sessions", sessions);
        assertThat(page, is("sessions"));
    }

    @Test
    public void whenCreateSession() throws IOException {
        Session input = new Session(0, "session name", new byte[0]);
        SessionService sessionService = mock(SessionService.class);
        MultipartFile file = mock(MultipartFile.class);
        SessionController sessionController = new SessionController(
                sessionService
        );
        String page = sessionController.createSession(input, file);
        verify(sessionService).add(input);
        assertThat(page, is("redirect:/sessions"));
    }

    @Test
    public void whenAddSession() {
        Session input = new Session(0, "session name", new byte[0]);
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        SessionService sessionService = mock(SessionService.class);
        SessionController sessionController = new SessionController(
                sessionService
        );
        String page = sessionController.addSession(model, httpSession);
        verify(model).addAttribute("session", input);
        assertThat(page, is("addSession"));
    }

}