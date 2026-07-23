package com.hiiragi.library.ui.cli;

import com.hiiragi.library.model.User;
import com.hiiragi.library.service.UserService;
import com.hiiragi.library.ui.cli.util.InputReader;

public class LoginPrompt{

    private UserService userService;

    public LoginPrompt(UserService userService){
        this.userService = userService;
    }

    public User login() {
        while (true) {
            String login = InputReader.readString("Username: ");
            String password = InputReader.readString("Password: ");

            User loggedUser = userService.login(login, password);

            if (loggedUser != null) {
                return loggedUser;
            }

            String option = InputReader.readString(
                    "Login failed. Press 1 to try again, or any other key to exit: ");

            if (!option.equals("1")) {
                return null;
            }
        }
    }
}
