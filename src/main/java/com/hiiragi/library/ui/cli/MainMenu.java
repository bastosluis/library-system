package com.hiiragi.library.ui.cli;

import com.hiiragi.library.model.User;
import com.hiiragi.library.service.BookService;
import com.hiiragi.library.service.UserService;
import com.hiiragi.library.ui.cli.book.BookMenu;
import com.hiiragi.library.ui.cli.user.UserMenu;

// Singleton Pattern
public enum MainMenu implements Menu{
    INSTANCE;
    private BookMenu bookMenu;
    private UserMenu userMenu;
    private BookService bookService;
    private UserService userService;
    private LoginMenu loginMenu;
    private HomeMenu homeMenu;

    @Override
    public void start(){
        while (true) {
            this.show();
            loginMenu = new LoginMenu(userService);
            User loggedUser = loginMenu.login();
            if (loggedUser == null) 
                return; // Exit application

            homeMenu = new HomeMenu();
            homeMenu.setUser(loggedUser);
            homeMenu.setBookService(bookService);
            homeMenu.setUserService(userService);
            homeMenu.start();
        }
    }

    @Override
    public void show() {
        System.out.println("Starting Library System...\n");
        System.out.println("Welcome to Hiiragi Library System!\n");
    }

    public BookMenu getBookMenu() {
        return bookMenu;
    }

    public void setBookMenu(BookMenu bookMenu) {
        this.bookMenu = bookMenu;
    }

    public UserMenu getUserMenu() {
        return userMenu;
    }

    public void setUserMenu(UserMenu userMenu) {
        this.userMenu = userMenu;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
