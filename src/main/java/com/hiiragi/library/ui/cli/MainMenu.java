package com.hiiragi.library.ui.cli;

import java.util.Optional;

import com.hiiragi.library.application.Session;
import com.hiiragi.library.model.User;
import com.hiiragi.library.repository.BookRepository;
import com.hiiragi.library.repository.LoanRepository;
import com.hiiragi.library.repository.UserRepository;
import com.hiiragi.library.service.AuthorizationService;
import com.hiiragi.library.service.BookService;
import com.hiiragi.library.service.LibraryService;
import com.hiiragi.library.service.LoanService;
import com.hiiragi.library.service.UserService;


// Singleton Pattern
public enum MainMenu implements Menu{
    INSTANCE;

    private LibraryService libraryService;
    private LoginPrompt loginPrompt;
    
    private HomeMenu homeMenu;
    
    @Override
    public void start(){
        System.out.println("Starting Library System...\n");
        Session session = new Session();
        BookRepository bookRepository = new BookRepository();
        UserRepository userRepository = new UserRepository();
        LoanRepository loanRepository = new LoanRepository();  

        AuthorizationService authorizationService = new AuthorizationService(session);

        UserService userService = new UserService(userRepository, authorizationService);

        loginPrompt = new LoginPrompt(userService);

        while (true) {
            this.show();
            Optional<User> loggedUser = loginPrompt.login();
            if (loggedUser.isEmpty()) {
                return; // Exit application
            }

            session.login(loggedUser.get());

            libraryService = new LibraryService(
                                                    new BookService(bookRepository, authorizationService), 
                                                    userService,
                                                    new LoanService(loanRepository, authorizationService),
                                                    authorizationService
                                                );
                                                
            homeMenu = new HomeMenu(session, libraryService);
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

    public LibraryService getLibraryService() {
        return libraryService;
    }

    public void setLibraryService(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

}
