package com.hexad.librarymanagement.controller;

import com.hexad.librarymanagement.dto.UserDto;
import com.hexad.librarymanagement.entity.User;
import com.hexad.librarymanagement.exception.BookNotFoundException;
import com.hexad.librarymanagement.exception.UserNotFoundException;
import com.hexad.librarymanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody UserDto user) {

        userService.addUser(user);
        return ResponseEntity.status(OK).body("User added");
    }


    @PostMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserDto user) {
        try {
            userService.updateUser(userId, user);
            return ResponseEntity.status(OK).body("User updated");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {

        UserDto user = userService.getUser(userId);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUser() {

        List<UserDto> users = userService.getAllUser();
        return new ResponseEntity<>(users, OK);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(OK).body("User removed");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

}
