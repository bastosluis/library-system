package com.hiiragi.library.ui.cli;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.hiiragi.library.application.Session;
import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.exceptions.EmptySessionException;
import com.hiiragi.library.exceptions.InvalidUserRoleException;
import com.hiiragi.library.exceptions.LoanAlreadyReturnedException;
import com.hiiragi.library.exceptions.NotFoundException;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.Loan;
import com.hiiragi.library.model.User;
import com.hiiragi.library.service.LibraryService;
import com.hiiragi.library.ui.cli.util.InputReader;
public class HomeMenu implements Menu{

    private final Session session;
    private final LibraryService libraryService;
    
    public HomeMenu(Session session, LibraryService libraryService) {
        this.session = session;
        this.libraryService = libraryService;
    }
    
    @Override
    public void start() {

        this.show();
        boolean logout = false;
        while (!logout){
            try{
                UserRole role = session.getCurrentUser()
                .orElseThrow(() -> new EmptySessionException())
                .getRole();
                
                logout = switch(role){
                    case UserRole.ADMIN -> handleAdminInput();
                    case UserRole.LIBRARIAN -> handleLibrarianInput();
                    case UserRole.MEMBER -> handleMemberInput();
                    };
            }
            catch (NumberFormatException e){
                System.out.println("Please select a valid option.");
            }
            catch (UnsupportedOperationException e){
                System.out.println(e.getMessage());
            }
        }  
    }

    private boolean handleMemberInput(){
        int option = InputReader.readInt("Option: ");
            switch (option) {
                case 1 -> handleBorrowBook();
                case 2 -> handleReturnBook();
                case 3 -> handleListBooks();
                case 4 -> handleSearchBook();
                case 9 -> {
                    System.out.println("Logging out...");
                    session.logout();
                    return true; // logout
                }
                case 0 -> System.exit(0);
                default -> System.out.println("Please select a valid option.");
            }
            return false;
    }

    private boolean handleLibrarianInput() {
        int option = InputReader.readInt("Option: ");

        switch (option) {
            case 1 -> handleBorrowBook();
            case 2 -> handleReturnBook();
            case 3 -> handleListBooks();
            case 4 -> handleSearchBook();
            case 5 -> handleAddBook();
            case 6 -> handleRemoveBook();
            case 7 -> handleAddMember();
            case 8 -> handleRemoveMember();
            case 9 -> {
                System.out.println("Logging out...");
                session.logout();
                return true;
            }
            case 0 -> System.exit(0);
            default -> System.out.println("Please select a valid option.");
        }

        return false;
    }

    private boolean handleAdminInput() {
        int option = InputReader.readInt("Option: ");

        switch (option) {
            case 1 -> handleBorrowBook();
            case 2 -> handleReturnBook();
            case 3 -> handleListBooks();
            case 4 -> handleSearchBook();
            case 5 -> handleAddBook();
            case 6 -> handleRemoveBook();
            case 7 -> handleAddUser();
            case 8 -> handleRemoveUser();
            case 9 -> {
                System.out.println("Logging out...");
                session.logout();
                return true;
            }
            case 0 -> System.exit(0);
            default -> System.out.println("Please select a valid option.");
        }

        return false;
    }

    private void handleBorrowBook() {
        String title = InputReader.readString("Book Title: \n");
        LocalDate dueDate = InputReader.readDate("Due Date: \n"); 
        try {
            libraryService.borrowBook(title, this.session.getCurrentUser().orElseThrow(() -> new EmptySessionException()).getId(), dueDate);
            System.out.println("Successfully borrowed "+title+"!");
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
    
    private void handleReturnBook() {
        Long userId = this.session.getCurrentUser()
                        .orElseThrow(() -> new EmptySessionException())
                        .getId();
        
        libraryService.printAllActiveLoansFromUser(userId);
        // List<Loan> loans = libraryService.getLoanService().findByUserId(this.session.getCurrentUser().get().getId());
        // if (loans.isEmpty()) {
        //     System.out.println("There are currently no loans yet.");
        //     return;
        // }
        // System.out.println("These are the books currently loaned:\n");
        
        // for (Loan loan : loans){
        //     String title = libraryService.getBookService().findById(loan.getBookId()).get().getTitle();
        //     System.out.println("- "+title+", Loan id: "+loan.getId()+
        //                                 "\n * Due Date: "+loan.getDueDate()+
        //                                 "\n * Loan Date: "+loan.getLoanDate()+
        //                                 "\n * Status: "+loan.getStatus());
        // }
        
        Long id = Long.valueOf(InputReader.readInt("Type the id of the book you want to return: "));
        try{
            libraryService.returnBook(id);
            System.out.println("Book sucessfully returned!");
        }
        catch (NotFoundException | LoanAlreadyReturnedException e){
           System.err.println(e.getMessage());
        }
    }

    private void handleListBooks() {
        List<Book> books = libraryService.findAllBooks();
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
        Optional<Book> foundBook = libraryService.findBookByTitle(title);

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
        User user = this.session.getCurrentUser().orElseThrow(() -> new EmptySessionException());
        System.out.println("\n===============\n");
        System.out.println("Welcome, " + user.getName() + "!\n");
        System.out.println("Please choose one of the following options (type the according number):\n");
        showUserMenu(user.getRole());
        System.out.println("\n===============\n");
    }

    private void showUserMenu(UserRole role){
        switch (role) {
            case UserRole.MEMBER -> System.out.println("""
                    1. Borrow Book
                    2. Return Book
                    3. List Books
                    4. Search Book
                    9. Logout
                    0. Exit Application
                """);
            case UserRole.LIBRARIAN -> System.out.println("""
                    1. Borrow Book
                    2. Return Book
                    3. List Books
                    4. Search Book
                    5. Add Book
                    6. Remove Book
                    7. Add Member
                    8. Remove Member
                    9. Logout
                    0. Exit Application
                    """);
            case UserRole.ADMIN -> System.out.println("""
                    1. Borrow Book
                    2. Return Book
                    3. List Books
                    4. Search Book
                    5. Add Book
                    6. Remove Book
                    7. Add User
                    8. Remove User
                    9. Logout
                    0. Exit Application
                    """);
            default -> throw new InvalidUserRoleException("Invalid user role: "+ role);
        }
    }
}