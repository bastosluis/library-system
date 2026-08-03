package com.hiiragi.library.ui.cli;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.exceptions.LoanAlreadyReturnedException;
import com.hiiragi.library.exceptions.NotFoundException;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.Loan;
import com.hiiragi.library.model.User;
import com.hiiragi.library.service.LibraryService;
import com.hiiragi.library.ui.cli.util.InputReader;

public class HomeMenu implements Menu{

    private User user;

    private final LibraryService libraryService;
    
    public HomeMenu(User user, LibraryService libraryService) {
        this.user = user;
        this.libraryService = libraryService;
    }
    
    @Override
    public void start() {

        this.show();

        while (true){
            
            try{
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
                    default -> System.out.println("Please select a valid option.");
                }
            }
            catch (NumberFormatException e){
                System.out.println("Please select a valid option.");
            }
            catch (UnsupportedOperationException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void handleBorrowBook() {
        String title = InputReader.readString("Book Title: \n");
        LocalDate dueDate = InputReader.readDate("Due Date: \n"); 
        try {
            libraryService.borrowBook(title, this.user, dueDate);
            System.out.println("Successfully borrowed "+title+"!");
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
    
    private void handleReturnBook() {
        List<Loan> loans = libraryService.getLoanService().findByUserId(this.user.getId());
        System.out.println("These are the books currently loaned:\n");

        for (Loan loan : loans){
            String title = libraryService.getBookService().findById(loan.getBookId()).get().getTitle();
            System.out.println("- "+title+", Loan id: "+loan.getId()+
                                        "\n * Due Date: "+loan.getDueDate()+
                                        "\n * Loan Date: "+loan.getLoanDate()+
                                        "\n * Status: "+loan.getStatus());
        }
        
        Long id = Long.valueOf(InputReader.readInt("Type the id of the book you want to return: \n"));
        try{
            libraryService.returnBook(id);
        }
        catch (NotFoundException | LoanAlreadyReturnedException e){
           System.err.println(e.getMessage());
        }
    }

    private void handleListBooks() {
        List<Book> books = libraryService.getBookService().findAll();
        if (books.isEmpty()){
            System.out.println("No books available.\n");
        }
        else{
            System.out.println("There are currently these books in the library:\n");
            for (Book book : books) {
                System.out.println("- "+book.getTitle()+"\n");
            }
        }
    }

    private void handleSearchBook() {
        String title = InputReader.readString("Book Title: \n");
        Optional<Book> foundBook = libraryService.getBookService().findByTitle(title);

        if (foundBook.isPresent()) {
            System.out.println(
                "Found the book " +
                foundBook.get().getTitle()
            );
        } else {
            System.out.println("Book not found.");
        }
    }    
    
    @Override
    public void show() {
        System.out.println("\n===============\n");
        System.out.println("Welcome, " + user.getName() + "!\n");
        System.out.println("Please choose one of the following options (type the according number):\n");
        switch (user.getRole()) {
            case UserRole.MEMBER -> System.out.println("""
                    1. Borrow Book
                    2. Return Book
                    3. List Books
                    4. Search Book
                    5. Logout
                    0. Exit Application
                """);
                
                default -> System.out.println("Invalid user role.");
                }
        System.out.println("\n===============\n");
    }

    public void setUser(User user) {
        this.user = user;
    }

}