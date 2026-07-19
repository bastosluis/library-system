package com.hiiragi.library.ui.cli;

import com.hiiragi.library.ui.cli.book.BookMenu;
import com.hiiragi.library.ui.cli.util.InputReader;
import com.hiiragi.library.model.User;

import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.service.BookService;
import com.hiiragi.library.service.UserService;

public class HomeMenu implements Menu{

    private User user;
    private UserService userService;
    private BookService bookService;
    
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void start() {
        
        this.show();

        while (true){
            
            int option = InputReader.readInt("Option: \n");

            switch (option) {
                case 1 -> borrowBook();
                case 2 -> returnBook();
                case 3 -> listBooks();
                case 4 -> searchBook();
                case 5 -> {
                    return; // logout
                }
                case 0 -> System.exit(0);
            }
        }
    }

    @Override
    public void show() {
        System.out.println("\n===============\n");
        System.out.println("Welcome, " + user.getName() + "!\n");
        System.out.println("Please choose one of the following options (type the according number):\n");
        switch (user.getRole()) {
            case UserRole.MEMBER:
                System.out.println("""
                    1. Borrow Book
                    2. Return Book
                    3. List Books
                    4. Search Book
                    5. Logout
                    0. Exit Application
                """);
                break;
                
                default:
                    System.out.println("Invalid user role.");
                    break;
                }
        System.out.println("\n===============\n");
    }

    public void setUser(User user) {
        this.user = user;
    }

}
