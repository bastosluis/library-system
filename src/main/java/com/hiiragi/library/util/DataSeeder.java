package com.hiiragi.library.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.User;
import com.hiiragi.library.repository.BookRepository;
import com.hiiragi.library.repository.UserRepository;

public class DataSeeder {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ObjectMapper mapper;

    public DataSeeder(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    public void seed() {
        List<User> users = load("/seed/users.json", User.class);
        List<Book> books = load("/seed/books.json", Book.class);

        users.forEach(userRepository::save);
        books.forEach(bookRepository::save);
    }
    
    private <T> List<T> load(String resource, Class<T> type) {
        InputStream input = getClass().getResourceAsStream(resource);
        
        if (input == null) {
            throw new IllegalArgumentException("Resource not found: "+ resource);
        }

        try {
            return mapper.readValue(
                input,
                mapper.getTypeFactory()
                    .constructCollectionType(List.class, type)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}