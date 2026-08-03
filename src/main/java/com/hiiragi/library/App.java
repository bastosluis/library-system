package com.hiiragi.library;

import com.hiiragi.library.repository.BookRepository;
import com.hiiragi.library.repository.LoanRepository;
import com.hiiragi.library.repository.UserRepository;
import com.hiiragi.library.service.BookService;
import com.hiiragi.library.service.LibraryService;
import com.hiiragi.library.service.LoanService;
import com.hiiragi.library.service.UserService;
import com.hiiragi.library.ui.cli.LoginPrompt;
import com.hiiragi.library.ui.cli.MainMenu;


/**
 * Library system project with maven
 */
public class App {

    public static void main(String[] args){

        BookRepository bookRepository = new BookRepository();
        UserRepository userRepository = new UserRepository();
        LoanRepository loanRepository = new LoanRepository();

        LibraryService libraryService = new LibraryService(new BookService(bookRepository), 
                                                            new UserService(userRepository),
                                                            new LoanService(loanRepository));

        MainMenu.INSTANCE.setLibraryService(libraryService);
        MainMenu.INSTANCE.setLoginPrompt(new LoginPrompt(libraryService.getUserService()));
        MainMenu.INSTANCE.start();
    }
}