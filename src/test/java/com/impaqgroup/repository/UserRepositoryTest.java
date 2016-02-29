package com.impaqgroup.repository;

import com.impaqgroup.WorkshopApplication;
import com.impaqgroup.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WorkshopApplication.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnOneUserFromDataBase() {
        // when
        List<User> users = userRepository.findAll();

        // then
        assertEquals(1, users.size());
    }
}