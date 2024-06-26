package com.example.spring_into.repository;

import com.example.spring_into.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OwnerRepositoryTest {
    private final String OWNER_EMAIL = "test@gmail.com";

    @Autowired
    private OwnerRepository ownerRepository;

    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setEmail(OWNER_EMAIL);
        owner.setFirstName("Ivan");
        owner.setLastName("Ivanov");
    }

    @Test
    void findByEmail_retunsOwner() {
        ownerRepository.save(owner);

        Owner testOwner = ownerRepository.findByEmail(OWNER_EMAIL).get();

        assertEquals("Ivan",testOwner.getFirstName());
        assertEquals("Ivanov",testOwner.getLastName());
    }

    @Test
    void findByEmail_userNotFound() {

        NoSuchElementException exception = assertThrows(NoSuchElementException.class,
                () -> ownerRepository.findByEmail(OWNER_EMAIL).get());
        assertEquals(exception.getMessage(),"No value present");
    }


}