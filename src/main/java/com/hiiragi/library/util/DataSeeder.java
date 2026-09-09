package com.hiiragi.library.util;

import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.User;
import com.hiiragi.library.repository.BookRepository;
import com.hiiragi.library.repository.UserRepository;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
public class DataSeeder implements CommandLineRunner{

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ObjectMapper mapper;

    @Override
    public void run(String... args){
        seed();
    }

    public DataSeeder(BookRepository bookRepository, UserRepository userRepository, ObjectMapper objectMapper) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.mapper = objectMapper;
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
        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }
}