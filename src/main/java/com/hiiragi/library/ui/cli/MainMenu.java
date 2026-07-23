package com.hiiragi.library.ui.cli;

import com.hiiragi.library.model.User;
import com.hiiragi.library.service.BookService;
import com.hiiragi.library.service.UserService;


// Singleton Pattern
public enum MainMenu implements Menu{
    INSTANCE;

    private BookService bookService;
    private UserService userService;
    private LoginPrompt loginPrompt;
    
    private HomeMenu homeMenu;
    
    @Override
    public void start(){
        System.out.println("Starting Library System...\n");
        while (true) {
            this.show();
            User loggedUser = loginPrompt.login();
            if (loggedUser == null) {
                return; // Exit application
            }
            
            homeMenu = new HomeMenu(loggedUser, userService, bookService);
            homeMenu.start();
        }
    }
    
    @Override
    public void show() {
        System.out.println("Welcome to Hiiragi Library System!\n");
    }
    
    public void setLoginPrompt(LoginPrompt loginPrompt) {
        this.loginPrompt = loginPrompt;
    }
    
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
