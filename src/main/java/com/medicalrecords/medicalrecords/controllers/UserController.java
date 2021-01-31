package com.medicalrecords.medicalrecords.controllers;

import com.medicalrecords.medicalrecords.entities.User;
import com.medicalrecords.medicalrecords.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController( UserService userService ) {
        this.userService = userService;
    }

    @PostMapping(path = "/addUser")
    public String addUser(@RequestBody User newUser ) {
        userService.addUser(newUser);
        return "User has been successfully added!";
    }
}
