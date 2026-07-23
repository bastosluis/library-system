package com.hiiragi.library.ui.cli;

import com.hiiragi.library.ui.cli.util.InputReader;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.User;

import java.util.List;

import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.service.BookService;
import com.hiiragi.library.service.UserService;

public class HomeMenu implements Menu{

    private User user;
    private UserService userService;
    private BookService bookService;
    
    public HomeMenu(User user, UserService userService, BookService bookService) {
        this.user = user;
        this.userService = userService;
        this.bookService = bookService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void start() {

        System.out.println("Welcome, " + user.getName() + "!\n");
        this.show();

        while (true){
            
            int option = InputReader.readInt("Option: ");

            switch (option) {
                case 1 -> handleBorrowBook();
                case 2 -> handleReturnBook();
                case 3 -> handleListBooks();
                case 4 -> handleSearchBook();
                case 5 -> {
                    System.out.println("Logging out...");
                    return; // logout
                }
                case 0 -> System.exit(0);
            }
        }
    }

    private void handleBorrowBook() {
        String title = InputReader.readString("Book Title: \n");
        try {
            bookService.borrowBook(title, this.user);
            System.out.println("Successfully borrowed "+title+"!");
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
    
    private void handleReturnBook() {
        // TODO: Loan service still not implemented
        throw new UnsupportedOperationException("Loan service still not implemented");
    }

    private void handleListBooks() {
        try {
            List<Book> books = bookService.findAll();
            System.out.println("There are currently these books in the library:\n");
            for (Book book : books) {
                System.out.println("- "+book.getTitle()+"\n");
            }

        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    private void handleSearchBook() {
        try {
            String title = InputReader.readString("Book Title: \n");
            Book foundBook = bookService.findByTitle(title);
            System.out.println("Found the book "+foundBook.getTitle()+" in the library.\n");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }    
    
    @Override
    public void show() {
        System.out.println("\n===============\n");
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
