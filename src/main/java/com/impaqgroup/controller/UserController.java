package com.impaqgroup.controller;

import com.impaqgroup.domain.User;
import com.impaqgroup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/all")
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

    @RequestMapping(value = "/update", method = PUT)
    public void updateUser(@RequestBody User user) {
        user.setDetails("Updated user");
        userRepository.save(user);
    }

    @RequestMapping(value = "/update", method = POST)
    public void addNewUser(@RequestBody User user) {
        user.setDetails("New user");
        userRepository.save(user);
    }
}