package com.hiiragi.library.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.enums.BookStatus;
import com.hiiragi.library.enums.LoanStatus;
import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.exceptions.BookNotFoundException;
import com.hiiragi.library.exceptions.InactiveUserException;
import com.hiiragi.library.exceptions.InvalidBookRemovalException;
import com.hiiragi.library.exceptions.InvalidUserRemovalException;
import com.hiiragi.library.exceptions.LoanAlreadyReturnedException;
import com.hiiragi.library.exceptions.LoanLimitExceededExcetion;
import com.hiiragi.library.exceptions.LoanNotFoundException;
import com.hiiragi.library.exceptions.NoAvailableCopiesException;
import com.hiiragi.library.exceptions.NotFoundException;
import com.hiiragi.library.exceptions.UnauthorizedException;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.User;
import com.hiiragi.library.repository.BookRepository;
import com.hiiragi.library.repository.LoanRepository;
import com.hiiragi.library.repository.UserRepository;
import static com.hiiragi.library.util.MockedNames.BOOK_TITLE;
import static com.hiiragi.library.util.MockedNames.USER_NAME;
import static com.hiiragi.library.util.MockedObjects.DUE_DATE;
import static com.hiiragi.library.util.MockedObjects.createBook;
import static com.hiiragi.library.util.MockedObjects.createSession;
import static com.hiiragi.library.util.MockedObjects.createUser;

public class LibraryServiceTest {
        private UserRepository userRepo;
        private BookRepository bookRepo;
        private LoanRepository loanRepo;
        private UserService userService;
        private BookService bookService;
        private LoanService LoanService;
        private AuthorizationService authorizationService; 
        private LibraryService libraryService;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp(){
        userRepo = new UserRepository();
        bookRepo = new BookRepository();
        loanRepo = new LoanRepository();
        authorizationService = new AuthorizationService(createSession(UserRole.MEMBER));
        userRepo.save(authorizationService.getSession().getCurrentUser().get());
        userService = new UserService(userRepo);
        bookService = new BookService(bookRepo);
        LoanService = new LoanService(loanRepo);

        libraryService = new LibraryService(bookService, userService, LoanService, authorizationService);
    }

    void seedBookRepo(){
        Book book = createBook();
        bookRepo.save(book);
    }

    void seedUserRepo(){
        userRepo.save(createUser("admin", "admin@admin.com", UserRole.ADMIN));
        userRepo.save(createUser("librarian", "librarian@librarian.com",UserRole.LIBRARIAN));
        userRepo.save(createUser("member", "member@member.com",UserRole.MEMBER));
    }

    void changeToRole(UserRole role){
        User user = createUser(role+"_login", role+"_email@example.com",role);
        userRepo.save(user);
        authorizationService.getSession().login(user);
    }

    // ===============
    // Book
    // ===============

    @Test
    void shouldFindAllBooks(){
        assertDoesNotThrow(() -> libraryService.findAllBooks());
    }
    
    @Test
    void shouldBorrowBook(){
        seedBookRepo();
        Book book = libraryService.findAllBooks().getFirst();
        User user = authorizationService.getSession().getCurrentUser().orElseThrow();
        libraryService.borrowBook(book.getTitle(), user.getId(), DUE_DATE);
        
        assertEquals(BookStatus.BORROWED, book.getCopies().getFirst().getStatus());
        assertFalse(loanRepo.findAll().isEmpty());
    }
    
    @Test
    void shouldNotBorrowBorrowedBook(){
        seedBookRepo();
        Book book = libraryService.findAllBooks().getFirst();
        User user = authorizationService.getSession().getCurrentUser().orElseThrow();
        user.setMaxLoans(2);
        libraryService.borrowBook(book.getTitle(), user.getId(), DUE_DATE);
        
        assertThrows(NoAvailableCopiesException.class, () -> libraryService.borrowBook(book.getTitle(), user.getId(), DUE_DATE));
    }
    
    @Test
    void shouldNotBorrowBooksAsInactive(){
        seedBookRepo();
        Book book = libraryService.findAllBooks().getFirst();
        User user = authorizationService.getSession().getCurrentUser().orElseThrow();
        user.setActive(false);
        
        assertThrows(InactiveUserException.class, () -> libraryService.borrowBook(book.getTitle(), user.getId(), DUE_DATE));
    }

