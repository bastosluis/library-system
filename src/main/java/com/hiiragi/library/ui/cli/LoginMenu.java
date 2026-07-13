package com.hiiragi.library.ui.cli;

import com.hiiragi.library.model.User;
import com.hiiragi.library.service.UserService;
import com.hiiragi.library.ui.cli.util.InputReader;

public class LoginMenu implements Menu{

    private UserService userService;

    LoginMenu(UserService userService){
        this.userService = userService;
    }
    
    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show'");
    }

    public User login(){
        while (true) {   
            this.show();
            String login = InputReader.readString("Username: ");
            String password = InputReader.readString("Password: ");

            User loggedUser = userService.login(login, password);

            if (loggedUser != null)
                return loggedUser;

            InputReader.readString("Invalid credentials. Press any key to try again.");
        }
    }
}
