package com.hiiragi.library;

import com.hiiragi.library.repository.BookRepository;
import com.hiiragi.library.repository.UserRepository;
import com.hiiragi.library.service.BookService;
import com.hiiragi.library.service.UserService;
import com.hiiragi.library.ui.cli.MainMenu;
import com.hiiragi.library.ui.cli.book.BookMenu;
import com.hiiragi.library.ui.cli.user.UserMenu;

/**
 * Library system project with maven
 */
public class App {

    public static void main(String[] args){

        BookRepository bookRepository = new BookRepository();
        UserRepository userRepository = new UserRepository();

        BookService bookService = new BookService(bookRepository);
        UserService userService = new UserService(userRepository);

        MainMenu.INSTANCE.setBookMenu(new BookMenu());
        MainMenu.INSTANCE.setUserMenu(new UserMenu());
        MainMenu.INSTANCE.start();
    }
}