    @Test
    void shouldNotBorrowBooksWithLoansMaxed(){
        seedBookRepo();
        Book book = libraryService.findAllBooks().getFirst();
        User user = authorizationService.getSession().getCurrentUser().orElseThrow();
        user.setMaxLoans(0);
        
        assertThrows(LoanLimitExceededExcetion.class, () -> libraryService.borrowBook(book.getTitle(), user.getId(), DUE_DATE));
    }
    
    @Test
    void shouldReturnBook(){
        seedBookRepo();
        Book book = libraryService.findAllBooks().getFirst();
        User user = authorizationService.getSession().getCurrentUser().orElseThrow();
        libraryService.borrowBook(book.getTitle(), user.getId(), DUE_DATE);
        Long loanId = loanRepo.findAll().getFirst().getId();
        
        assertDoesNotThrow(() -> libraryService.returnBook(loanId));
    }
    
    @Test
    void shouldNotReturnBookWithInexistentLoan(){
        assertThrows(LoanNotFoundException.class, () -> libraryService.returnBook(1L));
    }

    @Test
    void shouldNotReturnBookTwice(){
        seedBookRepo();
        Book book = libraryService.findAllBooks().getFirst();
        User user = authorizationService.getSession().getCurrentUser().orElseThrow();
        libraryService.borrowBook(book.getTitle(), user.getId(), DUE_DATE);
        Long loanId = loanRepo.findAll().getFirst().getId();
        try {
            libraryService.returnBook(loanId);
        } catch (NotFoundException | LoanAlreadyReturnedException e) {
            // no need to handle these exceptions inside this test
        }

        assertThrows(LoanAlreadyReturnedException.class, () -> libraryService.returnBook(loanId));
    }

    @Test
    void shouldNotReturnInexistentBook(){
        seedBookRepo();
        Book book = libraryService.findAllBooks().getFirst();
        User user = authorizationService.getSession().getCurrentUser().orElseThrow();
        libraryService.borrowBook(book.getTitle(), user.getId(), DUE_DATE);
        Long loanId = loanRepo.findAll().getFirst().getId();
        
        bookRepo.delete(book);

        assertThrows(BookNotFoundException.class,  () -> libraryService.returnBook(loanId));
    }

    @Test 
    void shouldAddBook(){
        changeToRole(UserRole.LIBRARIAN);
        assertDoesNotThrow(() -> libraryService.addBook(createBook()));
    }

    @Test
    void shouldNotAddBook(){
        assertThrows(UnauthorizedException.class, () -> libraryService.addBook(createBook()));
    }

    @Test
    void shouldRemoveBook(){
        seedBookRepo();
        changeToRole(UserRole.LIBRARIAN);
        assertDoesNotThrow(() -> libraryService.removeBook(1L));
    }

    @Test
    void shouldNotRemoveBookIfThereAreLoans(){
        seedBookRepo();
        libraryService.borrowBook(BOOK_TITLE, authorizationService.getSession().getCurrentUser().orElseThrow().getId(), DUE_DATE);
        changeToRole(UserRole.LIBRARIAN);
        assertThrows(InvalidBookRemovalException.class, () -> libraryService.removeBook(1L));   
    }
    
    // ===============
    // User
    // ===============

    @Test
    void shouldAddUser(){
        changeToRole(UserRole.ADMIN);
        assertDoesNotThrow(() -> libraryService.addUser(createUser("new", "new@email.com")));    
    }

    @Test
    void shouldNotAddUser(){
        assertThrows(UnauthorizedException.class, () -> libraryService.addUser(createUser("new", "new@email.com")));
    }

    @Test
    void shouldActivateUser(){
        changeToRole(UserRole.ADMIN);
        User user = createUser("inactive", "inactive@example.com");
        user.setActive(false);
        userRepo.save(user);

        assertDoesNotThrow(() -> libraryService.activateUser(user.getId()));
    }
    
