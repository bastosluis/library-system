package com.hiiragi.library.repository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.model.User;
import static com.hiiragi.library.util.MockedObjects.createUser;
/*
Very similar to BookRepositoryTest.
Both test classes are good candidates to refactoring 
*/
public class UserRepositoryTest {
    
    private UserRepository userRepo;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp(){
        userRepo = new UserRepository();
    }


    @Test
    void shouldSaveUser(){
        User user = createUser();
        userRepo.save(user);
        assertTrue(userRepo.findAll().contains(user));
    }

    @Test
    void shouldSaveMultipleUsers(){
        User user1 = createUser();
        User user2 = createUser();
        userRepo.save(user2);
        userRepo.save(user1);
        assertEquals(2, userRepo.findAll().size());    
    }

    @Test
    void shouldReturnUserWhenIdExists(){
        User user = createUser();
        userRepo.save(user);
        assertEquals(user.getId(), userRepo.findById(user.getId()).get().getId());
    }

    @Test
    void shouldReturnAllUsers(){
        User user = createUser();
        userRepo.save(user);
        assertEquals(user, userRepo.findAll().getFirst());
    }

    @Test
    void shouldDeleteUser(){
        User user = createUser();
        userRepo.save(user);
        userRepo.delete(user);
        assertTrue(userRepo.isEmpty());
    }

    @Test
    void shouldDeleteUserById(){
        User user = createUser();
        userRepo.save(user);
        userRepo.deleteById(user.getId());
        assertTrue(userRepo.isEmpty());
    }

    @Test
    void shouldNotDeleteInexistentUserById() {
        boolean deleted = userRepo.deleteById(999L);
        assertFalse(deleted);
    }

    @Test
    void shouldDeleteOnlySpecifiedUser(){
        for (int i = 0; i < 10; i++){
            userRepo.save(createUser());
        }
        User user = userRepo.findById(5L).get();
        userRepo.delete(user);
        assertEquals(Optional.empty(), userRepo.findById(5L));
    }
    
    @Test
    void shouldIncreaseSizeAfterSave(){
        User user = createUser();
        userRepo.save(user);
        assertEquals(1, userRepo.findAll().size());
    }

    @Test
    void shouldDecreaseSizeAfterDelete(){
        User user = createUser();
        userRepo.save(user);
        userRepo.deleteById(user.getId());
        assertEquals(0, userRepo.findAll().size());
    }

    @Test
    void shouldExistByEmail(){
        User user = createUser();
        userRepo.save(user);
        String email = user.getEmail();
        assertTrue(userRepo.existsByEmail(email));
    }

    @Test
    void shouldNotExistByEmailInEmptyRepo(){
        assertFalse(userRepo.existsByEmail("should_not_exist"));
    }

    @Test
    void shouldFindByName(){
        User user = createUser();
        userRepo.save(user);
        String name = user.getName();
        assertEquals(user, userRepo.findByName(name).get());
    }

    @Test
    void shouldNotFindByName(){
        User user = createUser();
        userRepo.save(user);
        assertEquals(Optional.empty(), userRepo.findByName("should_not_exist"));
    }
}
