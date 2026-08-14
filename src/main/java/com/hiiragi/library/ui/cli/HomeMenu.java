package com.hiiragi.library.ui.cli;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import com.hiiragi.library.application.Session;
import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.exceptions.BookNotFoundException;
import com.hiiragi.library.exceptions.DuplicateUserException;
import com.hiiragi.library.exceptions.EmptySessionException;
import com.hiiragi.library.exceptions.InvalidUserRoleException;
import com.hiiragi.library.exceptions.LoanAlreadyReturnedException;
import com.hiiragi.library.exceptions.NotFoundException;
import com.hiiragi.library.exceptions.UserNotFoundException;
import com.hiiragi.library.model.Author;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.Category;
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
    
    private void handleAddBook(){
        System.out.println("""
                We are adding a\n
                1. New entry
                2. New copy
                """);
        try {            
            int option = InputReader.readInt("Choose one option, or type anything else to cancel: ");
            if(option != 1 && option != 2){
                System.out.println("Cancelling operating...");  
                return;
            }
            switch(option){
                case 1 -> {
                    System.out.println("Adding a new book ENTRY to the database:");

                    String title = InputReader.readString("Book Title: ");
                    String isbn = InputReader.readString("ISBN: ");
                    String description = InputReader.readString("Description: ");
                    int year = InputReader.readInt("Publication Year: ");
                    String authorName = InputReader.readString("Author Name: ");
                    String nationality = InputReader.readString("Author Nationality: ");
                    String categoryName = InputReader.readString("Category Name: ");
                    String categoryDescription = InputReader.readString("Category Description: ");

                    Book book = new Book(
                        title,
                        isbn,
                        description,
                        Year.of(year),
                        new Author(authorName, nationality),
                        new Category(categoryName, categoryDescription)
                    );

                    libraryService.addBook(book);
                    System.out.println("Sucessfully added the new entry "+ title);
                }
                case 2 -> {
                    System.out.println("Adding a new book COPY to the database:");
                    String title = InputReader.readString("Book Title: ");
                    libraryService.addBook(libraryService.findBookByTitle(title)
                                    .orElseThrow(() -> new BookNotFoundException(title))
                                );
                    System.out.println("Sucessfully added a new copy of "+title);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Cancelling operation...");
        }
    }

    private void handleRemoveBook(){
        String title = InputReader.readString("Book title to be removed: ");
        libraryService.removeBook(libraryService.findBookByTitle(title)
                                .orElseThrow(() -> new BookNotFoundException(title))
                                .getId()
                            );
        System.out.println("Sucessfully removed book "+title);
    }

    private void handleAddUser() {
        String name = InputReader.readString("Name: ");
        String email = InputReader.readString("Email: ");
        String phone = InputReader.readString("Phone: ");
        int maxLoans = InputReader.readInt("Maximum number of loans: ");
        String login = InputReader.readString("Login: ");
        String password = InputReader.readString("Password: ");

        System.out.println("Select the user's role:");
        System.out.println("1. Member");
        System.out.println("2. Librarian");
        System.out.println("3. Admin");

        try {
            int roleOption = InputReader.readInt("Role: (type anything else to cancel)");

            while(roleOption != 1 && roleOption != 2 && roleOption != 3){
                    roleOption = InputReader.readInt("Please type a valid option.");
            }
            
            UserRole role = switch (roleOption) {
                case 1 -> UserRole.MEMBER;
                case 2 -> UserRole.LIBRARIAN;
                case 3 -> UserRole.ADMIN;
                default -> {
                    System.out.println("Invalid role, adding as member.");
                    yield UserRole.MEMBER;
                }
            };
            
            User user = new User(
                name,
                email,
                phone,
                true,
                maxLoans,
                role,
                login,
                password
            );
    
        libraryService.addUser(user).orElseThrow(() -> new DuplicateUserException(""));
        System.out.println("Sucessfully added user!");
        } catch (NumberFormatException e) {   
            System.out.println("Cancelling operation...");
        }
    }

    private void handleAddMember() {
        String name = InputReader.readString("Name: ");
        String email = InputReader.readString("Email: ");
        String phone = InputReader.readString("Phone: ");
        int maxLoans = InputReader.readInt("Maximum number of loans: ");
        String login = InputReader.readString("Login: ");
        String password = InputReader.readString("Password: ");

        User user = new User(
            name,
            email,
            phone,
            true,
            maxLoans,
            UserRole.MEMBER,
            login,
            password
        );

        libraryService.addUser(user).orElseThrow(() -> new DuplicateUserException(""));
        System.out.println("Sucessfully added this member!");
    }

    private void handleRemoveUser() {
        String login = InputReader.readString("Type the login of the user: ");

        User user = libraryService.findUserByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
        User currentUser = session.getCurrentUser().orElseThrow(() -> new EmptySessionException());
        if (user.equals(currentUser)){
            System.out.println("You can't remove yourself.");
            return;
        }

        if (user.getRole() == UserRole.ADMIN){
            System.out.println("You don't have permission to remove this administrator.");
            return;
        }
        libraryService.removeUser(user.getId());
        System.out.println("Sucessfully removed this user!");
    }

    private void handleRemoveMember() {
        String login = InputReader.readString("Type the login of the user: ");

        User user = libraryService.findUserByLogin(login).orElseThrow(() -> new UserNotFoundException(login));
        if (user.getRole() != UserRole.MEMBER){
            System.out.println("You don't have permission to remove this user.");
            return;
        }
        libraryService.removeUser(user.getId());
        System.out.println("Sucessfully removed this member!");
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