    @Test
    void shouldDeactivateUser(){
        changeToRole(UserRole.ADMIN);
        User user = createUser("active", "active@example.com");
        userRepo.save(user);

        assertDoesNotThrow(() -> libraryService.deactivateUser(user.getId()));
    }

    @Test
    void shouldRemoveUser(){
        userRepo.save(createUser("remove", "remove@email.com", UserRole.LIBRARIAN));
        changeToRole(UserRole.ADMIN);
        assertDoesNotThrow(() -> libraryService.removeUser(userRepo.findByLogin("remove").orElseThrow().getId()));
    }

    @Test
    void shouldNotRemoveUser(){
        userRepo.save(createUser("remove", "remove@email.com", UserRole.LIBRARIAN));
        assertThrows(UnauthorizedException.class, () -> libraryService.removeUser(userRepo.findByLogin("remove").orElseThrow().getId()));
    }

    @Test
    void shouldNotRemoveUserIfThereAreLoans(){
        seedBookRepo();
        libraryService.borrowBook(BOOK_TITLE, 1L, DUE_DATE);
        String login = authorizationService.getSession().getCurrentUser().orElseThrow().getLogin();
        changeToRole(UserRole.ADMIN);
        assertThrows(InvalidUserRemovalException.class, () -> libraryService.removeUser(userRepo.findByLogin(login).orElseThrow().getId()));
    }

    @Test
    void shouldFindAllUsers(){
        changeToRole(UserRole.ADMIN);
        assertDoesNotThrow(() -> libraryService.findAllUsers());
    }

    @Test
    void shouldNotFindAllUsers(){
        assertThrows(UnauthorizedException.class, () -> libraryService.findAllUsers());
    }

    @Test
    void shouldFindUserByLogin(){
        changeToRole(UserRole.ADMIN);
        assertDoesNotThrow(() -> libraryService.findUserByLogin(USER_NAME));
    }

    @Test
    void shouldNotFindUserByLogin(){
        assertThrows(UnauthorizedException.class, () -> libraryService.findUserByLogin(USER_NAME));
    }
    
    @Test
    void shouldFindUserById(){
        assertDoesNotThrow(() -> libraryService.findUserById(1L));
    }

    // ===============
    // Loan
    // ===============
    
    @Test
    void shouldFindAllLoans(){
        changeToRole(UserRole.LIBRARIAN);
        assertDoesNotThrow(() -> libraryService.findAllLoans());
    }

    @Test
    void shouldFindLoanById(){
        seedBookRepo();
        libraryService.borrowBook(BOOK_TITLE, authorizationService.getSession().getCurrentUser().orElseThrow().getId(), DUE_DATE);
        changeToRole(UserRole.ADMIN);
        assertDoesNotThrow(() -> libraryService.findLoanById(1L));
    }

    @Test
    void shouldFindLoansByUserId(){
        changeToRole(UserRole.ADMIN);
        assertDoesNotThrow(() -> libraryService.findLoansByUserId(1L));
    }

    @Test
    void shouldFindLoansByBookId(){
        seedBookRepo();
        changeToRole(UserRole.ADMIN);
        assertDoesNotThrow(() -> libraryService.findLoansByBookId(1L));
    }

    @Test
    void shouldNotFindLoanByBookId(){
        seedBookRepo();
        assertThrows(UnauthorizedException.class, () -> libraryService.findLoansByBookId(1L));
    }

    @Test
    void shouldFindLoansByStatus(){
        seedBookRepo();
        libraryService.borrowBook(BOOK_TITLE, authorizationService.getSession().getCurrentUser().orElseThrow().getId(), DUE_DATE);
        changeToRole(UserRole.LIBRARIAN);
        assertDoesNotThrow(() -> libraryService.findLoansByStatus(LoanStatus.ACTIVE));
    }

    @Test
    void shouldNotFindLoansByStatus(){
        seedBookRepo();
        libraryService.borrowBook(BOOK_TITLE, authorizationService.getSession().getCurrentUser().orElseThrow().getId(), DUE_DATE);
        assertThrows(UnauthorizedException.class ,() -> libraryService.findLoansByStatus(LoanStatus.ACTIVE));
    }

}
