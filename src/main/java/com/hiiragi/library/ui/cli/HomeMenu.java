package com.hiiragi.library.ui.cli;

import com.hiiragi.library.ui.cli.book.BookMenu;
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
            
            int option = InputReader.readInt("Option: \n");

            switch (option) {
                case 1 -> handleBorrowBook();
                case 2 -> handleReturnBook();
                case 3 -> handleListBooks();
                case 4 -> handleSearchBook();
                case 5 -> {
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
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Successfully borrowed "+title+"!");
    }
    
    private void handleReturnBook() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleReturnBook'");
    }

    private void handleListBooks() {
        try {
            List<Book> books = bookService.findAll();

            for (Book book : books) {
                System.out.println(book.getTitle());
            }

        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    private void handleSearchBook() {
        try {
            String title = InputReader.readString("Book Title: \n"); 
            System.out.println(bookService.findByTitle(title).getTitle());
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
