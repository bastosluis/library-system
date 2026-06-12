package com.hiiragi.library.ui.cli;

import com.hiiragi.library.ui.cli.book.BookMenu;
import com.hiiragi.library.ui.cli.user.UserMenu;

// Singleton Pattern
public enum MainMenu {
    INSTANCE;
    private BookMenu bookMenu;
    private UserMenu userMenu;

    public void start(){
        while (true) { 
            
        }
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
}
