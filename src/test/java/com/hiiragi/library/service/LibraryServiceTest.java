package com.hiiragi.library.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.enums.BookStatus;
import com.hiiragi.library.exceptions.BookNotFoundException;
import com.hiiragi.library.exceptions.LoanAlreadyReturnedException;
import com.hiiragi.library.exceptions.LoanNotFoundException;
import com.hiiragi.library.exceptions.NoAvailableCopiesException;
import com.hiiragi.library.exceptions.NotFoundException;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.User;
import com.hiiragi.library.repository.BookRepository;
import com.hiiragi.library.repository.LoanRepository;
import com.hiiragi.library.repository.UserRepository;
import static com.hiiragi.library.util.MockedObjects.DUE_DATE;
import static com.hiiragi.library.util.MockedObjects.createBook;
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
        private User loggedUser;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp(){
        userRepo = new UserRepository();
        bookRepo = new BookRepository();
        loanRepo = new LoanRepository();
        userService = new UserService(userRepo);
        bookService = new BookService(bookRepo);
        LoanService = new LoanService(loanRepo);
        loggedUser = createUser();
        userService.add(loggedUser);
        authorizationService = new AuthorizationService(loggedUser);
        libraryService = new LibraryService(bookService, userService, LoanService, authorizationService);
    }

    void seedLibraryService(){
        Book book = createBook();
        libraryService.getBookService().add(book);
    }

    @Test
    void shouldBorrowBook(){
        seedLibraryService();
        Book book = libraryService.getBookService().findAll().getFirst();
        User user = libraryService.getUserService().findAll().getFirst();
        libraryService.borrowBook(book.getTitle(), user, DUE_DATE);

        assertEquals(BookStatus.BORROWED, book.getCopies().getFirst().getStatus());
        assertFalse(libraryService.getLoanService().findAll().isEmpty());
    }

    @Test
    void shouldNotBorrowBook(){
        seedLibraryService();
        Book book = libraryService.getBookService().findAll().getFirst();
        User user = libraryService.getUserService().findAll().getFirst();
        libraryService.borrowBook(book.getTitle(), user, DUE_DATE);
        
        assertThrows(NoAvailableCopiesException.class, () -> libraryService.borrowBook(book.getTitle(), user, DUE_DATE));
    }

    @Test
    void shouldReturnBook(){
        seedLibraryService();
        Book book = libraryService.getBookService().findAll().getFirst();
        User user = libraryService.getUserService().findAll().getFirst();
        libraryService.borrowBook(book.getTitle(), user, DUE_DATE);
        Long loanId = libraryService.getLoanService().findAll().getFirst().getId();
        
        assertDoesNotThrow(() -> libraryService.returnBook(loanId));
    }
    
    @Test
    void shouldNotReturnBookWithInexistentLoan(){
        assertThrows(LoanNotFoundException.class, () -> libraryService.returnBook(1L));
    }

    @Test
    void shouldNotReturnBookTwice(){
        seedLibraryService();
        Book book = libraryService.getBookService().findAll().getFirst();
        User user = libraryService.getUserService().findAll().getFirst();
        libraryService.borrowBook(book.getTitle(), user, DUE_DATE);
        Long loanId = libraryService.getLoanService().findAll().getFirst().getId();
        try {
            libraryService.returnBook(loanId);
        } catch (NotFoundException | LoanAlreadyReturnedException e) {
            // no need to handle these exceptions inside this test
        }

        assertThrows(LoanAlreadyReturnedException.class, () -> libraryService.returnBook(loanId));
    }

    @Test
    void shouldNotReturnInexistentBook(){
        seedLibraryService();
        Book book = libraryService.getBookService().findAll().getFirst();
        User user = libraryService.getUserService().findAll().getFirst();
        libraryService.borrowBook(book.getTitle(), user, DUE_DATE);
        Long loanId = libraryService.getLoanService().findAll().getFirst().getId();
        
        libraryService.getBookService().removeById(book.getId());

        assertThrows(BookNotFoundException.class,  () -> libraryService.returnBook(loanId));
    }
}
