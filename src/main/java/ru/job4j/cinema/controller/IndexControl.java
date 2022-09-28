package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.model.User;

import javax.servlet.http.HttpSession;

import static ru.job4j.cinema.util.CheckHttpSession.checkUserAuthorization;

@ThreadSafe
@Controller
public class IndexControl {

    @GetMapping("/index")
    public String index(Model model, HttpSession httpsession) {
        User user = (User) httpsession.getAttribute("user");
        checkUserAuthorization(model, user);
        return "sessions";
    }
}