package com.elvisespinoza.dinnerreview.controller;

import com.elvisespinoza.dinnerreview.model.User;
import com.elvisespinoza.dinnerreview.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequestMapping(value = "/users")
@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{displayName}")
    public User getUser(@PathVariable String displayName) {
        verifyDisplayName(displayName);
        Optional<User> optionalUser = userRepository.findUserByDisplayName(displayName);
        if(!optionalUser.isPresent()) { // throw error if not found
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User was not found.");
        }
        // A user will always be referenced by their unique display name, not by a database Id.
        User user = optionalUser.get();
        user.setId(null);
        return user;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody User user) {
        verifyUser(user);
        userRepository.save(user);
    }


    private void verifyUser(User user) {
        verifyDisplayName(user.getDisplayName());
        Optional<User> optionalUser = userRepository.findUserByDisplayName(user.getDisplayName());
        if(optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A user under this display name already exists.");
        }
    }

    private void verifyDisplayName(String displayName) {
        if(displayName == null || displayName.isEmpty()) { // making sure display name isn't left empty
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Display name cannot be empty.");
        }
    }
}
