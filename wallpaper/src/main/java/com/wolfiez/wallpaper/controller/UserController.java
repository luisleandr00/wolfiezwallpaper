package com.wolfiez.wallpaper.controller;

import com.wolfiez.wallpaper.DTO.UserDto;
import com.wolfiez.wallpaper.entity.User;
import com.wolfiez.wallpaper.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDto updateDto) {
        User updatedUser = userService.updateUser(id, updateDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("keyword") String keyword) {
        List<User> searchResults = userService.searchUsers(keyword);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable String roleName) {
        List<User> usersByRole = userService.getUsersByRole(roleName);
        return new ResponseEntity<>(usersByRole, HttpStatus.OK);
    }
}