package io.spring.bookstore.rest;

import io.spring.bookstore.validation.ConfirmOrder;
import io.spring.bookstore.validation.Register;
import io.spring.bookstore.model.User;
import io.spring.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User registerUser(@Validated(Register.class) @RequestBody User user){
        return userService.registerUser(user);
    }
    @RequestMapping
    public User loggedUser() {
        return userService.getLoggedUser();
    }

    @PutMapping
    public User updateAddress(@Validated(ConfirmOrder.class)@RequestBody User user){
        return userService.updateUser(user);
    }
}
