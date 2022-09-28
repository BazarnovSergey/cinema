package ru.job4j.cinema.util;

import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;

public final class CheckHttpSession {

    public static void checkUserAuthorization(Model model, User user) {
        if (user == null) {
            user = new User();
            user.setUsername("Гость");
        }
        model.addAttribute("user", user);
    }

}
