package com.impaqgroup.controller;

import com.impaqgroup.domain.User;
import com.impaqgroup.domain.UserDto;
import com.impaqgroup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = GET)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/{userId}", method = GET)
    public User getUser(@PathVariable Long userId) {
        return userRepository.findOne(userId);
    }

    @RequestMapping(value = "/{userId}", method = DELETE)
    public void deleteUser(@PathVariable Long userId) {
        userRepository.delete(userId);
    }

    @RequestMapping(value = "/{userId}", method = PUT)
    @ResponseStatus(NO_CONTENT)
    public void updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User user = userRepository.findOne(userId);
        user.setName(userDto.getName());
        user.setDetails("Updated user");
        userRepository.save(user);
    }

    @RequestMapping(method = POST)
    @ResponseStatus(CREATED)
    public void addNewUser(@RequestBody User user) {
        user.setDetails("New user");
        userRepository.save(user);
    }
}